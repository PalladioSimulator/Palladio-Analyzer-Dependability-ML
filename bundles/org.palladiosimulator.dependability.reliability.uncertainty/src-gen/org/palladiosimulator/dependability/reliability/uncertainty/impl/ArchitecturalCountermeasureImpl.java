/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;

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
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.ArchitecturalCountermeasureImpl#getAppliedfailuretype <em>Appliedfailuretype</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ArchitecturalCountermeasureImpl extends EntityImpl implements ArchitecturalCountermeasure {
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
	 * The cached value of the '{@link #getAppliedfailuretype() <em>Appliedfailuretype</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAppliedfailuretype()
	 * @generated
	 * @ordered
	 */
	protected UncertaintyInducedFailureType appliedfailuretype;

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
	public UncertaintyInducedFailureType getAppliedfailuretype() {
		if (appliedfailuretype != null && appliedfailuretype.eIsProxy()) {
			InternalEObject oldAppliedfailuretype = (InternalEObject) appliedfailuretype;
			appliedfailuretype = (UncertaintyInducedFailureType) eResolveProxy(oldAppliedfailuretype);
			if (appliedfailuretype != oldAppliedfailuretype) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__APPLIEDFAILURETYPE, oldAppliedfailuretype,
							appliedfailuretype));
			}
		}
		return appliedfailuretype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncertaintyInducedFailureType basicGetAppliedfailuretype() {
		return appliedfailuretype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAppliedfailuretype(UncertaintyInducedFailureType newAppliedfailuretype) {
		UncertaintyInducedFailureType oldAppliedfailuretype = appliedfailuretype;
		appliedfailuretype = newAppliedfailuretype;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__APPLIEDFAILURETYPE, oldAppliedfailuretype,
					appliedfailuretype));
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
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__APPLIEDFAILURETYPE:
			if (resolve)
				return getAppliedfailuretype();
			return basicGetAppliedfailuretype();
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
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__APPLIEDFAILURETYPE:
			setAppliedfailuretype((UncertaintyInducedFailureType) newValue);
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
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__APPLIEDFAILURETYPE:
			setAppliedfailuretype((UncertaintyInducedFailureType) null);
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
		case UncertaintyPackage.ARCHITECTURAL_COUNTERMEASURE__APPLIEDFAILURETYPE:
			return appliedfailuretype != null;
		}
		return super.eIsSet(featureID);
	}

} //ArchitecturalCountermeasureImpl
