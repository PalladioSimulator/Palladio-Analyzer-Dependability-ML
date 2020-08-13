/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyImprovement;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;

import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;

import tools.mdsd.modelingfoundations.identifier.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Architectural Countermeasure</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.ArchitecturalCountermeasureImpl#getArchitecturalPreconditions <em>Architectural Preconditions</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.ArchitecturalCountermeasureImpl#getTargetUncertainty <em>Target Uncertainty</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.ArchitecturalCountermeasureImpl#getUncertaintyImprovement <em>Uncertainty Improvement</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArchitecturalCountermeasureImpl extends EntityImpl implements ArchitecturalCountermeasure {
	/**
	 * The cached value of the '{@link #getArchitecturalPreconditions() <em>Architectural Preconditions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArchitecturalPreconditions()
	 * @generated
	 * @ordered
	 */
	protected EList<ArchitecturalPrecondition> architecturalPreconditions;

	/**
	 * The cached value of the '{@link #getTargetUncertainty() <em>Target Uncertainty</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetUncertainty()
	 * @generated
	 * @ordered
	 */
	protected GroundRandomVariable targetUncertainty;

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
	protected ArchitecturalCountermeasureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UncertaintyPackage.Literals.ARCHITECTURAL_COUNTERMEASURE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ArchitecturalPrecondition> getArchitecturalPreconditions() {
		if (architecturalPreconditions == null) {
			architecturalPreconditions = new EObjectResolvingEList<ArchitecturalPrecondition>(
					ArchitecturalPrecondition.class, this,
					UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__ARCHITECTURAL_PRECONDITIONS);
		}
		return architecturalPreconditions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GroundRandomVariable getTargetUncertainty() {
		if (targetUncertainty != null && targetUncertainty.eIsProxy()) {
			InternalEObject oldTargetUncertainty = (InternalEObject) targetUncertainty;
			targetUncertainty = (GroundRandomVariable) eResolveProxy(oldTargetUncertainty);
			if (targetUncertainty != oldTargetUncertainty) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__TARGET_UNCERTAINTY, oldTargetUncertainty,
							targetUncertainty));
			}
		}
		return targetUncertainty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroundRandomVariable basicGetTargetUncertainty() {
		return targetUncertainty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetUncertainty(GroundRandomVariable newTargetUncertainty) {
		GroundRandomVariable oldTargetUncertainty = targetUncertainty;
		targetUncertainty = newTargetUncertainty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__TARGET_UNCERTAINTY, oldTargetUncertainty,
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
					UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT, oldUncertaintyImprovement,
					newUncertaintyImprovement);
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
				msgs = ((InternalEObject) uncertaintyImprovement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT, null, msgs);
			if (newUncertaintyImprovement != null)
				msgs = ((InternalEObject) newUncertaintyImprovement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT, null, msgs);
			msgs = basicSetUncertaintyImprovement(newUncertaintyImprovement, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT, newUncertaintyImprovement,
					newUncertaintyImprovement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT:
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
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__ARCHITECTURAL_PRECONDITIONS:
			return getArchitecturalPreconditions();
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__TARGET_UNCERTAINTY:
			if (resolve)
				return getTargetUncertainty();
			return basicGetTargetUncertainty();
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT:
			return getUncertaintyImprovement();
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
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__ARCHITECTURAL_PRECONDITIONS:
			getArchitecturalPreconditions().clear();
			getArchitecturalPreconditions().addAll((Collection<? extends ArchitecturalPrecondition>) newValue);
			return;
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__TARGET_UNCERTAINTY:
			setTargetUncertainty((GroundRandomVariable) newValue);
			return;
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT:
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
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__ARCHITECTURAL_PRECONDITIONS:
			getArchitecturalPreconditions().clear();
			return;
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__TARGET_UNCERTAINTY:
			setTargetUncertainty((GroundRandomVariable) null);
			return;
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT:
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
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__ARCHITECTURAL_PRECONDITIONS:
			return architecturalPreconditions != null && !architecturalPreconditions.isEmpty();
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__TARGET_UNCERTAINTY:
			return targetUncertainty != null;
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__UNCERTAINTY_IMPROVEMENT:
			return uncertaintyImprovement != null;
		}
		return super.eIsSet(featureID);
	}

} //ArchitecturalCountermeasureImpl
