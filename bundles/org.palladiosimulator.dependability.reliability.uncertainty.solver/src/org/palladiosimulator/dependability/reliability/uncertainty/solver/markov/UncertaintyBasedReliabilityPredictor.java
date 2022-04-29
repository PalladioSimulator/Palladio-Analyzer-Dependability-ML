package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.util.ArchitecturalPreconditionUtil.allPreconditionsFulfilled;

import java.util.List;
import java.util.Set;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.UncertaintyModelManager;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.util.ArchitecturalCountermeasureOperator;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;
import org.palladiosimulator.reliability.solver.pcm2markov.Pcm2MarkovStrategy;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import tools.mdsd.probdist.api.apache.supplier.MultinomialDistributionSupplier;
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
	
	private ArchitecturalCountermeasureOperator operator = null;

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

		getArchitecturalCountermeasureOperator(unresolved).applyToUncertaintyModels();
		
		var stateSpace = UncertaintyModelManager.get().getStateSpace();
		for (List<UncertaintyState> eachTuple : exploreStrategy.explore(stateSpace)) {
			var conditionalPoS = predict(unresolved, eachTuple);
			result.addAll(conditionalPoS);
		}

		return result;
	}

	public Set<ReliabilityPredictionResultPerScenario> predictConditionalSuccessProbability(PCMInstance unresolved,
			List<UncertaintyState> stateTuple) {
		var improved = getArchitecturalCountermeasureOperator(unresolved).applyToUncertainties(stateTuple);
		return predict(unresolved, improved);
	}
	
	private Set<ReliabilityPredictionResultPerScenario> predict(PCMInstance unresolved, List<UncertaintyState> stateTuple) {
		var resolved = resolveUncertainties(unresolved, stateTuple); 

		var conditionalPoS = predictProbabilityOfSuccessGiven(resolved);
		var probOfUncertainties = predictProbabilityOfUncertainties(stateTuple, resolved);
		
		return conditionalPoS.stream()
				.map(each -> ReliabilityPredictionResultPerScenario.of(each, stateTuple, probOfUncertainties))
				.collect(toSet());
	}

	private PCMInstance resolveUncertainties(PCMInstance modelToResolve, List<UncertaintyState> stateTuple) {
		var uncertaintyResolver = new UncertaintyResolver(modelToResolve);
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
	
	private ArchitecturalCountermeasureOperator getArchitecturalCountermeasureOperator(PCMInstance unresolved) {
		if (operator == null) {
			operator = ArchitecturalCountermeasureOperator.createOperatorFor(unresolved, uncertaintyRepo);
		}
		return operator;
	}

}
