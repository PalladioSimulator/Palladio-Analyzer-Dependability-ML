package org.palladiosimulator.dependability.reliability.uncertainty.precondition;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;

import com.google.common.collect.Sets;

public class ArchitecturalPreconditionManager {

	private final static Set<ArchitecturalPreconditionChecker> PRECONDITION_CHECKER = Sets.newHashSet();
	static {
		PRECONDITION_CHECKER.add(new ActiveComponentChecker());
		PRECONDITION_CHECKER.add(new UncertaintyModelEqualityChecker());
	}

	private static ArchitecturalPreconditionManager managerInstance = null;

	private ArchitecturalPreconditionManager() {

	}

	public static ArchitecturalPreconditionManager get() {
		if (managerInstance == null) {
			managerInstance = new ArchitecturalPreconditionManager();
		}
		return managerInstance;
	}

	public Optional<ArchitecturalPreconditionChecker> findPreconditionCheckerFor(
			ArchitecturalPrecondition precondtion) {
		return PRECONDITION_CHECKER.stream().filter(preconditionCheckerFor(precondtion)).findFirst();
	}

	private Predicate<ArchitecturalPreconditionChecker> preconditionCheckerFor(ArchitecturalPrecondition precondition) {
		return c -> c.isApplicable(precondition);
	}

}