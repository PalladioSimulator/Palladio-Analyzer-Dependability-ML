/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFactory
 * @model kind="package"
 * @generated
 */
public interface UncertaintyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "uncertainty";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://palladiosimulator.org/Uncertainty/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "uncertainty";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UncertaintyPackage eINSTANCE = org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyInducedFailureTypeImpl <em>Induced Failure Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyInducedFailureTypeImpl
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getUncertaintyInducedFailureType()
	 * @generated
	 */
	int UNCERTAINTY_INDUCED_FAILURE_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Refines</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_INDUCED_FAILURE_TYPE__REFINES = 0;

	/**
	 * The feature id for the '<em><b>Uncertainty Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_INDUCED_FAILURE_TYPE__UNCERTAINTY_MODEL = 1;

	/**
	 * The number of structural features of the '<em>Induced Failure Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_INDUCED_FAILURE_TYPE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Induced Failure Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_INDUCED_FAILURE_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyFailureTypeRepositoryImpl <em>Failure Type Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyFailureTypeRepositoryImpl
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getUncertaintyFailureTypeRepository()
	 * @generated
	 */
	int UNCERTAINTY_FAILURE_TYPE_REPOSITORY = 1;

	/**
	 * The feature id for the '<em><b>Uncertainty Induced Failure Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_FAILURE_TYPE_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES = 0;

	/**
	 * The number of structural features of the '<em>Failure Type Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_FAILURE_TYPE_REPOSITORY_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Failure Type Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_FAILURE_TYPE_REPOSITORY_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType <em>Induced Failure Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Induced Failure Type</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType
	 * @generated
	 */
	EClass getUncertaintyInducedFailureType();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType#getRefines <em>Refines</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Refines</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType#getRefines()
	 * @see #getUncertaintyInducedFailureType()
	 * @generated
	 */
	EReference getUncertaintyInducedFailureType_Refines();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType#getUncertaintyModel <em>Uncertainty Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Uncertainty Model</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType#getUncertaintyModel()
	 * @see #getUncertaintyInducedFailureType()
	 * @generated
	 */
	EReference getUncertaintyInducedFailureType_UncertaintyModel();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFailureTypeRepository <em>Failure Type Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Failure Type Repository</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFailureTypeRepository
	 * @generated
	 */
	EClass getUncertaintyFailureTypeRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFailureTypeRepository#getUncertaintyInducedFailureTypes <em>Uncertainty Induced Failure Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Uncertainty Induced Failure Types</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFailureTypeRepository#getUncertaintyInducedFailureTypes()
	 * @see #getUncertaintyFailureTypeRepository()
	 * @generated
	 */
	EReference getUncertaintyFailureTypeRepository_UncertaintyInducedFailureTypes();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UncertaintyFactory getUncertaintyFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyInducedFailureTypeImpl <em>Induced Failure Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyInducedFailureTypeImpl
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getUncertaintyInducedFailureType()
		 * @generated
		 */
		EClass UNCERTAINTY_INDUCED_FAILURE_TYPE = eINSTANCE.getUncertaintyInducedFailureType();

		/**
		 * The meta object literal for the '<em><b>Refines</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNCERTAINTY_INDUCED_FAILURE_TYPE__REFINES = eINSTANCE.getUncertaintyInducedFailureType_Refines();

		/**
		 * The meta object literal for the '<em><b>Uncertainty Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNCERTAINTY_INDUCED_FAILURE_TYPE__UNCERTAINTY_MODEL = eINSTANCE
				.getUncertaintyInducedFailureType_UncertaintyModel();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyFailureTypeRepositoryImpl <em>Failure Type Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyFailureTypeRepositoryImpl
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getUncertaintyFailureTypeRepository()
		 * @generated
		 */
		EClass UNCERTAINTY_FAILURE_TYPE_REPOSITORY = eINSTANCE.getUncertaintyFailureTypeRepository();

		/**
		 * The meta object literal for the '<em><b>Uncertainty Induced Failure Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNCERTAINTY_FAILURE_TYPE_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES = eINSTANCE
				.getUncertaintyFailureTypeRepository_UncertaintyInducedFailureTypes();

	}

} //UncertaintyPackage
