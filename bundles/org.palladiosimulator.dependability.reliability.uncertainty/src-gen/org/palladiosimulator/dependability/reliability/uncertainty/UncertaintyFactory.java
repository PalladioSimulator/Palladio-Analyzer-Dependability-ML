/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage
 * @generated
 */
public interface UncertaintyFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UncertaintyFactory eINSTANCE = org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Induced Failure Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Induced Failure Type</em>'.
	 * @generated
	 */
	UncertaintyInducedFailureType createUncertaintyInducedFailureType();

	/**
	 * Returns a new object of class '<em>Failure Type Repository</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Failure Type Repository</em>'.
	 * @generated
	 */
	UncertaintyFailureTypeRepository createUncertaintyFailureTypeRepository();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	UncertaintyPackage getUncertaintyPackage();

} //UncertaintyFactory
