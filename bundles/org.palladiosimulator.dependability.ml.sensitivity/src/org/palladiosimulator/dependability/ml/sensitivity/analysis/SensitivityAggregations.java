package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.MeasurableProperty;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SensitivityAggregations {

	public static class SensitivityEntry {

		public final static String SIGNATURE_DELIMITER = ",";

		private final String signature;

		private SensitivityEntry(List<MeasurableProperty> properties) {
			this.signature = constructSignatureFrom(properties);
		}

		public static SensitivityEntry from(Set<MeasurableProperty> properties) {
			return new SensitivityEntry(Lists.newArrayList(properties));
		}

		public static SensitivityEntry from(List<MeasurableProperty> properties) {
			return new SensitivityEntry(properties);
		}

		private static String constructSignatureFrom(List<MeasurableProperty> properties) {
			var builder = new StringBuilder();
			for (MeasurableProperty each : orderAlphabeticallyByPropertyName(properties)) {
				builder.append(each.toString()).append(SIGNATURE_DELIMITER);
			}

			builder.deleteCharAt(builder.lastIndexOf(SIGNATURE_DELIMITER));

			return builder.toString();
		}

		private static List<MeasurableProperty> orderAlphabeticallyByPropertyName(List<MeasurableProperty> properties) {
			properties.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
			return properties;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof SensitivityEntry) {
				return SensitivityEntry.class.cast(obj).signature.equals(signature);
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

	private static class AggregatedValue {

		public double sum;
		public int count;

		public AggregatedValue(double value) {
			this.sum = value;
			this.count = 1;
		}

		public void update(double value) {
			sum += value;
			count++;
		}

		public double calculateMean() {
			return sum / count;
		}

	}

	private final static double PROPERTY_OCCURENCE_COUNT = 1.0;

	private final Map<MeasurableProperty, AggregatedValue> propertySensitivityAggregations;
	private final Map<SensitivityEntry, AggregatedValue> mlSensitivityAggregations;

	public SensitivityAggregations() {
		this.propertySensitivityAggregations = Maps.newHashMap();
		this.mlSensitivityAggregations = Maps.newHashMap();
	}

	public void updatePropertySensitivity(MeasurableProperty property) {
		var aggValue = Optional.ofNullable(propertySensitivityAggregations.get(property));
		if (aggValue.isPresent()) {
			aggValue.get().update(PROPERTY_OCCURENCE_COUNT);
		} else {
			propertySensitivityAggregations.put(property, new AggregatedValue(PROPERTY_OCCURENCE_COUNT));
		}
	}

	public void updateMLSensitivity(SensitivityEntry entry, double value) {
		var aggValue = Optional.ofNullable(mlSensitivityAggregations.get(entry));
		if (aggValue.isPresent()) {
			aggValue.get().update(value);
		} else {
			mlSensitivityAggregations.put(entry, new AggregatedValue(value));
		}
	}

	public Set<String> getMeasurablePropertyNames() {
		return groupMeasurableProperties().keySet();
	}

	public Map<SensitivityEntry, Double> getMLSensitivityValues() {
		return mlSensitivityAggregations.entrySet().stream()
				.collect(toMap(entry -> entry.getKey(), entry -> entry.getValue().calculateMean()));
	}

	public Map<MeasurableProperty, Double> filterLocalSensitivityValues(String propertyName) {
		return groupMeasurableProperties().get(propertyName).stream()
				.collect(toMap(Function.identity(), this::getLocalSensitivityValue));
	}

	private Map<String, List<MeasurableProperty>> groupMeasurableProperties() {
		return propertySensitivityAggregations.keySet().stream().collect(groupingBy(MeasurableProperty::getName));
	}

	private Double getLocalSensitivityValue(MeasurableProperty property) {
		AggregatedValue value = Optional.ofNullable(propertySensitivityAggregations.get(property))
				.orElseThrow(MLSensitivityAnalysisException.supplierWithMessage(
						String.format("There is no local sensitivity value for property %s", property.getName())));
		return value.calculateMean();
	}

}
