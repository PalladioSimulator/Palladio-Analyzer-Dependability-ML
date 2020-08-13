/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.dependability.reliability.uncertainty.ProbabilisticImprovement;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage;

import tools.mdsd.probdist.distributionfunction.ProbabilityDistribution;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Probabilistic Improvement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.impl.ProbabilisticImprovementImpl#getProbabilityDistribution <em>Probability Distribution</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProbabilisticImprovementImpl extends UncertaintyImprovementImpl implements ProbabilisticImprovement {
	/**
	 * The cached value of the '{@link #getProbabilityDistribution() <em>Probability Distribution</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbabilityDistribution()
	 * @generated
	 * @ordered
	 */
	protected ProbabilityDistribution probabilityDistribution;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProbabilisticImprovementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UncertaintyPackage.Literals.PROBABILISTIC_IMPROVEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProbabilityDistribution getProbabilityDistribution() {
		if (probabilityDistribution != null && probabilityDistribution.eIsProxy()) {
			InternalEObject oldProbabilityDistribution = (InternalEObject) probabilityDistribution;
			probabilityDistribution = (ProbabilityDistribution) eResolveProxy(oldProbabilityDistribution);
			if (probabilityDistribution != oldProbabilityDistribution) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							UncertaintyPackage.PROBABILISTIC_IMPROVEMENT__PROBABILITY_DISTRIBUTION,
							oldProbabilityDistribution, probabilityDistribution));
			}
		}
		return probabilityDistribution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbabilityDistribution basicGetProbabilityDistribution() {
		return probabilityDistribution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProbabilityDistribution(ProbabilityDistribution newProbabilityDistribution) {
		ProbabilityDistribution oldProbabilityDistribution = probabilityDistribution;
		probabilityDistribution = newProbabilityDistribution;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UncertaintyPackage.PROBABILISTIC_IMPROVEMENT__PROBABILITY_DISTRIBUTION, oldProbabilityDistribution,
					probabilityDistribution));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UncertaintyPackage.PROBABILISTIC_IMPROVEMENT__PROBABILITY_DISTRIBUTION:
			if (resolve)
				return getProbabilityDistribution();
			return basicGetProbabilityDistribution();
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
		case UncertaintyPackage.PROBABILISTIC_IMPROVEMENT__PROBABILITY_DISTRIBUTION:
			setProbabilityDistribution((ProbabilityDistribution) newValue);
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
		case UncertaintyPackage.PROBABILISTIC_IMPROVEMENT__PROBABILITY_DISTRIBUTION:
			setProbabilityDistribution((ProbabilityDistribution) null);
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
		case UncertaintyPackage.PROBABILISTIC_IMPROVEMENT__PROBABILITY_DISTRIBUTION:
			return probabilityDistribution != null;
		}
		return super.eIsSet(featureID);
	}

} //ProbabilisticImprovementImpl
