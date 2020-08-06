package org.palladiosimulator.dependability.reliability.uncertainty.solver.api;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.palladiosimulator.analyzer.workflow.blackboard.PCMResourceSetPartition;
import org.palladiosimulator.analyzer.workflow.jobs.EventsTransformationJob;
import org.palladiosimulator.analyzer.workflow.jobs.LoadMiddlewareConfigurationIntoBlackboardJob;
import org.palladiosimulator.analyzer.workflow.jobs.LoadPCMModelsIntoBlackboardJob;
import org.palladiosimulator.analyzer.workflow.jobs.StoreAllPCMModelsJob;
import org.palladiosimulator.analyzer.workflow.jobs.ValidatePCMModelsJob;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFailureTypeRepository;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.markov.StateSpaceExplorationStrategy;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import de.uka.ipd.sdq.workflow.jobs.ICompositeJob;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.SequentialBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;
import de.uka.ipd.sdq.workflow.mdsd.blackboard.MDSDBlackboard;

public class UncertaintyBasedReliabilityPredictionConfig {

	private static class PCMInstanceBuilderJob extends SequentialBlackboardInteractingJob<MDSDBlackboard>
			implements ICompositeJob {

		public PCMInstanceBuilderJob(PCMSolverWorkflowRunConfiguration config) {
			super(false);

			this.myBlackboard = new MDSDBlackboard();

			this.addJob(new LoadPCMModelsIntoBlackboardJob(config));
			this.addJob(new LoadMiddlewareConfigurationIntoBlackboardJob(config));
			this.addJob(new ValidatePCMModelsJob(config));
			this.add(new EventsTransformationJob(config.getStoragePluginID(), config.getEventMiddlewareFile(), false));
			this.add(new StoreAllPCMModelsJob(config));
		}
	}

	public static class UncertaintyBasedReliabilityPredictionConfigBuilder {

		private PCMSolverWorkflowRunConfiguration runConfig;
		private String explorationStrategyName;
		private String uncertaintyRepository;

		private UncertaintyBasedReliabilityPredictionConfigBuilder() {

		}

		public UncertaintyBasedReliabilityPredictionConfigBuilder withReliabilityRunConfig(
				PCMSolverWorkflowRunConfiguration runConfig) {
			this.runConfig = runConfig;
			return this;
		}

		public UncertaintyBasedReliabilityPredictionConfigBuilder exploreStateSpaceWith(String strategyName) {
			this.explorationStrategyName = strategyName;
			return this;
		}

		public UncertaintyBasedReliabilityPredictionConfigBuilder andUncertainties(String uncertaintyRepository) {
			this.uncertaintyRepository = uncertaintyRepository;
			return this;
		}

		public UncertaintyBasedReliabilityPredictionConfig build() {
			requireNonNull(runConfig, "The reliability run config must not be null.");
			requireValidString(explorationStrategyName);
			requireValidString(uncertaintyRepository);

			var strategy = UncertaintyBasedReliabilityPredictor.findStrategyWith(explorationStrategyName).orElse(null);
			var uncertainties = loadModel().getUncertaintyInducedFailureTypes();
			var pcmInstance = buildPCMInstance();
			return new UncertaintyBasedReliabilityPredictionConfig(runConfig, strategy, uncertainties, pcmInstance);
		}

		private PCMInstance buildPCMInstance() {
			var pcmBuilderJob = new PCMInstanceBuilderJob(runConfig);
			try {
				pcmBuilderJob.execute(new NullProgressMonitor());
			} catch (JobFailedException | UserCanceledException e) {
				throw new RuntimeException("Something went wrong while building the PCM instance.", e);
			}
			return new PCMInstance((PCMResourceSetPartition) pcmBuilderJob.getBlackboard()
					.getPartition(LoadPCMModelsIntoBlackboardJob.PCM_MODELS_PARTITION_ID));
		}

		private UncertaintyFailureTypeRepository loadModel() {
			ResourceSet rs = new ResourceSetImpl();
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
			rs.getPackageRegistry().put(UncertaintyPackage.eNS_URI, UncertaintyPackage.eINSTANCE);
			
			var resource = rs.getResource(createModelURI(), true);
			EcoreUtil.resolveAll(rs);
			
			return (UncertaintyFailureTypeRepository) resource.getContents().get(0);
		}

		private URI createModelURI() {
			return URI.createURI(uncertaintyRepository);
		}

		private void requireValidString(String value) {
			requireNonNull(value, String.format("%s must not be null.", value));
			if (value.isBlank()) {
				throw new IllegalArgumentException(String.format("%s must not be blank.", value));
			}
		}

	}

	private final PCMSolverWorkflowRunConfiguration runConfig;
	private final Optional<StateSpaceExplorationStrategy> explorationStrategy;
	private final List<UncertaintyInducedFailureType> uncertainties;
	private final PCMInstance pcm;

	public UncertaintyBasedReliabilityPredictionConfig(PCMSolverWorkflowRunConfiguration runConfig,
			StateSpaceExplorationStrategy explorationStrategy, List<UncertaintyInducedFailureType> uncertainties,
			PCMInstance pcm) {
		this.runConfig = runConfig;
		this.explorationStrategy = Optional.ofNullable(explorationStrategy);
		this.uncertainties = uncertainties;
		this.pcm = pcm;
	}

	public static UncertaintyBasedReliabilityPredictionConfigBuilder newBuilder() {
		return new UncertaintyBasedReliabilityPredictionConfigBuilder();
	}

	public PCMSolverWorkflowRunConfiguration getRunConfig() {
		return runConfig;
	}

	public Optional<StateSpaceExplorationStrategy> getStateSpaceExplorationStrategy() {
		return explorationStrategy;
	}

	public List<UncertaintyInducedFailureType> getUncertainties() {
		return uncertainties;
	}

	public PCMInstance getPCMInstance() {
		return pcm;
	}

}
