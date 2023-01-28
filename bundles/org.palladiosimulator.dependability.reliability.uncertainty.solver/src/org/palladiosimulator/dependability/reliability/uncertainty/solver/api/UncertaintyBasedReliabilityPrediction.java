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

import tools.mdsd.probdist.api.factory.IProbabilityDistributionRegistry;

public class UncertaintyBasedReliabilityPrediction {

	private final static Set<StateSpaceExplorationStrategy> STATE_SPACE_STRATEGY_REGISTER = Sets.newHashSet();
	static {
		STATE_SPACE_STRATEGY_REGISTER.add(new BruteForceExplorationStrategy());
	}

	protected static Optional<StateSpaceExplorationStrategy> findStrategyWith(String name) {
		return STATE_SPACE_STRATEGY_REGISTER.stream().filter(strategyWith(name)).findFirst();
	}

	public static void registerStrategy(StateSpaceExplorationStrategy strategy) {
		STATE_SPACE_STRATEGY_REGISTER.add(strategy);
	}

	public static Set<String> getSupportedStrategies() {
		return STATE_SPACE_STRATEGY_REGISTER.stream().map(StateSpaceExplorationStrategy::getName).collect(toSet());
	}

	public static ReliabilityPredictionResult predict(UncertaintyBasedReliabilityPredictionConfig config, IProbabilityDistributionRegistry probabilityDistributionRegistry) {
		return buildReliabilityPredictor(config, probabilityDistributionRegistry).predictSuccessProbability(config.getPCMInstance());
	}

	public static ReliabilityPredictionResult predictGiven(List<UncertaintyState> uncertaintyStates,
			UncertaintyBasedReliabilityPredictionConfig config, IProbabilityDistributionRegistry probabilityDistributionRegistry) {
		var results = buildReliabilityPredictor(config, probabilityDistributionRegistry).predictConditionalSuccessProbability(config.getPCMInstance(),
				uncertaintyStates);
		return new ReliabilityPredictionResult(results);
	}

	private static UncertaintyBasedReliabilityPredictor buildReliabilityPredictor(
			UncertaintyBasedReliabilityPredictionConfig config, IProbabilityDistributionRegistry probabilityDistributionRegistry) {
		var builder = UncertaintyBasedReliabilityPredictor.newBuilder().withConfig(config.getRunConfig());

		if (config.getStateSpaceExplorationStrategy().isPresent()) {
			builder.exploreStateSpaceWith(config.getStateSpaceExplorationStrategy().get());
		} else {
			builder.bruteForceStateSpaceExploration();
		}

		builder.andUncertaintyRepo(config.getUncertaintyRepository());

		return builder.build(probabilityDistributionRegistry);
	}

	private static Predicate<StateSpaceExplorationStrategy> strategyWith(String queriedName) {
		return s -> s.getName().equals(queriedName);
	}

}
