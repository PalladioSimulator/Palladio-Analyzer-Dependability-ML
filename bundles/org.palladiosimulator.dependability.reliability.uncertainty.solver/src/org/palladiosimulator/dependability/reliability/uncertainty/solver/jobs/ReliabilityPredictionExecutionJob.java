package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPrediction;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPredictionConfig;

import de.uka.ipd.sdq.workflow.jobs.CleanupFailedException;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;
import tools.mdsd.probdist.api.apache.util.IProbabilityDistributionRepositoryLookup;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionRegistry;
import tools.mdsd.probdist.api.parser.ParameterParser;

public class ReliabilityPredictionExecutionJob extends ReliabilityPredictionRunJob {
    
    private final IProbabilityDistributionRegistry probabilityDistributionRegistry;
    private final IProbabilityDistributionFactory probabilityDistributionFactory;
    private final ParameterParser parameterParser;
    private final IProbabilityDistributionRepositoryLookup probDistRepoLookup;

	public ReliabilityPredictionExecutionJob(ReliabilityPredictionContext context, IProbabilityDistributionRegistry probabilityDistributionRegistry, IProbabilityDistributionFactory probabilityDistributionFactory, ParameterParser parameterParser, IProbabilityDistributionRepositoryLookup probDistRepoLookup) {
		super(context);
		this.probabilityDistributionRegistry = probabilityDistributionRegistry;
		this.probabilityDistributionFactory = probabilityDistributionFactory;
		this.parameterParser = parameterParser;
		this.probDistRepoLookup = probDistRepoLookup;
	}

	@Override
	public void execute(IProgressMonitor monitor) throws JobFailedException, UserCanceledException {
		var runConfig = UncertaintyBasedReliabilityPredictionConfig.newBuilder()
				.withReliabilityRunConfig(context.config)
				.andPcmModels(getBlackboard())
				.exploreStateSpaceWith(context.explorationStrategy)
				.build();
		var result = UncertaintyBasedReliabilityPrediction.predict(runConfig, probabilityDistributionRegistry, probabilityDistributionFactory, parameterParser, probDistRepoLookup);
		
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
