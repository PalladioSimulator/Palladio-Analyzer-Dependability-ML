package org.palladiosimulator.dependability.reliability.uncertainty.solver.tests;

import static java.util.Objects.requireNonNull;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.tests.BaseReliabilityPredictionTest.RELATIVE_ALLOCATION_TEST_MODEL_PATH;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.tests.BaseReliabilityPredictionTest.RELATIVE_UNCERTAINTY_TEST_MODEL_PATH;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.tests.BaseReliabilityPredictionTest.RELATIVE_USAGEMODEL_TEST_MODEL_PATH;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.tests.BaseReliabilityPredictionTest.makeAbsolute;

import java.util.List;
import java.util.function.BiConsumer;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.palladiosimulator.analyzer.workflow.ConstantsContainer;
import org.palladiosimulator.analyzer.workflow.blackboard.PCMResourceSetPartition;
import org.palladiosimulator.analyzer.workflow.jobs.LoadPCMModelsIntoBlackboardJob;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPrediction;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPredictionConfig;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.markov.ReliabilityPredictionResult;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.util.PCMInstanceBuilderJob;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;
import org.palladiosimulator.reliability.solver.pcm2markov.Pcm2MarkovStrategy;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import com.google.common.collect.Lists;

import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;

public class ReliabilityPredictionTestDefinition {

	public interface PredictionResultBasedAssertion
			extends BiConsumer<MarkovTransformationResult, ReliabilityPredictionResult> {

	}

	private final static String TEST_STRATEGY = "Brute force exploration strategy";

	private PCMSolverWorkflowRunConfiguration pcmRelConfig = null;
	private UncertaintyBasedReliabilityPredictionConfig uncertaintyPCMRelConfig = null;
	private MarkovTransformationResult pcmRelResult = null;
	private ReliabilityPredictionResult uncertaintyBasedResult = null;
	private List<PredictionResultBasedAssertion> assertions = Lists.newArrayList();

	private ReliabilityPredictionTestDefinition() {

	}

	public static ReliabilityPredictionTestDefinition createTest() {
		return new ReliabilityPredictionTestDefinition();
	}

	public ReliabilityPredictionTestDefinition givenDefaultRunConfigs() {
		this.pcmRelConfig = createDefaultPCMRelConfig();
		this.uncertaintyPCMRelConfig = createDefaultUncertaintyBasedPCMRelConfig();
		return this;
	}

	public ReliabilityPredictionTestDefinition givenCustomRunConfigs(PCMSolverWorkflowRunConfiguration pcmRelConfig,
			UncertaintyBasedReliabilityPredictionConfig uncertaintyPCMRelConfig) {
		this.pcmRelConfig = pcmRelConfig;
		this.uncertaintyPCMRelConfig = uncertaintyPCMRelConfig;
		return this;
	}

	public ReliabilityPredictionTestDefinition whenApplyingPCMRel() {
		requireNonNull(pcmRelConfig, "The PCM-Rel config must be specified first.");

		var pcmModel = loadPCMInstance();
		var solver = new Pcm2MarkovStrategy(pcmRelConfig);
		solver.transform(pcmModel);
		pcmRelResult = solver.getAllSolvedValues().get(0);

		return this;
	}

	public ReliabilityPredictionTestDefinition whenApplyingUncertaintyBasedPCMRel() {
		requireNonNull(uncertaintyPCMRelConfig, "The uncertainty based PCM-Rel config must be specified first.");

		uncertaintyBasedResult = UncertaintyBasedReliabilityPrediction.predict(uncertaintyPCMRelConfig);

		return this;
	}

	public ReliabilityPredictionTestDefinition whenApplyingUncertaintyBasedPCMRelGiven(
			List<UncertaintyState> uncertaintyStates) {
		requireNonNull(uncertaintyPCMRelConfig, "The uncertainty based PCM-Rel config must be specified first.");

		uncertaintyBasedResult = UncertaintyBasedReliabilityPrediction.predictGiven(uncertaintyStates,
				uncertaintyPCMRelConfig);

		return this;
	}

