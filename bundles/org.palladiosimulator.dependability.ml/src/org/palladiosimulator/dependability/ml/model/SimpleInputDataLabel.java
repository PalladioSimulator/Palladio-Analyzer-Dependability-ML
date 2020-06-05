package org.palladiosimulator.dependability.ml.model;

public class SimpleInputDataLabel<T> extends InputDataLabel {

	private SimpleInputDataLabel(Object label) {
		super(label);
	}
	
	public static SimpleInputDataLabel<Number> numericalLabel(Number value) {
		return new SimpleInputDataLabel<Number>(value);
	}
	
	public static SimpleInputDataLabel<String> categoricalLabel(String value) {
		return new SimpleInputDataLabel<String>(value);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getLabel() {
		return (T) super.getLabel();
	}

}
