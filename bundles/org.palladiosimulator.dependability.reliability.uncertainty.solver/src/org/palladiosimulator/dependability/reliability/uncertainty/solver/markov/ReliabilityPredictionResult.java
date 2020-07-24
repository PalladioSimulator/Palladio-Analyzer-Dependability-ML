package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;

import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ReliabilityPredictionResult {

	private static class ProbabilityResult {

		public double successProbability = 0;
		public double physicalStateProbability = 0;

		public ProbabilityResult(double successProbability, double physicalStateProbability) {
			this.successProbability = successProbability;
			this.physicalStateProbability = physicalStateProbability;
		}

		public static ProbabilityResult of(double successProbability, double physicalStateProbability) {
			return new ProbabilityResult(successProbability, physicalStateProbability);
		}
		
		public static ProbabilityResult from(MarkovTransformationResult result) {
			return new ProbabilityResult(result.getSuccessProbability(), result.getCumulatedPhysicalStateProbability());
		}

		public static ProbabilityResult zeroValued() {
			return new ProbabilityResult(0, 0);
		}

	}

	private final Map<UsageScenario, ProbabilityResult> probabilities;
	private final Double probabilityOfUncertainties;

	private ReliabilityPredictionResult() {
		this.probabilities = Maps.newHashMap();
		this.probabilityOfUncertainties = 1.0;
	}

	private ReliabilityPredictionResult(List<MarkovTransformationResult> markovResults, Double probOfUncertainties) {
		this.probabilities = markovResults.stream()
				.collect(toMap(r -> r.getScenario(), r -> ProbabilityResult.from(r)));
		this.probabilityOfUncertainties = probOfUncertainties;
	}

	public static ReliabilityPredictionResult of(List<MarkovTransformationResult> probOfSuccess,
			Double probOfUncertainties) {
		return new ReliabilityPredictionResult(probOfSuccess, probOfUncertainties);
	}

	public static ReliabilityPredictionResult empty() {
		return new ReliabilityPredictionResult(Lists.newArrayList(), 0.0);
	}

	public double getProbabilityOfSuccess(UsageScenario scenario) {
		return findProbability(scenario).successProbability;
	}
	
	public double getProbabilityOfFailure(UsageScenario scenario) {
		return 1 - getProbabilityOfSuccess(scenario);
	}

	public double getPhysicalStateProbability(UsageScenario scenario) {
		return findProbability(scenario).physicalStateProbability;
	}

	private ProbabilityResult findProbability(UsageScenario scenario) {
		return Optional.ofNullable(probabilities.get(scenario)).orElse(ProbabilityResult.zeroValued());
	}

	public static BinaryOperator<ReliabilityPredictionResult> marginalizingUncertainties() {
		return (r1, r2) -> {
			var newResult = new ReliabilityPredictionResult();
			for (UsageScenario each : r1.probabilities.keySet()) {
				var prob1 = r1.probabilities.get(each);
				var prob2 = r2.probabilities.get(each);

				var successProbability = (r1.probabilityOfUncertainties * prob1.successProbability)
						+ (r2.probabilityOfUncertainties * prob2.successProbability);
				var physicalStateProbability = (prob1.physicalStateProbability + prob2.physicalStateProbability) / 2;
				
				newResult.probabilities.put(each, ProbabilityResult.of(successProbability, physicalStateProbability));
			}
			return newResult;
		};
	}

}