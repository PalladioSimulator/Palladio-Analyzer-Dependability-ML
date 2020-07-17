package org.palladiosimulator.dependability.reliability.uncertainty.ui.launch;

import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.DEFAULT_ATTR;
import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.EXPLORATION_STRATEGY_ATTR;
import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.UNCERTAINTY_MODEL_ATTR;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPredictionConfig;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPredictor;
import org.palladiosimulator.reliability.solver.runconfig.PCMSolverReliabilityLaunchConfigurationDelegate;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import de.uka.ipd.sdq.workflow.jobs.CleanupFailedException;
import de.uka.ipd.sdq.workflow.jobs.IJob;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;

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
			return new IJob() {

				@Override
				public String getName() {
					return "ReliabilityPredictionExecution";
				}

				@Override
				public void execute(IProgressMonitor monitor) throws JobFailedException, UserCanceledException {
					String uncertaintyModelLocation = "";
					String explorationStrategy = "";
					try {
						uncertaintyModelLocation = launchConfig.getAttribute(UNCERTAINTY_MODEL_ATTR, DEFAULT_ATTR);
						explorationStrategy = launchConfig.getAttribute(EXPLORATION_STRATEGY_ATTR, DEFAULT_ATTR);
					} catch (CoreException e) {
						throw new RuntimeException(String.format("The attributes %1s and %2s are not available.",
								UNCERTAINTY_MODEL_ATTR, EXPLORATION_STRATEGY_ATTR));
					}

					var reliabilityConfig = UncertaintyBasedReliabilityPredictionConfig.newBuilder()
							.withReliabilityRunConfig(config).andUncertainties(uncertaintyModelLocation)
							.exploreStateSpaceWith(explorationStrategy)
							.build();
					
					UncertaintyBasedReliabilityPredictor.predict(reliabilityConfig);
				}

				@Override
				public void cleanup(IProgressMonitor monitor) throws CleanupFailedException {

				}
			};
		}

	}

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		new ReliabilityPredictionLaunchConfigurationInnerDelegate(configuration).launch(configuration, mode, launch,
				monitor);
	}

}
