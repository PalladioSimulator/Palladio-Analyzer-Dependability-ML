package org.palladiosimulator.dependability.reliability.uncertainty.precondition;

import java.util.Map;
import java.util.Optional;

import org.palladiosimulator.dependability.reliability.uncertainty.ActiveComponentPrecondition;
import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;

import com.google.common.collect.Maps;

public class ArchitecturalPreconditionManager {

	private final static Map<Class<? extends ArchitecturalPrecondition>, ArchitecturalPreconditionChecker<?>> PRECONDITION_CHECKER = Maps
			.newHashMap();
	static {
		PRECONDITION_CHECKER.put(ActiveComponentPrecondition.class, new ActiveComponentChecker());
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

	@SuppressWarnings("unchecked")
	public <T extends ArchitecturalPrecondition> Optional<ArchitecturalPreconditionChecker<T>> findPreconditionCheckerFor(
			T precondtion) {
		var result = PRECONDITION_CHECKER.get(precondtion.getClass());
		return Optional.ofNullable((ArchitecturalPreconditionChecker<T>) result);
	}

}