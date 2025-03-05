package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.util.ArchitecturalPreconditionUtil.allPreconditionsFulfilled;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.UncertaintyModelManager;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.util.ArchitecturalCountermeasureOperator;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;
import org.palladiosimulator.reliability.solver.pcm2markov.Pcm2MarkovStrategy;
import org.palladiosimulator.solver.core.models.PCMInstance;
import org.palladiosimulator.solver.core.runconfig.PCMSolverWorkflowRunConfiguration;

import tools.mdsd.probdist.api.apache.util.IProbabilityDistributionRepositoryLookup;
import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionRegistry;
import tools.mdsd.probdist.api.parser.ParameterParser;
import tools.mdsd.probdist.api.random.ISeedProvider;

public class UncertaintyBasedReliabilityPredictor {

    public static class UncertaintyBasedReliabilityPredictionBuilder {

        private PCMSolverWorkflowRunConfiguration config = null;
        private StateSpaceExplorationStrategy exploreStrategy = null;
        private UncertaintyRepository uncertaintyRepo = null;
        private final IProbabilityDistributionRegistry<CategoricalValue> probabilityDistributionRegistry;
        private final IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory;
        private final ParameterParser parameterParser;
        private final IProbabilityDistributionRepositoryLookup probDistRepoLookup;
        private final Optional<ISeedProvider> seedProvider;

        public UncertaintyBasedReliabilityPredictionBuilder(
                IProbabilityDistributionRegistry<CategoricalValue> probabilityDistributionRegistry,
                IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
                ParameterParser parameterParser, IProbabilityDistributionRepositoryLookup probDistRepoLookup,
                Optional<ISeedProvider> seedProvider) {
            this.probabilityDistributionRegistry = probabilityDistributionRegistry;
            this.probabilityDistributionFactory = probabilityDistributionFactory;
            this.parameterParser = parameterParser;
            this.probDistRepoLookup = probDistRepoLookup;
            this.seedProvider = seedProvider;
        }

        public UncertaintyBasedReliabilityPredictionBuilder withConfig(PCMSolverWorkflowRunConfiguration config) {
            this.config = config;
            return this;
        }

        public UncertaintyBasedReliabilityPredictionBuilder bruteForceStateSpaceExploration() {
            this.exploreStrategy = new BruteForceExplorationStrategy();
            return this;
        }

        public UncertaintyBasedReliabilityPredictionBuilder exploreStateSpaceWith(
                StateSpaceExplorationStrategy strategy) {
            this.exploreStrategy = strategy;
            return this;
        }

        public UncertaintyBasedReliabilityPredictionBuilder andUncertaintyRepo(UncertaintyRepository uncertaintyRepo) {
            this.uncertaintyRepo = uncertaintyRepo;
            return this;
        }

        public UncertaintyBasedReliabilityPredictor build() {
            checkValidity();

            adjustConfig();

            return new UncertaintyBasedReliabilityPredictor(exploreStrategy, config, uncertaintyRepo,
                    probabilityDistributionRegistry, probabilityDistributionFactory, parameterParser,
                    probDistRepoLookup, seedProvider);
        }

        private void checkValidity() {
            requireNonNull(config, "The reliability configuration is missing.");
            requireNonNull(uncertaintyRepo, "The uncertainty repository is missing.");
        }

        private void adjustConfig() {
            // Deactivate result presentation if state space is explored. This is due to the
            // high number of possible results which pop up after each state analysis.
            if (nonNull(exploreStrategy)) {
                config.setShowHtmlResults(false);
            }
        }

    }

    private final UncertaintyRepository uncertaintyRepo;
    private final PCMSolverWorkflowRunConfiguration config;
    private final StateSpaceExplorationStrategy exploreStrategy;
    private final IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory;
    private final ParameterParser parameterParser;
    private final Optional<ISeedProvider> seedProvider;

    private ArchitecturalCountermeasureOperator operator = null;

    private UncertaintyBasedReliabilityPredictor(StateSpaceExplorationStrategy exploreStrategy,
            PCMSolverWorkflowRunConfiguration config, UncertaintyRepository uncertaintyRepo,
            IProbabilityDistributionRegistry<CategoricalValue> probabilityDistributionRegistry,
            IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
            ParameterParser parameterParser, IProbabilityDistributionRepositoryLookup probDistRepoLookup,
            Optional<ISeedProvider> seedProvider) {
        this.config = config;
        this.exploreStrategy = exploreStrategy;
        this.uncertaintyRepo = uncertaintyRepo;
        this.probabilityDistributionFactory = probabilityDistributionFactory;
        this.parameterParser = parameterParser;
        this.seedProvider = seedProvider;

        var manager = UncertaintyModelManager.get();
        manager.reset();
        manager.manage(uncertaintyRepo.getUncertaintyInducedFailureTypes(), probabilityDistributionFactory,
                parameterParser, seedProvider);

    }

