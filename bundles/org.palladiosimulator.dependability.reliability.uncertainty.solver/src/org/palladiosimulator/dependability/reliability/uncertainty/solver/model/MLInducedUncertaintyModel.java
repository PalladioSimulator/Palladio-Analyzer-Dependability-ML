package org.palladiosimulator.dependability.reliability.uncertainty.solver.model;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.palladiosimulator.dependability.ml.sensitivity.analysis.ProbabilisticSensitivityModel;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityModel.MLOutcomeMeasure;
import org.palladiosimulator.dependability.ml.sensitivity.api.MLSensitivityAnalyser;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.CustomizedSensitivityProperty;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.SensitivityProperty;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.property.conversion.SensitivityPropertyConventions;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.templatevariable.TemplateVariableDefinitions;

public class MLInducedUncertaintyModel implements UncertaintyModel {

	private final Set<UncertaintyState> valueSpace;
	private final ProbabilisticSensitivityModel sensitivityModel;

	public MLInducedUncertaintyModel(UncertaintyInducedFailureType uncertainty) {
		this.sensitivityModel = initSensitivityModel(uncertainty);
		this.valueSpace = computeValueSpace(uncertainty);
	}

	private ProbabilisticSensitivityModel initSensitivityModel(UncertaintyInducedFailureType uncertainty) {
		var model = ProbabilisticSensitivityModel.createFrom(getProbabilisticModel(uncertainty),
				getTemplates(uncertainty));
		model.setMLOutcomeMeasure(MLOutcomeMeasure.FAIL);
		return model;
	}

	private GroundProbabilisticNetwork getProbabilisticModel(UncertaintyInducedFailureType uncertainty) {
		return uncertainty.getUncertaintyModel();
	}

	private TemplateVariableDefinitions getTemplates(UncertaintyInducedFailureType uncertainty) {
		var localModel = uncertainty.getUncertaintyModel().getLocalProbabilisticModels().get(0);
		var anyVariable = localModel.getGroundRandomVariables().get(0);
		return (TemplateVariableDefinitions) anyVariable.getInstantiatedTemplate().eContainer();
	}

	private Set<UncertaintyState> computeValueSpace(UncertaintyInducedFailureType uncertainty) {
		var statesIncludingMLVar = DiscreteUncertaintyStateSpace.valueSpaceOf(uncertainty);
		return excludeMLInputVariable(statesIncludingMLVar);
	}

	private Set<UncertaintyState> excludeMLInputVariable(Set<UncertaintyState> values) {
		var mlVariable = sensitivityModel.findMLRandomVariable();
		values.removeIf(state -> state.getId().equals(mlVariable.getEntityName()));
		return values;
	}

	@Override
	public Set<UncertaintyState> getValueSpace() {
		return valueSpace;
	}

	@Override
	public double probability(List<UncertaintyState> values) {
		return marginalizingMLVariable(filterRelevantStates(values));
	}

	private double marginalizingMLVariable(List<SensitivityProperty> properties) {
		var probability = 0.0;

		sensitivityModel.setMLOutcomeMeasure(MLOutcomeMeasure.SUCCESS);
		probability += sensitivityModel.inferSensitivity(properties);

		sensitivityModel.setMLOutcomeMeasure(MLOutcomeMeasure.FAIL);
		probability += sensitivityModel.inferSensitivity(properties);

		return probability;
	}

	@Override
	public double probabilityOfFailureGiven(List<UncertaintyState> values) {
		var probOfFailure = sensitivityModel.inferSensitivity(filterRelevantStates(values));
		var probOfUncertainty = probability(values);
		return probOfFailure / probOfUncertainty;
	}

	private List<SensitivityProperty> filterRelevantStates(List<UncertaintyState> values) {
		return values.stream()
				.map(this::toMeasurableProperty)
				.collect(toList());
	}

	private SensitivityProperty toMeasurableProperty(UncertaintyState state) {
		return findMeasurablePropertyOf(state).orElse(createCustomizedMeasurableProperty(state));
	}

	private Optional<SensitivityProperty> findMeasurablePropertyOf(UncertaintyState state) {
		for (PropertyMeasure each : MLSensitivityAnalyser.getAnalysablePropertyMeasures()) {
			if (areSemanticallyEqual(state, each)) {
				var result = (SensitivityProperty) each.findMeasurablePropertyWith(state.getValue()).get();
				return Optional.of(result);
			}
		}
		return Optional.empty();
	}

	private boolean areSemanticallyEqual(UncertaintyState state, PropertyMeasure measure) {
		return SensitivityPropertyConventions.areSemanticallyEqual(state.getInstantiatedTemplate(), measure);
	}

	private CustomizedSensitivityProperty createCustomizedMeasurableProperty(UncertaintyState state) {
		return CustomizedSensitivityProperty.createFrom(state.getValue(), state.getInstantiatedTemplate());
	}

}
