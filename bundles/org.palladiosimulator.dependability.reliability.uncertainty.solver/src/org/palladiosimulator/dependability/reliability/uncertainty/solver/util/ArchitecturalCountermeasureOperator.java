package org.palladiosimulator.dependability.reliability.uncertainty.solver.util;

import static java.util.stream.Collectors.toList;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.util.ArchitecturalPreconditionUtil.allPreconditionsFulfilled;

import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.DeterministicImprovement;
import org.palladiosimulator.dependability.reliability.uncertainty.GlobalUncertaintyCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.ProbabilisticImprovement;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyImprovement;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintySpecificCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.improvement.UncertaintyImprovementCalculator;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.UncertaintyModelManager;
import org.palladiosimulator.dependability.reliability.uncertainty.util.UncertaintySwitch;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticModel;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;
import org.palladiosimulator.envdyn.environment.staticmodel.LocalProbabilisticNetwork;
import org.palladiosimulator.solver.models.PCMInstance;

import com.google.common.collect.Lists;

import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.entity.Conditionable;
import tools.mdsd.probdist.api.entity.ConditionalProbabilityDistribution;
import tools.mdsd.probdist.api.entity.UnivariateProbabilitiyMassFunction;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.api.factory.ProbabilityDistributionFactory;
import tools.mdsd.probdist.api.parser.ParameterParser.Sample;
import tools.mdsd.probdist.distributionfunction.Domain;
import tools.mdsd.probdist.distributionfunction.SimpleParameter;

public class ArchitecturalCountermeasureOperator {
	
	private final PCMInstance pcmModel;
	private final UncertaintyRepository uncertaintyRepo;
	private final IProbabilityDistributionFactory probabilityDistributionFactory;

	private ArchitecturalCountermeasureOperator(PCMInstance pcmModel, UncertaintyRepository uncertaintyRepo, IProbabilityDistributionFactory probabilityDistributionFactory) {
		this.pcmModel = pcmModel;
		this.uncertaintyRepo = uncertaintyRepo;
		this.probabilityDistributionFactory = probabilityDistributionFactory;
	}

	public static ArchitecturalCountermeasureOperator createOperatorFor(PCMInstance pcmModel,
			UncertaintyRepository uncertaintyRepo, IProbabilityDistributionFactory probabilityDistributionFactory) {
		return new ArchitecturalCountermeasureOperator(pcmModel, uncertaintyRepo, probabilityDistributionFactory);
	}

	public List<UncertaintyState> applyToUncertainties(List<UncertaintyState> stateTuple) {
		if (uncertaintyRepo.getArchitecturalCountermeasures().size() == 0) {
			return stateTuple;
		}
		
		filterApplicableCountermeasures().forEach(c -> applyUncertaintySpecific(c, stateTuple));
		
		return stateTuple;
	}

	public void applyToUncertaintyModels() {
		if (uncertaintyRepo.getArchitecturalCountermeasures().size() == 0) {
			return;
		}
		
		filterApplicableCountermeasures().forEach(this::apply);
	}
	
	private List<ArchitecturalCountermeasure> filterApplicableCountermeasures() {
		return uncertaintyRepo.getArchitecturalCountermeasures().stream()
				.filter(c -> allPreconditionsFulfilled(c, pcmModel))
				.filter(c -> allPreconditionsFulfilled(c.getAppliedFailureType(), pcmModel))
				.collect(toList());
	}

	private void applyUncertaintySpecific(ArchitecturalCountermeasure countermeasure, List<UncertaintyState> stateTuple) {
		new UncertaintySwitch<Void>() {

			@Override
			public Void caseUncertaintySpecificCountermeasure(UncertaintySpecificCountermeasure countermeasure) {			
				var improvement = createCPDFrom(countermeasure.getUncertaintyImprovement());
				
				var targetUncertainty = stateTuple.stream()
						.filter(each -> each.instantiates(countermeasure.getTargetUncertainty()))
						.findAny()
						.orElseThrow(() -> new RuntimeException("There is no uncertainty with target for " + countermeasure.getEntityName()));
				var valueToImprove = asConditional(targetUncertainty.getValue());
				var improvedValue = improvement.given(valueToImprove).sample();
				
				stateTuple.remove(targetUncertainty);
				
				var improvedUncertainty = targetUncertainty.newValuedStateWith(improvedValue);
				stateTuple.add(improvedUncertainty);		

				return null;
			}

			@Override
			public Void caseGlobalUncertaintyCountermeasure(GlobalUncertaintyCountermeasure countermeasure) {
				apply(countermeasure);
				return null;
			}

		}.doSwitch(countermeasure);
	}
	
