package org.palladiosimulator.dependability.ml.model;

import java.util.List;

import com.google.common.collect.Lists;

public class OutputData {
	
	private final List<String> CHARACTER_BLACKLIST = Lists.newArrayList("\"");
	
	private final String confidence;
	private final String prediction;
	
	public OutputData(String confidence, String prediction) {
		this.confidence = confidence;
		this.prediction = prediction;
	}

	public double getPredictionConfidence() {
		return Double.valueOf(removeUnwantedCharacters(confidence));
	}

	public String getPrediction() {
		return removeUnwantedCharacters(prediction);
	}
	
	private String removeUnwantedCharacters(String strToCleanUp) {
		var cleanedUpStr = strToCleanUp;
		for (String each : CHARACTER_BLACKLIST) {
			cleanedUpStr = strToCleanUp.replaceAll(each, "");
		}
		return cleanedUpStr;
	}	
}
