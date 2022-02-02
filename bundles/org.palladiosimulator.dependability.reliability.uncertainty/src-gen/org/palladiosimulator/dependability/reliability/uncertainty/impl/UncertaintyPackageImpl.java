/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.impl;

import de.uka.ipd.sdq.identifier.IdentifierPackage;

import de.uka.ipd.sdq.probfunction.ProbfunctionPackage;

import de.uka.ipd.sdq.stoex.StoexPackage;

import de.uka.ipd.sdq.units.UnitsPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.palladiosimulator.dependability.reliability.uncertainty.ActiveComponentPrecondition;
import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;
import org.palladiosimulator.dependability.reliability.uncertainty.DeterministicImprovement;
import org.palladiosimulator.dependability.reliability.uncertainty.GlobalUncertaintyCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.MapEntry;
import org.palladiosimulator.dependability.reliability.uncertainty.ProbabilisticImprovement;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFactory;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyImprovement;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintySpecificCountermeasure;

import org.palladiosimulator.envdyn.environment.dynamicmodel.DynamicmodelPackage;

import org.palladiosimulator.envdyn.environment.staticmodel.StaticmodelPackage;

import org.palladiosimulator.envdyn.environment.templatevariable.TemplatevariablePackage;

import org.palladiosimulator.pcm.PcmPackage;

import org.palladiosimulator.pcm.core.entity.EntityPackage;

import org.palladiosimulator.pcm.reliability.ReliabilityPackage;

import tools.mdsd.probdist.distributionfunction.DistributionfunctionPackage;

