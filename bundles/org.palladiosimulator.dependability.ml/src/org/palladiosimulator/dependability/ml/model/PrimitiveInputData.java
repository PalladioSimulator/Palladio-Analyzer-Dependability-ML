package org.palladiosimulator.dependability.ml.model;

import java.util.Map;

public class PrimitiveInputData<T> extends InputData {

	private final static String KEY = "primitive";
	
	private PrimitiveInputData(Object value) {
		super(Map.entry(KEY, value));
	}
	
	public static PrimitiveInputData<Number> numericalValue(Number value) {
		return new PrimitiveInputData<Number>(value);
	}
	
	public static PrimitiveInputData<String> categoricalValue(String value) {
		return new PrimitiveInputData<String>(value);
	}
	
	@SuppressWarnings("unchecked")
	public T getValue() {
		return (T) getValueOf(KEY);
	}
	
}
