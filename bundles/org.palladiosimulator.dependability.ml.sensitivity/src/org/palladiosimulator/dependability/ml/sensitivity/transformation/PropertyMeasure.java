package org.palladiosimulator.dependability.ml.sensitivity.transformation;

import static java.util.Objects.hash;

import java.util.Set;

import org.palladiosimulator.dependability.ml.model.InputData;
import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;

import tools.mdsd.probdist.api.entity.CategoricalValue;

public abstract class PropertyMeasure {

	public class MeasurableProperty {

		private final CategoricalValue measuredValue;

		public MeasurableProperty(CategoricalValue measuredValue) {
			this.measuredValue = measuredValue;
		}

		public String getId() {
			return PropertyMeasure.this.getId();
		}

		public String getName() {
			return PropertyMeasure.this.getName();
		}

		public CategoricalValue getMeasuredValue() {
			return enrichWithId(measuredValue);
		}

		@Override
		public boolean equals(Object other) {
			if (MeasurableProperty.class.isInstance(other) == false) {
				return false;
			}

			var otherProperty = (MeasurableProperty) other;
			var isEqual = otherProperty.getId().equals(this.getId());
			isEqual &= otherProperty.measuredValue.get().equals(this.measuredValue.get());
			return isEqual;
		}

		@Override
		public int hashCode() {
			return hash(getId(), measuredValue.get());
		}

		@Override
		public String toString() {
			return getMeasuredValue().toString();
		}

		private CategoricalValue enrichWithId(CategoricalValue value) {
			return CategoricalValue.create(String.format("(%1s=%2s)", getId(), value.get()));
		}
	}

	public MeasurableProperty newMeasurablePropertyWith(CategoricalValue value) {
		if (isNotInPropertySpace(value)) {
			MLSensitivityAnalysisException.throwWithMessageAndCause(String
					.format("Value %1s is not contained in the value space of property measure %2s.", value, getName()),
					new IllegalArgumentException());
		}

		return new MeasurableProperty(value);

	}

	private boolean isNotInPropertySpace(CategoricalValue value) {
		return getMeasurablePropertySpace().stream().noneMatch(prop -> prop.measuredValue.get().equals(value.get()));
	}

	public abstract MeasurableProperty apply(InputData inputData);

	public abstract Boolean isApplicableTo(InputData inputData);

	public abstract Set<MeasurableProperty> getMeasurablePropertySpace();

	public abstract String getId();

	public abstract String getName();
}
