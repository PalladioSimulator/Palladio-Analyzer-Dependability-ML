package org.palladiosimulator.dependability.ml.sensitivity.transformation;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.palladiosimulator.dependability.ml.model.InputData;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure.MeasurableProperty;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class AnalysisTransformation {

	private final Set<PropertyMeasure> propertyMeasures;

	public AnalysisTransformation(Set<PropertyMeasure> propertyMeasures) {
		this.propertyMeasures = propertyMeasures;
	}

	public Set<MeasurableProperty> computeMeasurableProperties(InputData inputData) {
		return propertyMeasures.stream().map(each -> each.apply(inputData)).collect(toSet());
	}

	public Optional<PropertyMeasure> findPropertyMeasureWith(String propertId) {
		return propertyMeasures.stream().filter(each -> each.getId().equals(propertId)).findFirst();
	}

	public Set<List<MeasurableProperty>> computeMeasurableSpace() {
		List<Set<MeasurableProperty>> measurableSpace = Lists.newArrayList();
		for (PropertyMeasure eachMeasure : propertyMeasures) {
			var propertySpace = Sets.newHashSet(eachMeasure.getMeasurablePropertySpace());
			measurableSpace.add(propertySpace);
		}
		return Sets.cartesianProduct(measurableSpace);
	}

}
