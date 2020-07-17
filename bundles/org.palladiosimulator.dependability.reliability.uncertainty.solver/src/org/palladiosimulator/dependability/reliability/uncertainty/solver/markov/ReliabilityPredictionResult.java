package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;

import com.google.common.collect.Lists;

public class ReliabilityPredictionResult {

	private static class ProbabilityResult {

		public double successProbability = 0;
		public double physicalStateProbability = 0;

		public ProbabilityResult(double successProbability, double physicalStateProbability) {
			this.successProbability = successProbability;
			this.physicalStateProbability = physicalStateProbability;
		}

		public static ProbabilityResult from(MarkovTransformationResult result) {
			return new ProbabilityResult(result.getSuccessProbability(), result.getCumulatedPhysicalStateProbability());
		}

		public static ProbabilityResult zeroValued() {
			return new ProbabilityResult(0, 0);
		}

		public ProbabilityResult add(ProbabilityResult probsToAdd) {
			double successProb = this.successProbability + probsToAdd.successProbability;
			double physicalStateProb = this.physicalStateProbability + probsToAdd.physicalStateProbability;
			return new ProbabilityResult(successProb, physicalStateProb);
		}

	}

	private final Map<UsageScenario, ProbabilityResult> probabilities;

	private ReliabilityPredictionResult(List<MarkovTransformationResult> markovResults) {
		this.probabilities = markovResults.stream()
				.collect(toMap(r -> r.getScenario(), r -> ProbabilityResult.from(r)));
	}

	public static ReliabilityPredictionResult of(List<MarkovTransformationResult> markovResults) {
		return new ReliabilityPredictionResult(markovResults);
	}

	public void mergeWith(ReliabilityPredictionResult result) {
		if (isNotMergable(result)) {
			// TODO Logging
			return;
		}

		for (UsageScenario each : result.probabilities.keySet()) {
			probabilities.merge(each, result.probabilities.get(each), (v1, v2) -> v1.add(v2));
		}
	}

	public double getProbabilityOfSuccess(UsageScenario scenario) {
		return findProbability(scenario).successProbability;
	}

	public double getPhysicalStateProbability(UsageScenario scenario) {
		return findProbability(scenario).physicalStateProbability;
	}

	public double getTotalProbabilityOfSuccess() {
		return probabilities.values().stream().reduce(ProbabilityResult.zeroValued(),
				(subtotal, prob) -> subtotal.add(prob)).successProbability;
	}
	
	private boolean isNotMergable(ReliabilityPredictionResult result) {
		if (result.probabilities.size() != probabilities.size()) {
			return true;
		}

		List<UsageScenario> copy = Lists.newArrayList(result.probabilities.keySet());
		for (UsageScenario each : probabilities.keySet()) {
			copy.removeIf(scenario -> scenario.getId().equals(each.getId()));
		}
		return copy.isEmpty() == false;
	}

	private ProbabilityResult findProbability(UsageScenario scenario) {
		return Optional.ofNullable(probabilities.get(scenario)).orElse(ProbabilityResult.zeroValued());
	}

}