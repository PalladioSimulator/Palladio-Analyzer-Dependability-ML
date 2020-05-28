package org.palladiosimulator.dependability.ml.model;

public class InputDataLabel<T> {

	private final T label;

	public InputDataLabel(T label) {
		this.label = label;
	}

	public T getLabel() {
		return label;
	}
}
