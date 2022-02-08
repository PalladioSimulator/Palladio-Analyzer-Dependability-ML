/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.dependability.reliability.uncertainty.GlobalUncertaintyCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;

import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Global Uncertainty Countermeasure</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.GlobalUncertaintyCountermeasureImpl#getImprovedUncertaintyModel <em>Improved Uncertainty Model</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GlobalUncertaintyCountermeasureImpl extends ArchitecturalCountermeasureImpl
		implements GlobalUncertaintyCountermeasure {
	/**
	 * The cached value of the '{@link #getImprovedUncertaintyModel() <em>Improved Uncertainty Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImprovedUncertaintyModel()
	 * @generated
	 * @ordered
	 */
	protected GroundProbabilisticNetwork improvedUncertaintyModel;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GlobalUncertaintyCountermeasureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UncertaintyPackage.Literals.GLOBAL_UNCERTAINTY_COUNTERMEASURE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GroundProbabilisticNetwork getImprovedUncertaintyModel() {
		if (improvedUncertaintyModel != null && improvedUncertaintyModel.eIsProxy()) {
			InternalEObject oldImprovedUncertaintyModel = (InternalEObject) improvedUncertaintyModel;
			improvedUncertaintyModel = (GroundProbabilisticNetwork) eResolveProxy(oldImprovedUncertaintyModel);
			if (improvedUncertaintyModel != oldImprovedUncertaintyModel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							UncertaintyPackage.GLOBAL_UNCERTAINTY_COUNTERMEASURE__IMPROVED_UNCERTAINTY_MODEL,
							oldImprovedUncertaintyModel, improvedUncertaintyModel));
			}
		}
		return improvedUncertaintyModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroundProbabilisticNetwork basicGetImprovedUncertaintyModel() {
		return improvedUncertaintyModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImprovedUncertaintyModel(GroundProbabilisticNetwork newImprovedUncertaintyModel) {
		GroundProbabilisticNetwork oldImprovedUncertaintyModel = improvedUncertaintyModel;
		improvedUncertaintyModel = newImprovedUncertaintyModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UncertaintyPackage.GLOBAL_UNCERTAINTY_COUNTERMEASURE__IMPROVED_UNCERTAINTY_MODEL,
					oldImprovedUncertaintyModel, improvedUncertaintyModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UncertaintyPackage.GLOBAL_UNCERTAINTY_COUNTERMEASURE__IMPROVED_UNCERTAINTY_MODEL:
			if (resolve)
				return getImprovedUncertaintyModel();
			return basicGetImprovedUncertaintyModel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UncertaintyPackage.GLOBAL_UNCERTAINTY_COUNTERMEASURE__IMPROVED_UNCERTAINTY_MODEL:
			setImprovedUncertaintyModel((GroundProbabilisticNetwork) newValue);
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
		case UncertaintyPackage.GLOBAL_UNCERTAINTY_COUNTERMEASURE__IMPROVED_UNCERTAINTY_MODEL:
			setImprovedUncertaintyModel((GroundProbabilisticNetwork) null);
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
		case UncertaintyPackage.GLOBAL_UNCERTAINTY_COUNTERMEASURE__IMPROVED_UNCERTAINTY_MODEL:
			return improvedUncertaintyModel != null;
		}
		return super.eIsSet(featureID);
	}

} //GlobalUncertaintyCountermeasureImpl
