/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyImprovement;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintySpecificCountermeasure;

import org.palladiosimulator.envdyn.environment.templatevariable.TemplateVariable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Specific Countermeasure</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintySpecificCountermeasureImpl#getTargetUncertainty <em>Target Uncertainty</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintySpecificCountermeasureImpl#getUncertaintyImprovement <em>Uncertainty Improvement</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UncertaintySpecificCountermeasureImpl extends ArchitecturalCountermeasureImpl
		implements UncertaintySpecificCountermeasure {
	/**
	 * The cached value of the '{@link #getTargetUncertainty() <em>Target Uncertainty</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetUncertainty()
	 * @generated
	 * @ordered
	 */
	protected TemplateVariable targetUncertainty;

	/**
	 * The cached value of the '{@link #getUncertaintyImprovement() <em>Uncertainty Improvement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUncertaintyImprovement()
	 * @generated
	 * @ordered
	 */
	protected UncertaintyImprovement uncertaintyImprovement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UncertaintySpecificCountermeasureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UncertaintyPackage.Literals.UNCERTAINTY_SPECIFIC_COUNTERMEASURE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TemplateVariable getTargetUncertainty() {
		if (targetUncertainty != null && targetUncertainty.eIsProxy()) {
			InternalEObject oldTargetUncertainty = (InternalEObject) targetUncertainty;
			targetUncertainty = (TemplateVariable) eResolveProxy(oldTargetUncertainty);
			if (targetUncertainty != oldTargetUncertainty) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__TARGET_UNCERTAINTY,
							oldTargetUncertainty, targetUncertainty));
			}
		}
		return targetUncertainty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateVariable basicGetTargetUncertainty() {
		return targetUncertainty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetUncertainty(TemplateVariable newTargetUncertainty) {
		TemplateVariable oldTargetUncertainty = targetUncertainty;
		targetUncertainty = newTargetUncertainty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__TARGET_UNCERTAINTY, oldTargetUncertainty,
					targetUncertainty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UncertaintyImprovement getUncertaintyImprovement() {
		return uncertaintyImprovement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUncertaintyImprovement(UncertaintyImprovement newUncertaintyImprovement,
			NotificationChain msgs) {
		UncertaintyImprovement oldUncertaintyImprovement = uncertaintyImprovement;
		uncertaintyImprovement = newUncertaintyImprovement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT,
					oldUncertaintyImprovement, newUncertaintyImprovement);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUncertaintyImprovement(UncertaintyImprovement newUncertaintyImprovement) {
		if (newUncertaintyImprovement != uncertaintyImprovement) {
			NotificationChain msgs = null;
			if (uncertaintyImprovement != null)
				msgs = ((InternalEObject) uncertaintyImprovement).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT,
						null, msgs);
			if (newUncertaintyImprovement != null)
				msgs = ((InternalEObject) newUncertaintyImprovement).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT,
						null, msgs);
			msgs = basicSetUncertaintyImprovement(newUncertaintyImprovement, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT,
					newUncertaintyImprovement, newUncertaintyImprovement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT:
			return basicSetUncertaintyImprovement(null, msgs);
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
		case UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__TARGET_UNCERTAINTY:
			if (resolve)
				return getTargetUncertainty();
			return basicGetTargetUncertainty();
		case UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT:
			return getUncertaintyImprovement();
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
		case UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__TARGET_UNCERTAINTY:
			setTargetUncertainty((TemplateVariable) newValue);
			return;
		case UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT:
			setUncertaintyImprovement((UncertaintyImprovement) newValue);
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
		case UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__TARGET_UNCERTAINTY:
			setTargetUncertainty((TemplateVariable) null);
			return;
		case UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT:
			setUncertaintyImprovement((UncertaintyImprovement) null);
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
		case UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__TARGET_UNCERTAINTY:
			return targetUncertainty != null;
		case UncertaintyPackage.UNCERTAINTY_SPECIFIC_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT:
			return uncertaintyImprovement != null;
		}
		return super.eIsSet(featureID);
	}

} //UncertaintySpecificCountermeasureImpl
