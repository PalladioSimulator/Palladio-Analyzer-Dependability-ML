/**
 */
package org.palladiosimulator.dependability.reliability.uncertainty;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Deterministic Improvement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.dependability.reliability.uncertainty.DeterministicImprovement#getMappingTable <em>Mapping Table</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getDeterministicImprovement()
 * @model
 * @generated
 */
public interface DeterministicImprovement extends UncertaintyImprovement {
	/**
	 * Returns the value of the '<em><b>Mapping Table</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.dependability.reliability.uncertainty.MapEntry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping Table</em>' containment reference list.
	 * @see org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyPackage#getDeterministicImprovement_MappingTable()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<MapEntry> getMappingTable();

} // DeterministicImprovement
