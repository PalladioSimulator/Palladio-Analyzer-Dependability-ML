package org.palladiosimulator.dependability.reliability.uncertainty.solver.model;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.palladiosimulator.dependability.ml.sensitivity.analysis.ProbabilisticSensitivityModel;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityModel.MLOutcomeMeasure;
import org.palladiosimulator.dependability.ml.sensitivity.api.MLSensitivityAnalyser;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.MeasurableProperty;
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

	private double marginalizingMLVariable(List<MeasurableProperty> properties) {
		var probability = 0.0;
		
		sensitivityModel.setMLOutcomeMeasure(MLOutcomeMeasure.SUCCESS);
		probability += sensitivityModel.inferSensitivity(properties);
		
		sensitivityModel.setMLOutcomeMeasure(MLOutcomeMeasure.FAIL);
		probability += sensitivityModel.inferSensitivity(properties);
		
		return probability;
	}

	@Override
	public double probabilityOfFailure(List<UncertaintyState> values) {
		return sensitivityModel.inferSensitivity(filterRelevantStates(values));
	}

	private List<MeasurableProperty> filterRelevantStates(List<UncertaintyState> values) {
		return values.stream().map(this::toMeasurableProperty).filter(Optional::isPresent).map(Optional::get)
				.collect(toList());
	}

	private Optional<MeasurableProperty> toMeasurableProperty(UncertaintyState state) {
		for (String each : MLSensitivityAnalyser.getAnalysablePropertyNames()) {
			var prop = MLSensitivityAnalyser.findAnalysablePropertyMeasureWith(each).get();
			if (state.getId().startsWith(prop.getId())) {
				return Optional.of(new MeasurableProperty(prop.getId(), state.getValue()));
			}
		}
		return Optional.empty();
	}

}
