package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.UncertaintyModelManager;
import org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.FailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.ReliabilityPackage;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.transformations.EMFHelper;

public class UncertaintyResolver {

	private final static EMFHelper EMF_HELPER = new EMFHelper();

	private final PCMInstance pcmInstance;

	public UncertaintyResolver(PCMInstance pcmInstance) {
		this.pcmInstance = pcmInstance;
	}

	public void resolve(UncertaintyInducedFailureType uncertainty, List<UncertaintyState> values) {
		for (FailureType eachFailureType : filterFailureTypes(pcmInstance)) {
				if (isRefined(eachFailureType, uncertainty)) {
					var desc = findFailureOccurenceDesc(eachFailureType, pcmInstance)
							.orElseThrow(() -> new RuntimeException(
									String.format("No failure occurence describtion found for failure type %s",
											eachFailureType.getEntityName())));
					updateFailureDescribtion(desc, computeProbabilityOfFailure(uncertainty, values));
				}
			
		}
	}
	
	private double computeProbabilityOfFailure(UncertaintyInducedFailureType uncertainty,
			List<UncertaintyState> values) {
		var uncertaintyModel = UncertaintyModelManager.get().findModelFor(uncertainty).orElseThrow();
		return uncertaintyModel.probabilityOfFailure(values);
	}

	public PCMInstance getResolved() {
		return pcmInstance;
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

	private void updateFailureDescribtion(FailureOccurrenceDescription desc, double newFailureProbability) {
		desc.setFailureProbability(newFailureProbability);
	}

}
