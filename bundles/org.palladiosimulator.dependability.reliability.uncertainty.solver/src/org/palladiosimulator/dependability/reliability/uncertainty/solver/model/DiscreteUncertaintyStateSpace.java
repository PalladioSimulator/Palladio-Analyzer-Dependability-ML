package org.palladiosimulator.dependability.reliability.uncertainty.solver.model;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;
import org.palladiosimulator.envdyn.environment.staticmodel.LocalProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.templatevariable.TemplateVariable;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.factory.ProbabilityDistributionFactory;
import tools.mdsd.probdist.distributionfunction.ParamRepresentation;
import tools.mdsd.probdist.distributionfunction.SimpleParameter;
import tools.mdsd.probdist.distributionfunction.TabularCPD;
import tools.mdsd.probdist.distributionfunction.TabularCPDEntry;

public class DiscreteUncertaintyStateSpace {

	public static class UncertaintyState {

		private final GroundRandomVariable variable;
		private final Set<CategoricalValue> valueSpace;
		private final CategoricalValue value;

		private UncertaintyState(UncertaintyState state, CategoricalValue value) {
			this.variable = state.variable;
			this.valueSpace = state.valueSpace;
			this.value = value;
		}

		private UncertaintyState(GroundRandomVariable variable, Set<CategoricalValue> valueSpace) {
			this.variable = variable;
			this.valueSpace = valueSpace;
			this.value = null;
		}

		public static UncertaintyState of(GroundRandomVariable variable, Set<CategoricalValue> valueSpace) {
			return new UncertaintyState(variable, valueSpace);
		}

		public UncertaintyState newValuedStateWith(CategoricalValue value) {
			return new UncertaintyState(this, value);
		}

		public String getId() {
			return variable.getEntityName();
		}

		public Set<CategoricalValue> getValueSpace() {
			return valueSpace;
		}

		public CategoricalValue getValue() {
			return value;
		}
		
		public boolean instantiates(TemplateVariable template) {
			return getInstantiatedTemplate().getId().equals(template.getId());
		}
		
		public TemplateVariable getInstantiatedTemplate() {
			return variable.getInstantiatedTemplate();
		}
		
		private boolean valueSpaceIsEqual(UncertaintyState state) {
			if (state.valueSpace.size() != this.valueSpace.size()) {
				return false;
			}
			
			var copy = Sets.newHashSet(this.valueSpace);
			for (CategoricalValue each : state.valueSpace) {
				copy.removeIf(v -> v.get().equals(each.get()));
			}
			return copy.isEmpty();
		}

	}

	private final List<UncertaintyState> states;

	private DiscreteUncertaintyStateSpace(List<UncertaintyState> states) {
		this.states = mergeToDistinctStates(states);
	}

	private List<UncertaintyState> mergeToDistinctStates(List<UncertaintyState> states) {
		var distinctStates = Lists.<UncertaintyState>newArrayList();
		for (UncertaintyState each : states) {
			if (distinctStates.stream().noneMatch(s -> s.valueSpaceIsEqual(each))) {
				distinctStates.add(each);
			}
		}
		return distinctStates;
	}

	public static DiscreteUncertaintyStateSpace of(UncertaintyState... uncertaintyStates) {
		return new DiscreteUncertaintyStateSpace(Lists.newArrayList(uncertaintyStates));
	}

	public static DiscreteUncertaintyStateSpace of(List<UncertaintyState> uncertaintyStates) {
		return new DiscreteUncertaintyStateSpace(uncertaintyStates);
	}

	public static Set<UncertaintyState> valueSpaceOf(UncertaintyInducedFailureType uncertainty) {
		GroundProbabilisticNetwork groundNetwork = uncertainty.getUncertaintyModel();
		if (groundNetwork.getLocalProbabilisticModels().size() != 1) {
			throw new IllegalArgumentException("The bayesian network must include only one local network.");
		}

		return deriveUncertaintyStates(groundNetwork.getLocalProbabilisticModels().get(0));
	}

	private static Set<UncertaintyState> deriveUncertaintyStates(LocalProbabilisticNetwork localNetwork) {
		return localNetwork.getGroundRandomVariables().stream().map(DiscreteUncertaintyStateSpace::toUncertaintyState)
				.collect(toSet());
	}

	public static UncertaintyState toUncertaintyState(GroundRandomVariable variable) {
		var probDist = variable.getDescriptiveModel().getDistribution();
		if (probDist.getParams().size() != 1) {
			throw new IllegalArgumentException("The distribution is not valid for this operation.");
		}

		var param = probDist.getParams().get(0).getRepresentation();
		return UncertaintyState.of(variable, getValueSpace(param));
	}

	private static Set<CategoricalValue> getValueSpace(ParamRepresentation param) {
		if (SimpleParameter.class.isInstance(param)) {
			return getValueSpace(SimpleParameter.class.cast(param));
		} else if (TabularCPD.class.isInstance(param)) {
			TabularCPDEntry any = TabularCPD.class.cast(param).getCpdEntries().get(0);
			return getValueSpace(any.getEntry());
		}

		throw new IllegalArgumentException("The distribution parameter type is not proper in this context.");
	}

	private static Set<CategoricalValue> getValueSpace(SimpleParameter param) {
		var samples = ProbabilityDistributionFactory.getParameterParser().parseSampleSpace(param);
		return samples.stream().map(each -> each.value).collect(toSet());
	}

	public Set<List<UncertaintyState>> cartesianProduct() {
		return Sets.cartesianProduct(getStateSpace()).stream().map(enrichWithValues()).collect(toSet());
	}

	public Optional<UncertaintyState> findStateInstantiating(TemplateVariable template) {
		return states.stream().filter(each -> each.instantiates(template)).findFirst();
	}

	public int getNumberOfStates() {
		return states.size();
	}

	private List<Set<CategoricalValue>> getStateSpace() {
		return states.stream().map(each -> each.getValueSpace()).collect(toList());
	}

	private Function<List<CategoricalValue>, List<UncertaintyState>> enrichWithValues() {
		return values -> {
			List<UncertaintyState> valuedStates = Lists.newArrayList();
			for (int i = 0; i < states.size(); i++) {
				var valuedState = states.get(i).newValuedStateWith(values.get(i));
				valuedStates.add(valuedState);
			}
			return valuedStates;
		};
	}

}
