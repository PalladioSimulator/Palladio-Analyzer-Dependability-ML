package org.palladiosimulator.dependability.ml.sensitivity.prediction;

public abstract class MLModel {
	
	public abstract MLPredictionResult makePrediction(InputData inputData);
}
