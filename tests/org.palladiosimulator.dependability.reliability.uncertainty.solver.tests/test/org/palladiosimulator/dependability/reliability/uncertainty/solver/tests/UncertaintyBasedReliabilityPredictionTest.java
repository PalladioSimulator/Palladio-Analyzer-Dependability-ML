package org.palladiosimulator.dependability.reliability.uncertainty.solver.tests;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Ignore;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.tests.ReliabilityPredictionTestDefinition.PredictionResultBasedAssertion;

import com.google.common.collect.Sets;

import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.parser.DefaultParameterParser;
import tools.mdsd.probdist.api.parser.ParameterParser;

class UncertaintyBasedReliabilityPredictionTest extends BaseReliabilityPredictionTest {

    private static final String ML_VARIABLE = "MLPredictionSensitivity";

    @Ignore
    public void test() {
        ParameterParser parameterParser = new DefaultParameterParser();
        getUncertaintyStateSpace(parameterParser).forEach(this::test);
    }

    public void test(UncertaintyState uncertaintyState) {
        ReliabilityPredictionTestDefinition.createTest()
            .givenDefaultRunConfigs()
            .whenApplyingPCMRel()
            .whenApplyingUncertaintyBasedPCMRel()
            .thenAssert(uncertaintyProbabilityIsSmallerThanSuccessProbability(uncertaintyState))
            .test();
    }

    private PredictionResultBasedAssertion uncertaintyProbabilityIsSmallerThanSuccessProbability(
            UncertaintyState uncertaintyState) {
        return (pcmRelResult, uncertaintyResult) -> {
            var success = pcmRelResult.getSuccessProbability();

            var usageScenario = pcmRelResult.getScenario();
            var predictionPerUsage = uncertaintyResult.filterPredictionResultsFor(usageScenario)
                .iterator()
                .next();
            var joined = predictionPerUsage.getJoinedSuccessAndUncertaintyProbability();

            assertTrue(joined <= success);
        };
    }

    private Set<UncertaintyState> getUncertaintyStateSpace(ParameterParser parameterParser) {
        var stateSpace = DiscreteUncertaintyStateSpace.valueSpaceOf(getUncertaintyInducedFailureType(),
                parameterParser);
        excludeMLUncertaintyState(stateSpace);
        return enrichToValuedUncertaintyStates(stateSpace);
    }

    private void excludeMLUncertaintyState(Set<UncertaintyState> stateSpace) {
        stateSpace.removeIf(each -> each.getId()
            .equals(ML_VARIABLE));
    }

    private Set<UncertaintyState> enrichToValuedUncertaintyStates(Set<UncertaintyState> stateSpace) {
        var stateToEnrich = stateSpace.iterator()
            .next();

        Set<UncertaintyState> enriched = Sets.newHashSet();
        for (CategoricalValue each : stateToEnrich.getValueSpace()) {
            enriched.add(stateToEnrich.newValuedStateWith(each));
        }
        return enriched;
    }

    private UncertaintyInducedFailureType getUncertaintyInducedFailureType() {
        var repoURI = URI.createURI(makeAbsolute(RELATIVE_UNCERTAINTY_TEST_MODEL_PATH), true);
        var repoResource = new ResourceSetImpl().getResource(repoURI, true);
        var repo = Optional.ofNullable(repoResource)
            .map(Resource::getContents)
            .map(Collection::iterator)
            .map(Iterator::next)
            .filter(UncertaintyRepository.class::isInstance)
            .map(UncertaintyRepository.class::cast)
            .orElseThrow();
        return repo.getUncertaintyInducedFailureTypes()
            .get(0);
    }

}
