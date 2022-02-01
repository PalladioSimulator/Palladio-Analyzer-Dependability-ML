package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import static java.util.stream.Collectors.toSet;

import java.util.Set;

import org.palladiosimulator.pcm.usagemodel.UsageScenario;

import com.google.common.collect.Sets;

public class ReliabilityPredictionResult {

	private final static double ZERO_PROBABILITY = 0.0;
	
	private final Set<ReliabilityPredictionResultPerScenario> results;
	
	public ReliabilityPredictionResult() {
		this.results = Sets.newHashSet();
	}
	
	public ReliabilityPredictionResult(Set<ReliabilityPredictionResultPerScenario> results) {
		this.results = results;
	}
	
	protected void add(ReliabilityPredictionResultPerScenario result) {
		this.results.add(result);
	}
	
	protected void addAll(Set<ReliabilityPredictionResultPerScenario> results) {
		this.results.addAll(results);
	}
	
	public Set<ReliabilityPredictionResultPerScenario> filterPredictionResultsFor(UsageScenario scenario) {
		return results.stream()
				.filter(each -> each.getUsageScenario().getId().equals(scenario.getId()))
				.collect(toSet());
	}
	
	public double getProbabilityOfSuccess(UsageScenario scenario) {
		return marginalizingUncertainties(scenario);
	}

	public double getProbabilityOfFailure(UsageScenario scenario) {
		return 1 - getProbabilityOfSuccess(scenario);
	}
	
	private double marginalizingUncertainties(UsageScenario scenario) {
		var scenarioSpecificResults = filterPredictionResultsFor(scenario);
		
		var summedUncertaintyProbs = scenarioSpecificResults.stream()
				.map(ReliabilityPredictionResultPerScenario::getProbabilityOfUncertainty)
				.reduce(Double::sum)
				.orElse(ZERO_PROBABILITY);
		requireUnitMeasure(summedUncertaintyProbs);

		var probabilityOfSuccess = scenarioSpecificResults.stream()
				.map(ReliabilityPredictionResultPerScenario::getJoinedSuccessAndUncertaintyProbability)
				.reduce(Double::sum)
				.orElseThrow(() -> new RuntimeException("Something went wrong during marginalizing process."));		
		return probabilityOfSuccess;
	}

	private void requireUnitMeasure(double summedUncertaintyProbs) {
		final var tolerance = 0.0001;
		if (Math.abs(1 - summedUncertaintyProbs) > tolerance) {
			throw new RuntimeException("The sum of uncertainty probabilities must be equal to one.");
		}
	}
	
}