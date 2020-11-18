package org.palladiosimulator.dependability.ml.sensitivity.transformation;

import static java.util.Objects.hash;

import tools.mdsd.probdist.api.entity.CategoricalValue;

public abstract class SensitivityProperty {

	private final static String ID_VALUE_DELIMITER = "=";

	private final String id;
	private final CategoricalValue value;

	protected SensitivityProperty(String id, CategoricalValue value) {
		this.id = id;
		this.value = normalizeValueIfNecessary(value);
	}

	private CategoricalValue normalizeValueIfNecessary(CategoricalValue value) {
		return isAlreadyNormalized(value) ? value : enrichWithId(value);
	}

	private boolean isAlreadyNormalized(CategoricalValue value) {
		var components = value.get().split(ID_VALUE_DELIMITER);
		if (components.length != 2) {
			return false;
		}
		
		var originalValue = new StringBuilder(components[1])
				.deleteCharAt(components[1].length() - 1)
				.toString();
		return constructEnrichedValue(originalValue).equals(value.get());
	}

	private CategoricalValue enrichWithId(CategoricalValue value) {
		return CategoricalValue.create(constructEnrichedValue(value.get()));
	}

	private String constructEnrichedValue(String value) {
		return String.format("(%s)", getId().concat(ID_VALUE_DELIMITER).concat(value));
	}

	public CategoricalValue getValue() {
		return value;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object other) {
		if (SensitivityProperty.class.isInstance(other) == false) {
			return false;
		}

		var otherProperty = (SensitivityProperty) other;
		return otherProperty.value.get().equals(this.value.get());
	}

	@Override
	public int hashCode() {
		return hash(value.get());
	}

	@Override
	public String toString() {
		return getValue().toString();
	}

}
