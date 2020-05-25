package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import java.util.Map;
import java.util.Set;

import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityAggregations.SensitivityEntry;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.MeasurableProperty;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;

public interface SensitivityModel {

	public void setSensitivityValues(Map<MeasurableProperty, Double> sensitivityValues);

	public void setMLSensitivityValues(Map<SensitivityEntry, Double> mlSensitivityValues);
	
	public SensitivityModel deriveFrom(Set<PropertyMeasure> propertyMeasures);
}
