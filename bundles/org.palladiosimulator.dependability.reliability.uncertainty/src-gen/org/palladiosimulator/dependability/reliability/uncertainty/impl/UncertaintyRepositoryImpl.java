/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository;

import tools.mdsd.modelingfoundations.identifier.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyRepositoryImpl#getUncertaintyInducedFailureTypes <em>Uncertainty Induced Failure Types</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyRepositoryImpl#getArchitecturalCountermeasures <em>Architectural Countermeasures</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyRepositoryImpl#getArchitecturalPreconditions <em>Architectural Preconditions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UncertaintyRepositoryImpl extends EntityImpl implements UncertaintyRepository {
	/**
	 * The cached value of the '{@link #getUncertaintyInducedFailureTypes() <em>Uncertainty Induced Failure Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUncertaintyInducedFailureTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<UncertaintyInducedFailureType> uncertaintyInducedFailureTypes;

	/**
	 * The cached value of the '{@link #getArchitecturalCountermeasures() <em>Architectural Countermeasures</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArchitecturalCountermeasures()
	 * @generated
	 * @ordered
	 */
	protected EList<ArchitecturalCountermeasure> architecturalCountermeasures;

	/**
	 * The cached value of the '{@link #getArchitecturalPreconditions() <em>Architectural Preconditions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArchitecturalPreconditions()
	 * @generated
	 * @ordered
	 */
	protected EList<ArchitecturalPrecondition> architecturalPreconditions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UncertaintyRepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UncertaintyPackage.Literals.UNCERTAINTY_REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<UncertaintyInducedFailureType> getUncertaintyInducedFailureTypes() {
		if (uncertaintyInducedFailureTypes == null) {
			uncertaintyInducedFailureTypes = new EObjectContainmentEList<UncertaintyInducedFailureType>(
					UncertaintyInducedFailureType.class, this,
					UncertaintyPackage.UNCERTAINTY_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES);
		}
		return uncertaintyInducedFailureTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ArchitecturalCountermeasure> getArchitecturalCountermeasures() {
		if (architecturalCountermeasures == null) {
			architecturalCountermeasures = new EObjectContainmentEList<ArchitecturalCountermeasure>(
					ArchitecturalCountermeasure.class, this,
					UncertaintyPackage.UNCERTAINTY_REPOSITORY__ARCHITECTURAL_COUNTERMEASURES);
		}
		return architecturalCountermeasures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ArchitecturalPrecondition> getArchitecturalPreconditions() {
		if (architecturalPreconditions == null) {
			architecturalPreconditions = new EObjectContainmentEList<ArchitecturalPrecondition>(
					ArchitecturalPrecondition.class, this,
					UncertaintyPackage.UNCERTAINTY_REPOSITORY__ARCHITECTURAL_PRECONDITIONS);
		}
		return architecturalPreconditions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES:
			return ((InternalEList<?>) getUncertaintyInducedFailureTypes()).basicRemove(otherEnd, msgs);
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__ARCHITECTURAL_COUNTERMEASURES:
			return ((InternalEList<?>) getArchitecturalCountermeasures()).basicRemove(otherEnd, msgs);
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__ARCHITECTURAL_PRECONDITIONS:
			return ((InternalEList<?>) getArchitecturalPreconditions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES:
			return getUncertaintyInducedFailureTypes();
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__ARCHITECTURAL_COUNTERMEASURES:
			return getArchitecturalCountermeasures();
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__ARCHITECTURAL_PRECONDITIONS:
			return getArchitecturalPreconditions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES:
			getUncertaintyInducedFailureTypes().clear();
			getUncertaintyInducedFailureTypes().addAll((Collection<? extends UncertaintyInducedFailureType>) newValue);
			return;
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__ARCHITECTURAL_COUNTERMEASURES:
			getArchitecturalCountermeasures().clear();
			getArchitecturalCountermeasures().addAll((Collection<? extends ArchitecturalCountermeasure>) newValue);
			return;
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__ARCHITECTURAL_PRECONDITIONS:
			getArchitecturalPreconditions().clear();
			getArchitecturalPreconditions().addAll((Collection<? extends ArchitecturalPrecondition>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES:
			getUncertaintyInducedFailureTypes().clear();
			return;
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__ARCHITECTURAL_COUNTERMEASURES:
			getArchitecturalCountermeasures().clear();
			return;
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__ARCHITECTURAL_PRECONDITIONS:
			getArchitecturalPreconditions().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES:
			return uncertaintyInducedFailureTypes != null && !uncertaintyInducedFailureTypes.isEmpty();
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__ARCHITECTURAL_COUNTERMEASURES:
			return architecturalCountermeasures != null && !architecturalCountermeasures.isEmpty();
		case UncertaintyPackage.UNCERTAINTY_REPOSITORY__ARCHITECTURAL_PRECONDITIONS:
			return architecturalPreconditions != null && !architecturalPreconditions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //UncertaintyRepositoryImpl
