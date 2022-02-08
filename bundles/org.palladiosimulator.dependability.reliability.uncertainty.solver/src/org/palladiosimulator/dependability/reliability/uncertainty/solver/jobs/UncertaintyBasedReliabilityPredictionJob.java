package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import static java.util.Objects.requireNonNull;

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

public class UncertaintyBasedReliabilityPredictionJob extends SequentialBlackboardInteractingJob<MDSDBlackboard> implements ICompositeJob {

	public static class UncertaintyBasedReliabilityPredictionJobBuilder {
		
		private PCMSolverWorkflowRunConfiguration config = null;
		private String uncertaintyModel = "";
		private String explorationStrategy = "";
		private ILaunchConfiguration launchConfig = null;
		
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
		
		public UncertaintyBasedReliabilityPredictionJobBuilder applyArchitecturalTemplates(ILaunchConfiguration launchConfig) {
			this.launchConfig = launchConfig;
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
			
			var applyATs = launchConfig != null;
			if (applyATs) {
				addATJob(relPredictionJob);
			}
			
			relPredictionJob.addJob(new ValidatePCMModelsJob(config));
			relPredictionJob.addJob(new EventsTransformationJob(config.getStoragePluginID(), config.getEventMiddlewareFile(), false));
			relPredictionJob.addJob(new RootReliabilityPredictionRunJob(config, uncertaintyModel, explorationStrategy));
			
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
	
	public static UncertaintyBasedReliabilityPredictionJobBuilder newBuilder() {
		return new UncertaintyBasedReliabilityPredictionJobBuilder();
	}
	
}
