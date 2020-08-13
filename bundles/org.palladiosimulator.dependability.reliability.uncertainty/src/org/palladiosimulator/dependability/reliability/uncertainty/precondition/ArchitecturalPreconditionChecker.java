package org.palladiosimulator.dependability.reliability.uncertainty.precondition;

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;
import org.palladiosimulator.solver.models.PCMInstance;

public interface ArchitecturalPreconditionChecker<T extends ArchitecturalPrecondition> {
	
	public boolean isFulfilled(T precondition, PCMInstance pcmModel);
}
