package org.palladiosimulator.dependability.reliability.uncertainty.solver.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPrediction;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPredictionConfig;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.markov.ReliabilityPredictionResult;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;
import org.palladiosimulator.solver.models.PCMInstance;

class MarginalizingUncertaintiesTest extends UncertaintyBasedReliabilityPredictionTest {

	private final static double DELTA = 0.01;
	private final static String TEST_STRATEGY = "Brute force exploration strategy";

	@Test
	void test() {
		var testInstance = givenAPCMTestInstance();
		var nonUncertaintyBasedResult = whenApplyingClassicReliabilityPredictionTo(testInstance);
		var uncertaintyBasedResult = andWhenApplyingUncertaintyBasedReliabilityPredictionTo(testInstance);
		thenAssertEqualSuccessProbabilities(nonUncertaintyBasedResult.get(0), uncertaintyBasedResult);
	}

	private ReliabilityPredictionResult andWhenApplyingUncertaintyBasedReliabilityPredictionTo(
			PCMInstance testInstance) {
		var config = buildUncertaintyBasedReliabilityPredictionConfig();
		return UncertaintyBasedReliabilityPrediction.predict(config);
	}

	private void thenAssertEqualSuccessProbabilities(MarkovTransformationResult nonUncertaintyBasedResult,
			ReliabilityPredictionResult uncertaintyBasedResult) {
		var usageScenario = nonUncertaintyBasedResult.getScenario();
		
		var expected = nonUncertaintyBasedResult.getSuccessProbability();
		var actual = uncertaintyBasedResult.getProbabilityOfSuccessGiven(usageScenario);
		assertEquals(expected, actual, DELTA);
	}

	private UncertaintyBasedReliabilityPredictionConfig buildUncertaintyBasedReliabilityPredictionConfig() {
		return UncertaintyBasedReliabilityPredictionConfig.newBuilder()
				.withReliabilityRunConfig(runConfig)
				.andUncertainties(getUncertaintyModelURI())
				.exploreStateSpaceWith(TEST_STRATEGY)
				.build();
	}

}
