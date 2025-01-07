package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.emf.common.util.URI;
import org.palladiosimulator.analyzer.workflow.jobs.EventsTransformationJob;
import org.palladiosimulator.analyzer.workflow.jobs.LoadModelIntoBlackboardJob;
import org.palladiosimulator.analyzer.workflow.jobs.LoadPCMModelsIntoBlackboardJob;
import org.palladiosimulator.analyzer.workflow.jobs.ValidatePCMModelsJob;
import org.palladiosimulator.architecturaltemplates.jobs.RunATJob;
import org.palladiosimulator.architecturaltemplates.jobs.config.ATExtensionConfigurationBuilder;
import org.palladiosimulator.experimentautomation.application.jobs.PrepareBlackboardJob;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import de.uka.ipd.sdq.workflow.jobs.ICompositeJob;
import de.uka.ipd.sdq.workflow.jobs.SequentialBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.mdsd.blackboard.MDSDBlackboard;
import tools.mdsd.probdist.api.apache.util.IProbabilityDistributionRepositoryLookup;
import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionRegistry;
import tools.mdsd.probdist.api.parser.ParameterParser;
import tools.mdsd.probdist.api.random.ISeedProvider;

public class UncertaintyBasedReliabilityPredictionJob extends SequentialBlackboardInteractingJob<MDSDBlackboard>
        implements ICompositeJob {

    public static class UncertaintyBasedReliabilityPredictionJobBuilder {

        private PCMSolverWorkflowRunConfiguration config = null;
        private String uncertaintyModel = "";
        private String explorationStrategy = "";
        private boolean applyATs = false;
        private ILaunchConfiguration launchConfig = null;
        private Optional<String> exportLocation = Optional.empty();

        private final IProbabilityDistributionRegistry<CategoricalValue> probabilityDistributionRegistry;
        private final IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory;
        private final ParameterParser parameterParser;
        private final IProbabilityDistributionRepositoryLookup probDistRepoLookup;
        private final Optional<ISeedProvider> seedProvider;

        public UncertaintyBasedReliabilityPredictionJobBuilder(
                IProbabilityDistributionRegistry<CategoricalValue> probabilityDistributionRegistry,
                IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
                ParameterParser parameterParser, IProbabilityDistributionRepositoryLookup probDistRepoLookup,
                Optional<ISeedProvider> seedProvider) {
            this.probabilityDistributionRegistry = probabilityDistributionRegistry;
            this.probabilityDistributionFactory = probabilityDistributionFactory;
            this.parameterParser = parameterParser;
            this.probDistRepoLookup = probDistRepoLookup;
            this.seedProvider = seedProvider;
        }

        public UncertaintyBasedReliabilityPredictionJobBuilder withConfig(PCMSolverWorkflowRunConfiguration config) {
            this.config = config;
            return this;
        }

        public UncertaintyBasedReliabilityPredictionJobBuilder andUncertaintyModel(String uncertaintyModel) {
            this.uncertaintyModel = uncertaintyModel;
            return this;
        }

        public UncertaintyBasedReliabilityPredictionJobBuilder andExplorationStrategy(String explorationStrategy) {
            this.explorationStrategy = explorationStrategy;
            return this;
        }

        public UncertaintyBasedReliabilityPredictionJobBuilder applyArchitecturalTemplates(
                ILaunchConfiguration launchConfig) {
            this.launchConfig = launchConfig;
            this.applyATs = true;
            return this;
        }

        public UncertaintyBasedReliabilityPredictionJobBuilder exportResults(String exportLocation) {
            this.exportLocation = Optional.ofNullable(exportLocation);
            return this;
        }

        public UncertaintyBasedReliabilityPredictionJob build() {
            var relPredictionJob = new UncertaintyBasedReliabilityPredictionJob();
            relPredictionJob.myBlackboard = new MDSDBlackboard();

            requireNonNull(config, "The reliability config must be specified.");
            var pcmInstanceBuilderJob = new PCMInstanceBuilderJob(config);
            relPredictionJob.addJob(pcmInstanceBuilderJob);
            relPredictionJob.addJob(new LoadModelIntoBlackboardJob(URI.createURI(uncertaintyModel),
                    LoadPCMModelsIntoBlackboardJob.PCM_MODELS_PARTITION_ID));
            relPredictionJob.addJob(new PrepareBlackboardJob());

            if (applyATs) {
                addATJob(relPredictionJob);
            }

            relPredictionJob.addJob(new ValidatePCMModelsJob(config));
            relPredictionJob.addJob(
                    new EventsTransformationJob(config.getStoragePluginID(), config.getEventMiddlewareFile(), false));

            Optional<URI> exportLocationURI;
            if (exportLocation.isEmpty()) {
                exportLocationURI = Optional.empty();
            } else {
                var uri = URI.createURI(exportLocation.get());
                exportLocationURI = Optional.of(uri);
            }
            relPredictionJob.addJob(new RootReliabilityPredictionRunJob(config, uncertaintyModel, explorationStrategy,
                    exportLocationURI, probabilityDistributionRegistry, probabilityDistributionFactory, parameterParser,
                    probDistRepoLookup, seedProvider));

            return relPredictionJob;
        }

        private void addATJob(UncertaintyBasedReliabilityPredictionJob rootJob) {
            var atJob = new RunATJob();
            var jobConfigurationBuilder = new ATExtensionConfigurationBuilder();
            try {
                atJob.setJobConfiguration(jobConfigurationBuilder.buildConfiguration(launchConfig.getAttributes()));
            } catch (CoreException e) {
                throw new RuntimeException("Launch configuration has no attributes.");
            }

            rootJob.addJob(atJob);
        }

    }

    public static UncertaintyBasedReliabilityPredictionJobBuilder newBuilder(
            IProbabilityDistributionRegistry<CategoricalValue> probabilityDistributionRegistry,
            IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
            ParameterParser parameterParser, IProbabilityDistributionRepositoryLookup probDistRepoLookup,
            Optional<ISeedProvider> seedProvider) {
        return new UncertaintyBasedReliabilityPredictionJobBuilder(probabilityDistributionRegistry,
                probabilityDistributionFactory, parameterParser, probDistRepoLookup, seedProvider);
    }

}
