package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import de.uka.ipd.sdq.workflow.jobs.SequentialBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.mdsd.blackboard.MDSDBlackboard;

public class RootReliabilityPredictionRunJob extends SequentialBlackboardInteractingJob<MDSDBlackboard> {
	
	public RootReliabilityPredictionRunJob(PCMSolverWorkflowRunConfiguration config, String uncertaintyModel,
			String explorationStrategy) {
		var context = new ReliabilityPredictionContext(config, uncertaintyModel, explorationStrategy);
		
		addJob(new ReliabilityPredictionExecutionJob(context));
		addJob(new ReliabilityPredictionResultVisualizationJob(context));
	}

}
