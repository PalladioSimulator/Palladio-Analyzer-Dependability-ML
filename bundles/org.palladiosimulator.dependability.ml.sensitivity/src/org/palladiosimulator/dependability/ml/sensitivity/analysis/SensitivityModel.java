package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityAggregations.MLSensitivityEntry;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.SensitivityProperty;

public abstract class SensitivityModel {

	public static enum MLOutcomeMeasure {
		SUCCESS("Success"), 
		FAIL("Fail");

		private final String name;

		private MLOutcomeMeasure(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	protected MLOutcomeMeasure outcomeMeasure = MLOutcomeMeasure.SUCCESS;
	
	public void setMLOutcomeMeasure(MLOutcomeMeasure measure) {
		this.outcomeMeasure = measure;
	}
	
	public abstract void setSensitivityValues(Map<SensitivityProperty, Double> sensitivityValues);

	public abstract double getSensitivityValuesOf(SensitivityProperty property);

	public abstract void setMLSensitivityValues(Map<MLSensitivityEntry, Double> mlSensitivityValues);

	public abstract double inferSensitivity(List<SensitivityProperty> properties);

	public abstract void saveAt(URI location);

	public abstract SensitivityModel deriveFrom(Set<PropertyMeasure> propertyMeasures);
}
