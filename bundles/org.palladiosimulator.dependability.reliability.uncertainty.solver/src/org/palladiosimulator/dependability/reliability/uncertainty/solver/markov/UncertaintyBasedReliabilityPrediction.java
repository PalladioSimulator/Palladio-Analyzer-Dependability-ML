package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.List;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.UncertaintyModelManager;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;
import org.palladiosimulator.reliability.solver.pcm2markov.Pcm2MarkovStrategy;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import com.google.common.collect.Lists;

public class UncertaintyBasedReliabilityPrediction {

	public static class UncertaintyBasedReliabilityPredictionBuilder {

		private PCMSolverWorkflowRunConfiguration config = null;
		private StateSpaceExplorationStrategy exploreStrategy = null;
		private List<UncertaintyInducedFailureType> uncertainties = Lists.newArrayList();

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

		public UncertaintyBasedReliabilityPredictionBuilder addUncertaintyFailureType(
				UncertaintyInducedFailureType uncertainty) {
			this.uncertainties.add(uncertainty);
			return this;
		}

		public UncertaintyBasedReliabilityPredictionBuilder addUncertaintyFailureTypes(
				List<UncertaintyInducedFailureType> uncertainties) {
			this.uncertainties.addAll(uncertainties);
			return this;
		}

		public UncertaintyBasedReliabilityPrediction build() {
			checkValidity();

			adjustConfig();
			
			return new UncertaintyBasedReliabilityPrediction(exploreStrategy, config, uncertainties);
		}

		private void checkValidity() {
			requireNonNull(config, "The reliability configuration is missing.");
			requireNonNull(uncertainties, "The uncertainty induced failure types are missing.");
			if (uncertainties.isEmpty()) {
				throw new IllegalArgumentException("The uncertainty induced failure types are not specified.");
			}
		}

		private void adjustConfig() {
			// Deactivate result presentation if state space is explored. This is due to the
			// high number of possible results which pop up after each state analysis.
			if (nonNull(exploreStrategy)) {
				config.setShowHtmlResults(false);
			}
		}

	}

	private final List<UncertaintyInducedFailureType> uncertainties;
	private final PCMSolverWorkflowRunConfiguration config;
	private final StateSpaceExplorationStrategy exploreStrategy;

	private UncertaintyBasedReliabilityPrediction(StateSpaceExplorationStrategy exploreStrategy,
			PCMSolverWorkflowRunConfiguration config, List<UncertaintyInducedFailureType> uncertainties) {
		this.config = config;
		this.exploreStrategy = exploreStrategy;
		this.uncertainties = uncertainties;
		
		UncertaintyModelManager.get().manage(uncertainties);
	}

	public static UncertaintyBasedReliabilityPredictionBuilder newBuilder() {
		return new UncertaintyBasedReliabilityPredictionBuilder();
	}

	public ReliabilityPredictionResult predict(PCMInstance unresolved) {
		requireNonNull(exploreStrategy, "Cannot predict reliability when no evaluation strategy is selected.");

		List<ReliabilityPredictionResult> results = Lists.newArrayList();

		var stateSpace = UncertaintyModelManager.get().getStateSpace();
		for (List<UncertaintyState> eachTuple : exploreStrategy.explore(stateSpace)) {
			results.add(predict(unresolved, eachTuple));
		}

		return results.stream().reduce(ReliabilityPredictionResult.marginalizingUncertainties())
				.orElse(ReliabilityPredictionResult.empty());
	}

	public ReliabilityPredictionResult predict(PCMInstance unresolvedModel, List<UncertaintyState> stateTuple) {
		var resolvedModel = resolveUncertainties(unresolvedModel, stateTuple);

		var probOfSuccess = predictProbabilityOfSuccessGiven(resolvedModel);
		var probOfUncertainties = predictProbabilityOfUncertainties(stateTuple);
		return ReliabilityPredictionResult.of(probOfSuccess, probOfUncertainties);
	}

	private PCMInstance resolveUncertainties(PCMInstance modelToResolve, List<UncertaintyState> stateTuple) {
		var uncertaintyResolver = new UncertaintyResolver(modelToResolve);
		uncertainties.forEach(uncertainty -> uncertaintyResolver.resolve(uncertainty, stateTuple));
		return modelToResolve;
	}

	private List<MarkovTransformationResult> predictProbabilityOfSuccessGiven(PCMInstance pcmModel) {
		var solver = new Pcm2MarkovStrategy(config);
		solver.transform(pcmModel);
		return solver.getAllSolvedValues();
	}

	// Assuming independence of the uncertainty models
	private Double predictProbabilityOfUncertainties(List<UncertaintyState> stateTuple) {
		var probOfUncertainties = 1.0;
		for (UncertaintyInducedFailureType each : uncertainties) {
			var uncertaintyModel = UncertaintyModelManager.get().findModelFor(each).orElseThrow();
			probOfUncertainties *= uncertaintyModel.probability(stateTuple);
		}
		return probOfUncertainties;
	}

}
