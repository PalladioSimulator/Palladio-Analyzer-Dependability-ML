package org.palladiosimulator.dependability.ml.model;

import java.util.List;

import com.google.common.collect.Lists;

public class MLPredictionResult {

	private final boolean isExpected;
	private final List<OutputData> results;
	
	public MLPredictionResult(boolean isExpected) {
		this.isExpected = isExpected;
		this.results = Lists.newArrayList();
	}

	public boolean isExpectedResult() {
		return isExpected;
	}

	public List<OutputData> getPredictions() {
		return results;
	}

}
