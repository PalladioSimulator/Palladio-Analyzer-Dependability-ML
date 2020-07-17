/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Failure Type Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFailureTypeRepository#getUncertaintyInducedFailureTypes <em>Uncertainty Induced Failure Types</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintyFailureTypeRepository()
 * @model
 * @generated
 */
public interface UncertaintyFailureTypeRepository extends EObject {
	/**
	 * Returns the value of the '<em><b>Uncertainty Induced Failure Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uncertainty Induced Failure Types</em>' containment reference list.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintyFailureTypeRepository_UncertaintyInducedFailureTypes()
	 * @model containment="true"
	 * @generated
	 */
	EList<UncertaintyInducedFailureType> getUncertaintyInducedFailureTypes();

} // UncertaintyFailureTypeRepository