	private void apply(ArchitecturalCountermeasure countermeasure) {
		new UncertaintySwitch<Void>() {

			@Override
			public Void caseUncertaintySpecificCountermeasure(UncertaintySpecificCountermeasure countermeasure) {
				var surrogate = makeSurrogate(countermeasure.getAppliedFailureType());
				var uncertaintyModel = surrogate.getUncertaintyModel();
				var affectedVariable = uncertaintyModel.getLocalProbabilisticModels().get(0).getGroundRandomVariables().stream()
						.filter(variable -> variable.getInstantiatedTemplate().getId().equals(countermeasure.getTargetUncertainty().getId()))
						.findFirst()
						.get();

				switchDistributions(affectedVariable, countermeasure.getUncertaintyImprovement());	
				
				UncertaintyModelManager.get().updateModel(surrogate, probabilityDistributionFactory);	

				return null;
			}

			@Override
			public Void caseGlobalUncertaintyCountermeasure(GlobalUncertaintyCountermeasure countermeasure) {
				for (UncertaintyInducedFailureType each : uncertaintyRepo.getUncertaintyInducedFailureTypes()) {
					if (each.getId().equals(countermeasure.getAppliedFailureType().getId()) == false) {
						continue;
					}

					var surrogate = makeSurrogate(countermeasure.getAppliedFailureType());
					var original = retrieveFailureVariableFrom(surrogate);
					var improved = retrieveFailureVariableFrom(countermeasure.getImprovedUncertaintyModel());
					original.getDescriptiveModel().setDistribution(improved.getDescriptiveModel().getDistribution());					
					
					UncertaintyModelManager.get().updateModel(surrogate, probabilityDistributionFactory);
				}
				return null;
			}

		}.doSwitch(countermeasure);
	}
	
	private void switchDistributions(GroundRandomVariable affectedVariable, UncertaintyImprovement improvement) {
		var generator = createGeneratorDistribution(affectedVariable, improvement);
		var oldDistribution = affectedVariable.getDescriptiveModel().getDistribution();

		var params = oldDistribution.getParams();
		if (params.size() != 1 || SimpleParameter.class.isInstance(params.get(0).getRepresentation()) == false) {
			throw new IllegalArgumentException("The distribution structure is not supported.");
		}
		
		var adjustedParam = generateDistributionParams((SimpleParameter) params.get(0).getRepresentation(), generator);
		params.get(0).setRepresentation(adjustedParam);
	}

	// Currently only SimpleParameter are supported
	private SimpleParameter generateDistributionParams(SimpleParameter param, UnivariateProbabilitiyMassFunction generator) {
		var samples = ProbabilityDistributionFactory.getParameterParser().parseSampleSpace(param);
		for (Sample each : samples) {
			var newProbability = generator.probability(each.value);
			each.probability = newProbability;
		}
			
		var builder = new StringBuilder();
		for (Sample each : samples) {
			builder.append(String.format("{%1s,%2s};", each.value, each.probability));
		}
		 
		var strParam = builder.deleteCharAt(builder.length() - 1).toString();
		param.setValue(strParam);
		
		return param;
	}

	private UnivariateProbabilitiyMassFunction createGeneratorDistribution(GroundRandomVariable affectedVariable,
			UncertaintyImprovement uncertaintyImprovement) {
		var oldDistribution = affectedVariable.getDescriptiveModel().getDistribution();
		return new UnivariateProbabilitiyMassFunction(oldDistribution.getInstantiated()) {

			private final UnivariateProbabilitiyMassFunction oldDistFunction = (UnivariateProbabilitiyMassFunction) ProbabilityDistributionFactory.get()
					.getInstanceOf(oldDistribution)
					.orElseThrow();
			private final ConditionalProbabilityDistribution improvement = createCPDFrom(uncertaintyImprovement);

			@Override
			public CategoricalValue sample() {
				var conditional = asConditional(oldDistFunction.sample());
				return improvement.given(conditional).sample();
			}

			@Override
			public Double probability(CategoricalValue value) {
				var probability = 0.0;
				for (CategoricalValue each : DiscreteUncertaintyStateSpace.toUncertaintyState(affectedVariable).getValueSpace()) {
					var probOfUncertainty = oldDistFunction.probability(each);
					var condProb = improvement.given(asConditional(each)).probability(value);
					probability += probOfUncertainty * condProb;
				}
				return probability;
			}

		};
	}
	
	private ConditionalProbabilityDistribution createCPDFrom(UncertaintyImprovement improvement) {
		return new UncertaintySwitch<ConditionalProbabilityDistribution>() {

			@Override
			public ConditionalProbabilityDistribution caseProbabilisticImprovement(ProbabilisticImprovement probImprovement) {
				return UncertaintyImprovementCalculator.get().createCPD(probImprovement.getProbabilityDistribution());
			}

			@Override
			public ConditionalProbabilityDistribution caseDeterministicImprovement(DeterministicImprovement detImprovement) {
				return UncertaintyImprovementCalculator.get().createIndicatorCPD(detImprovement);
			}

		}.doSwitch(improvement); 
	}
	
	private List<Conditionable.Conditional> asConditional(CategoricalValue value) {
		return Lists.newArrayList(new Conditionable.Conditional(Domain.CATEGORY, value));
	}
	
	private GroundRandomVariable retrieveFailureVariableFrom(UncertaintyInducedFailureType type) {
		if (type.getFailureVariable() != null) {
			return type.getFailureVariable();
		}
		return retrieveFailureVariableFrom(type.getUncertaintyModel());
	};
	
	private GroundRandomVariable retrieveFailureVariableFrom(GroundProbabilisticNetwork model) {
		var results = UncertaintyModelUtil.filterRandomVariablesOnlyWithParent(model);
		if (results.size() != 1) {
			throw new RuntimeException("There are several variables with parents.");
		}
		return results.iterator().next();
	}
	
	// The method makes an surrogate for the given failure type. The reason is that the uncertainty model
	// will be possibly changed during analysis which might produce side effects if no surrogate is made.
	private UncertaintyInducedFailureType makeSurrogate(UncertaintyInducedFailureType failureType) {
		var surrogate = EcoreUtil.copy(failureType);
		surrogate.setFailureVariable(failureType.getFailureVariable());
		surrogate.setRefines(failureType.getRefines());
		surrogate.getArchitecturalPreconditions().addAll(failureType.getArchitecturalPreconditions());
		
		var originalModel = failureType.getUncertaintyModel();
		var surrogatedModel = EcoreUtil.copy(originalModel);
		for (GroundProbabilisticModel each : originalModel.getLocalModels()) {
			var surrogatedGroundModel = EcoreUtil.copy(each);
			surrogatedGroundModel.setDistribution(each.getDistribution());
			surrogatedGroundModel.setInstantiatedFactor(each.getInstantiatedFactor());
			
			surrogatedModel.getLocalModels().add(surrogatedGroundModel);
		}
		
		for (LocalProbabilisticNetwork eachNet : originalModel.getLocalProbabilisticModels()) {
			var surrogatedLocalNetwork = EcoreUtil.copy(eachNet);
			for (GroundRandomVariable eachVar : eachNet.getGroundRandomVariables()) {
				var surrogatedVariable = surrogatedLocalNetwork.getGroundRandomVariables().stream()
						.filter(v -> v.getId().equals(eachVar.getId()))
						.findFirst()
						.orElseThrow(() -> new RuntimeException(
								"Ground random variables have not been properly copied."));
				surrogatedVariable.setInstantiatedTemplate(eachVar.getInstantiatedTemplate());
				
				if (eachVar.getAppliedObjects().isEmpty() == false) {
					surrogatedVariable.getAppliedObjects().addAll(eachVar.getAppliedObjects());
				}
				
				if (eachVar.getDependenceStructure().isEmpty() == false) {
					surrogatedVariable.getDependenceStructure().addAll(eachVar.getDependenceStructure());
				}
				
				var descModel = surrogatedModel.getLocalModels().stream()
						.filter(m -> m.getId().equals(eachVar.getDescriptiveModel().getId()))
						.findFirst()
						.orElseThrow(() -> new RuntimeException(
								"Could not found local model: " + eachVar.getDescriptiveModel().getEntityName()));
				surrogatedVariable.setDescriptiveModel(descModel);
				
				surrogatedLocalNetwork.getGroundRandomVariables().add(surrogatedVariable);
			}
			surrogatedModel.getLocalProbabilisticModels().add(surrogatedLocalNetwork);
		}
		
		surrogate.setUncertaintyModel(surrogatedModel);
		
		return surrogate;
	}

}
