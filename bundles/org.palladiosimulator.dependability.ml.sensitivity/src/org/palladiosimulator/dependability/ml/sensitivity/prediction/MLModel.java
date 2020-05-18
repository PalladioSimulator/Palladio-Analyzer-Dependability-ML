package org.palladiosimulator.dependability.ml.sensitivity.prediction;

import org.palladiosimulator.dependability.ml.sensitivity.transformation.InputData;

public abstract class MLModel {

	public static class PredictionResult {
		
	}
	
	public abstract PredictionResult predict(InputData inputData);
}
