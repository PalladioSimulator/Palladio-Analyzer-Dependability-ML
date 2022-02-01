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

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;

import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;

import org.palladiosimulator.pcm.reliability.FailureType;

import tools.mdsd.modelingfoundations.identifier.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Induced Failure Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyInducedFailureTypeImpl#getRefines <em>Refines</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyInducedFailureTypeImpl#getUncertaintyModel <em>Uncertainty Model</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyInducedFailureTypeImpl#getFailureVariable <em>Failure Variable</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.UncertaintyInducedFailureTypeImpl#getArchitecturalPreconditions <em>Architectural Preconditions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UncertaintyInducedFailureTypeImpl extends EntityImpl implements UncertaintyInducedFailureType {
	/**
	 * The cached value of the '{@link #getRefines() <em>Refines</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefines()
	 * @generated
	 * @ordered
	 */
	protected FailureType refines;

	/**
	 * The cached value of the '{@link #getUncertaintyModel() <em>Uncertainty Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUncertaintyModel()
	 * @generated
	 * @ordered
	 */
	protected GroundProbabilisticNetwork uncertaintyModel;

	/**
	 * The cached value of the '{@link #getFailureVariable() <em>Failure Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFailureVariable()
	 * @generated
	 * @ordered
	 */
	protected GroundRandomVariable failureVariable;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UncertaintyInducedFailureTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UncertaintyPackage.Literals.UNCERTAINTY_INDUCED_FAILURE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FailureType getRefines() {
		if (refines != null && refines.eIsProxy()) {
			InternalEObject oldRefines = (InternalEObject) refines;
			refines = (FailureType) eResolveProxy(oldRefines);
			if (refines != oldRefines) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__REFINES, oldRefines, refines));
			}
		}
		return refines;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FailureType basicGetRefines() {
		return refines;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRefines(FailureType newRefines) {
		FailureType oldRefines = refines;
		refines = newRefines;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__REFINES, oldRefines, refines));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GroundProbabilisticNetwork getUncertaintyModel() {
		if (uncertaintyModel != null && uncertaintyModel.eIsProxy()) {
			InternalEObject oldUncertaintyModel = (InternalEObject) uncertaintyModel;
			uncertaintyModel = (GroundProbabilisticNetwork) eResolveProxy(oldUncertaintyModel);
			if (uncertaintyModel != oldUncertaintyModel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__UNCERTAINTY_MODEL, oldUncertaintyModel,
							uncertaintyModel));
			}
		}
		return uncertaintyModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroundProbabilisticNetwork basicGetUncertaintyModel() {
		return uncertaintyModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUncertaintyModel(GroundProbabilisticNetwork newUncertaintyModel) {
		GroundProbabilisticNetwork oldUncertaintyModel = uncertaintyModel;
		uncertaintyModel = newUncertaintyModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__UNCERTAINTY_MODEL, oldUncertaintyModel,
					uncertaintyModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GroundRandomVariable getFailureVariable() {
		if (failureVariable != null && failureVariable.eIsProxy()) {
			InternalEObject oldFailureVariable = (InternalEObject) failureVariable;
			failureVariable = (GroundRandomVariable) eResolveProxy(oldFailureVariable);
			if (failureVariable != oldFailureVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__FAILURE_VARIABLE, oldFailureVariable,
							failureVariable));
			}
		}
		return failureVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroundRandomVariable basicGetFailureVariable() {
		return failureVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFailureVariable(GroundRandomVariable newFailureVariable) {
		GroundRandomVariable oldFailureVariable = failureVariable;
		failureVariable = newFailureVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__FAILURE_VARIABLE, oldFailureVariable,
					failureVariable));
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
					UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__ARCHITECTURAL_PRECONDITIONS);
		}
		return architecturalPreconditions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__REFINES:
			if (resolve)
				return getRefines();
			return basicGetRefines();
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__UNCERTAINTY_MODEL:
			if (resolve)
				return getUncertaintyModel();
			return basicGetUncertaintyModel();
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__FAILURE_VARIABLE:
			if (resolve)
				return getFailureVariable();
			return basicGetFailureVariable();
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__ARCHITECTURAL_PRECONDITIONS:
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
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__REFINES:
			setRefines((FailureType) newValue);
			return;
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__UNCERTAINTY_MODEL:
			setUncertaintyModel((GroundProbabilisticNetwork) newValue);
			return;
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__FAILURE_VARIABLE:
			setFailureVariable((GroundRandomVariable) newValue);
			return;
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__ARCHITECTURAL_PRECONDITIONS:
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
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__REFINES:
			setRefines((FailureType) null);
			return;
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__UNCERTAINTY_MODEL:
			setUncertaintyModel((GroundProbabilisticNetwork) null);
			return;
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__FAILURE_VARIABLE:
			setFailureVariable((GroundRandomVariable) null);
			return;
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__ARCHITECTURAL_PRECONDITIONS:
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
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__REFINES:
			return refines != null;
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__UNCERTAINTY_MODEL:
			return uncertaintyModel != null;
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__FAILURE_VARIABLE:
			return failureVariable != null;
		case UncertaintyPackage.UNCERTAINTY_INDUCED_FAILURE_TYPE__ARCHITECTURAL_PRECONDITIONS:
			return architecturalPreconditions != null && !architecturalPreconditions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //UncertaintyInducedFailureTypeImpl
