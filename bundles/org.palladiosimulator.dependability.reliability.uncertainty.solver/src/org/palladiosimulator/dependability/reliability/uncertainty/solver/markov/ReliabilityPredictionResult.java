package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import static java.util.stream.Collectors.toMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;

public class ReliabilityPredictionResult {

	private final static double ZERO_PROBABILITY = 0.0;

	private final Map<String, Double> successProbabilityToUsageScenario;
	private final double probabilityOfUncertainties;

	private ReliabilityPredictionResult() {
		this(ZERO_PROBABILITY);
	}

	public String toString() {
		String str = "successProbabilityToUsageScenario: ";
		 Iterator<Map.Entry<String, Double>> iterator = successProbabilityToUsageScenario.entrySet().iterator();
		    while (iterator.hasNext()) {
		        Map.Entry<String, Double> entry = iterator.next();
		        str += "k:" + entry.getKey() + ", v:" + entry.getValue() + "; ";
		        // System.out.println(entry.getKey() + ":" + entry.getValue());
		    }
		str += "probabilityOfUncertainties: " + probabilityOfUncertainties;
		return str;
	}
	
	private ReliabilityPredictionResult(double probabilityOfUncertainties) {
		this(new HashMap<UsageScenario, Double>(), probabilityOfUncertainties);
	}

	private ReliabilityPredictionResult(List<MarkovTransformationResult> results, double probabilityOfUncertainties) {
		this.successProbabilityToUsageScenario = initSuccessProbabilityEntries(results);
		this.probabilityOfUncertainties = probabilityOfUncertainties;
	}

	private ReliabilityPredictionResult(Map<UsageScenario, Double> results, double probabilityOfUncertainties) {
		this.successProbabilityToUsageScenario = initSuccessProbabilityEntries(results);
		this.probabilityOfUncertainties = probabilityOfUncertainties;
	}

	private static Map<String, Double> initSuccessProbabilityEntries(List<MarkovTransformationResult> results) {
		return results.stream().collect(toMap(r -> r.getScenario().getId(), r -> r.getSuccessProbability()));
	}

	private static Map<String, Double> initSuccessProbabilityEntries(Map<UsageScenario, Double> results) {
		return results.entrySet().stream().collect(toMap(e -> e.getKey().getId(), Entry::getValue));
	}

	public static ReliabilityPredictionResult of(List<MarkovTransformationResult> results,
			double probabilityOfUncertainties) {
		return new ReliabilityPredictionResult(results, probabilityOfUncertainties);
	}

	public static ReliabilityPredictionResult of(Map<UsageScenario, Double> successProbabilityToUsageScenario,
			double probabilityOfUncertainties) {
		return new ReliabilityPredictionResult(successProbabilityToUsageScenario, probabilityOfUncertainties);
	}

	public static ReliabilityPredictionResult empty() {
		return new ReliabilityPredictionResult();
	}

	public double getProbabilityOfSuccessGiven(UsageScenario scenario) {
		return findProbability(scenario).orElse(ZERO_PROBABILITY);
	}

	public double getProbabilityOfFailureGiven(UsageScenario scenario) {
		return 1 - getProbabilityOfSuccessGiven(scenario);
	}

	public double getProbabilityOfUncertainty() {
		return probabilityOfUncertainties;
	}

	public double getJoinedSuccessAndUncertaintyProbability(UsageScenario scenario) {
		return getProbabilityOfSuccessGiven(scenario) * getProbabilityOfUncertainty();
	}

	public double getJoinedFailureAndUncertaintyProbability(UsageScenario scenario) {
		return getProbabilityOfFailureGiven(scenario) * getProbabilityOfUncertainty();
	}

	private Optional<Double> findProbability(UsageScenario scenario) {
		return Optional.ofNullable(successProbabilityToUsageScenario.get(scenario.getId()));
	}

}