	public ReliabilityPredictionTestDefinition whenApplyingUncertaintyBasedPCMRelWith(
			UncertaintyBasedReliabilityPredictionConfig config) {
		requireNonNull(config, "The config must not be null.");

		uncertaintyBasedResult = UncertaintyBasedReliabilityPrediction.predict(config);

		return this;
	}

	public ReliabilityPredictionTestDefinition thenAssert(PredictionResultBasedAssertion assertion) {
		this.assertions.add(assertion);
		return this;
	}

	public ReliabilityPredictionTestDefinition thenAssert(PredictionResultBasedAssertion... assertions) {
		this.assertions.addAll(Lists.newArrayList(assertions));
		return this;
	}

	public void test() {
		assertions.forEach(assertion -> assertion.accept(pcmRelResult, uncertaintyBasedResult));
	}

	private UncertaintyBasedReliabilityPredictionConfig createDefaultUncertaintyBasedPCMRelConfig() {
		return UncertaintyBasedReliabilityPredictionConfig.newBuilder()
				.withReliabilityRunConfig(pcmRelConfig)
				.andUncertainties(getUncertaintyModelURI())
				.exploreStateSpaceWith(TEST_STRATEGY)
				.build();
	}

	private PCMSolverWorkflowRunConfiguration createDefaultPCMRelConfig() {
		var config = new PCMSolverWorkflowRunConfiguration();
		config.setReliabilityAnalysis(true);
		config.setPrintMarkovStatistics(false);
		config.setPrintMarkovSingleResults(false);
		config.setSensitivityModelEnabled(false);
		config.setSensitivityModelFileName(null);
		config.setSensitivityLogFileName(null);
		config.setDeleteTemporaryDataAfterAnalysis(true);
		config.setDistance(1.0);
		config.setDomainSize(32);
		config.setIterationOverPhysicalSystemStatesEnabled(true);
		config.setMarkovModelReductionEnabled(true);
		config.setNumberOfEvaluatedSystemStates(1);
		config.setNumberOfEvaluatedSystemStatesEnabled(false);
		config.setSolvingTimeLimitEnabled(false);
		config.setLogFile(null);
		config.setNumberOfEvaluatedSystemStatesEnabled(false);
		config.setNumberOfEvaluatedSystemStates(0);
		config.setNumberOfExactDecimalPlacesEnabled(false);
		config.setNumberOfExactDecimalPlaces(0);
		config.setSolvingTimeLimitEnabled(false);
		config.setMarkovModelStorageEnabled(false);
		config.setIterationOverPhysicalSystemStatesEnabled(true);
		config.setMarkovEvaluationMode("POINTSOFFAILURE");
		config.setSaveResultsToFileEnabled(false);
		config.setRMIMiddlewareFile(ConstantsContainer.DEFAULT_RMI_MIDDLEWARE_REPOSITORY_FILE);
		config.setEventMiddlewareFile(ConstantsContainer.DEFAULT_EVENT_MIDDLEWARE_FILE);
		config.setUsageModelFile(makeAbsolute(RELATIVE_USAGEMODEL_TEST_MODEL_PATH));
		config.setAllocationFiles(Lists.newArrayList(makeAbsolute(RELATIVE_ALLOCATION_TEST_MODEL_PATH)));
		return config;
	}

	private PCMInstance loadPCMInstance() {
		var pcmBuilderJob = new PCMInstanceBuilderJob(pcmRelConfig);
		try {
			pcmBuilderJob.execute(new NullProgressMonitor());
		} catch (JobFailedException | UserCanceledException e) {
			throw new RuntimeException("Something went wrong while building the PCM instance.", e);
		}
		return new PCMInstance((PCMResourceSetPartition) pcmBuilderJob.getBlackboard()
				.getPartition(LoadPCMModelsIntoBlackboardJob.PCM_MODELS_PARTITION_ID));
	}

	private String getUncertaintyModelURI() {
		return makeAbsolute(RELATIVE_UNCERTAINTY_TEST_MODEL_PATH);
	}

}
