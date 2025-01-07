package org.palladiosimulator.dependability.reliability.uncertainty.solver.api;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.palladiosimulator.dependability.reliability.uncertainty.solver.markov.BruteForceExplorationStrategy;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.markov.ReliabilityPredictionResult;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.markov.StateSpaceExplorationStrategy;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.markov.UncertaintyBasedReliabilityPredictor;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;

import com.google.common.collect.Sets;

import tools.mdsd.probdist.api.apache.util.IProbabilityDistributionRepositoryLookup;
import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionRegistry;
import tools.mdsd.probdist.api.parser.ParameterParser;
import tools.mdsd.probdist.api.random.ISeedProvider;

public class UncertaintyBasedReliabilityPrediction {

    private final static Set<StateSpaceExplorationStrategy> STATE_SPACE_STRATEGY_REGISTER = Sets.newHashSet();
    static {
        STATE_SPACE_STRATEGY_REGISTER.add(new BruteForceExplorationStrategy());
    }

    protected static Optional<StateSpaceExplorationStrategy> findStrategyWith(String name) {
        return STATE_SPACE_STRATEGY_REGISTER.stream()
            .filter(strategyWith(name))
            .findFirst();
    }

    public static void registerStrategy(StateSpaceExplorationStrategy strategy) {
        STATE_SPACE_STRATEGY_REGISTER.add(strategy);
    }

    public static Set<String> getSupportedStrategies() {
        return STATE_SPACE_STRATEGY_REGISTER.stream()
            .map(StateSpaceExplorationStrategy::getName)
            .collect(toSet());
    }

    public static ReliabilityPredictionResult predict(UncertaintyBasedReliabilityPredictionConfig config,
            IProbabilityDistributionRegistry<CategoricalValue> probabilityDistributionRegistry,
            IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
            ParameterParser parameterParser, IProbabilityDistributionRepositoryLookup probDistRepoLookup,
            Optional<ISeedProvider> seedProvider) {
        return buildReliabilityPredictor(config, probabilityDistributionRegistry, probabilityDistributionFactory,
                parameterParser, probDistRepoLookup, seedProvider).predictSuccessProbability(config.getPCMInstance());
    }

    public static ReliabilityPredictionResult predictGiven(List<UncertaintyState> uncertaintyStates,
            UncertaintyBasedReliabilityPredictionConfig config,
            IProbabilityDistributionRegistry<CategoricalValue> probabilityDistributionRegistry,
            IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
            ParameterParser parameterParser, IProbabilityDistributionRepositoryLookup probDistRepoLookup,
            Optional<ISeedProvider> seedProvider) {
        var results = buildReliabilityPredictor(config, probabilityDistributionRegistry, probabilityDistributionFactory,
                parameterParser, probDistRepoLookup, seedProvider)
                    .predictConditionalSuccessProbability(config.getPCMInstance(), uncertaintyStates);
        return new ReliabilityPredictionResult(results);
    }

    private static UncertaintyBasedReliabilityPredictor buildReliabilityPredictor(
            UncertaintyBasedReliabilityPredictionConfig config,
            IProbabilityDistributionRegistry<CategoricalValue> probabilityDistributionRegistry,
            IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
            ParameterParser parameterParser, IProbabilityDistributionRepositoryLookup probDistRepoLookup,
            Optional<ISeedProvider> seedProvider) {
        var builder = UncertaintyBasedReliabilityPredictor
            .newBuilder(probabilityDistributionRegistry, probabilityDistributionFactory, parameterParser,
                    probDistRepoLookup, seedProvider)
            .withConfig(config.getRunConfig());

        if (config.getStateSpaceExplorationStrategy()
            .isPresent()) {
            builder.exploreStateSpaceWith(config.getStateSpaceExplorationStrategy()
                .get());
        } else {
            builder.bruteForceStateSpaceExploration();
        }

        builder.andUncertaintyRepo(config.getUncertaintyRepository());

        return builder.build();
    }

    private static Predicate<StateSpaceExplorationStrategy> strategyWith(String queriedName) {
        return s -> s.getName()
            .equals(queriedName);
    }

}
