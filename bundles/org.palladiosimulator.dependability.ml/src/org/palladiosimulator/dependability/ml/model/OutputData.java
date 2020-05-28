package org.palladiosimulator.dependability.ml.model;

public class OutputData<T> {

	private final T result;
	private final double confidence;
	
	public OutputData(T result, double confidence) {
		this.result = result;
		this.confidence = confidence;
	}

	public T getResult() {
		return result;
	}

	public double getConfidence() {
		return confidence;
	}
	
}
