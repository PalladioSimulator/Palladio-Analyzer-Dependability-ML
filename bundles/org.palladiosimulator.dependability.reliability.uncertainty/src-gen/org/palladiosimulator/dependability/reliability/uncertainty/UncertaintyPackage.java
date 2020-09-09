/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import tools.mdsd.modelingfoundations.identifier.IdentifierPackage;

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
	 * The feature id for the '<em><b>Failure Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_INDUCED_FAILURE_TYPE__FAILURE_VARIABLE = 2;

	/**
	 * The feature id for the '<em><b>Architectural Preconditions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_INDUCED_FAILURE_TYPE__ARCHITECTURAL_PRECONDITIONS = 3;

	/**
	 * The number of structural features of the '<em>Induced Failure Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_INDUCED_FAILURE_TYPE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Induced Failure Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_INDUCED_FAILURE_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyRepositoryImpl <em>Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyRepositoryImpl
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getUncertaintyRepository()
	 * @generated
	 */
	int UNCERTAINTY_REPOSITORY = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_REPOSITORY__ID = IdentifierPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_REPOSITORY__ENTITY_NAME = IdentifierPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Uncertainty Induced Failure Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES = IdentifierPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Architectural Countermeasures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_REPOSITORY__ARCHITECTURAL_COUNTERMEASURES = IdentifierPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Architectural Preconditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_REPOSITORY__ARCHITECTURAL_PRECONDITIONS = IdentifierPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_REPOSITORY_FEATURE_COUNT = IdentifierPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_REPOSITORY_OPERATION_COUNT = IdentifierPackage.ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.ArchitecturalCountermeasureImpl <em>Architectural Countermeasure</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.ArchitecturalCountermeasureImpl
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getArchitecturalCountermeasure()
	 * @generated
	 */
	int ARCHITECTURAL_COUNTERMEASURE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHITECTURAL_COUNTERMEASURE__ID = IdentifierPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHITECTURAL_COUNTERMEASURE__ENTITY_NAME = IdentifierPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Architectural Preconditions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHITECTURAL_COUNTERMEASURE__ARCHITECTURAL_PRECONDITIONS = IdentifierPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target Uncertainty</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHITECTURAL_COUNTERMEASURE__TARGET_UNCERTAINTY = IdentifierPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Uncertainty Improvement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT = IdentifierPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Architectural Countermeasure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHITECTURAL_COUNTERMEASURE_FEATURE_COUNT = IdentifierPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Architectural Countermeasure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHITECTURAL_COUNTERMEASURE_OPERATION_COUNT = IdentifierPackage.ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.ArchitecturalPreconditionImpl <em>Architectural Precondition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.ArchitecturalPreconditionImpl
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getArchitecturalPrecondition()
	 * @generated
	 */
	int ARCHITECTURAL_PRECONDITION = 3;

	/**
	 * The number of structural features of the '<em>Architectural Precondition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHITECTURAL_PRECONDITION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Architectural Precondition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHITECTURAL_PRECONDITION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.ActiveComponentPreconditionImpl <em>Active Component Precondition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.ActiveComponentPreconditionImpl
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getActiveComponentPrecondition()
	 * @generated
	 */
	int ACTIVE_COMPONENT_PRECONDITION = 4;

	/**
	 * The feature id for the '<em><b>Required Active Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVE_COMPONENT_PRECONDITION__REQUIRED_ACTIVE_COMPONENT = ARCHITECTURAL_PRECONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Active Component Precondition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVE_COMPONENT_PRECONDITION_FEATURE_COUNT = ARCHITECTURAL_PRECONDITION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Active Component Precondition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVE_COMPONENT_PRECONDITION_OPERATION_COUNT = ARCHITECTURAL_PRECONDITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyImprovementImpl <em>Improvement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyImprovementImpl
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getUncertaintyImprovement()
	 * @generated
	 */
	int UNCERTAINTY_IMPROVEMENT = 5;

	/**
	 * The number of structural features of the '<em>Improvement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_IMPROVEMENT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Improvement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_IMPROVEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.DeterministicImprovementImpl <em>Deterministic Improvement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.DeterministicImprovementImpl
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getDeterministicImprovement()
	 * @generated
	 */
	int DETERMINISTIC_IMPROVEMENT = 6;

	/**
	 * The feature id for the '<em><b>Mapping Table</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DETERMINISTIC_IMPROVEMENT__MAPPING_TABLE = UNCERTAINTY_IMPROVEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Deterministic Improvement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DETERMINISTIC_IMPROVEMENT_FEATURE_COUNT = UNCERTAINTY_IMPROVEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Deterministic Improvement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DETERMINISTIC_IMPROVEMENT_OPERATION_COUNT = UNCERTAINTY_IMPROVEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.ProbabilisticImprovementImpl <em>Probabilistic Improvement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.ProbabilisticImprovementImpl
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getProbabilisticImprovement()
	 * @generated
	 */
	int PROBABILISTIC_IMPROVEMENT = 7;

	/**
	 * The feature id for the '<em><b>Probability Distribution</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_IMPROVEMENT__PROBABILITY_DISTRIBUTION = UNCERTAINTY_IMPROVEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Probabilistic Improvement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_IMPROVEMENT_FEATURE_COUNT = UNCERTAINTY_IMPROVEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Probabilistic Improvement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_IMPROVEMENT_OPERATION_COUNT = UNCERTAINTY_IMPROVEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.MapEntryImpl <em>Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.MapEntryImpl
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getMapEntry()
	 * @generated
	 */
	int MAP_ENTRY = 8;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENTRY_OPERATION_COUNT = 0;

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
	 * Returns the meta object for the reference '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType#getFailureVariable <em>Failure Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Failure Variable</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType#getFailureVariable()
	 * @see #getUncertaintyInducedFailureType()
	 * @generated
	 */
	EReference getUncertaintyInducedFailureType_FailureVariable();

	/**
	 * Returns the meta object for the reference list '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType#getArchitecturalPreconditions <em>Architectural Preconditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Architectural Preconditions</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType#getArchitecturalPreconditions()
	 * @see #getUncertaintyInducedFailureType()
	 * @generated
	 */
	EReference getUncertaintyInducedFailureType_ArchitecturalPreconditions();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repository</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository
	 * @generated
	 */
	EClass getUncertaintyRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository#getUncertaintyInducedFailureTypes <em>Uncertainty Induced Failure Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Uncertainty Induced Failure Types</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository#getUncertaintyInducedFailureTypes()
	 * @see #getUncertaintyRepository()
	 * @generated
	 */
	EReference getUncertaintyRepository_UncertaintyInducedFailureTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository#getArchitecturalCountermeasures <em>Architectural Countermeasures</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Architectural Countermeasures</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository#getArchitecturalCountermeasures()
	 * @see #getUncertaintyRepository()
	 * @generated
	 */
	EReference getUncertaintyRepository_ArchitecturalCountermeasures();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository#getArchitecturalPreconditions <em>Architectural Preconditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Architectural Preconditions</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository#getArchitecturalPreconditions()
	 * @see #getUncertaintyRepository()
	 * @generated
	 */
	EReference getUncertaintyRepository_ArchitecturalPreconditions();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure <em>Architectural Countermeasure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Architectural Countermeasure</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure
	 * @generated
	 */
	EClass getArchitecturalCountermeasure();

	/**
	 * Returns the meta object for the reference list '{@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure#getArchitecturalPreconditions <em>Architectural Preconditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Architectural Preconditions</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure#getArchitecturalPreconditions()
	 * @see #getArchitecturalCountermeasure()
	 * @generated
	 */
	EReference getArchitecturalCountermeasure_ArchitecturalPreconditions();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure#getTargetUncertainty <em>Target Uncertainty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Uncertainty</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure#getTargetUncertainty()
	 * @see #getArchitecturalCountermeasure()
	 * @generated
	 */
	EReference getArchitecturalCountermeasure_TargetUncertainty();

	/**
	 * Returns the meta object for the containment reference '{@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure#getUncertaintyImprovement <em>Uncertainty Improvement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Uncertainty Improvement</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure#getUncertaintyImprovement()
	 * @see #getArchitecturalCountermeasure()
	 * @generated
	 */
	EReference getArchitecturalCountermeasure_UncertaintyImprovement();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition <em>Architectural Precondition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Architectural Precondition</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition
	 * @generated
	 */
	EClass getArchitecturalPrecondition();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.dependability.reliability.uncertainty.ActiveComponentPrecondition <em>Active Component Precondition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Active Component Precondition</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.ActiveComponentPrecondition
	 * @generated
	 */
	EClass getActiveComponentPrecondition();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.dependability.reliability.uncertainty.ActiveComponentPrecondition#getRequiredActiveComponent <em>Required Active Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Required Active Component</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.ActiveComponentPrecondition#getRequiredActiveComponent()
	 * @see #getActiveComponentPrecondition()
	 * @generated
	 */
	EReference getActiveComponentPrecondition_RequiredActiveComponent();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyImprovement <em>Improvement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Improvement</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyImprovement
	 * @generated
	 */
	EClass getUncertaintyImprovement();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.dependability.reliability.uncertainty.DeterministicImprovement <em>Deterministic Improvement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Deterministic Improvement</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.DeterministicImprovement
	 * @generated
	 */
	EClass getDeterministicImprovement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.dependability.reliability.uncertainty.DeterministicImprovement#getMappingTable <em>Mapping Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mapping Table</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.DeterministicImprovement#getMappingTable()
	 * @see #getDeterministicImprovement()
	 * @generated
	 */
	EReference getDeterministicImprovement_MappingTable();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.dependability.reliability.uncertainty.ProbabilisticImprovement <em>Probabilistic Improvement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Probabilistic Improvement</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.ProbabilisticImprovement
	 * @generated
	 */
	EClass getProbabilisticImprovement();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.dependability.reliability.uncertainty.ProbabilisticImprovement#getProbabilityDistribution <em>Probability Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Probability Distribution</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.ProbabilisticImprovement#getProbabilityDistribution()
	 * @see #getProbabilisticImprovement()
	 * @generated
	 */
	EReference getProbabilisticImprovement_ProbabilityDistribution();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.dependability.reliability.uncertainty.MapEntry <em>Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Entry</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.MapEntry
	 * @generated
	 */
	EClass getMapEntry();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.dependability.reliability.uncertainty.MapEntry#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.MapEntry#getKey()
	 * @see #getMapEntry()
	 * @generated
	 */
	EAttribute getMapEntry_Key();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.dependability.reliability.uncertainty.MapEntry#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.MapEntry#getValue()
	 * @see #getMapEntry()
	 * @generated
	 */
	EAttribute getMapEntry_Value();

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
		 * The meta object literal for the '<em><b>Failure Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNCERTAINTY_INDUCED_FAILURE_TYPE__FAILURE_VARIABLE = eINSTANCE
				.getUncertaintyInducedFailureType_FailureVariable();

		/**
		 * The meta object literal for the '<em><b>Architectural Preconditions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNCERTAINTY_INDUCED_FAILURE_TYPE__ARCHITECTURAL_PRECONDITIONS = eINSTANCE
				.getUncertaintyInducedFailureType_ArchitecturalPreconditions();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyRepositoryImpl <em>Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyRepositoryImpl
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getUncertaintyRepository()
		 * @generated
		 */
		EClass UNCERTAINTY_REPOSITORY = eINSTANCE.getUncertaintyRepository();

		/**
		 * The meta object literal for the '<em><b>Uncertainty Induced Failure Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNCERTAINTY_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES = eINSTANCE
				.getUncertaintyRepository_UncertaintyInducedFailureTypes();

		/**
		 * The meta object literal for the '<em><b>Architectural Countermeasures</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNCERTAINTY_REPOSITORY__ARCHITECTURAL_COUNTERMEASURES = eINSTANCE
				.getUncertaintyRepository_ArchitecturalCountermeasures();

		/**
		 * The meta object literal for the '<em><b>Architectural Preconditions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNCERTAINTY_REPOSITORY__ARCHITECTURAL_PRECONDITIONS = eINSTANCE
				.getUncertaintyRepository_ArchitecturalPreconditions();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.ArchitecturalCountermeasureImpl <em>Architectural Countermeasure</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.ArchitecturalCountermeasureImpl
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getArchitecturalCountermeasure()
		 * @generated
		 */
		EClass ARCHITECTURAL_COUNTERMEASURE = eINSTANCE.getArchitecturalCountermeasure();

		/**
		 * The meta object literal for the '<em><b>Architectural Preconditions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARCHITECTURAL_COUNTERMEASURE__ARCHITECTURAL_PRECONDITIONS = eINSTANCE
				.getArchitecturalCountermeasure_ArchitecturalPreconditions();

		/**
		 * The meta object literal for the '<em><b>Target Uncertainty</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARCHITECTURAL_COUNTERMEASURE__TARGET_UNCERTAINTY = eINSTANCE
				.getArchitecturalCountermeasure_TargetUncertainty();

		/**
		 * The meta object literal for the '<em><b>Uncertainty Improvement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT = eINSTANCE
				.getArchitecturalCountermeasure_UncertaintyImprovement();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.ArchitecturalPreconditionImpl <em>Architectural Precondition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.ArchitecturalPreconditionImpl
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getArchitecturalPrecondition()
		 * @generated
		 */
		EClass ARCHITECTURAL_PRECONDITION = eINSTANCE.getArchitecturalPrecondition();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.ActiveComponentPreconditionImpl <em>Active Component Precondition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.ActiveComponentPreconditionImpl
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getActiveComponentPrecondition()
		 * @generated
		 */
		EClass ACTIVE_COMPONENT_PRECONDITION = eINSTANCE.getActiveComponentPrecondition();

		/**
		 * The meta object literal for the '<em><b>Required Active Component</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTIVE_COMPONENT_PRECONDITION__REQUIRED_ACTIVE_COMPONENT = eINSTANCE
				.getActiveComponentPrecondition_RequiredActiveComponent();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyImprovementImpl <em>Improvement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyImprovementImpl
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getUncertaintyImprovement()
		 * @generated
		 */
		EClass UNCERTAINTY_IMPROVEMENT = eINSTANCE.getUncertaintyImprovement();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.DeterministicImprovementImpl <em>Deterministic Improvement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.DeterministicImprovementImpl
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getDeterministicImprovement()
		 * @generated
		 */
		EClass DETERMINISTIC_IMPROVEMENT = eINSTANCE.getDeterministicImprovement();

		/**
		 * The meta object literal for the '<em><b>Mapping Table</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DETERMINISTIC_IMPROVEMENT__MAPPING_TABLE = eINSTANCE.getDeterministicImprovement_MappingTable();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.ProbabilisticImprovementImpl <em>Probabilistic Improvement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.ProbabilisticImprovementImpl
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getProbabilisticImprovement()
		 * @generated
		 */
		EClass PROBABILISTIC_IMPROVEMENT = eINSTANCE.getProbabilisticImprovement();

		/**
		 * The meta object literal for the '<em><b>Probability Distribution</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBABILISTIC_IMPROVEMENT__PROBABILITY_DISTRIBUTION = eINSTANCE
				.getProbabilisticImprovement_ProbabilityDistribution();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.MapEntryImpl <em>Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.MapEntryImpl
		 * @see org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyPackageImpl#getMapEntry()
		 * @generated
		 */
		EClass MAP_ENTRY = eINSTANCE.getMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_ENTRY__KEY = eINSTANCE.getMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_ENTRY__VALUE = eINSTANCE.getMapEntry_Value();

	}

} //UncertaintyPackage