import tools.mdsd.probdist.distributiontype.DistributiontypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UncertaintyPackageImpl extends EPackageImpl implements UncertaintyPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uncertaintyInducedFailureTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uncertaintyRepositoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass architecturalCountermeasureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass architecturalPreconditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass activeComponentPreconditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uncertaintyImprovementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deterministicImprovementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass probabilisticImprovementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uncertaintySpecificCountermeasureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass globalUncertaintyCountermeasureEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private UncertaintyPackageImpl() {
		super(eNS_URI, UncertaintyFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link UncertaintyPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static UncertaintyPackage init() {
		if (isInited)
			return (UncertaintyPackage) EPackage.Registry.INSTANCE.getEPackage(UncertaintyPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredUncertaintyPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		UncertaintyPackageImpl theUncertaintyPackage = registeredUncertaintyPackage instanceof UncertaintyPackageImpl
				? (UncertaintyPackageImpl) registeredUncertaintyPackage
				: new UncertaintyPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		TemplatevariablePackage.eINSTANCE.eClass();
		StaticmodelPackage.eINSTANCE.eClass();
		DynamicmodelPackage.eINSTANCE.eClass();
		IdentifierPackage.eINSTANCE.eClass();
		tools.mdsd.modelingfoundations.identifier.IdentifierPackage.eINSTANCE.eClass();
		PcmPackage.eINSTANCE.eClass();
		ProbfunctionPackage.eINSTANCE.eClass();
		DistributiontypePackage.eINSTANCE.eClass();
		DistributionfunctionPackage.eINSTANCE.eClass();
		StoexPackage.eINSTANCE.eClass();
		UnitsPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theUncertaintyPackage.createPackageContents();

		// Initialize created meta-data
		theUncertaintyPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUncertaintyPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(UncertaintyPackage.eNS_URI, theUncertaintyPackage);
		return theUncertaintyPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUncertaintyInducedFailureType() {
		return uncertaintyInducedFailureTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUncertaintyInducedFailureType_Refines() {
		return (EReference) uncertaintyInducedFailureTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUncertaintyInducedFailureType_UncertaintyModel() {
		return (EReference) uncertaintyInducedFailureTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUncertaintyInducedFailureType_FailureVariable() {
		return (EReference) uncertaintyInducedFailureTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUncertaintyInducedFailureType_ArchitecturalPreconditions() {
		return (EReference) uncertaintyInducedFailureTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUncertaintyRepository() {
		return uncertaintyRepositoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUncertaintyRepository_UncertaintyInducedFailureTypes() {
		return (EReference) uncertaintyRepositoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUncertaintyRepository_ArchitecturalCountermeasures() {
		return (EReference) uncertaintyRepositoryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUncertaintyRepository_ArchitecturalPreconditions() {
		return (EReference) uncertaintyRepositoryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArchitecturalCountermeasure() {
		return architecturalCountermeasureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArchitecturalCountermeasure_ArchitecturalPreconditions() {
		return (EReference) architecturalCountermeasureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArchitecturalCountermeasure_Appliedfailuretype() {
		return (EReference) architecturalCountermeasureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArchitecturalPrecondition() {
		return architecturalPreconditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getActiveComponentPrecondition() {
		return activeComponentPreconditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getActiveComponentPrecondition_RequiredActiveComponent() {
		return (EReference) activeComponentPreconditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUncertaintyImprovement() {
		return uncertaintyImprovementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDeterministicImprovement() {
		return deterministicImprovementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDeterministicImprovement_MappingTable() {
		return (EReference) deterministicImprovementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getProbabilisticImprovement() {
		return probabilisticImprovementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProbabilisticImprovement_ProbabilityDistribution() {
		return (EReference) probabilisticImprovementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMapEntry() {
		return mapEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMapEntry_Key() {
		return (EAttribute) mapEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMapEntry_Value() {
		return (EAttribute) mapEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUncertaintySpecificCountermeasure() {
		return uncertaintySpecificCountermeasureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUncertaintySpecificCountermeasure_TargetUncertainty() {
		return (EReference) uncertaintySpecificCountermeasureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUncertaintySpecificCountermeasure_UncertaintyImprovement() {
		return (EReference) uncertaintySpecificCountermeasureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGlobalUncertaintyCountermeasure() {
		return globalUncertaintyCountermeasureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGlobalUncertaintyCountermeasure_ImprovedUncertaintyModel() {
		return (EReference) globalUncertaintyCountermeasureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UncertaintyFactory getUncertaintyFactory() {
		return (UncertaintyFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		uncertaintyInducedFailureTypeEClass = createEClass(UNCERTAINTY_INDUCED_FAILURE_TYPE);
		createEReference(uncertaintyInducedFailureTypeEClass, UNCERTAINTY_INDUCED_FAILURE_TYPE__REFINES);
		createEReference(uncertaintyInducedFailureTypeEClass, UNCERTAINTY_INDUCED_FAILURE_TYPE__UNCERTAINTY_MODEL);
		createEReference(uncertaintyInducedFailureTypeEClass, UNCERTAINTY_INDUCED_FAILURE_TYPE__FAILURE_VARIABLE);
		createEReference(uncertaintyInducedFailureTypeEClass,
				UNCERTAINTY_INDUCED_FAILURE_TYPE__ARCHITECTURAL_PRECONDITIONS);

		uncertaintyRepositoryEClass = createEClass(UNCERTAINTY_REPOSITORY);
		createEReference(uncertaintyRepositoryEClass, UNCERTAINTY_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES);
		createEReference(uncertaintyRepositoryEClass, UNCERTAINTY_REPOSITORY__ARCHITECTURAL_COUNTERMEASURES);
		createEReference(uncertaintyRepositoryEClass, UNCERTAINTY_REPOSITORY__ARCHITECTURAL_PRECONDITIONS);

		architecturalCountermeasureEClass = createEClass(ARCHITECTURAL_COUNTERMEASURE);
		createEReference(architecturalCountermeasureEClass, ARCHITECTURAL_COUNTERMEASURE__ARCHITECTURAL_PRECONDITIONS);
		createEReference(architecturalCountermeasureEClass, ARCHITECTURAL_COUNTERMEASURE__APPLIEDFAILURETYPE);

		architecturalPreconditionEClass = createEClass(ARCHITECTURAL_PRECONDITION);

		activeComponentPreconditionEClass = createEClass(ACTIVE_COMPONENT_PRECONDITION);
		createEReference(activeComponentPreconditionEClass, ACTIVE_COMPONENT_PRECONDITION__REQUIRED_ACTIVE_COMPONENT);

		uncertaintyImprovementEClass = createEClass(UNCERTAINTY_IMPROVEMENT);

		deterministicImprovementEClass = createEClass(DETERMINISTIC_IMPROVEMENT);
		createEReference(deterministicImprovementEClass, DETERMINISTIC_IMPROVEMENT__MAPPING_TABLE);

		probabilisticImprovementEClass = createEClass(PROBABILISTIC_IMPROVEMENT);
		createEReference(probabilisticImprovementEClass, PROBABILISTIC_IMPROVEMENT__PROBABILITY_DISTRIBUTION);

		mapEntryEClass = createEClass(MAP_ENTRY);
		createEAttribute(mapEntryEClass, MAP_ENTRY__KEY);
		createEAttribute(mapEntryEClass, MAP_ENTRY__VALUE);

		uncertaintySpecificCountermeasureEClass = createEClass(UNCERTAINTY_SPECIFIC_COUNTERMEASURE);
		createEReference(uncertaintySpecificCountermeasureEClass,
				UNCERTAINTY_SPECIFIC_COUNTERMEASURE__TARGET_UNCERTAINTY);
		createEReference(uncertaintySpecificCountermeasureEClass,
				UNCERTAINTY_SPECIFIC_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT);

		globalUncertaintyCountermeasureEClass = createEClass(GLOBAL_UNCERTAINTY_COUNTERMEASURE);
		createEReference(globalUncertaintyCountermeasureEClass,
				GLOBAL_UNCERTAINTY_COUNTERMEASURE__IMPROVED_UNCERTAINTY_MODEL);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		tools.mdsd.modelingfoundations.identifier.IdentifierPackage theIdentifierPackage_1 = (tools.mdsd.modelingfoundations.identifier.IdentifierPackage) EPackage.Registry.INSTANCE
				.getEPackage(tools.mdsd.modelingfoundations.identifier.IdentifierPackage.eNS_URI);
		ReliabilityPackage theReliabilityPackage = (ReliabilityPackage) EPackage.Registry.INSTANCE
				.getEPackage(ReliabilityPackage.eNS_URI);
		StaticmodelPackage theStaticmodelPackage = (StaticmodelPackage) EPackage.Registry.INSTANCE
				.getEPackage(StaticmodelPackage.eNS_URI);
		EntityPackage theEntityPackage = (EntityPackage) EPackage.Registry.INSTANCE.getEPackage(EntityPackage.eNS_URI);
		DistributionfunctionPackage theDistributionfunctionPackage = (DistributionfunctionPackage) EPackage.Registry.INSTANCE
				.getEPackage(DistributionfunctionPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		TemplatevariablePackage theTemplatevariablePackage = (TemplatevariablePackage) EPackage.Registry.INSTANCE
				.getEPackage(TemplatevariablePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		uncertaintyInducedFailureTypeEClass.getESuperTypes().add(theIdentifierPackage_1.getEntity());
		uncertaintyRepositoryEClass.getESuperTypes().add(theIdentifierPackage_1.getEntity());
		architecturalCountermeasureEClass.getESuperTypes().add(theIdentifierPackage_1.getEntity());
		architecturalPreconditionEClass.getESuperTypes().add(theIdentifierPackage_1.getEntity());
		activeComponentPreconditionEClass.getESuperTypes().add(this.getArchitecturalPrecondition());
		deterministicImprovementEClass.getESuperTypes().add(this.getUncertaintyImprovement());
		probabilisticImprovementEClass.getESuperTypes().add(this.getUncertaintyImprovement());
		uncertaintySpecificCountermeasureEClass.getESuperTypes().add(this.getArchitecturalCountermeasure());
		globalUncertaintyCountermeasureEClass.getESuperTypes().add(this.getArchitecturalCountermeasure());

		// Initialize classes, features, and operations; add parameters
		initEClass(uncertaintyInducedFailureTypeEClass, UncertaintyInducedFailureType.class,
				"UncertaintyInducedFailureType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUncertaintyInducedFailureType_Refines(), theReliabilityPackage.getFailureType(), null,
				"refines", null, 1, 1, UncertaintyInducedFailureType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUncertaintyInducedFailureType_UncertaintyModel(),
				theStaticmodelPackage.getGroundProbabilisticNetwork(), null, "uncertaintyModel", null, 1, 1,
				UncertaintyInducedFailureType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUncertaintyInducedFailureType_FailureVariable(),
				theStaticmodelPackage.getGroundRandomVariable(), null, "failureVariable", null, 0, 1,
				UncertaintyInducedFailureType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUncertaintyInducedFailureType_ArchitecturalPreconditions(),
				this.getArchitecturalPrecondition(), null, "architecturalPreconditions", null, 0, -1,
				UncertaintyInducedFailureType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(uncertaintyRepositoryEClass, UncertaintyRepository.class, "UncertaintyRepository", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUncertaintyRepository_UncertaintyInducedFailureTypes(),
				this.getUncertaintyInducedFailureType(), null, "uncertaintyInducedFailureTypes", null, 0, -1,
				UncertaintyRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUncertaintyRepository_ArchitecturalCountermeasures(), this.getArchitecturalCountermeasure(),
				null, "architecturalCountermeasures", null, 0, -1, UncertaintyRepository.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getUncertaintyRepository_ArchitecturalPreconditions(), this.getArchitecturalPrecondition(), null,
				"architecturalPreconditions", null, 0, -1, UncertaintyRepository.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(architecturalCountermeasureEClass, ArchitecturalCountermeasure.class, "ArchitecturalCountermeasure",
				IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArchitecturalCountermeasure_ArchitecturalPreconditions(), this.getArchitecturalPrecondition(),
				null, "architecturalPreconditions", null, 1, -1, ArchitecturalCountermeasure.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getArchitecturalCountermeasure_Appliedfailuretype(), this.getUncertaintyInducedFailureType(),
				null, "appliedfailuretype", null, 1, 1, ArchitecturalCountermeasure.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(architecturalPreconditionEClass, ArchitecturalPrecondition.class, "ArchitecturalPrecondition",
				IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(activeComponentPreconditionEClass, ActiveComponentPrecondition.class, "ActiveComponentPrecondition",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getActiveComponentPrecondition_RequiredActiveComponent(),
				theEntityPackage.getInterfaceProvidingRequiringEntity(), null, "requiredActiveComponent", null, 1, 1,
				ActiveComponentPrecondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(uncertaintyImprovementEClass, UncertaintyImprovement.class, "UncertaintyImprovement", IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(deterministicImprovementEClass, DeterministicImprovement.class, "DeterministicImprovement",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDeterministicImprovement_MappingTable(), this.getMapEntry(), null, "mappingTable", null, 1,
				-1, DeterministicImprovement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(probabilisticImprovementEClass, ProbabilisticImprovement.class, "ProbabilisticImprovement",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProbabilisticImprovement_ProbabilityDistribution(),
				theDistributionfunctionPackage.getProbabilityDistribution(), null, "probabilityDistribution", null, 1,
				1, ProbabilisticImprovement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapEntryEClass, MapEntry.class, "MapEntry", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapEntry_Key(), theEcorePackage.getEString(), "key", "", 1, 1, MapEntry.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapEntry_Value(), theEcorePackage.getEString(), "value", null, 1, 1, MapEntry.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(uncertaintySpecificCountermeasureEClass, UncertaintySpecificCountermeasure.class,
				"UncertaintySpecificCountermeasure", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUncertaintySpecificCountermeasure_TargetUncertainty(),
				theTemplatevariablePackage.getTemplateVariable(), null, "targetUncertainty", null, 1, 1,
				UncertaintySpecificCountermeasure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUncertaintySpecificCountermeasure_UncertaintyImprovement(), this.getUncertaintyImprovement(),
				null, "uncertaintyImprovement", null, 1, 1, UncertaintySpecificCountermeasure.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(globalUncertaintyCountermeasureEClass, GlobalUncertaintyCountermeasure.class,
				"GlobalUncertaintyCountermeasure", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGlobalUncertaintyCountermeasure_ImprovedUncertaintyModel(),
				theStaticmodelPackage.getGroundProbabilisticNetwork(), null, "improvedUncertaintyModel", null, 1, 1,
				GlobalUncertaintyCountermeasure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //UncertaintyPackageImpl
