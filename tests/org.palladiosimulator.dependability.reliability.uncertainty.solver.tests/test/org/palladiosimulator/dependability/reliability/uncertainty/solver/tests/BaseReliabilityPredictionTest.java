package org.palladiosimulator.dependability.reliability.uncertainty.solver.tests;

import java.nio.file.Paths;

import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.BeforeAll;

import tools.mdsd.library.standalone.initialization.StandaloneInitializationException;
import tools.mdsd.library.standalone.initialization.StandaloneInitializerBuilder;

public abstract class BaseReliabilityPredictionTest {

	public static final String PROJECT_NAME_TEST = "org.palladiosimulator.dependability.reliability.uncertainty.solver.tests";
	public static final String RELATIVE_UNCERTAINTY_TEST_MODEL_PATH = Paths.get("models", "UncertaintyModelRepository.uncertainty").toString();
	public static final String RELATIVE_ALLOCATION_TEST_MODEL_PATH = Paths.get("models", "default.allocation").toString();
	public static final String RELATIVE_USAGEMODEL_TEST_MODEL_PATH = Paths.get("models", "default.usagemodel").toString();

	@BeforeAll
	public static void globalSetUp() throws StandaloneInitializationException {
		var standaloneInitializer = StandaloneInitializerBuilder.builder()
				.registerProjectURI(BaseReliabilityPredictionTest.class, PROJECT_NAME_TEST)
				.build();
		standaloneInitializer.init();
	}
	
	public static String makePlatformResourceURI(String relativeTestModelPath) {
		var pathName = Paths.get(PROJECT_NAME_TEST, relativeTestModelPath).toString();
		return URI.createPlatformResourceURI(pathName, true).toString();
	}

}
