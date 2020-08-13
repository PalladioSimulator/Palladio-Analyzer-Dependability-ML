package org.palladiosimulator.dependability.reliability.uncertainty.improvement;

import java.util.function.Function;
import java.util.function.Predicate;

import org.palladiosimulator.dependability.reliability.uncertainty.DeterministicImprovement;
import org.palladiosimulator.dependability.reliability.uncertainty.MapEntry;
import org.palladiosimulator.dependability.reliability.uncertainty.ProbabilisticImprovement;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyImprovement;
import org.palladiosimulator.dependability.reliability.uncertainty.util.UncertaintySwitch;

import com.google.common.collect.Lists;

import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.entity.Conditionable.Conditional;
import tools.mdsd.probdist.api.entity.ConditionalProbabilityDistribution;
import tools.mdsd.probdist.api.factory.ProbabilityDistributionFactory;
import tools.mdsd.probdist.distributionfunction.Domain;

public class UncertaintyImprovementCalculator {

	private final static UncertaintyImprovementCalculator CALCULATOR_INSTANCE = new UncertaintyImprovementCalculator();

	private UncertaintyImprovementCalculator() {

	}

	public static UncertaintyImprovementCalculator get() {
		return CALCULATOR_INSTANCE;
	}

	public CategoricalValue calculate(UncertaintyImprovement improvement, CategoricalValue value) {
		return new UncertaintySwitch<CategoricalValue>() {

			@Override
			public CategoricalValue caseProbabilisticImprovement(ProbabilisticImprovement object) {
				return calculateProbabilistically(object, value);
			}

			@Override
			public CategoricalValue caseDeterministicImprovement(DeterministicImprovement object) {
				return calculateDeterministically(object, value);
			}

		}.doSwitch(improvement);
	}

	private CategoricalValue calculateProbabilistically(ProbabilisticImprovement improvement, CategoricalValue value) {
		var distribution = (ConditionalProbabilityDistribution) ProbabilityDistributionFactory.get()
				.getInstanceOf(improvement.getProbabilityDistribution()).orElseThrow();

		var conditionals = Lists.newArrayList(new Conditional(Domain.CATEGORY, value));
		return distribution.given(conditionals).sample();
	}

	private CategoricalValue calculateDeterministically(DeterministicImprovement improvement, CategoricalValue value) {
		return improvement.getMappingTable().stream().filter(entryMatching(value.get())).map(toCategoricalValue())
				.findFirst().orElse(value);
	}

	private Function<MapEntry, CategoricalValue> toCategoricalValue() {
		return entry -> CategoricalValue.create(entry.getValue());
	}

	private Predicate<MapEntry> entryMatching(String value) {
		return entry -> entry.getKey().equals(value);
	}
}
