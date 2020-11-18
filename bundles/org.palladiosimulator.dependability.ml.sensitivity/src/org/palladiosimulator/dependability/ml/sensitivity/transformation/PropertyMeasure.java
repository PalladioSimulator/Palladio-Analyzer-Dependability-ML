package org.palladiosimulator.dependability.ml.sensitivity.transformation;

import java.util.Optional;
import java.util.Set;

import org.palladiosimulator.dependability.ml.model.InputData;

import tools.mdsd.probdist.api.entity.CategoricalValue;

public abstract class PropertyMeasure {

	public class MeasurableSensitivityProperty extends SensitivityProperty {

		private MeasurableSensitivityProperty(CategoricalValue value) {
			super(PropertyMeasure.this.getId(), value);
		}

	}

	/**
	 * Generates a property from a raw value, i.e. not enriched by the property
	 * measure id.
	 * 
	 * @param value corresponds to the raw value
	 * @return the measurable property containing the enriched value.
	 */
	protected MeasurableSensitivityProperty generateFromRaw(CategoricalValue value) {
		return new MeasurableSensitivityProperty(value);
	}

	public Optional<MeasurableSensitivityProperty> findMeasurablePropertyWith(CategoricalValue value) {
		return getMeasurablePropertySpace().stream()
				.filter(p -> p.getValue().get().equals(value.get()))
				.findFirst();
	}

	public abstract MeasurableSensitivityProperty apply(InputData inputData);

	public abstract Boolean isApplicableTo(InputData inputData);

	public abstract Set<MeasurableSensitivityProperty> getMeasurablePropertySpace();

	public abstract String getId();

	public abstract String getName();
}
