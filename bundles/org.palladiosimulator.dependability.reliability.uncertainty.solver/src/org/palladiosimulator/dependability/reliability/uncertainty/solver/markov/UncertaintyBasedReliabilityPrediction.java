package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.util.ArchitecturalPreconditionUtil.allPreconditionsFulfilled;

import java.util.List;
import java.util.Optional;

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository;
import org.palladiosimulator.dependability.reliability.uncertainty.improvement.UncertaintyImprovementCalculator;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.UncertaintyModelManager;
import org.palladiosimulator.reliability.solver.pcm2markov.MarkovTransformationResult;
import org.palladiosimulator.reliability.solver.pcm2markov.Pcm2MarkovStrategy;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import com.google.common.collect.Lists;

import tools.mdsd.probdist.api.apache.supplier.MultinomialDistributionSupplier;
import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.factory.ProbabilityDistributionFactory;

public class UncertaintyBasedReliabilityPrediction {

	public static class UncertaintyBasedReliabilityPredictionBuilder {

		private PCMSolverWorkflowRunConfiguration config = null;
		private StateSpaceExplorationStrategy exploreStrategy = null;
		private UncertaintyRepository uncertaintyRepo = null;

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

		public UncertaintyBasedReliabilityPrediction build() {
			checkValidity();

			adjustConfig();

			return new UncertaintyBasedReliabilityPrediction(exploreStrategy, config, uncertaintyRepo);
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

	private UncertaintyBasedReliabilityPrediction(StateSpaceExplorationStrategy exploreStrategy,
			PCMSolverWorkflowRunConfiguration config, UncertaintyRepository uncertaintyRepo) {
		this.config = config;
		this.exploreStrategy = exploreStrategy;
		this.uncertaintyRepo = uncertaintyRepo;

		UncertaintyModelManager.get().manage(uncertaintyRepo.getUncertaintyInducedFailureTypes());

		initProbabilityDistributions();
	}

	private void initProbabilityDistributions() {
		ProbabilityDistributionFactory.get().register(new MultinomialDistributionSupplier());
	}

	public static UncertaintyBasedReliabilityPredictionBuilder newBuilder() {
		return new UncertaintyBasedReliabilityPredictionBuilder();
	}

	public ReliabilityPredictionResult predict(PCMInstance unresolved) {
		requireNonNull(exploreStrategy, "Cannot predict reliability when no evaluation strategy is selected.");

		List<ReliabilityPredictionResult> results = Lists.newArrayList();

		var stateSpace = UncertaintyModelManager.get().getStateSpace();
		for (List<UncertaintyState> eachTuple : exploreStrategy.explore(stateSpace)) {
			results.add(predict(unresolved, eachTuple));
		}

		return results.stream().reduce(ReliabilityPredictionResult.marginalizingUncertainties())
				.orElse(ReliabilityPredictionResult.empty());
	}

	public ReliabilityPredictionResult predict(PCMInstance unresolvedModel, List<UncertaintyState> stateTuple) {
		applyArchitecturalCountermeasures(unresolvedModel, stateTuple);

		var resolvedModel = resolveUncertainties(unresolvedModel, stateTuple);

		var probOfSuccess = predictProbabilityOfSuccessGiven(resolvedModel);
		var probOfUncertainties = predictProbabilityOfUncertainties(stateTuple);
		return ReliabilityPredictionResult.of(probOfSuccess, probOfUncertainties);
	}

	private void applyArchitecturalCountermeasures(PCMInstance pcmModel, List<UncertaintyState> stateTuple) {
		for (ArchitecturalCountermeasure each : uncertaintyRepo.getArchitecturalCountermeasures()) {
			var result = findApplicableState(each, stateTuple);
			if (result.isEmpty()) {
				break;
			}

			var oldState = result.get();
			stateTuple.remove(oldState);

			var improvedValue = applyArchitecturalCountermeasure(each, pcmModel, oldState);
			var improvedState = oldState.newValuedStateWith(improvedValue);
			stateTuple.add(improvedState);
		}
	}
	
	private Optional<UncertaintyState> findApplicableState(ArchitecturalCountermeasure countermeasure,
			List<UncertaintyState> stateTuple) {
		return stateTuple.stream()
				.filter(each -> each.getId().equals(countermeasure.getTargetUncertainty().getEntityName())).findFirst();
	}

	private CategoricalValue applyArchitecturalCountermeasure(ArchitecturalCountermeasure countermeasure,
			PCMInstance pcmModel, UncertaintyState state) {
		if (allPreconditionsFulfilled(countermeasure, pcmModel)) {
			var improvement = countermeasure.getUncertaintyImprovement();
			return UncertaintyImprovementCalculator.get().calculate(improvement, state.getValue());
		}
		return state.getValue();
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
	private Double predictProbabilityOfUncertainties(List<UncertaintyState> stateTuple) {
		var probOfUncertainties = 1.0;
		for (UncertaintyInducedFailureType each : uncertaintyRepo.getUncertaintyInducedFailureTypes()) {
			var uncertaintyModel = UncertaintyModelManager.get().findModelFor(each).orElseThrow();
			probOfUncertainties *= uncertaintyModel.probability(stateTuple);
		}
		return probOfUncertainties;
	}

}
