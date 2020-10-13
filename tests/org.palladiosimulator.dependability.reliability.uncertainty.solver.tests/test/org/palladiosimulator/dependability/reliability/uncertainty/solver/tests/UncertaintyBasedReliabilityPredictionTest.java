package org.palladiosimulator.dependability.reliability.uncertainty.solver.tests;

import java.nio.file.Paths;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.palladiosimulator.analyzer.workflow.ConstantsContainer;
import org.palladiosimulator.analyzer.workflow.blackboard.PCMResourceSetPartition;
import org.palladiosimulator.analyzer.workflow.jobs.LoadPCMModelsIntoBlackboardJob;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.util.PCMInstanceBuilderJob;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;
import org.palladiosimulator.reliability.solver.pcm2markov.Pcm2MarkovStrategy;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import com.google.common.collect.Lists;

import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;
import tools.mdsd.library.standalone.initialization.StandaloneInitializationException;
import tools.mdsd.library.standalone.initialization.StandaloneInitializerBuilder;

public abstract class UncertaintyBasedReliabilityPredictionTest {

	private static final String PROJECT_NAME_TEST = "org.palladiosimulator.dependability.reliability.uncertainty.solver.tests";
	private static final String RELATIVE_UNCERTAINTY_TEST_MODEL_PATH = Paths.get("models", "UncertaintyModelRepository.uncertainty").toString();
	private static final String RELATIVE_ALLOCATION_TEST_MODEL_PATH = Paths.get("models", "default.allocation").toString();
	private static final String RELATIVE_USAGEMODEL_TEST_MODEL_PATH = Paths.get("models", "default.usagemodel").toString();

	protected static PCMSolverWorkflowRunConfiguration runConfig = null;

	private PCMInstance testPCMInstance = null;

	@BeforeAll
	public static void globalSetUp() throws StandaloneInitializationException {
		var standaloneInitializer = StandaloneInitializerBuilder.builder()
				.registerProjectURI(UncertaintyBasedReliabilityPredictionTest.class, PROJECT_NAME_TEST)
				.build();
		standaloneInitializer.init();

		runConfig = createDefaultRunConfig();
	}

	@BeforeEach
	public void setUpBeforeEachTest() throws Exception {
		testPCMInstance = loadPCMInstance();
	}

	protected String getUncertaintyModelURI() {
		return makePlatformResourceURI(RELATIVE_UNCERTAINTY_TEST_MODEL_PATH);
	}

	protected PCMInstance givenAPCMTestInstance() {
		return testPCMInstance;
	}

	protected List<MarkovTransformationResult> whenApplyingClassicReliabilityPredictionTo(PCMInstance pcmModel) {
		var solver = new Pcm2MarkovStrategy(runConfig);
		solver.transform(pcmModel);
		return solver.getAllSolvedValues();
	}

	private static PCMSolverWorkflowRunConfiguration createDefaultRunConfig() {
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
		config.setUsageModelFile(makePlatformResourceURI(RELATIVE_USAGEMODEL_TEST_MODEL_PATH));
		config.setAllocationFiles(Lists.newArrayList(makePlatformResourceURI(RELATIVE_ALLOCATION_TEST_MODEL_PATH)));
		return config;
	}

	private static String makePlatformResourceURI(String relativeTestModelPath) {
		var pathName = Paths.get(PROJECT_NAME_TEST, relativeTestModelPath).toString();
		return URI.createPlatformResourceURI(pathName, true).toString();
	}

	private PCMInstance loadPCMInstance() {
		var pcmBuilderJob = new PCMInstanceBuilderJob(runConfig);
		try {
			pcmBuilderJob.execute(new NullProgressMonitor());
		} catch (JobFailedException | UserCanceledException e) {
			throw new RuntimeException("Something went wrong while building the PCM instance.", e);
		}
		return new PCMInstance((PCMResourceSetPartition) pcmBuilderJob.getBlackboard()
				.getPartition(LoadPCMModelsIntoBlackboardJob.PCM_MODELS_PARTITION_ID));
	}

}
