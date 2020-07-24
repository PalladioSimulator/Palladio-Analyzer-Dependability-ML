/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.impl;

import de.uka.ipd.sdq.identifier.IdentifierPackage;

import de.uka.ipd.sdq.probfunction.ProbfunctionPackage;

import de.uka.ipd.sdq.stoex.StoexPackage;

import de.uka.ipd.sdq.units.UnitsPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFactory;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFailureTypeRepository;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;

import org.palladiosimulator.envdyn.environment.dynamicmodel.DynamicmodelPackage;

import org.palladiosimulator.envdyn.environment.staticmodel.StaticmodelPackage;

import org.palladiosimulator.envdyn.environment.templatevariable.TemplatevariablePackage;

import org.palladiosimulator.pcm.PcmPackage;

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
	private EClass uncertaintyFailureTypeRepositoryEClass = null;

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
	public EClass getUncertaintyFailureTypeRepository() {
		return uncertaintyFailureTypeRepositoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUncertaintyFailureTypeRepository_UncertaintyInducedFailureTypes() {
		return (EReference) uncertaintyFailureTypeRepositoryEClass.getEStructuralFeatures().get(0);
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

		uncertaintyFailureTypeRepositoryEClass = createEClass(UNCERTAINTY_FAILURE_TYPE_REPOSITORY);
		createEReference(uncertaintyFailureTypeRepositoryEClass,
				UNCERTAINTY_FAILURE_TYPE_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES);
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
		ReliabilityPackage theReliabilityPackage = (ReliabilityPackage) EPackage.Registry.INSTANCE
				.getEPackage(ReliabilityPackage.eNS_URI);
		StaticmodelPackage theStaticmodelPackage = (StaticmodelPackage) EPackage.Registry.INSTANCE
				.getEPackage(StaticmodelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

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

		initEClass(uncertaintyFailureTypeRepositoryEClass, UncertaintyFailureTypeRepository.class,
				"UncertaintyFailureTypeRepository", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUncertaintyFailureTypeRepository_UncertaintyInducedFailureTypes(),
				this.getUncertaintyInducedFailureType(), null, "uncertaintyInducedFailureTypes", null, 0, -1,
				UncertaintyFailureTypeRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //UncertaintyPackageImpl
