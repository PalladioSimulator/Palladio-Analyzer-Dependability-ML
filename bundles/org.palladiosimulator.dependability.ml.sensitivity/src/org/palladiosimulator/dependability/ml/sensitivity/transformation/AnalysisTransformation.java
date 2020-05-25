package org.palladiosimulator.dependability.ml.sensitivity.transformation;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.palladiosimulator.dependability.ml.sensitivity.prediction.InputData;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import tools.mdsd.probdist.api.entity.CategoricalValue;

public class AnalysisTransformation {

	private final Set<PropertyMeasure> propertyMeasures;

	public AnalysisTransformation(Set<PropertyMeasure> propertyMeasures) {
		this.propertyMeasures = propertyMeasures;
	}

	public Set<MeasurableProperty> computeMeasurableProperties(InputData inputData) {
		return propertyMeasures.stream().map(each -> each.apply(inputData)).collect(toSet());
	}

	public Optional<PropertyMeasure> findPropertyMeasureWith(String propertyName) {
		return propertyMeasures.stream().filter(each -> each.getPropertyName().equals(propertyName)).findFirst();
	}

	public Set<List<MeasurableProperty>> computePropertyMeasureValueSpace() {
		List<Set<MeasurableProperty>> propertyValueSpaces = Lists.newArrayList();
		for (PropertyMeasure eachProperty : propertyMeasures) {
			Set<MeasurableProperty> propertyValueSpace = Sets.newHashSet();
			for (CategoricalValue eachValue : eachProperty.getValueSpace()) {
				propertyValueSpace.add(new MeasurableProperty(eachProperty.getPropertyName(), eachValue));
			}
			propertyValueSpaces.add(propertyValueSpace);
		}
		return Sets.cartesianProduct(propertyValueSpaces);
	}

}
