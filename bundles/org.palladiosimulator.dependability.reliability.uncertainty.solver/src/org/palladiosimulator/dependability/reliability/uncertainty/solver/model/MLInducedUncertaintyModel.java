package org.palladiosimulator.dependability.reliability.uncertainty.solver.model;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.palladiosimulator.dependability.ml.model.InputData;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.ProbabilisticSensitivityModel;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityModel.MLOutcomeMeasure;
import org.palladiosimulator.dependability.ml.sensitivity.api.MLSensitivityAnalyser;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure.MeasurableProperty;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.property.conversion.MeasurablePropertyConversion;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.templatevariable.TemplateVariableDefinitions;

import com.google.common.collect.Sets;

import tools.mdsd.probdist.api.entity.CategoricalValue;

public class MLInducedUncertaintyModel implements UncertaintyModel {

	/**
	 * Is only created whenever an uncertainty state includes a variable which forms
	 * a hand-crafted sensitivity factor and not yet a registered property measure.
	 * 
	 * @author scheerer
	 *
	 */
	private static class CustomizedPropertyMeasure extends PropertyMeasure {

		private final String id;

		public CustomizedPropertyMeasure(UncertaintyState state) {
			this.id = MeasurablePropertyConversion.convertToMeasurablePropertyId(state.getInstantiatedTemplate());
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public String getName() {
			return id;
		}

		@Override
		public MeasurableProperty newMeasurablePropertyWith(CategoricalValue value) {
			return new MeasurableProperty(value);
		}

		@Override
		public MeasurableProperty apply(InputData inputData) {
			return null;
		}

		@Override
		public Boolean isApplicableTo(InputData inputData) {
			return false;
		}

		@Override
		public Set<MeasurableProperty> getMeasurablePropertySpace() {
			return Sets.newHashSet();
		}

	}

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
	public double probabilityOfFailureGiven(List<UncertaintyState> values) {
		var probOfFailure = sensitivityModel.inferSensitivity(filterRelevantStates(values));
		var probOfUncertainty = probability(values);
		return probOfFailure / probOfUncertainty;
	}

	private List<MeasurableProperty> filterRelevantStates(List<UncertaintyState> values) {
		return values.stream().map(this::toMeasurableProperty).collect(toList());
	}

	private MeasurableProperty toMeasurableProperty(UncertaintyState state) {
		return findMeasurablePropertyOf(state).orElse(createCustomizedMeasurableProperty(state));
	}

	private Optional<MeasurableProperty> findMeasurablePropertyOf(UncertaintyState state) {
		for (PropertyMeasure each : MLSensitivityAnalyser.getAnalysablePropertyMeasures()) {
			var measure = MLSensitivityAnalyser.findAnalysablePropertyMeasureWith(each.getId()).get();
			if (areSemanticallyEqual(state, each)) {
				return Optional.of(measure.newMeasurablePropertyWith(state.getValue()));
			}
		}
		return Optional.empty();
	}

	private boolean areSemanticallyEqual(UncertaintyState state, PropertyMeasure measure) {
		return MeasurablePropertyConversion.areSemanticallyEqual(state.getInstantiatedTemplate(), measure);
	}

	private MeasurableProperty createCustomizedMeasurableProperty(UncertaintyState state) {
		return new CustomizedPropertyMeasure(state).newMeasurablePropertyWith(state.getValue());
	}

}
