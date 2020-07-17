package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.List;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.markov.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;
import org.palladiosimulator.reliability.solver.pcm2markov.Pcm2MarkovStrategy;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import com.google.common.collect.Lists;

public class UncertaintyBasedReliabilityPrediction {

	public static class UncertaintyBasedReliabilityPredictionBuilder {

		private PCMSolverWorkflowRunConfiguration config = null;
		private StateSpaceExplorationStrategy evalStrategy = null;
		private List<UncertaintyInducedFailureType> uncertainties = Lists.newArrayList();

		public UncertaintyBasedReliabilityPredictionBuilder withConfig(PCMSolverWorkflowRunConfiguration config) {
			this.config = config;
			return this;
		}

		public UncertaintyBasedReliabilityPredictionBuilder bruteForceStateSpaceExploration() {
			this.evalStrategy = new BruteForceExplorationStrategy();
			return this;
		}

		public UncertaintyBasedReliabilityPredictionBuilder exploreStateSpaceWith(
				StateSpaceExplorationStrategy strategy) {
			this.evalStrategy = strategy;
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

			return new UncertaintyBasedReliabilityPrediction(evalStrategy, uncertainties, config);
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
			if (nonNull(evalStrategy)) {
				config.setShowHtmlResults(false);
			}
		}

	}

	private final UncertaintyResolver uncertaintyResolver;
	private final PCMSolverWorkflowRunConfiguration config;
	private final StateSpaceExplorationStrategy evalStrategy;
	private final List<UncertaintyInducedFailureType> uncertainties;

	private UncertaintyBasedReliabilityPrediction(StateSpaceExplorationStrategy evalStrategy,
			List<UncertaintyInducedFailureType> uncertainties, PCMSolverWorkflowRunConfiguration config) {
		this.config = config;
		this.evalStrategy = evalStrategy;
		this.uncertainties = uncertainties;
		this.uncertaintyResolver = new UncertaintyResolver(uncertainties);
	}

	public static UncertaintyBasedReliabilityPredictionBuilder newBuilder() {
		return new UncertaintyBasedReliabilityPredictionBuilder();
	}

	public ReliabilityPredictionResult predict(PCMInstance unresolved) {
		requireNonNull(evalStrategy, "Cannot predict reliability when no evaluation strategy is selected.");

		ReliabilityPredictionResult result = null;

		DiscreteUncertaintyStateSpace stateSpace = DiscreteUncertaintyStateSpace.deriveFrom(uncertainties);
		for (List<UncertaintyState> eachTuple : evalStrategy.explore(stateSpace)) {
			ReliabilityPredictionResult interimResult = predict(unresolved, eachTuple);
			if (isNull(result)) {
				result = interimResult;
			} else {
				result.mergeWith(interimResult);
			}
		}

		return result;
	}

	public ReliabilityPredictionResult predict(PCMInstance unresolved, List<UncertaintyState> stateTuple) {
		PCMInstance resolved = uncertaintyResolver.resolve(unresolved, stateTuple);
		List<MarkovTransformationResult> markovResults = runReliabilityPrediction(resolved);
		return ReliabilityPredictionResult.of(markovResults);
	}

	private List<MarkovTransformationResult> runReliabilityPrediction(PCMInstance resolvedPcmModel) {
		Pcm2MarkovStrategy solver = new Pcm2MarkovStrategy(config);
		solver.transform(resolvedPcmModel);
		return solver.getAllSolvedValues();
	}

}
