package org.palladiosimulator.dependability.reliability.uncertainty.ui.launch;

import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.APPLY_AT_ATTR;
import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.DEFAULT_ATTR;
import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.EXPLORATION_STRATEGY_ATTR;
import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.EXPORT_FILE_ATTR;
import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.EXPORT_RESULT_ATTR;
import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.UNCERTAINTY_MODEL_ATTR;

import java.util.Optional;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs.UncertaintyBasedReliabilityPredictionJob;
import org.palladiosimulator.reliability.solver.runconfig.PCMSolverReliabilityLaunchConfigurationDelegate;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import de.uka.ipd.sdq.workflow.jobs.IJob;
import tools.mdsd.probdist.api.apache.util.IProbabilityDistributionRepositoryLookup;
import tools.mdsd.probdist.api.apache.util.ProbabilityDistributionRepositoryLookup;
import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionRegistry;
import tools.mdsd.probdist.api.factory.ProbabilityDistributionFactory;
import tools.mdsd.probdist.api.parser.DefaultParameterParser;
import tools.mdsd.probdist.api.parser.ParameterParser;
import tools.mdsd.probdist.distributiontype.ProbabilityDistributionRepository;
import tools.mdsd.probdist.model.basic.loader.BasicDistributionTypesLoader;

public class ReliabilityPredictionLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

    private class ReliabilityPredictionLaunchConfigurationInnerDelegate
            extends PCMSolverReliabilityLaunchConfigurationDelegate {

        private final ILaunchConfiguration launchConfig;

        public ReliabilityPredictionLaunchConfigurationInnerDelegate(ILaunchConfiguration launchConfig) {
            this.launchConfig = launchConfig;
        }

        @Override
        protected IJob createWorkflowJob(PCMSolverWorkflowRunConfiguration config, ILaunch launch)
                throws CoreException {

            String uncertaintyModelLocation = "";
            String explorationStrategy = "";
            try {
                uncertaintyModelLocation = launchConfig.getAttribute(UNCERTAINTY_MODEL_ATTR, DEFAULT_ATTR);
                explorationStrategy = launchConfig.getAttribute(EXPLORATION_STRATEGY_ATTR, DEFAULT_ATTR);
            } catch (CoreException e) {
                throw new RuntimeException(String.format("The attributes %1s and %2s are not available.",
                        UNCERTAINTY_MODEL_ATTR, EXPLORATION_STRATEGY_ATTR));
            }

            ParameterParser parameterParser = new DefaultParameterParser();
            ProbabilityDistributionFactory defaultProbabilityDistributionFactory = new ProbabilityDistributionFactory(
                    Optional.empty());
            IProbabilityDistributionRegistry<CategoricalValue> probabilityDistributionRegistry = defaultProbabilityDistributionFactory;
            IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory = defaultProbabilityDistributionFactory;

            ProbabilityDistributionRepository probabilityDistributionRepository = BasicDistributionTypesLoader
                .loadRepository();
            IProbabilityDistributionRepositoryLookup probDistRepoLookup = new ProbabilityDistributionRepositoryLookup(
                    probabilityDistributionRepository);
            var jobBuilder = UncertaintyBasedReliabilityPredictionJob
                .newBuilder(probabilityDistributionRegistry, probabilityDistributionFactory, parameterParser,
                        probDistRepoLookup)
                .withConfig(config)
                .andUncertaintyModel(uncertaintyModelLocation)
                .andExplorationStrategy(explorationStrategy);

            var applyATs = launchConfig.getAttribute(APPLY_AT_ATTR, false);
            if (applyATs) {
                jobBuilder.applyArchitecturalTemplates(launchConfig);
            }

            var exportResults = launchConfig.getAttribute(EXPORT_RESULT_ATTR, false);
            if (exportResults) {
                var exportLocation = launchConfig.getAttribute(EXPORT_FILE_ATTR, DEFAULT_ATTR);
                jobBuilder.exportResults(exportLocation);
            }

            return jobBuilder.build();
        }
    }

    @Override
    public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
            throws CoreException {
        new ReliabilityPredictionLaunchConfigurationInnerDelegate(configuration).launch(configuration, mode, launch,
                monitor);
    }

}
