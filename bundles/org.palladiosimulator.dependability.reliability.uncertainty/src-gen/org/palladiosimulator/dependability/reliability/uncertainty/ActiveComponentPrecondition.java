/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty;

import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Active Component Precondition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.ActiveComponentPrecondition#getRequiredActiveComponent <em>Required Active Component</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getActiveComponentPrecondition()
 * @model
 * @generated
 */
public interface ActiveComponentPrecondition extends ArchitecturalPrecondition {
	/**
	 * Returns the value of the '<em><b>Required Active Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Active Component</em>' reference.
	 * @see #setRequiredActiveComponent(InterfaceProvidingRequiringEntity)
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getActiveComponentPrecondition_RequiredActiveComponent()
	 * @model required="true"
	 * @generated
	 */
	InterfaceProvidingRequiringEntity getRequiredActiveComponent();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.dependability.reliability.uncertainty.ActiveComponentPrecondition#getRequiredActiveComponent <em>Required Active Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Active Component</em>' reference.
	 * @see #getRequiredActiveComponent()
	 * @generated
	 */
	void setRequiredActiveComponent(InterfaceProvidingRequiringEntity value);

} // ActiveComponentPrecondition
