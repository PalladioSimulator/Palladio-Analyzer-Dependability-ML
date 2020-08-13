/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty;

import org.eclipse.emf.common.util.EList;

import tools.mdsd.modelingfoundations.identifier.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository#getUncertaintyInducedFailureTypes <em>Uncertainty Induced Failure Types</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository#getArchitecturalCountermeasures <em>Architectural Countermeasures</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository#getArchitecturalPreconditions <em>Architectural Preconditions</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintyRepository()
 * @model
 * @generated
 */
public interface UncertaintyRepository extends Entity {
	/**
	 * Returns the value of the '<em><b>Uncertainty Induced Failure Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uncertainty Induced Failure Types</em>' containment reference list.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintyRepository_UncertaintyInducedFailureTypes()
	 * @model containment="true"
	 * @generated
	 */
	EList<UncertaintyInducedFailureType> getUncertaintyInducedFailureTypes();

	/**
	 * Returns the value of the '<em><b>Architectural Countermeasures</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Architectural Countermeasures</em>' containment reference list.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintyRepository_ArchitecturalCountermeasures()
	 * @model containment="true"
	 * @generated
	 */
	EList<ArchitecturalCountermeasure> getArchitecturalCountermeasures();

	/**
	 * Returns the value of the '<em><b>Architectural Preconditions</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Architectural Preconditions</em>' containment reference list.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintyRepository_ArchitecturalPreconditions()
	 * @model containment="true"
	 * @generated
	 */
	EList<ArchitecturalPrecondition> getArchitecturalPreconditions();

} // UncertaintyRepository
