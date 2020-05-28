package org.palladiosimulator.dependability.ml.model;

public class InputData<T> {

	private final T value;

	public InputData(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

}
