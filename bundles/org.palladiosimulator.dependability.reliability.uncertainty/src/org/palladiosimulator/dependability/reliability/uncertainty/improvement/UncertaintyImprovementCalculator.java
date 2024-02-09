package org.palladiosimulator.dependability.reliability.uncertainty.improvement;

import java.util.List;
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
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.distributionfunction.Domain;
import tools.mdsd.probdist.distributionfunction.ProbabilityDistribution;
import tools.mdsd.probdist.distributionfunction.TabularCPD;

public class UncertaintyImprovementCalculator {

    private final static UncertaintyImprovementCalculator CALCULATOR_INSTANCE = new UncertaintyImprovementCalculator();

    private UncertaintyImprovementCalculator() {

    }

    public static UncertaintyImprovementCalculator get() {
        return CALCULATOR_INSTANCE;
    }

    public CategoricalValue calculate(UncertaintyImprovement improvement, CategoricalValue value,
            IProbabilityDistributionFactory probabilityDistributionFactory) {
        return new UncertaintySwitch<CategoricalValue>() {

            @Override
            public CategoricalValue caseProbabilisticImprovement(ProbabilisticImprovement object) {
                return calculateProbabilistically(object, value, probabilityDistributionFactory);
            }

            @Override
            public CategoricalValue caseDeterministicImprovement(DeterministicImprovement object) {
                return calculateDeterministically(object, value);
            }

        }.doSwitch(improvement);
    }

    private CategoricalValue calculateProbabilistically(ProbabilisticImprovement improvement, CategoricalValue value,
            IProbabilityDistributionFactory probabilityDistributionFactory) {
        var distribution = createCPD(improvement.getProbabilityDistribution(), probabilityDistributionFactory);

        List<Conditional<CategoricalValue>> conditionals = Lists.newArrayList(new Conditional(Domain.CATEGORY, value));
        return distribution.given(conditionals)
            .sample();
    }

    public ConditionalProbabilityDistribution createCPD(ProbabilityDistribution dist,
            IProbabilityDistributionFactory probabilityDistributionFactory) {
        if (dist.getParams()
            .isEmpty()) {
            throw new IllegalArgumentException("The distribution parameters must be set.");
        }

        var paramRepresentation = dist.getParams()
            .get(0)
            .getRepresentation();
        if (TabularCPD.class.isInstance(paramRepresentation) == false) {
            throw new IllegalArgumentException("The parameter representation must be of type TabularCPD.");
        }

        return new ConditionalProbabilityDistribution(dist, (TabularCPD) paramRepresentation,
                probabilityDistributionFactory);
    }

    public ConditionalProbabilityDistribution createIndicatorCPD(DeterministicImprovement improvement,
            IProbabilityDistributionFactory probabilityDistributionFactory) {
        return new ConditionalProbabilityDistribution(null, null, probabilityDistributionFactory) {

            private CategoricalValue givenValue = null;

            @Override
            public Double probability(CategoricalValue value) {
                if (givenValue == null) {
                    throw new RuntimeException("The conditional value must be set.");
                }

                var deterministicValue = calculate(improvement, givenValue, probabilityDistributionFactory);
                return value.get()
                    .equals(deterministicValue.get()) ? 1.0 : 0.0;
            }

            @Override
            public CategoricalValue sample() {
                if (givenValue == null) {
                    throw new RuntimeException("The conditional value must be set.");
                }

                return calculate(improvement, givenValue, probabilityDistributionFactory);
            }

            @Override
            public ConditionalProbabilityDistribution given(List<Conditional<CategoricalValue>> conditionals) {
                if (conditionals.size() != 1) {
                    throw new IllegalArgumentException("There must be no more than one conditional.");
                }

                var value = conditionals.get(0);
                if (CategoricalValue.class.isInstance(value) == false) {
                    throw new IllegalArgumentException("The conditional must be a categorical value.");
                }

                givenValue = value.getValue();

                return this;
            }

        };
    }

    private CategoricalValue calculateDeterministically(DeterministicImprovement improvement, CategoricalValue value) {
        return improvement.getMappingTable()
            .stream()
            .filter(entryMatching(value.get()))
            .map(toCategoricalValue())
            .findFirst()
            .orElse(value);
    }

    private Function<MapEntry, CategoricalValue> toCategoricalValue() {
        return entry -> CategoricalValue.create(entry.getValue());
    }

    private Predicate<MapEntry> entryMatching(String value) {
        return entry -> entry.getKey()
            .equals(value);
    }
}
