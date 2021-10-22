package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPrediction;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPredictionConfig;

import de.uka.ipd.sdq.workflow.jobs.CleanupFailedException;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;

public class ReliabilityPredictionExecutionJob extends ReliabilityPredictionRunJob {

	public ReliabilityPredictionExecutionJob(ReliabilityPredictionContext context) {
		super(context);
	}

	@Override
	public void execute(IProgressMonitor monitor) throws JobFailedException, UserCanceledException {
		var runConfig = UncertaintyBasedReliabilityPredictionConfig.newBuilder()
				.withReliabilityRunConfig(context.config)
				.andPcmModels(getBlackboard())
				.andUncertainties(context.uncertaintyModel)
				.exploreStateSpaceWith(context.explorationStrategy)
				.build();
		var result = UncertaintyBasedReliabilityPrediction.predict(runConfig);
		
		context.result = result;
	}

	@Override
	public void cleanup(IProgressMonitor monitor) throws CleanupFailedException {
		// Nothing to do here
	}

	@Override
	public String getName() {
		return ReliabilityPredictionExecutionJob.class.getName();
	}

}
