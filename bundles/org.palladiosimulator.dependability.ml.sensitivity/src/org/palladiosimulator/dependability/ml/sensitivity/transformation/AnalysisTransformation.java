package org.palladiosimulator.dependability.ml.sensitivity.transformation;

import static java.util.stream.Collectors.toSet;

import java.util.Set;

public class AnalysisTransformation {

	private final Set<PropertyMeasure> propertyMeasures;

	public AnalysisTransformation(Set<PropertyMeasure> propertyMeasures) {
		this.propertyMeasures = propertyMeasures;
	}

	public Set<MeasureableProperty> transformMeasurableProperties(InputData inputData) {
		return propertyMeasures.stream().map(each -> each.apply(inputData)).collect(toSet());
	}
}
