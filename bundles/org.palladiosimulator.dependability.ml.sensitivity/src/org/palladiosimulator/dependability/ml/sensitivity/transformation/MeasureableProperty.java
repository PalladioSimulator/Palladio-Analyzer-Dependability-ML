package org.palladiosimulator.dependability.ml.sensitivity.transformation;

public class MeasureableProperty {

	private final String name;

	public MeasureableProperty(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object other) {
		if (MeasureableProperty.class.isInstance(other) == false) {
			return false;
		}

		return MeasureableProperty.class.cast(other).getName().equals(name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
