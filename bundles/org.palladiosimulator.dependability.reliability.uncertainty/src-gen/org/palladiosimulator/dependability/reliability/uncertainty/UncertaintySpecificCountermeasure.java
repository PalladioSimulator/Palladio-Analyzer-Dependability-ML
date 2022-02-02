/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty;

import org.palladiosimulator.envdyn.environment.templatevariable.TemplateVariable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Specific Countermeasure</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintySpecificCountermeasure#getTargetUncertainty <em>Target Uncertainty</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintySpecificCountermeasure#getUncertaintyImprovement <em>Uncertainty Improvement</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintySpecificCountermeasure()
 * @model
 * @generated
 */
public interface UncertaintySpecificCountermeasure extends ArchitecturalCountermeasure {
	/**
	 * Returns the value of the '<em><b>Target Uncertainty</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Uncertainty</em>' reference.
	 * @see #setTargetUncertainty(TemplateVariable)
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintySpecificCountermeasure_TargetUncertainty()
	 * @model required="true"
	 * @generated
	 */
	TemplateVariable getTargetUncertainty();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintySpecificCountermeasure#getTargetUncertainty <em>Target Uncertainty</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Uncertainty</em>' reference.
	 * @see #getTargetUncertainty()
	 * @generated
	 */
	void setTargetUncertainty(TemplateVariable value);

	/**
	 * Returns the value of the '<em><b>Uncertainty Improvement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uncertainty Improvement</em>' containment reference.
	 * @see #setUncertaintyImprovement(UncertaintyImprovement)
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintySpecificCountermeasure_UncertaintyImprovement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	UncertaintyImprovement getUncertaintyImprovement();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintySpecificCountermeasure#getUncertaintyImprovement <em>Uncertainty Improvement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uncertainty Improvement</em>' containment reference.
	 * @see #getUncertaintyImprovement()
	 * @generated
	 */
	void setUncertaintyImprovement(UncertaintyImprovement value);

} // UncertaintySpecificCountermeasure
