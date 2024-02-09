package org.palladiosimulator.dependability.reliability.uncertainty.solver.model;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.envdyn.api.entity.bn.BayesianNetwork;
import org.palladiosimulator.envdyn.api.entity.bn.InputValue;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;

import com.google.common.collect.Lists;

import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.api.parser.ParameterParser;
import tools.mdsd.probdist.distributionfunction.TabularCPD;

public class BayesianUncertaintyModel implements UncertaintyModel {

	private final BayesianNetwork bayesianNetwork;
	private final GroundRandomVariable failureVariable;
	private final Set<UncertaintyState> valueSpace;
	private final ParameterParser parameterParser;

	public BayesianUncertaintyModel(UncertaintyInducedFailureType uncertainty, IProbabilityDistributionFactory probabilityDistributionFactory, ParameterParser parameterParser) {
		this.bayesianNetwork = new BayesianNetwork(null, uncertainty.getUncertaintyModel(), probabilityDistributionFactory);
		this.failureVariable = uncertainty.getFailureVariable();
		this.valueSpace = computeValueSpace(uncertainty);
		this.parameterParser = parameterParser;
	}

	private Set<UncertaintyState> computeValueSpace(UncertaintyInducedFailureType uncertainty) {
		var statesIncludingFailureVar = DiscreteUncertaintyStateSpace.valueSpaceOf(uncertainty, parameterParser);
		return excludeFailureVariable(statesIncludingFailureVar);
	}

	private Set<UncertaintyState> excludeFailureVariable(Set<UncertaintyState> values) {
		values.removeIf(state -> state.getId().equals(failureVariable.getEntityName()));
		return values;
	}

	@Override
	public Set<UncertaintyState> getValueSpace() {
		return valueSpace;
	}

	@Override
	public double probability(List<UncertaintyState> values) {
		return marginalizingFailureVariable(filterRelevantValues(values), parameterParser);
	}

	@Override
	public double probabilityOfFailureGiven(List<UncertaintyState> values) {
		var probOfFailure = bayesianNetwork.probability(filterRelevantValues(values));
		var probOfUncertainty = probability(values);
		return probOfFailure / probOfUncertainty;
	}

	private double marginalizingFailureVariable(List<InputValue> values, ParameterParser parameterParser) {
		var probability = 0.0;
		for (CategoricalValue each : retrieveValueSpaceOf(failureVariable, parameterParser)) {
			var copiedValues = Lists.newArrayList(values);
			copiedValues.add(InputValue.create(each, failureVariable));

			probability += bayesianNetwork.probability(copiedValues);
		}
		return probability;
	}

	private Set<CategoricalValue> retrieveValueSpaceOf(GroundRandomVariable variable, ParameterParser parameterParser) {
		var distribution = failureVariable.getDescriptiveModel().getDistribution();
		var paramRep = distribution.getParams().get(0).getRepresentation();
		if (paramRep instanceof TabularCPD) {
			var param = TabularCPD.class.cast(paramRep).getCpdEntries().get(0).getEntry();
			var samples = parameterParser.parseSampleSpace(param);
			return samples.stream().map(each -> each.value).collect(toSet());
		}

		throw new RuntimeException(
				String.format("Variable %s is supposed to be described by a conditional probability distribution.",
						variable.getEntityName()));
	}

	private List<InputValue> filterRelevantValues(List<UncertaintyState> values) {
		List<InputValue> inputValues = Lists.newArrayList();
		for (GroundRandomVariable each : bayesianNetwork.getGroundVariables()) {
			var value = findValue(each, values).orElseThrow();
			inputValues.add(InputValue.create(value, each));
		}
		return inputValues;
	}

	private Optional<CategoricalValue> findValue(GroundRandomVariable variable, List<UncertaintyState> values) {
		return values.stream()
				.filter(each -> each.getId().equals(variable.getEntityName()))
				.map(UncertaintyState::getValue)
				.findFirst();
	}

}
