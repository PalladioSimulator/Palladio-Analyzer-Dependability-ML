package org.palladiosimulator.dependability.reliability.uncertainty.solver.model;

import java.util.List;
import java.util.Set;

import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;

public interface UncertaintyModel {

	public Set<UncertaintyState> getValueSpace();

	public double probability(List<UncertaintyState> values);
	
	public double probabilityOfFailureGiven(List<UncertaintyState> values);
}
