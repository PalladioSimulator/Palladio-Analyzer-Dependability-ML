/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty;

import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Global Uncertainty Countermeasure</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.GlobalUncertaintyCountermeasure#getImprovedUncertaintyModel <em>Improved Uncertainty Model</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getGlobalUncertaintyCountermeasure()
 * @model
 * @generated
 */
public interface GlobalUncertaintyCountermeasure extends ArchitecturalCountermeasure {
	/**
	 * Returns the value of the '<em><b>Improved Uncertainty Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Improved Uncertainty Model</em>' reference.
	 * @see #setImprovedUncertaintyModel(GroundProbabilisticNetwork)
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getGlobalUncertaintyCountermeasure_ImprovedUncertaintyModel()
	 * @model required="true"
	 * @generated
	 */
	GroundProbabilisticNetwork getImprovedUncertaintyModel();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.dependability.reliability.uncertainty.GlobalUncertaintyCountermeasure#getImprovedUncertaintyModel <em>Improved Uncertainty Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Improved Uncertainty Model</em>' reference.
	 * @see #getImprovedUncertaintyModel()
	 * @generated
	 */
	void setImprovedUncertaintyModel(GroundProbabilisticNetwork value);

} // GlobalUncertaintyCountermeasure
