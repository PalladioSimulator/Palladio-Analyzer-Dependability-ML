package org.palladiosimulator.dependability.reliability.uncertainty.precondition;

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;
import org.palladiosimulator.solver.models.PCMInstance;

public interface ArchitecturalPreconditionChecker {
	
	public boolean isApplicable(ArchitecturalPrecondition precondition);
	
	public default boolean isNotApplicable(ArchitecturalPrecondition precondition) {
		return isApplicable(precondition) == false;
	}
	
	public boolean isFulfilled(PCMInstance pcmModel, ArchitecturalPrecondition precondition);

	public default boolean isNotFulfiled(PCMInstance pcmModel, ArchitecturalPrecondition precondition) {
		return isFulfilled(pcmModel, precondition) == false;
	}
}
