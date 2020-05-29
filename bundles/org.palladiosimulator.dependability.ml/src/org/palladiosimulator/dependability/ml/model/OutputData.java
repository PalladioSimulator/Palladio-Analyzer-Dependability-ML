package org.palladiosimulator.dependability.ml.model;

public class OutputData {
	
	private final String confidence;
	private final String prediction;
	
	public OutputData(String confidence, String prediction) {
		this.confidence = confidence;
		this.prediction = prediction;
	}

	public double getPredictionConfidence() {
		return Double.valueOf(confidence);
	}

	public String getPrediction() {
		return prediction;
	}
}
