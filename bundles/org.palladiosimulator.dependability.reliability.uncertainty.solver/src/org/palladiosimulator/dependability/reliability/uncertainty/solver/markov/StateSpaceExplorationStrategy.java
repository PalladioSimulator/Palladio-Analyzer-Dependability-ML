package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import java.util.List;
import java.util.Set;

import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;

public interface StateSpaceExplorationStrategy {
	
	public Set<List<UncertaintyState>> explore(DiscreteUncertaintyStateSpace stateSpace);
	
	public String getName();
}
