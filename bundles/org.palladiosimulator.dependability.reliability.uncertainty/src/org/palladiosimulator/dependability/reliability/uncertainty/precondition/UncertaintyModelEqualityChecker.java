package org.palladiosimulator.dependability.reliability.uncertainty.precondition;

import java.util.function.Predicate;

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyModelEquality;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;
import org.palladiosimulator.envdyn.environment.staticmodel.LocalProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.templatevariable.DependenceRelation;
import org.palladiosimulator.solver.core.models.PCMInstance;

import com.google.common.collect.Lists;

public class UncertaintyModelEqualityChecker implements ArchitecturalPreconditionChecker {

	protected UncertaintyModelEqualityChecker() {

	}

	@Override
	public boolean isApplicable(ArchitecturalPrecondition precondition) {
		return precondition instanceof UncertaintyModelEquality;
	}

	@Override
	public boolean isFulfilled(PCMInstance pcmModel, ArchitecturalPrecondition precondition) {
		if (isNotApplicable(precondition)) {
			return false;
		}

		var equalityPrecondition = (UncertaintyModelEquality) precondition;
		return areStructuralEqual(equalityPrecondition.getFirst(), equalityPrecondition.getSecond());
	}

	private boolean areStructuralEqual(GroundProbabilisticNetwork first, GroundProbabilisticNetwork second) {
		var firstLocalModels = first.getLocalProbabilisticModels();
		var secondLocalModels = second.getLocalProbabilisticModels();

		if (firstLocalModels.size() != 1 || secondLocalModels.size() != 1) {
			throw new IllegalArgumentException(
					"Models are not following the expected structure, i.e. they encompass more than one local model");
		}

		if (firstLocalModels.size() != secondLocalModels.size()) {
			return false;
		}

		if (instantiateDistinctRandomVariables(firstLocalModels.get(0), secondLocalModels.get(0))) {
			return false;
		}

		if (haveDistinctDependencyStructures(firstLocalModels.get(0), secondLocalModels.get(0))) {
			return false;
		}

		return true;
	}

	private boolean instantiateDistinctRandomVariables(LocalProbabilisticNetwork firstNetwork,
			LocalProbabilisticNetwork secondNetwork) {
		var copySecondVariables = Lists.newArrayList(secondNetwork.getGroundRandomVariables());

		for (GroundRandomVariable each : firstNetwork.getGroundRandomVariables()) {
			copySecondVariables.removeIf(instantiateSameTemplate(each));
		}

		if (copySecondVariables.isEmpty()) {
			return false;
		}
		return true;
	}

	private boolean haveDistinctDependencyStructures(LocalProbabilisticNetwork firstNetwork,
			LocalProbabilisticNetwork secondNetwork) {
		for (GroundRandomVariable each : firstNetwork.getGroundRandomVariables()) {
			var counterpart = secondNetwork.getGroundRandomVariables().stream()
					.filter(instantiateSameTemplate(each))
					.findFirst()
					.get();
			
			if (each.getDependenceStructure().size() != counterpart.getDependenceStructure().size()) {
				return true;
			}
			
			if (each.getDependenceStructure().size() == 0) {
				continue;
			}
			
			var dependencies = Lists.newArrayList(counterpart.getDependenceStructure());
			for (DependenceRelation relation : each.getDependenceStructure()) {
				dependencies.removeIf(r -> r.getSource().getId().equals(relation.getSource().getId()));
			}
			if (dependencies.isEmpty() == false) {
				return true;
			}
		}
		return false;
	}
	
	private Predicate<GroundRandomVariable> instantiateSameTemplate(GroundRandomVariable each) {
		return v -> v.getInstantiatedTemplate().getId().equals(each.getInstantiatedTemplate().getId());
	}

}
