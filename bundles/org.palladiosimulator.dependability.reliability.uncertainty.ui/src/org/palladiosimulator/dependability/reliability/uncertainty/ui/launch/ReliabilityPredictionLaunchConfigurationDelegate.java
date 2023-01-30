package org.palladiosimulator.dependability.reliability.uncertainty.ui.launch;

import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.APPLY_AT_ATTR;
import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.DEFAULT_ATTR;
import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.EXPLORATION_STRATEGY_ATTR;
import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.EXPORT_FILE_ATTR;
import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.EXPORT_RESULT_ATTR;
import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.UNCERTAINTY_MODEL_ATTR;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs.UncertaintyBasedReliabilityPredictionJob;
import org.palladiosimulator.reliability.solver.runconfig.PCMSolverReliabilityLaunchConfigurationDelegate;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import de.uka.ipd.sdq.workflow.jobs.IJob;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionRegistry;
import tools.mdsd.probdist.api.factory.ProbabilityDistributionFactory;

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
			
			ProbabilityDistributionFactory defaultProbabilityDistributionFactory = ProbabilityDistributionFactory.get();
			IProbabilityDistributionRegistry probabilityDistributionRegistry = defaultProbabilityDistributionFactory;
			IProbabilityDistributionFactory probabilityDistributionFactory = defaultProbabilityDistributionFactory;
			var jobBuilder = UncertaintyBasedReliabilityPredictionJob.newBuilder(probabilityDistributionFactory)
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
				
			return jobBuilder.build(probabilityDistributionRegistry);
		}
	}

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		new ReliabilityPredictionLaunchConfigurationInnerDelegate(configuration).launch(configuration, mode, launch,
				monitor);
	}

}
