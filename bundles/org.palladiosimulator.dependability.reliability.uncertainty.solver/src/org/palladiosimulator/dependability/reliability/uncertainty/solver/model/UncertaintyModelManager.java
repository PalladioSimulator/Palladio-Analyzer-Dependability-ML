package org.palladiosimulator.dependability.reliability.uncertainty.solver.model;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;

import com.google.common.collect.Maps;

public class UncertaintyModelManager {

	private static UncertaintyModelManager managerInstance = null;

	private final Map<String, UncertaintyModel> managedModels;

	private UncertaintyModelManager() {
		this.managedModels = Maps.newHashMap();
	}
	
	public static UncertaintyModelManager get() {
		if (isNull(managerInstance)) {
			managerInstance = new UncertaintyModelManager();
		}
		return managerInstance;
	}
	
	public void reset() {
		managedModels.clear();
	}

	public void updateModel(UncertaintyInducedFailureType uncertainty) {
		if (findModelFor(uncertainty).isPresent()) {
			manage(uncertainty);
		} else {
			// TODO: logging
		}
	}

	public Optional<UncertaintyModel> findModelFor(UncertaintyInducedFailureType uncertainty) {
		return Optional.ofNullable(managedModels.get(uncertainty.getId()));
	}

	public DiscreteUncertaintyStateSpace getStateSpace() {
		managedModels.forEach((k, v) -> System.out.println(k.toString() + ", " + v.toString()));

		var states = managedModels.values().stream()
				.flatMap(m -> m.getValueSpace().stream())
				.collect(toList());
		return DiscreteUncertaintyStateSpace.of(states);
	}

	// This method might be adapted if new uncertainty model implementations emerge.
	public void manage(List<UncertaintyInducedFailureType> uncertainties) {
		uncertainties.forEach(this::manage);
	}

	public void manage(UncertaintyInducedFailureType failureType) {
		if (isNull(failureType.getFailureVariable())) {
			managedModels.put(failureType.getId(), new MLInducedUncertaintyModel(failureType));
		} else {
			managedModels.put(failureType.getId(), new BayesianUncertaintyModel(failureType));
		}
	}

}
