/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty;

import tools.mdsd.probdist.distributionfunction.ProbabilityDistribution;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Probabilistic Improvement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.ProbabilisticImprovement#getProbabilityDistribution <em>Probability Distribution</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getProbabilisticImprovement()
 * @model
 * @generated
 */
public interface ProbabilisticImprovement extends UncertaintyImprovement {
	/**
	 * Returns the value of the '<em><b>Probability Distribution</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Probability Distribution</em>' reference.
	 * @see #setProbabilityDistribution(ProbabilityDistribution)
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getProbabilisticImprovement_ProbabilityDistribution()
	 * @model required="true"
	 * @generated
	 */
	ProbabilityDistribution getProbabilityDistribution();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.dependability.reliability.uncertainty.ProbabilisticImprovement#getProbabilityDistribution <em>Probability Distribution</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Probability Distribution</em>' reference.
	 * @see #getProbabilityDistribution()
	 * @generated
	 */
	void setProbabilityDistribution(ProbabilityDistribution value);

} // ProbabilisticImprovement
