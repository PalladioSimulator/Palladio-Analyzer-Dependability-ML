package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import org.eclipse.emf.common.util.URI;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.markov.ReliabilityPredictionResult;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

public class ReliabilityPredictionContext {

	protected final PCMSolverWorkflowRunConfiguration config;
	protected final String uncertaintyModel;
	protected final String explorationStrategy;
	
	public URI exportLocation;
	public ReliabilityPredictionResult result;

	protected ReliabilityPredictionContext(PCMSolverWorkflowRunConfiguration config, String uncertaintyModel,
			String explorationStrategy, ReliabilityPredictionResult result) {
		this.config = config;
		this.uncertaintyModel = uncertaintyModel;
		this.explorationStrategy = explorationStrategy;
		this.result = result;
		this.exportLocation = null;
	}
	
	public ReliabilityPredictionContext(PCMSolverWorkflowRunConfiguration config, String uncertaintyModel,
			String explorationStrategy) {
		this(config, uncertaintyModel, explorationStrategy, null);
	}
	
}
