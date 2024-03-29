package org.palladiosimulator.dependability.reliability.uncertainty.solver.model;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;

import com.google.common.collect.Maps;

import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.api.parser.ParameterParser;

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

    public void updateModel(UncertaintyInducedFailureType uncertainty,
            IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
            ParameterParser parameterParser) {
        if (findModelFor(uncertainty).isPresent()) {
            manage(uncertainty, probabilityDistributionFactory, parameterParser);
        } else {
            // TODO: logging
        }
    }

    public Optional<UncertaintyModel> findModelFor(UncertaintyInducedFailureType uncertainty) {
        return Optional.ofNullable(managedModels.get(uncertainty.getId()));
    }

    public DiscreteUncertaintyStateSpace getStateSpace() {
        managedModels.forEach((k, v) -> System.out.println(k.toString() + ", " + v.toString()));

        var states = managedModels.values()
            .stream()
            .flatMap(m -> m.getValueSpace()
                .stream())
            .collect(toList());
        return DiscreteUncertaintyStateSpace.of(states);
    }

    // This method might be adapted if new uncertainty model implementations emerge.
    public void manage(List<UncertaintyInducedFailureType> uncertainties,
            final IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
            ParameterParser parameterParser) {
        Consumer<UncertaintyInducedFailureType> consumer = new Consumer<>() {
            public void accept(UncertaintyInducedFailureType failureType) {
                manage(failureType, probabilityDistributionFactory, parameterParser);
            }
        };
        uncertainties.forEach(consumer);
    }

    public void manage(UncertaintyInducedFailureType failureType,
            IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
            ParameterParser parameterParser) {
        if (isNull(failureType.getFailureVariable())) {
            managedModels.put(failureType.getId(),
                    new MLInducedUncertaintyModel(failureType, probabilityDistributionFactory, parameterParser));
        } else {
            managedModels.put(failureType.getId(),
                    new BayesianUncertaintyModel(failureType, probabilityDistributionFactory, parameterParser));
        }
    }

}
