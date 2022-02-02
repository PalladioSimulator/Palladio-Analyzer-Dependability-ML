/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty;

import org.eclipse.emf.common.util.EList;

import tools.mdsd.modelingfoundations.identifier.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Architectural Countermeasure</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure#getArchitecturalPreconditions <em>Architectural Preconditions</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure#getAppliedfailuretype <em>Appliedfailuretype</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getArchitecturalCountermeasure()
 * @model abstract="true"
 * @generated
 */
public interface ArchitecturalCountermeasure extends Entity {
	/**
	 * Returns the value of the '<em><b>Architectural Preconditions</b></em>' reference list.
	 * The list contents are of type {@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Architectural Preconditions</em>' reference list.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getArchitecturalCountermeasure_ArchitecturalPreconditions()
	 * @model required="true"
	 * @generated
	 */
	EList<ArchitecturalPrecondition> getArchitecturalPreconditions();

	/**
	 * Returns the value of the '<em><b>Appliedfailuretype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Appliedfailuretype</em>' reference.
	 * @see #setAppliedfailuretype(UncertaintyInducedFailureType)
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getArchitecturalCountermeasure_Appliedfailuretype()
	 * @model required="true"
	 * @generated
	 */
	UncertaintyInducedFailureType getAppliedfailuretype();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure#getAppliedfailuretype <em>Appliedfailuretype</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Appliedfailuretype</em>' reference.
	 * @see #getAppliedfailuretype()
	 * @generated
	 */
	void setAppliedfailuretype(UncertaintyInducedFailureType value);

} // ArchitecturalCountermeasure
