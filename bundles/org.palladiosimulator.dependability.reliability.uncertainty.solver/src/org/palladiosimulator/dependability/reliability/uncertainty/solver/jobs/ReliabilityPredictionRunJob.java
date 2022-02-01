package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import de.uka.ipd.sdq.workflow.jobs.SequentialBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.mdsd.blackboard.MDSDBlackboard;

public abstract class ReliabilityPredictionRunJob extends SequentialBlackboardInteractingJob<MDSDBlackboard> {

	protected final ReliabilityPredictionContext context;

	public ReliabilityPredictionRunJob(ReliabilityPredictionContext context) {
		this.context = context;
	}

}
