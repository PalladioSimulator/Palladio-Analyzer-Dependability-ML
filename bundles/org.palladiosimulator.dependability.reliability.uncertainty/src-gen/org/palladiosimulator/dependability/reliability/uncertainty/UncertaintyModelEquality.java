/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty;

import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Equality</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyModelEquality#getFirst <em>First</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyModelEquality#getSecond <em>Second</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintyModelEquality()
 * @model
 * @generated
 */
public interface UncertaintyModelEquality extends ArchitecturalPrecondition {
	/**
	 * Returns the value of the '<em><b>First</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First</em>' reference.
	 * @see #setFirst(GroundProbabilisticNetwork)
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintyModelEquality_First()
	 * @model required="true"
	 * @generated
	 */
	GroundProbabilisticNetwork getFirst();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyModelEquality#getFirst <em>First</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First</em>' reference.
	 * @see #getFirst()
	 * @generated
	 */
	void setFirst(GroundProbabilisticNetwork value);

	/**
	 * Returns the value of the '<em><b>Second</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Second</em>' reference.
	 * @see #setSecond(GroundProbabilisticNetwork)
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getUncertaintyModelEquality_Second()
	 * @model required="true"
	 * @generated
	 */
	GroundProbabilisticNetwork getSecond();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyModelEquality#getSecond <em>Second</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Second</em>' reference.
	 * @see #getSecond()
	 * @generated
	 */
	void setSecond(GroundProbabilisticNetwork value);

} // UncertaintyModelEquality
