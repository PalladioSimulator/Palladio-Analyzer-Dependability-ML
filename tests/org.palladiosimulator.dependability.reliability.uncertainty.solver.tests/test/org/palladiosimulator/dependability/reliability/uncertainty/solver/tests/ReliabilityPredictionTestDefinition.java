package org.palladiosimulator.dependability.reliability.uncertainty.solver.tests;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.tests.BaseReliabilityPredictionTest.RELATIVE_ALLOCATION_TEST_MODEL_PATH;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.tests.BaseReliabilityPredictionTest.RELATIVE_UNCERTAINTY_TEST_MODEL_PATH;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.tests.BaseReliabilityPredictionTest.RELATIVE_USAGEMODEL_TEST_MODEL_PATH;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.tests.BaseReliabilityPredictionTest.makeAbsolute;

import java.util.List;
import java.util.function.BiConsumer;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.palladiosimulator.analyzer.workflow.ConstantsContainer;
import org.palladiosimulator.analyzer.workflow.blackboard.PCMResourceSetPartition;
import org.palladiosimulator.analyzer.workflow.jobs.EventsTransformationJob;
import org.palladiosimulator.analyzer.workflow.jobs.LoadModelIntoBlackboardJob;
import org.palladiosimulator.analyzer.workflow.jobs.LoadPCMModelsIntoBlackboardJob;
import org.palladiosimulator.analyzer.workflow.jobs.ValidatePCMModelsJob;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPrediction;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPredictionConfig;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs.PCMInstanceBuilderJob;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs.ReliabilityPredictionContext;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs.ReliabilityPredictionExecutionJob;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs.ReliabilityPredictionRunJob;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs.UncertaintyBasedReliabilityPredictionJob;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.markov.ReliabilityPredictionResult;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.tests.util.PCMTestInstanceBuilderJob;
import org.palladiosimulator.experimentautomation.application.jobs.PrepareBlackboardJob;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;
import org.palladiosimulator.reliability.solver.pcm2markov.Pcm2MarkovStrategy;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import com.google.common.collect.Lists;

import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;
import de.uka.ipd.sdq.workflow.mdsd.blackboard.MDSDBlackboard;
import tools.mdsd.probdist.api.apache.util.IProbabilityDistributionRepositoryLookup;
import tools.mdsd.probdist.api.apache.util.ProbabilityDistributionRepositoryLookup;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionRegistry;
import tools.mdsd.probdist.api.factory.ProbabilityDistributionFactory;
import tools.mdsd.probdist.api.parser.DefaultParameterParser;
import tools.mdsd.probdist.api.parser.ParameterParser;
import tools.mdsd.probdist.distributiontype.ProbabilityDistributionRepository;
import tools.mdsd.probdist.model.basic.loader.BasicDistributionTypesLoader;

public class ReliabilityPredictionTestDefinition {

	private class ReliabilityPredictionResultJob extends ReliabilityPredictionRunJob {

		public ReliabilityPredictionResultJob(ReliabilityPredictionContext context) {
			super(context);
		}
		
		public ReliabilityPredictionResult getPredictionResult() {
			return context.result;
		}
		
	}
	
	public interface PredictionResultBasedAssertion
			extends BiConsumer<MarkovTransformationResult, ReliabilityPredictionResult> {

	}

	private final static String TEST_STRATEGY = "Brute force exploration strategy";

	private PCMSolverWorkflowRunConfiguration pcmRelConfig = null;
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
		return this;
	}

	public ReliabilityPredictionTestDefinition givenCustomRunConfigs(PCMSolverWorkflowRunConfiguration pcmRelConfig) {
		this.pcmRelConfig = pcmRelConfig;
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
		var relPredictionJob = new UncertaintyBasedReliabilityPredictionJob();
		relPredictionJob.setBlackboard(new MDSDBlackboard());
		
		requireNonNull(pcmRelConfig, "The reliability config must be specified.");
		var pcmInstanceBuilderJob = new PCMInstanceBuilderJob(pcmRelConfig);
		relPredictionJob.addJob(pcmInstanceBuilderJob);
		relPredictionJob.addJob(new LoadModelIntoBlackboardJob(URI.createURI(getUncertaintyModelURI()), 
				LoadPCMModelsIntoBlackboardJob.PCM_MODELS_PARTITION_ID));
		relPredictionJob.addJob(new PrepareBlackboardJob());
		relPredictionJob.addJob(new ValidatePCMModelsJob(pcmRelConfig));
		relPredictionJob.addJob(new EventsTransformationJob(pcmRelConfig.getStoragePluginID(), pcmRelConfig.getEventMiddlewareFile(), false));
		
		var context = new ReliabilityPredictionContext(pcmRelConfig, getUncertaintyModelURI(), TEST_STRATEGY);
		
		ParameterParser parameterParser = new DefaultParameterParser();
		ProbabilityDistributionFactory defaultProbabilityDistributionFactory = new ProbabilityDistributionFactory();
		IProbabilityDistributionRegistry probabilityDistributionRegistry = defaultProbabilityDistributionFactory;
		IProbabilityDistributionFactory probabilityDistributionFactory = defaultProbabilityDistributionFactory;
		
        ProbabilityDistributionRepository probabilityDistributionRepository = BasicDistributionTypesLoader.loadRepository();
		IProbabilityDistributionRepositoryLookup probDistRepoLookup = new ProbabilityDistributionRepositoryLookup(probabilityDistributionRepository);
		relPredictionJob.add(new ReliabilityPredictionExecutionJob(context, probabilityDistributionRegistry, probabilityDistributionFactory, parameterParser, probDistRepoLookup));
		
		var resultJob = new ReliabilityPredictionResultJob(context);
		relPredictionJob.add(resultJob);
		
		try {
			relPredictionJob.execute(new NullProgressMonitor());
		} catch (Exception e) {
			fail("Something went wrong during the uncertainty based reliability job", e);
		}
		
		uncertaintyBasedResult = resultJob.getPredictionResult();
		
		return this;
		
	}

	public ReliabilityPredictionTestDefinition whenApplyingUncertaintyBasedPCMRelWith(
			UncertaintyBasedReliabilityPredictionConfig config, IProbabilityDistributionRegistry probabilityDistributionRegistry, IProbabilityDistributionFactory probabilityDistributionFactory, ParameterParser parameterParser, IProbabilityDistributionRepositoryLookup probDistRepoLookup) {
		requireNonNull(config, "The config must not be null.");

		uncertaintyBasedResult = UncertaintyBasedReliabilityPrediction.predict(config, probabilityDistributionRegistry, probabilityDistributionFactory, parameterParser, probDistRepoLookup);

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
		return new PCMInstance((PCMResourceSetPartition) loadPcmModel()
				.getPartition(LoadPCMModelsIntoBlackboardJob.PCM_MODELS_PARTITION_ID));
	}
	
	private MDSDBlackboard loadPcmModel() {
		var pcmBuilderJob = new PCMTestInstanceBuilderJob(pcmRelConfig);
		try {
			pcmBuilderJob.execute(new NullProgressMonitor());
		} catch (JobFailedException | UserCanceledException e) {
			throw new RuntimeException("Something went wrong while building the PCM instance.", e);
		}
		return pcmBuilderJob.getBlackboard();
	}

	private String getUncertaintyModelURI() {
		return makeAbsolute(RELATIVE_UNCERTAINTY_TEST_MODEL_PATH);
	}

}
