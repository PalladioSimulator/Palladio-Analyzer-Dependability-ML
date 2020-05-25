package org.palladiosimulator.dependability.ml.sensitivity.transformation;

import static java.util.Objects.hash;

import tools.mdsd.probdist.api.entity.CategoricalValue;

public class MeasurableProperty {

	private final String name;
	private final CategoricalValue measuredValue;

	public MeasurableProperty(String name, CategoricalValue measuredValue) {
		this.name = name;
		this.measuredValue = measuredValue;
	}

	public String getName() {
		return name;
	}

	public CategoricalValue getMeasuredValue() {
		return measuredValue;
	}

	@Override
	public boolean equals(Object other) {
		if (MeasurableProperty.class.isInstance(other) == false) {
			return false;
		}

		MeasurableProperty otherProperty = (MeasurableProperty) other;
		return Boolean.logicalAnd(otherProperty.getName().equals(name),
				otherProperty.getMeasuredValue().equals(getMeasuredValue()));
	}

	@Override
	public int hashCode() {
		return hash(name, measuredValue.get());
	}
	
	@Override
	public String toString() {
		return String.format("(%1s:%2s)", name, measuredValue.get());
	}

}
