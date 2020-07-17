/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyFailureTypeRepository;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Failure Type Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyFailureTypeRepositoryImpl#getUncertaintyInducedFailureTypes <em>Uncertainty Induced Failure Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UncertaintyFailureTypeRepositoryImpl extends MinimalEObjectImpl.Container
		implements UncertaintyFailureTypeRepository {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UncertaintyFailureTypeRepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UncertaintyPackage.Literals.UNCERTAINTY_FAILURE_TYPE_REPOSITORY;
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
					UncertaintyPackage.UNCERTAINTY_FAILURE_TYPE_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES);
		}
		return uncertaintyInducedFailureTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case UncertaintyPackage.UNCERTAINTY_FAILURE_TYPE_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES:
			return ((InternalEList<?>) getUncertaintyInducedFailureTypes()).basicRemove(otherEnd, msgs);
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
		case UncertaintyPackage.UNCERTAINTY_FAILURE_TYPE_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES:
			return getUncertaintyInducedFailureTypes();
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
		case UncertaintyPackage.UNCERTAINTY_FAILURE_TYPE_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES:
			getUncertaintyInducedFailureTypes().clear();
			getUncertaintyInducedFailureTypes().addAll((Collection<? extends UncertaintyInducedFailureType>) newValue);
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
		case UncertaintyPackage.UNCERTAINTY_FAILURE_TYPE_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES:
			getUncertaintyInducedFailureTypes().clear();
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
		case UncertaintyPackage.UNCERTAINTY_FAILURE_TYPE_REPOSITORY__UNCERTAINTY_INDUCED_FAILURE_TYPES:
			return uncertaintyInducedFailureTypes != null && !uncertaintyInducedFailureTypes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //UncertaintyFailureTypeRepositoryImpl
