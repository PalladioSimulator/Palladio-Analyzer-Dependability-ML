package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import org.palladiosimulator.dependability.reliability.uncertainty.solver.markov.ReliabilityPredictionResult;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

public class ReliabilityPredictionContext {

	protected final PCMSolverWorkflowRunConfiguration config;
	protected final String uncertaintyModel;
	protected final String explorationStrategy;
	
	protected ReliabilityPredictionResult result;

	protected ReliabilityPredictionContext(PCMSolverWorkflowRunConfiguration config, String uncertaintyModel,
			String explorationStrategy, ReliabilityPredictionResult result) {
		this.config = config;
		this.uncertaintyModel = uncertaintyModel;
		this.explorationStrategy = explorationStrategy;
		this.result = result;
	}
	
	protected ReliabilityPredictionContext(PCMSolverWorkflowRunConfiguration config, String uncertaintyModel,
			String explorationStrategy) {
		this(config, uncertaintyModel, explorationStrategy, null);
	}

}
