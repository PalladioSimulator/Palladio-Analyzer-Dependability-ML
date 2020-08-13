/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty;

import org.eclipse.emf.common.util.EList;

import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;

import tools.mdsd.modelingfoundations.identifier.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Architectural Countermeasure</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure#getArchitecturalPreconditions <em>Architectural Preconditions</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure#getTargetUncertainty <em>Target Uncertainty</em>}</li>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure#getUncertaintyImprovement <em>Uncertainty Improvement</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getArchitecturalCountermeasure()
 * @model
 * @generated
 */
public interface ArchitecturalCountermeasure extends Entity {
	/**
	 * Returns the value of the '<em><b>Architectural Preconditions</b></em>' reference list.
	 * The list contents are of type {@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Architectural Preconditions</em>' reference list.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getArchitecturalCountermeasure_ArchitecturalPreconditions()
	 * @model required="true"
	 * @generated
	 */
	EList<ArchitecturalPrecondition> getArchitecturalPreconditions();

	/**
	 * Returns the value of the '<em><b>Target Uncertainty</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Uncertainty</em>' reference.
	 * @see #setTargetUncertainty(GroundRandomVariable)
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getArchitecturalCountermeasure_TargetUncertainty()
	 * @model required="true"
	 * @generated
	 */
	GroundRandomVariable getTargetUncertainty();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure#getTargetUncertainty <em>Target Uncertainty</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Uncertainty</em>' reference.
	 * @see #getTargetUncertainty()
	 * @generated
	 */
	void setTargetUncertainty(GroundRandomVariable value);

	/**
	 * Returns the value of the '<em><b>Uncertainty Improvement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uncertainty Improvement</em>' containment reference.
	 * @see #setUncertaintyImprovement(UncertaintyImprovement)
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getArchitecturalCountermeasure_UncertaintyImprovement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	UncertaintyImprovement getUncertaintyImprovement();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure#getUncertaintyImprovement <em>Uncertainty Improvement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uncertainty Improvement</em>' containment reference.
	 * @see #getUncertaintyImprovement()
	 * @generated
	 */
	void setUncertaintyImprovement(UncertaintyImprovement value);

} // ArchitecturalCountermeasure
