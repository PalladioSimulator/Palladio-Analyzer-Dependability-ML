package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import java.util.List;

import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;

public class ReliabilityPredictionResultPerScenario {

	private final UsageScenario usageScenario;
	private final List<UncertaintyState> stateTuple;
	private final double probabilityOfSuccess;
	private final double probabilityOfUncertainties;

	private ReliabilityPredictionResultPerScenario(MarkovTransformationResult markovResult,
			List<UncertaintyState> stateTuple, double probabilityOfUncertainties) {
		this.usageScenario = markovResult.getScenario();
		this.stateTuple = stateTuple;
		this.probabilityOfSuccess = markovResult.getSuccessProbability();
		this.probabilityOfUncertainties = probabilityOfUncertainties;
	}

	public static ReliabilityPredictionResultPerScenario of(MarkovTransformationResult results,
			List<UncertaintyState> stateTuple, double probabilityOfUncertainties) {
		return new ReliabilityPredictionResultPerScenario(results, stateTuple, probabilityOfUncertainties);
	}

	public UsageScenario getUsageScenario() {
		return usageScenario;
	}

	public List<UncertaintyState> getUncertainties() {
		return stateTuple;
	}

	public double getProbabilityOfUncertainty() {
		return probabilityOfUncertainties;
	}

	public double getConditionalProbabilityOfSuccess() {
		return probabilityOfSuccess;
	}

	public double getConditionalProbabilityOfFailure() {
		return 1 - probabilityOfSuccess;
	}

	// Assuming independence of usage scenario and observed uncertainties
	public double getJoinedSuccessAndUncertaintyProbability() {
		return getConditionalProbabilityOfSuccess() * getProbabilityOfUncertainty();
	}

	public double getJoinedFailureAndUncertaintyProbability() {
		return getProbabilityOfUncertainty() - getJoinedSuccessAndUncertaintyProbability();
	}

}
