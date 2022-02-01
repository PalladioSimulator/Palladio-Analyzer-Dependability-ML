package org.palladiosimulator.dependability.reliability.uncertainty.solver.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.tests.ReliabilityPredictionTestDefinition.PredictionResultBasedAssertion;

class MarginalizingUncertaintiesTest extends BaseReliabilityPredictionTest {

	private final static double DELTA = 0.01;

	@Test
	void test() {
		ReliabilityPredictionTestDefinition.createTest()
			.givenDefaultRunConfigs()
			.whenApplyingPCMRel()
			.whenApplyingUncertaintyBasedPCMRel()
			.thenAssert(equalSuccessProbabilities())
			.test();
	}
	
	private PredictionResultBasedAssertion equalSuccessProbabilities() {
		return (pcmRelResult, uncertaintyBasedResult) -> {
			var usageScenario = pcmRelResult.getScenario();
			
			var expected = pcmRelResult.getSuccessProbability();
			var actual = uncertaintyBasedResult.getProbabilityOfSuccess(usageScenario);
			assertEquals(expected, actual, DELTA);
		};
	}

}
