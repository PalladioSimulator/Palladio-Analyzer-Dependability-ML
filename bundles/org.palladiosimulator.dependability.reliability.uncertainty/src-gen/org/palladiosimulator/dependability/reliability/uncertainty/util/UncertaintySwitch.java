/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.palladiosimulator.dependability.reliability.uncertainty.*;

import tools.mdsd.modelingfoundations.identifier.Entity;
import tools.mdsd.modelingfoundations.identifier.Identifier;
import tools.mdsd.modelingfoundations.identifier.NamedElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage
 * @generated
 */
public class UncertaintySwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static UncertaintyPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncertaintySwitch() {
		if (modelPackage == null) {
			modelPackage = UncertaintyPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE: {
			UncertaintyInducedFailureType uncertaintyInducedFailureType = (UncertaintyInducedFailureType) theEObject;
			T result = caseUncertaintyInducedFailureType(uncertaintyInducedFailureType);
			if (result == null)
				result = caseEntity(uncertaintyInducedFailureType);
			if (result == null)
				result = caseIdentifier(uncertaintyInducedFailureType);
			if (result == null)
				result = caseNamedElement(uncertaintyInducedFailureType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY: {
			UncertaintyRepository uncertaintyRepository = (UncertaintyRepository) theEObject;
			T result = caseUncertaintyRepository(uncertaintyRepository);
			if (result == null)
				result = caseEntity(uncertaintyRepository);
			if (result == null)
				result = caseIdentifier(uncertaintyRepository);
			if (result == null)
				result = caseNamedElement(uncertaintyRepository);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE: {
			ArchitecturalCountermeasure architecturalCountermeasure = (ArchitecturalCountermeasure) theEObject;
			T result = caseArchitecturalCountermeasure(architecturalCountermeasure);
			if (result == null)
				result = caseEntity(architecturalCountermeasure);
			if (result == null)
				result = caseIdentifier(architecturalCountermeasure);
			if (result == null)
				result = caseNamedElement(architecturalCountermeasure);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UncertaintyPackage.ARCHITECTURAL_PRECONDITION: {
			ArchitecturalPrecondition architecturalPrecondition = (ArchitecturalPrecondition) theEObject;
			T result = caseArchitecturalPrecondition(architecturalPrecondition);
			if (result == null)
				result = caseEntity(architecturalPrecondition);
			if (result == null)
				result = caseIdentifier(architecturalPrecondition);
			if (result == null)
				result = caseNamedElement(architecturalPrecondition);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UncertaintyPackage.ACTIVE_COMPONENT: {
			ActiveComponent activeComponent = (ActiveComponent) theEObject;
			T result = caseActiveComponent(activeComponent);
			if (result == null)
				result = caseArchitecturalPrecondition(activeComponent);
			if (result == null)
				result = caseEntity(activeComponent);
			if (result == null)
				result = caseIdentifier(activeComponent);
			if (result == null)
				result = caseNamedElement(activeComponent);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UncertaintyPackage.UNCERTAINTY_IMPROVEMENT: {
			UncertaintyImprovement uncertaintyImprovement = (UncertaintyImprovement) theEObject;
			T result = caseUncertaintyImprovement(uncertaintyImprovement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UncertaintyPackage.DETERMINISTIC_IMPROVEMENT: {
			DeterministicImprovement deterministicImprovement = (DeterministicImprovement) theEObject;
			T result = caseDeterministicImprovement(deterministicImprovement);
			if (result == null)
				result = caseUncertaintyImprovement(deterministicImprovement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UncertaintyPackage.PROBABILISTIC_IMPROVEMENT: {
			ProbabilisticImprovement probabilisticImprovement = (ProbabilisticImprovement) theEObject;
			T result = caseProbabilisticImprovement(probabilisticImprovement);
			if (result == null)
				result = caseUncertaintyImprovement(probabilisticImprovement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UncertaintyPackage.MAP_ENTRY: {
			MapEntry mapEntry = (MapEntry) theEObject;
			T result = caseMapEntry(mapEntry);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE: {
			UncertaintySpecificCountermeasure uncertaintySpecificCountermeasure = (UncertaintySpecificCountermeasure) theEObject;
			T result = caseUncertaintySpecificCountermeasure(uncertaintySpecificCountermeasure);
			if (result == null)
				result = caseArchitecturalCountermeasure(uncertaintySpecificCountermeasure);
			if (result == null)
				result = caseEntity(uncertaintySpecificCountermeasure);
			if (result == null)
				result = caseIdentifier(uncertaintySpecificCountermeasure);
			if (result == null)
				result = caseNamedElement(uncertaintySpecificCountermeasure);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UncertaintyPackage.GLOBAL_UNCERTAINTY_COUNTERMEASURE: {
			GlobalUncertaintyCountermeasure globalUncertaintyCountermeasure = (GlobalUncertaintyCountermeasure) theEObject;
			T result = caseGlobalUncertaintyCountermeasure(globalUncertaintyCountermeasure);
			if (result == null)
				result = caseArchitecturalCountermeasure(globalUncertaintyCountermeasure);
			if (result == null)
				result = caseEntity(globalUncertaintyCountermeasure);
			if (result == null)
				result = caseIdentifier(globalUncertaintyCountermeasure);
			if (result == null)
				result = caseNamedElement(globalUncertaintyCountermeasure);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UncertaintyPackage.UNCERTAINTY_MODEL_EQUALITY: {
			UncertaintyModelEquality uncertaintyModelEquality = (UncertaintyModelEquality) theEObject;
			T result = caseUncertaintyModelEquality(uncertaintyModelEquality);
			if (result == null)
				result = caseArchitecturalPrecondition(uncertaintyModelEquality);
			if (result == null)
				result = caseEntity(uncertaintyModelEquality);
			if (result == null)
				result = caseIdentifier(uncertaintyModelEquality);
			if (result == null)
				result = caseNamedElement(uncertaintyModelEquality);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Induced Failure Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Induced Failure Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUncertaintyInducedFailureType(UncertaintyInducedFailureType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Repository</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Repository</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUncertaintyRepository(UncertaintyRepository object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Architectural Countermeasure</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Architectural Countermeasure</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArchitecturalCountermeasure(ArchitecturalCountermeasure object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Architectural Precondition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Architectural Precondition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArchitecturalPrecondition(ArchitecturalPrecondition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Active Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Active Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActiveComponent(ActiveComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Improvement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Improvement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUncertaintyImprovement(UncertaintyImprovement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Deterministic Improvement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Deterministic Improvement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeterministicImprovement(DeterministicImprovement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Probabilistic Improvement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Probabilistic Improvement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProbabilisticImprovement(ProbabilisticImprovement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Map Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Map Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapEntry(MapEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Specific Countermeasure</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Specific Countermeasure</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUncertaintySpecificCountermeasure(UncertaintySpecificCountermeasure object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Global Uncertainty Countermeasure</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Global Uncertainty Countermeasure</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGlobalUncertaintyCountermeasure(GlobalUncertaintyCountermeasure object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model Equality</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Equality</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUncertaintyModelEquality(UncertaintyModelEquality object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdentifier(Identifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedElement(NamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntity(Entity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //UncertaintySwitch
