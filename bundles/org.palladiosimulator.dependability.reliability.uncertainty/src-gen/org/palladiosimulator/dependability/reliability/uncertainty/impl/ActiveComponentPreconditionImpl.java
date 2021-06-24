/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.dependability.reliability.uncertainty.ActiveComponentPrecondition;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Active Component Precondition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.ActiveComponentPreconditionImpl#getRequiredActiveComponent <em>Required Active Component</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ActiveComponentPreconditionImpl extends ArchitecturalPreconditionImpl
		implements ActiveComponentPrecondition {
	/**
	 * The cached value of the '{@link #getRequiredActiveComponent() <em>Required Active Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequiredActiveComponent()
	 * @generated
	 * @ordered
	 */
	protected InterfaceProvidingRequiringEntity requiredActiveComponent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActiveComponentPreconditionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UncertaintyPackage.Literals.ACTIVE_COMPONENT_PRECONDITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InterfaceProvidingRequiringEntity getRequiredActiveComponent() {
		if (requiredActiveComponent != null && ((EObject) requiredActiveComponent).eIsProxy()) {
			InternalEObject oldRequiredActiveComponent = (InternalEObject) requiredActiveComponent;
			requiredActiveComponent = (InterfaceProvidingRequiringEntity) eResolveProxy(oldRequiredActiveComponent);
			if (requiredActiveComponent != oldRequiredActiveComponent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							UncertaintyPackage.ACTIVE_COMPONENT_PRECONDITION__REQUIRED_ACTIVE_COMPONENT,
							oldRequiredActiveComponent, requiredActiveComponent));
			}
		}
		return requiredActiveComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InterfaceProvidingRequiringEntity basicGetRequiredActiveComponent() {
		return requiredActiveComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRequiredActiveComponent(InterfaceProvidingRequiringEntity newRequiredActiveComponent) {
		InterfaceProvidingRequiringEntity oldRequiredActiveComponent = requiredActiveComponent;
		requiredActiveComponent = newRequiredActiveComponent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UncertaintyPackage.ACTIVE_COMPONENT_PRECONDITION__REQUIRED_ACTIVE_COMPONENT,
					oldRequiredActiveComponent, requiredActiveComponent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UncertaintyPackage.ACTIVE_COMPONENT_PRECONDITION__REQUIRED_ACTIVE_COMPONENT:
			if (resolve)
				return getRequiredActiveComponent();
			return basicGetRequiredActiveComponent();
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
		case UncertaintyPackage.ACTIVE_COMPONENT_PRECONDITION__REQUIRED_ACTIVE_COMPONENT:
			setRequiredActiveComponent((InterfaceProvidingRequiringEntity) newValue);
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
		case UncertaintyPackage.ACTIVE_COMPONENT_PRECONDITION__REQUIRED_ACTIVE_COMPONENT:
			setRequiredActiveComponent((InterfaceProvidingRequiringEntity) null);
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
		case UncertaintyPackage.ACTIVE_COMPONENT_PRECONDITION__REQUIRED_ACTIVE_COMPONENT:
			return requiredActiveComponent != null;
		}
		return super.eIsSet(featureID);
	}

} //ActiveComponentPreconditionImpl
