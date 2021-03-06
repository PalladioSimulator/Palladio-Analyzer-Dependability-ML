/**
 *
 * $Id$
 */
package org.palladiosimulator.dependability.reliability.uncertainty.validation;

import org.eclipse.emf.common.util.EList;

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;

/**
 * A sample validator interface for {@link org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ArchitecturalCountermeasureValidator {
	boolean validate();

	boolean validateArchitecturalPreconditions(EList<ArchitecturalPrecondition> value);

	boolean validateAppliedFailureType(UncertaintyInducedFailureType value);
}
