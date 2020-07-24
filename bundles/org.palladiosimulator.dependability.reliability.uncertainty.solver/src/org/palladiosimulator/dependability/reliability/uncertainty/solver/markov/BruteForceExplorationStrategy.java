package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import java.util.List;
import java.util.Set;

import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;

public class BruteForceExplorationStrategy implements StateSpaceExplorationStrategy {

	// Note that the state space grows exponentially. Therefore, the strategy should
	// be used with care.
	@Override
	public Set<List<UncertaintyState>> explore(DiscreteUncertaintyStateSpace stateSpace) {
		return stateSpace.cartesianProduct();
	}

	@Override
	public String getName() {
		return "Brute force exploration strategy";
	}

}
