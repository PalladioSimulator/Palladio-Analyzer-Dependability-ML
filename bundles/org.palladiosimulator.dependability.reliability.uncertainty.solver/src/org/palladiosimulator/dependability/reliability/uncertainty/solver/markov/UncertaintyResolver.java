package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.markov.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.envdyn.api.entity.bn.BayesianNetwork;
import org.palladiosimulator.envdyn.api.entity.bn.BayesianNetwork.InputValue;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;
import org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.FailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.ReliabilityPackage;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.transformations.EMFHelper;

import com.google.common.collect.Lists;

import tools.mdsd.probdist.api.entity.CategoricalValue;

public class UncertaintyResolver {

	private final static EMFHelper EMF_HELPER = new EMFHelper();

	private final List<UncertaintyInducedFailureType> uncertainties;

	public UncertaintyResolver(List<UncertaintyInducedFailureType> uncertainties) {
		this.uncertainties = uncertainties;
	}

	public PCMInstance resolve(PCMInstance pcmModel, List<UncertaintyState> values) {
		for (FailureType eachFailureType : filterFailureTypes(pcmModel)) {
			for (UncertaintyInducedFailureType eachUncertainty : uncertainties) {
				if (isRefined(eachFailureType, eachUncertainty)) {
					FailureOccurrenceDescription desc = findFailureOccurenceDesc(eachFailureType, pcmModel)
							.orElseThrow(() -> new RuntimeException(
									String.format("No failure occurence describtion found for failure type %s",
											eachFailureType.getEntityName())));
					updateFailureOccurenceDescribtion(desc, computeFailureProbability(eachUncertainty, values));
				}
			}
		}
		return pcmModel;
	}

	private List<FailureType> filterFailureTypes(PCMInstance pcmModel) {
		return pcmModel.getRepositories().stream().flatMap(each -> filterFailureTypes(each).stream()).collect(toList());
	}

	private List<FailureType> filterFailureTypes(Repository root) {
		return EMF_HELPER.getElements(root, ReliabilityPackage.eINSTANCE.getFailureType()).stream()
				.map(FailureType.class::cast).collect(toList());
	}

	private boolean isRefined(FailureType failureType, UncertaintyInducedFailureType uncertainty) {
		return areEqual(uncertainty.getRefines(), failureType);
	}

	private boolean areEqual(FailureType first, FailureType second) {
		return first.getId().equals(second.getId());
	}

	private double computeFailureProbability(UncertaintyInducedFailureType uncertainty, List<UncertaintyState> values) {
		List<InputValue> inputValues = Lists.newArrayList();

		BayesianNetwork bn = new BayesianNetwork(null, uncertainty.getUncertaintyModel());
		for (GroundRandomVariable each : bn.getGroundVariables()) {
			CategoricalValue value = findValue(each, values).orElseThrow(() -> new RuntimeException(
					String.format("An error occurred; there is no value for variable %s", each.getEntityName())));
			inputValues.add(InputValue.create(value, each));
		}
		
		return bn.probability(inputValues);
	}

	private Optional<CategoricalValue> findValue(GroundRandomVariable variable, List<UncertaintyState> values) {
		return values.stream().filter(each -> each.getId().equals(variable.getId())).map(UncertaintyState::getValue)
				.findFirst();
	}

	private Optional<FailureOccurrenceDescription> findFailureOccurenceDesc(FailureType failureType,
			PCMInstance pcmModel) {
		return pcmModel.getRepositories().stream().flatMap(each -> filterFailureOccurenceDescs(each).stream())
				.filter(describing(failureType)).findFirst();
	}

	private Predicate<FailureOccurrenceDescription> describing(FailureType searchedFailureType) {
		return desc -> {
			FailureType describedFailure = null;
			if (InternalFailureOccurrenceDescription.class.isInstance(desc)) {
				describedFailure = InternalFailureOccurrenceDescription.class.cast(desc)
						.getSoftwareInducedFailureType__InternalFailureOccurrenceDescription();
			} else {
				describedFailure = ExternalFailureOccurrenceDescription.class.cast(desc)
						.getFailureType__ExternalFailureOccurrenceDescription();
			}

			return areEqual(describedFailure, searchedFailureType);
		};
	}

	private List<FailureOccurrenceDescription> filterFailureOccurenceDescs(Repository root) {
		return EMF_HELPER.getElements(root, ReliabilityPackage.eINSTANCE.getFailureOccurrenceDescription()).stream()
				.map(FailureOccurrenceDescription.class::cast).collect(toList());
	}

	private void updateFailureOccurenceDescribtion(FailureOccurrenceDescription desc, double newFailureProbability) {
		desc.setFailureProbability(newFailureProbability);
	}

}
