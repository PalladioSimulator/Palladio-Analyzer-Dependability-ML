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
	 * Returns a new object of class '<em>Repository</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Repository</em>'.
	 * @generated
	 */
	UncertaintyRepository createUncertaintyRepository();

	/**
	 * Returns a new object of class '<em>Active Component Precondition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Active Component Precondition</em>'.
	 * @generated
	 */
	ActiveComponentPrecondition createActiveComponentPrecondition();

	/**
	 * Returns a new object of class '<em>Deterministic Improvement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Deterministic Improvement</em>'.
	 * @generated
	 */
	DeterministicImprovement createDeterministicImprovement();

	/**
	 * Returns a new object of class '<em>Probabilistic Improvement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Probabilistic Improvement</em>'.
	 * @generated
	 */
	ProbabilisticImprovement createProbabilisticImprovement();

	/**
	 * Returns a new object of class '<em>Map Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Entry</em>'.
	 * @generated
	 */
	MapEntry createMapEntry();

	/**
	 * Returns a new object of class '<em>Specific Countermeasure</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Specific Countermeasure</em>'.
	 * @generated
	 */
	UncertaintySpecificCountermeasure createUncertaintySpecificCountermeasure();

	/**
	 * Returns a new object of class '<em>Global Uncertainty Countermeasure</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Global Uncertainty Countermeasure</em>'.
	 * @generated
	 */
	GlobalUncertaintyCountermeasure createGlobalUncertaintyCountermeasure();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	UncertaintyPackage getUncertaintyPackage();

} //UncertaintyFactory