    public static UncertaintyBasedReliabilityPredictionBuilder newBuilder(
            IProbabilityDistributionRegistry<CategoricalValue> probabilityDistributionRegistry,
            IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
            ParameterParser parameterParser, IProbabilityDistributionRepositoryLookup probDistRepoLookup,
            Optional<ISeedProvider> seedProvider) {
        return new UncertaintyBasedReliabilityPredictionBuilder(probabilityDistributionRegistry,
                probabilityDistributionFactory, parameterParser, probDistRepoLookup, seedProvider);
    }

    public ReliabilityPredictionResult predictSuccessProbability(PCMInstance unresolved) {
        requireNonNull(exploreStrategy, "Cannot predict reliability when no evaluation strategy is selected.");

        ReliabilityPredictionResult result = new ReliabilityPredictionResult();

        getArchitecturalCountermeasureOperator(unresolved, probabilityDistributionFactory, parameterParser,
                seedProvider).applyToUncertaintyModels();

        var stateSpace = UncertaintyModelManager.get()
            .getStateSpace();
        for (List<UncertaintyState> eachTuple : exploreStrategy.explore(stateSpace)) {
            var conditionalPoS = predict(unresolved, eachTuple);
            result.addAll(conditionalPoS);
        }

        return result;
    }

    public Set<ReliabilityPredictionResultPerScenario> predictConditionalSuccessProbability(PCMInstance unresolved,
            List<UncertaintyState> stateTuple) {
        var improved = getArchitecturalCountermeasureOperator(unresolved, probabilityDistributionFactory,
                parameterParser, seedProvider).applyToUncertainties(stateTuple);
        return predict(unresolved, improved);
    }

    private Set<ReliabilityPredictionResultPerScenario> predict(PCMInstance unresolved,
            List<UncertaintyState> stateTuple) {
        var resolved = resolveUncertainties(unresolved, stateTuple);

        var conditionalPoS = predictProbabilityOfSuccessGiven(resolved);
        var probOfUncertainties = predictProbabilityOfUncertainties(stateTuple, resolved);

        return conditionalPoS.stream()
            .map(each -> ReliabilityPredictionResultPerScenario.of(each, stateTuple, probOfUncertainties))
            .collect(toSet());
    }

    private PCMInstance resolveUncertainties(PCMInstance modelToResolve, List<UncertaintyState> stateTuple) {
        var uncertaintyResolver = new UncertaintyResolver(modelToResolve);
        uncertaintyRepo.getUncertaintyInducedFailureTypes()
            .forEach(uncertainty -> uncertaintyResolver.resolve(uncertainty, stateTuple));
        return modelToResolve;
    }

    private List<MarkovTransformationResult> predictProbabilityOfSuccessGiven(PCMInstance pcmModel) {
        var solver = new Pcm2MarkovStrategy(config);
        solver.transform(pcmModel);
        return solver.getAllSolvedValues();
    }

    // Assuming independence of the uncertainty models
    private Double predictProbabilityOfUncertainties(List<UncertaintyState> stateTuple, PCMInstance pcmModel) {
        var probOfUncertainties = 1.0;
        for (UncertaintyInducedFailureType each : uncertaintyRepo.getUncertaintyInducedFailureTypes()) {
            if (allPreconditionsFulfilled(each, pcmModel)) {
                var uncertaintyModel = UncertaintyModelManager.get()
                    .findModelFor(each)
                    .orElseThrow();
                probOfUncertainties *= uncertaintyModel.probability(stateTuple);
            }
        }
        return probOfUncertainties;
    }

    private ArchitecturalCountermeasureOperator getArchitecturalCountermeasureOperator(PCMInstance unresolved,
            IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
            ParameterParser parameterParser, Optional<ISeedProvider> seedProvider) {
        if (operator == null) {
            operator = ArchitecturalCountermeasureOperator.createOperatorFor(unresolved, uncertaintyRepo,
                    probabilityDistributionFactory, parameterParser, seedProvider);
        }
        return operator;
    }

}
