package org.palladiosimulator.dependability.ml.sensitivity.transformation.property.conversion;

import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure.MeasurableProperty;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;
import org.palladiosimulator.envdyn.environment.templatevariable.TemplateVariable;

/**
 * The class defines functions or rather rules that semantically relates the
 * concepts MeasurableProperty, PropertyMeasure and TemplateVariables.
 * 
 * @author scheerer
 *
 */
public class MeasurablePropertyConversion {

	/**
	 * Converts a property measure to the semantically correct template variable
	 * name.Per convention the following applies: MeasurableProperty.id ==
	 * PropertyMeasure.id so that MeasurableProperty.id ==
	 * TemplateVariabe.entityName applies.
	 * 
	 * @param property to convert.
	 * @return the converted template variable name.
	 */
	public static String convertToTemplateVariableName(PropertyMeasure measure) {
		return measure.getId();
	}

	/**
	 * Converts a property to the semantically correct template variable name.Per
	 * convention the following applies: MeasurableProperty.id ==
	 * TemplateVariabe.entityName
	 * 
	 * @param property to convert.
	 * @return the converted template variable name.
	 */
	public static String convertToTemplateVariableName(MeasurableProperty property) {
		return property.getId();
	}

	/**
	 * @see #convertToTemplateVariableName(MeasurableProperty)
	 * @param template to restore the id from.
	 * @return the restored id.
	 */
	public static String convertToMeasurablePropertyId(TemplateVariable template) {
		return template.getEntityName();
	}

	public static boolean areSemanticallyEqual(GroundRandomVariable variable, PropertyMeasure measure) {
		return areSemanticallyEqual(variable.getInstantiatedTemplate(), measure);
	}
	
	public static boolean areSemanticallyEqual(GroundRandomVariable variable, MeasurableProperty property) {
		return areSemanticallyEqual(variable.getInstantiatedTemplate(), property);
	}

	public static boolean areSemanticallyEqual(TemplateVariable template, PropertyMeasure measure) {
		return convertToMeasurablePropertyId(template).equals(measure.getId());
	}
	
	public static boolean areSemanticallyEqual(TemplateVariable template, MeasurableProperty property) {
		return convertToMeasurablePropertyId(template).equals(property.getId());
	}

}
