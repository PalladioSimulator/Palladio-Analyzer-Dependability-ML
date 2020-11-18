package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure.MeasurableProperty;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SensitivityAggregations {

	public static class MLSensitivityEntry {

		public final static String SIGNATURE_DELIMITER = ",";

		private final String signature;

		private MLSensitivityEntry(List<MeasurableProperty> properties) {
			this.signature = constructSignatureFrom(properties);
		}

		public static MLSensitivityEntry from(Set<MeasurableProperty> properties) {
			return new MLSensitivityEntry(Lists.newArrayList(properties));
		}

		public static MLSensitivityEntry from(List<MeasurableProperty> properties) {
			return new MLSensitivityEntry(properties);
		}

		private static String constructSignatureFrom(List<MeasurableProperty> properties) {
			var builder = new StringBuilder();
			
			for (MeasurableProperty each : orderAlphabeticallyByName(properties)) {
				builder.append(each.getMeasuredValue().toString()).append(SIGNATURE_DELIMITER);
			}
			builder.deleteCharAt(builder.lastIndexOf(SIGNATURE_DELIMITER));

			return builder.toString();
		}

		private static List<MeasurableProperty> orderAlphabeticallyByName(List<MeasurableProperty> properties) {
			properties.sort((p1, p2) -> p1.getId().compareTo(p2.getId()));
			return properties;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof MLSensitivityEntry) {
				return MLSensitivityEntry.class.cast(obj).signature.equals(signature);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return signature.hashCode();
		}

		public List<String> getSignatureComponents() {
			return Lists.newArrayList(signature.split(SIGNATURE_DELIMITER));
		}

	}

	private static class MLSensitivityValue {

		private double sensitivity;
		private int occurenceCounter;

		public MLSensitivityValue(double initial) {
			this.sensitivity = initial;
			this.occurenceCounter = 1;
		}

		public MLSensitivityValue incrementalUpdate(MLSensitivityValue newValue) {
			sensitivity += newValue.sensitivity;
			occurenceCounter++;
			return this;
		}

		public double computeNormalizedAggregatedValue() {
			return sensitivity / occurenceCounter;
		}

	}

	private final static double PROPERTY_OCCURENCE_COUNT = 1.0;

	private final Map<MeasurableProperty, Double> propertySensitivityAggregations;
	private final Map<MLSensitivityEntry, MLSensitivityValue> mlSensitivityAggregations;

	private int globalCount;

	public SensitivityAggregations() {
		this.propertySensitivityAggregations = Maps.newHashMap();
		this.mlSensitivityAggregations = Maps.newHashMap();
		this.globalCount = 0;
	}

	public void record(Set<MeasurableProperty> measuredProperties, double predictionAccuracy) {
		measuredProperties.forEach(this::updatePropertySensitivity);
		updateMLSensitivity(MLSensitivityEntry.from(measuredProperties), predictionAccuracy);

		incrementGlobalCounter();
	}

	public Set<String> getMeasurablePropertyIds() {
		return groupMeasurableProperties().keySet();
	}

	public Map<MLSensitivityEntry, Double> getMLSensitivityValues() {
		return mlSensitivityAggregations.entrySet().stream()
				.collect(toMap(Map.Entry::getKey, normalizeAggregatedValues()));
	}

	public Map<MeasurableProperty, Double> getPropertySensitivityValues(String propertyName) {
		return groupMeasurableProperties().get(propertyName).stream()
				.collect(toMap(Function.identity(), this::normalizeAggregatedValues));
	}

	private Double normalizeAggregatedValues(MeasurableProperty property) {
		var value = Optional.ofNullable(propertySensitivityAggregations.get(property))
				.orElseThrow(MLSensitivityAnalysisException.supplierWithMessage(
						String.format("There is no local sensitivity value for property %s", property.getName())));
		return value / globalCount;
	}

	private Function<Map.Entry<MLSensitivityEntry, MLSensitivityValue>, Double> normalizeAggregatedValues() {
		return e -> e.getValue().computeNormalizedAggregatedValue();
	}

	private Map<String, List<MeasurableProperty>> groupMeasurableProperties() {
		return propertySensitivityAggregations.keySet().stream().collect(groupingBy(MeasurableProperty::getId));
	}

	private void updatePropertySensitivity(MeasurableProperty property) {
		propertySensitivityAggregations.merge(property, PROPERTY_OCCURENCE_COUNT, Double::sum);
	}

	private void updateMLSensitivity(MLSensitivityEntry entry, double value) {
		mlSensitivityAggregations.merge(entry, new MLSensitivityValue(value), MLSensitivityValue::incrementalUpdate);
	}

	private void incrementGlobalCounter() {
		globalCount++;
	}

}
