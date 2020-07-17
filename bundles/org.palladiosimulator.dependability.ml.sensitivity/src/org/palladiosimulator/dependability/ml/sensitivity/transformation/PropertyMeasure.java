package org.palladiosimulator.dependability.ml.sensitivity.transformation;

import java.util.Set;

import org.palladiosimulator.dependability.ml.model.InputData;

import tools.mdsd.probdist.api.entity.CategoricalValue;

public interface PropertyMeasure {

	public MeasurableProperty apply(InputData inputData);

	public Boolean isApplicableTo(InputData inputData);
	
	public Set<CategoricalValue> getValueSpace();
	
	public String getPropertyName();
}
