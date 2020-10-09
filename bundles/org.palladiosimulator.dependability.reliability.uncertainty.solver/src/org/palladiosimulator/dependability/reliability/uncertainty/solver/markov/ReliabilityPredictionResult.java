package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;

import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;

import com.google.common.collect.Maps;

public class ReliabilityPredictionResult {

	private final static double ZERO_PROBABILITY = 0.0;

	private final Map<String, Double> successProbabilityToUsageScenario;
	private final double probabilityOfUncertainties;

	private ReliabilityPredictionResult() {
		this.successProbabilityToUsageScenario = Maps.newHashMap();
		this.probabilityOfUncertainties = ZERO_PROBABILITY;
	}

	private ReliabilityPredictionResult(List<MarkovTransformationResult> results, double probabilityOfUncertainties) {
		this.successProbabilityToUsageScenario = initSuccessProbabilityEntries(results);
		this.probabilityOfUncertainties = probabilityOfUncertainties;
	}

	private Map<String, Double> initSuccessProbabilityEntries(List<MarkovTransformationResult> results) {
		return results.stream().collect(toMap(r -> r.getScenario().getId(), r -> r.getSuccessProbability()));
	}

	public static ReliabilityPredictionResult of(List<MarkovTransformationResult> results,
			double probabilityOfUncertainties) {
		return new ReliabilityPredictionResult(results, probabilityOfUncertainties);
	}

	public static ReliabilityPredictionResult empty() {
		return new ReliabilityPredictionResult();
	}

	public double getProbabilityOfSuccess(UsageScenario scenario) {
		return findProbability(scenario).orElse(ZERO_PROBABILITY);
	}

	public double getProbabilityOfFailure(UsageScenario scenario) {
		return 1 - getProbabilityOfSuccess(scenario);
	}

	public double getProbabilityOfUncertainty() {
		return probabilityOfUncertainties;
	}

	private Optional<Double> findProbability(UsageScenario scenario) {
		return Optional.ofNullable(successProbabilityToUsageScenario.get(scenario.getId()));
	}

	public static BinaryOperator<ReliabilityPredictionResult> marginalizingUncertaintiesForEachScenario() {
		return (r1, r2) -> {
			var newResult = new ReliabilityPredictionResult();
			for (String each : r1.successProbabilityToUsageScenario.keySet()) {
				var successProbability1 = r1.successProbabilityToUsageScenario.get(each);
				var successProbability2 = r2.successProbabilityToUsageScenario.get(each);

				var accumulatedSuccessProbability = (r1.probabilityOfUncertainties * successProbability1)
						+ (r2.probabilityOfUncertainties * successProbability2);

				newResult.successProbabilityToUsageScenario.put(each, accumulatedSuccessProbability);
			}
			return newResult;
		};
	}

}