/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.palladiosimulator.dependability.reliability.uncertainty.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UncertaintyFactoryImpl extends EFactoryImpl implements UncertaintyFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static UncertaintyFactory init() {
		try {
			UncertaintyFactory theUncertaintyFactory = (UncertaintyFactory) EPackage.Registry.INSTANCE
					.getEFactory(UncertaintyPackage.eNS_URI);
			if (theUncertaintyFactory != null) {
				return theUncertaintyFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new UncertaintyFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncertaintyFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE:
			return createUncertaintyInducedFailureType();
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY:
			return createUncertaintyRepository();
		case UncertaintyPackage.ACTIVE_COMPONENT_PRECONDITION:
			return createActiveComponentPrecondition();
		case UncertaintyPackage.DETERMINISTIC_IMPROVEMENT:
			return createDeterministicImprovement();
		case UncertaintyPackage.PROBABILISTIC_IMPROVEMENT:
			return createProbabilisticImprovement();
		case UncertaintyPackage.MAP_ENTRY:
			return createMapEntry();
		case UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE:
			return createUncertaintySpecificCountermeasure();
		case UncertaintyPackage.GLOBAL_UNCERTAINTY_COUNTERMEASURE:
			return createGlobalUncertaintyCountermeasure();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UncertaintyInducedFailureType createUncertaintyInducedFailureType() {
		UncertaintyInducedFailureTypeImpl uncertaintyInducedFailureType = new UncertaintyInducedFailureTypeImpl();
		return uncertaintyInducedFailureType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UncertaintyRepository createUncertaintyRepository() {
		UncertaintyRepositoryImpl uncertaintyRepository = new UncertaintyRepositoryImpl();
		return uncertaintyRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ActiveComponentPrecondition createActiveComponentPrecondition() {
		ActiveComponentPreconditionImpl activeComponentPrecondition = new ActiveComponentPreconditionImpl();
		return activeComponentPrecondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DeterministicImprovement createDeterministicImprovement() {
		DeterministicImprovementImpl deterministicImprovement = new DeterministicImprovementImpl();
		return deterministicImprovement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProbabilisticImprovement createProbabilisticImprovement() {
		ProbabilisticImprovementImpl probabilisticImprovement = new ProbabilisticImprovementImpl();
		return probabilisticImprovement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MapEntry createMapEntry() {
		MapEntryImpl mapEntry = new MapEntryImpl();
		return mapEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UncertaintySpecificCountermeasure createUncertaintySpecificCountermeasure() {
		UncertaintySpecificCountermeasureImpl uncertaintySpecificCountermeasure = new UncertaintySpecificCountermeasureImpl();
		return uncertaintySpecificCountermeasure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GlobalUncertaintyCountermeasure createGlobalUncertaintyCountermeasure() {
		GlobalUncertaintyCountermeasureImpl globalUncertaintyCountermeasure = new GlobalUncertaintyCountermeasureImpl();
		return globalUncertaintyCountermeasure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UncertaintyPackage getUncertaintyPackage() {
		return (UncertaintyPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static UncertaintyPackage getPackage() {
		return UncertaintyPackage.eINSTANCE;
	}

} //UncertaintyFactoryImpl
