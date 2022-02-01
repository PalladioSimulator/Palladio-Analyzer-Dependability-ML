package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.util.ArchitecturalPreconditionUtil.allPreconditionsFulfilled;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository;
import org.palladiosimulator.dependability.reliability.uncertainty.improvement.UncertaintyImprovementCalculator;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.UncertaintyModelManager;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;
import org.palladiosimulator.reliability.solver.pcm2markov.Pcm2MarkovStrategy;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import com.google.common.collect.Lists;

import tools.mdsd.probdist.api.apache.supplier.MultinomialDistributionSupplier;
import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.factory.ProbabilityDistributionFactory;

public class UncertaintyBasedReliabilityPredictor {

	public static class UncertaintyBasedReliabilityPredictionBuilder {

		private PCMSolverWorkflowRunConfiguration config = null;
		private StateSpaceExplorationStrategy exploreStrategy = null;
		private UncertaintyRepository uncertaintyRepo = null;

		public UncertaintyBasedReliabilityPredictionBuilder withConfig(PCMSolverWorkflowRunConfiguration config) {
			this.config = config;
			return this;
		}

		public UncertaintyBasedReliabilityPredictionBuilder bruteForceStateSpaceExploration() {
			this.exploreStrategy = new BruteForceExplorationStrategy();
			return this;
		}

		public UncertaintyBasedReliabilityPredictionBuilder exploreStateSpaceWith(
				StateSpaceExplorationStrategy strategy) {
			this.exploreStrategy = strategy;
			return this;
		}

		public UncertaintyBasedReliabilityPredictionBuilder andUncertaintyRepo(UncertaintyRepository uncertaintyRepo) {
			this.uncertaintyRepo = uncertaintyRepo;
			return this;
		}

		public UncertaintyBasedReliabilityPredictor build() {
			checkValidity();

			adjustConfig();

			return new UncertaintyBasedReliabilityPredictor(exploreStrategy, config, uncertaintyRepo);
		}

		private void checkValidity() {
			requireNonNull(config, "The reliability configuration is missing.");
			requireNonNull(uncertaintyRepo, "The uncertainty repository is missing.");
		}

		private void adjustConfig() {
			// Deactivate result presentation if state space is explored. This is due to the
			// high number of possible results which pop up after each state analysis.
			if (nonNull(exploreStrategy)) {
				config.setShowHtmlResults(false);
			}
		}

	}

	private final UncertaintyRepository uncertaintyRepo;
	private final PCMSolverWorkflowRunConfiguration config;
	private final StateSpaceExplorationStrategy exploreStrategy;

	private UncertaintyBasedReliabilityPredictor(StateSpaceExplorationStrategy exploreStrategy,
			PCMSolverWorkflowRunConfiguration config, UncertaintyRepository uncertaintyRepo) {
		this.config = config;
		this.exploreStrategy = exploreStrategy;
		this.uncertaintyRepo = uncertaintyRepo;

		UncertaintyModelManager.get().manage(uncertaintyRepo.getUncertaintyInducedFailureTypes());

		initProbabilityDistributions();
	}

	private void initProbabilityDistributions() {
		ProbabilityDistributionFactory.get().register(new MultinomialDistributionSupplier());
	}

	public static UncertaintyBasedReliabilityPredictionBuilder newBuilder() {
		return new UncertaintyBasedReliabilityPredictionBuilder();
	}

	public ReliabilityPredictionResult predictSuccessProbability(PCMInstance unresolved) {
		requireNonNull(exploreStrategy, "Cannot predict reliability when no evaluation strategy is selected.");

		ReliabilityPredictionResult result = new ReliabilityPredictionResult();

		var stateSpace = UncertaintyModelManager.get().getStateSpace();
		for (List<UncertaintyState> eachTuple : exploreStrategy.explore(stateSpace)) {
			var conditionalPoS = predictConditionalSuccessProbability(unresolved, eachTuple);
			result.addAll(conditionalPoS);
		}

		return result;
	}

	public Set<ReliabilityPredictionResultPerScenario> predictConditionalSuccessProbability(PCMInstance unresolvedModel,
			List<UncertaintyState> original) {
		var improved = applyArchitecturalCountermeasures(unresolvedModel, original);

		var resolvedModel = resolveUncertainties(unresolvedModel, improved); 

		var conditionalPoS = predictProbabilityOfSuccessGiven(resolvedModel);
		var probOfUncertainties = predictProbabilityOfUncertainties(original, resolvedModel);
		
		return conditionalPoS.stream()
				.map(each -> ReliabilityPredictionResultPerScenario.of(each, improved, probOfUncertainties))
				.collect(toSet());
	}

	private List<UncertaintyState> applyArchitecturalCountermeasures(PCMInstance pcmModel, List<UncertaintyState> stateTuple) {
		List<UncertaintyState> improved = Lists.newArrayList(stateTuple);
		for (ArchitecturalCountermeasure each : filterActiveArchitecturalCountermeasures(pcmModel)) {
			var result = findApplicableState(each, improved);
			if (result.isEmpty()) {
				break;
			}

			var oldState = result.get();
			improved.remove(oldState);

			var improvedValue = applyArchitecturalCountermeasure(each, oldState);
			var improvedState = oldState.newValuedStateWith(improvedValue);
			improved.add(improvedState);
		}
		return improved;
	}

	private Set<ArchitecturalCountermeasure> filterActiveArchitecturalCountermeasures(PCMInstance pcmModel) {
		return uncertaintyRepo.getArchitecturalCountermeasures().stream()
				.filter(c -> allPreconditionsFulfilled(c, pcmModel))
				.collect(toSet());
	}

	private Optional<UncertaintyState> findApplicableState(ArchitecturalCountermeasure countermeasure,
			List<UncertaintyState> stateTuple) {
		return stateTuple.stream()
				.filter(each -> each.instantiates(countermeasure.getTargetUncertainty()))
				.findFirst();
	}

	private CategoricalValue applyArchitecturalCountermeasure(ArchitecturalCountermeasure countermeasure, UncertaintyState state) {
		var improvement = countermeasure.getUncertaintyImprovement();
		return UncertaintyImprovementCalculator.get().calculate(improvement, state.getValue());
	}

	private PCMInstance resolveUncertainties(PCMInstance modelToResolve, List<UncertaintyState> stateTuple) {
		var uncertaintyResolver = new UncertaintyResolver(modelToResolve);
		System.out.println("UncertaintyBasedReliabilityPredictor:resolveUncertainties");
		uncertaintyRepo.getUncertaintyInducedFailureTypes()
				.forEach(uncertainty -> uncertaintyResolver.resolve(uncertainty, stateTuple));
		return modelToResolve;
	}

	private List<MarkovTransformationResult> predictProbabilityOfSuccessGiven(PCMInstance pcmModel) {
		var solver = new Pcm2MarkovStrategy(config);
		solver.transform(pcmModel);
		return solver.getAllSolvedValues();
	}

	// Assuming independence of the uncertainty models
	private Double predictProbabilityOfUncertainties(List<UncertaintyState> stateTuple, PCMInstance pcmModel) {
		var probOfUncertainties = 1.0;
		for (UncertaintyInducedFailureType each : uncertaintyRepo.getUncertaintyInducedFailureTypes()) {
			if (allPreconditionsFulfilled(each, pcmModel)) {
				var uncertaintyModel = UncertaintyModelManager.get().findModelFor(each).orElseThrow();
				probOfUncertainties *= uncertaintyModel.probability(stateTuple);
			}
		}
		return probOfUncertainties;
	}

}
