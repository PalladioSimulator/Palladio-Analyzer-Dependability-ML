/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty;

import org.eclipse.emf.ecore.EObject;

import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;

import org.palladiosimulator.pcm.reliability.FailureType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Induced Failure Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType#getRefines <em>Refines</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType#getUncertaintyModel <em>Uncertainty Model</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType#getFailureVariable <em>Failure Variable</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintyInducedFailureType()
 * @model
 * @generated
 */
public interface UncertaintyInducedFailureType extends EObject {
	/**
	 * Returns the value of the '<em><b>Refines</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refines</em>' reference.
	 * @see #setRefines(FailureType)
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintyInducedFailureType_Refines()
	 * @model required="true"
	 * @generated
	 */
	FailureType getRefines();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType#getRefines <em>Refines</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refines</em>' reference.
	 * @see #getRefines()
	 * @generated
	 */
	void setRefines(FailureType value);

	/**
	 * Returns the value of the '<em><b>Uncertainty Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uncertainty Model</em>' reference.
	 * @see #setUncertaintyModel(GroundProbabilisticNetwork)
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintyInducedFailureType_UncertaintyModel()
	 * @model required="true"
	 * @generated
	 */
	GroundProbabilisticNetwork getUncertaintyModel();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType#getUncertaintyModel <em>Uncertainty Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uncertainty Model</em>' reference.
	 * @see #getUncertaintyModel()
	 * @generated
	 */
	void setUncertaintyModel(GroundProbabilisticNetwork value);

	/**
	 * Returns the value of the '<em><b>Failure Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Failure Variable</em>' reference.
	 * @see #setFailureVariable(GroundRandomVariable)
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintyInducedFailureType_FailureVariable()
	 * @model
	 * @generated
	 */
	GroundRandomVariable getFailureVariable();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType#getFailureVariable <em>Failure Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Failure Variable</em>' reference.
	 * @see #getFailureVariable()
	 * @generated
	 */
	void setFailureVariable(GroundRandomVariable value);

} // UncertaintyInducedFailureType
