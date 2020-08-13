package org.palladiosimulator.dependability.reliability.uncertainty.precondition;

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;
import org.palladiosimulator.solver.models.PCMInstance;

public interface ArchitecturalPreconditionChecker<T extends ArchitecturalPrecondition> {
	
	public boolean isFulfilled(T precondition, PCMInstance pcmModel);

	public default boolean isNotFulfiled(T precondition, PCMInstance pcmModel) {
		return isFulfilled(precondition, pcmModel) == false;
	}
}
