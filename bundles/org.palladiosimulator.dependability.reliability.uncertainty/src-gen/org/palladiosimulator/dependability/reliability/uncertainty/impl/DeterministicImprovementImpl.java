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

import org.palladiosimulator.dependability.reliability.uncertainty.DeterministicImprovement;
import org.palladiosimulator.dependability.reliability.uncertainty.MapEntry;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Deterministic Improvement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.DeterministicImprovementImpl#getMappingTable <em>Mapping Table</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DeterministicImprovementImpl extends UncertaintyImprovementImpl implements DeterministicImprovement {
	/**
	 * The cached value of the '{@link #getMappingTable() <em>Mapping Table</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappingTable()
	 * @generated
	 * @ordered
	 */
	protected EList<MapEntry> mappingTable;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeterministicImprovementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UncertaintyPackage.Literals.DETERMINISTIC_IMPROVEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<MapEntry> getMappingTable() {
		if (mappingTable == null) {
			mappingTable = new EObjectContainmentEList<MapEntry>(MapEntry.class, this,
					UncertaintyPackage.DETERMINISTIC_IMPROVEMENT__MAPPING_TABLE);
		}
		return mappingTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case UncertaintyPackage.DETERMINISTIC_IMPROVEMENT__MAPPING_TABLE:
			return ((InternalEList<?>) getMappingTable()).basicRemove(otherEnd, msgs);
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
		case UncertaintyPackage.DETERMINISTIC_IMPROVEMENT__MAPPING_TABLE:
			return getMappingTable();
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
		case UncertaintyPackage.DETERMINISTIC_IMPROVEMENT__MAPPING_TABLE:
			getMappingTable().clear();
			getMappingTable().addAll((Collection<? extends MapEntry>) newValue);
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
		case UncertaintyPackage.DETERMINISTIC_IMPROVEMENT__MAPPING_TABLE:
			getMappingTable().clear();
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
		case UncertaintyPackage.DETERMINISTIC_IMPROVEMENT__MAPPING_TABLE:
			return mappingTable != null && !mappingTable.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DeterministicImprovementImpl
