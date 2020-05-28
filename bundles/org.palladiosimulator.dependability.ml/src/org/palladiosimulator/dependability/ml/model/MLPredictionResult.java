package org.palladiosimulator.dependability.ml.model;

public class MLPredictionResult {

	private final boolean isExpected;
	private final double confidence;
	
	public MLPredictionResult(boolean isExpected, double confidence) {
		this.isExpected = isExpected;
		this.confidence = confidence;
	}

	public boolean isExpectedResult() {
		return isExpected;
	}

	public double getPredictionConfidence() {
		return confidence;
	}

}
