package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import de.uka.ipd.sdq.workflow.jobs.SequentialBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.mdsd.blackboard.MDSDBlackboard;
import tools.mdsd.probdist.api.apache.util.IProbabilityDistributionRepositoryLookup;
import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionRegistry;
import tools.mdsd.probdist.api.parser.ParameterParser;
import tools.mdsd.probdist.api.random.ISeedProvider;

public class RootReliabilityPredictionRunJob extends SequentialBlackboardInteractingJob<MDSDBlackboard> {

    public RootReliabilityPredictionRunJob(PCMSolverWorkflowRunConfiguration config, String uncertaintyModel,
            String explorationStrategy, Optional<URI> exportLocation,
            IProbabilityDistributionRegistry<CategoricalValue> probabilityDistributionRegistry,
            IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
            ParameterParser parameterParser, IProbabilityDistributionRepositoryLookup probDistRepoLookup,
            Optional<ISeedProvider> seedProvider) {
        var context = new ReliabilityPredictionContext(config, uncertaintyModel, explorationStrategy);

        addJob(new ReliabilityPredictionExecutionJob(context, probabilityDistributionRegistry,
                probabilityDistributionFactory, parameterParser, probDistRepoLookup, seedProvider));
        addJob(new ReliabilityPredictionResultVisualizationJob(context));

        if (exportLocation.isPresent()) {
            context.exportLocation = exportLocation.get();
            addJob(new CsvResultExportJob(context));
        }
    }

}
