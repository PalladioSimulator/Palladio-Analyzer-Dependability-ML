/**
 *
 * $Id$
 */
package org.palladiosimulator.dependability.reliability.uncertainty.validation;

import org.eclipse.emf.common.util.EList;

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;

import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;

import org.palladiosimulator.pcm.reliability.FailureType;

/**
 * A sample validator interface for {@link org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface UncertaintyInducedFailureTypeValidator {
	boolean validate();

	boolean validateRefines(FailureType value);

	boolean validateUncertaintyModel(GroundProbabilisticNetwork value);

	boolean validateFailureVariable(GroundRandomVariable value);

	boolean validateArchitecturalPreconditions(EList<ArchitecturalPrecondition> value);
}