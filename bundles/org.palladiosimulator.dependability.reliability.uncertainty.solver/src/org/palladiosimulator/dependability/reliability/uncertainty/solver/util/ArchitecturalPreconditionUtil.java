package org.palladiosimulator.dependability.reliability.uncertainty.solver.util;

import java.util.function.Predicate;

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.precondition.ArchitecturalPreconditionManager;
import org.palladiosimulator.solver.models.PCMInstance;

public class ArchitecturalPreconditionUtil {

	public static boolean allPreconditionsFulfilled(ArchitecturalCountermeasure countermeasure, PCMInstance pcmModel) {
		return countermeasure.getArchitecturalPreconditions().stream().allMatch(isFulfilled(pcmModel));
	}

	public static boolean allPreconditionsFulfilled(UncertaintyInducedFailureType uncertainty, PCMInstance pcmModel) {
		return uncertainty.getArchitecturalPreconditions().stream().allMatch(isFulfilled(pcmModel));
	}

	private static Predicate<ArchitecturalPrecondition> isFulfilled(PCMInstance pcmModel) {
		return precondition -> {
			var checker = ArchitecturalPreconditionManager.get().findPreconditionCheckerFor(precondition);
			if (checker.isPresent()) {
				return checker.get().isFulfilled(pcmModel, precondition);
			}
			return false;
		};
	}

}
