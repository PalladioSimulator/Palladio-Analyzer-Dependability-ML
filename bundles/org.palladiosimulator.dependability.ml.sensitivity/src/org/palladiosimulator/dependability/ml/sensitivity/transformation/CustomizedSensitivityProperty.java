package org.palladiosimulator.dependability.ml.sensitivity.transformation;

import org.palladiosimulator.dependability.ml.sensitivity.transformation.property.conversion.SensitivityPropertyConventions;
import org.palladiosimulator.envdyn.environment.templatevariable.TemplateVariable;

import tools.mdsd.probdist.api.entity.CategoricalValue;

public class CustomizedSensitivityProperty extends SensitivityProperty {

	private CustomizedSensitivityProperty(TemplateVariable template, CategoricalValue value) {
		super(SensitivityPropertyConventions.convertToPropertyId(template), value);
	}

	public static CustomizedSensitivityProperty createFrom(CategoricalValue value, TemplateVariable template) {
		return new CustomizedSensitivityProperty(template, value);
	}

}
