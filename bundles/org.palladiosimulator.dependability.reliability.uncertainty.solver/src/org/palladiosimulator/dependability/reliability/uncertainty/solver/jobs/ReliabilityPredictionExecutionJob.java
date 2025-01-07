package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import java.util.Optional;

import org.eclipse.core.runtime.IProgressMonitor;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPrediction;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPredictionConfig;

import de.uka.ipd.sdq.workflow.jobs.CleanupFailedException;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;
import tools.mdsd.probdist.api.apache.util.IProbabilityDistributionRepositoryLookup;
import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionRegistry;
import tools.mdsd.probdist.api.parser.ParameterParser;
import tools.mdsd.probdist.api.random.ISeedProvider;

public class ReliabilityPredictionExecutionJob extends ReliabilityPredictionRunJob {

    private final IProbabilityDistributionRegistry<CategoricalValue> probabilityDistributionRegistry;
    private final IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory;
    private final ParameterParser parameterParser;
    private final IProbabilityDistributionRepositoryLookup probDistRepoLookup;
    private final Optional<ISeedProvider> seedProvider;

    public ReliabilityPredictionExecutionJob(ReliabilityPredictionContext context,
            IProbabilityDistributionRegistry<CategoricalValue> probabilityDistributionRegistry,
            IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
            ParameterParser parameterParser, IProbabilityDistributionRepositoryLookup probDistRepoLookup,
            Optional<ISeedProvider> seedProvider) {
        super(context);
        this.probabilityDistributionRegistry = probabilityDistributionRegistry;
        this.probabilityDistributionFactory = probabilityDistributionFactory;
        this.parameterParser = parameterParser;
        this.probDistRepoLookup = probDistRepoLookup;
        this.seedProvider = seedProvider;
    }

    @Override
    public void execute(IProgressMonitor monitor) throws JobFailedException, UserCanceledException {
        var runConfig = UncertaintyBasedReliabilityPredictionConfig.newBuilder()
            .withReliabilityRunConfig(context.config)
            .andPcmModels(getBlackboard())
            .exploreStateSpaceWith(context.explorationStrategy)
            .build();
        var result = UncertaintyBasedReliabilityPrediction.predict(runConfig, probabilityDistributionRegistry,
                probabilityDistributionFactory, parameterParser, probDistRepoLookup, seedProvider);

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
