package org.palladiosimulator.dependability.reliability.uncertainty.solver.markov;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Predicate;

import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.UncertaintyModelManager;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.FailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.HardwareInducedFailureType;
import org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.NetworkInducedFailureType;
import org.palladiosimulator.pcm.reliability.ReliabilityPackage;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.reliability.util.ReliabilitySwitch;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.transformations.EMFHelper;

import com.google.common.collect.Lists;

public class UncertaintyResolver {

	private class ProbabilityUpdater {

		public void update(FailureType failureType, double failureProb) {
			new ReliabilitySwitch<Boolean>() {

				@Override
				public Boolean caseHardwareInducedFailureType(HardwareInducedFailureType object) {
					filterProcessingResourceSpecsWith(object).forEach(spec -> spec.setMTTF(failureProb));
					return true;
				}

				@Override
				public Boolean caseNetworkInducedFailureType(NetworkInducedFailureType object) {
					filterLinkingResourcesWith(object)
							.forEach(link -> link.getCommunicationLinkResourceSpecifications_LinkingResource()
									.setFailureProbability(failureProb));
					return true;
				}

				@Override
				public Boolean caseSoftwareInducedFailureType(SoftwareInducedFailureType object) {
					filterFailureOccurenceDescsWith(object).forEach(desc -> desc.setFailureProbability(failureProb));
					return true;
				}

			}.doSwitch(failureType);
		}

		private List<ProcessingResourceSpecification> filterProcessingResourceSpecsWith(
				HardwareInducedFailureType failureType) {
			return pcmInstance.getResourceEnvironment().getResourceContainer_ResourceEnvironment().stream()
					.flatMap(each -> each.getActiveResourceSpecifications_ResourceContainer().stream())
					.filter(describing(failureType)).collect(toList());
		}

		private List<LinkingResource> filterLinkingResourcesWith(NetworkInducedFailureType failureType) {
			return pcmInstance.getResourceEnvironment().getLinkingResources__ResourceEnvironment().stream()
					.filter(describing(failureType)).collect(toList());
		}

		private List<FailureOccurrenceDescription> filterFailureOccurenceDescsWith(
				SoftwareInducedFailureType failureType) {
			List<FailureOccurrenceDescription> result = Lists.newArrayList();
			for (AssemblyContext eachAssemblyContext : pcmInstance.getSystem()
					.getAssemblyContexts__ComposedStructure()) {
				var instantiated = eachAssemblyContext.getEncapsulatedComponent__AssemblyContext();
				if (instantiated instanceof BasicComponent) {
					var seffs = BasicComponent.class.cast(instantiated)
							.getServiceEffectSpecifications__BasicComponent();
					for (ServiceEffectSpecification eachSeff : seffs) {
						var descs = filterFailureOccurenceDescs(eachSeff).stream().filter(describing(failureType))
								.collect(toList());
						if (descs.isEmpty() == false) {
							result.addAll(descs);
						}
					}
				}
			}
			return result;
		}

		private Predicate<ProcessingResourceSpecification> describing(HardwareInducedFailureType searchedFailureType) {
			return spec -> spec.getActiveResourceType_ActiveResourceSpecification().getId()
					.equals(searchedFailureType.getProcessingResourceType__HardwareInducedFailureType().getId());
		}

		private Predicate<LinkingResource> describing(NetworkInducedFailureType searchedFailureType) {
			return link -> link.getCommunicationLinkResourceSpecifications_LinkingResource()
					.getCommunicationLinkResourceType_CommunicationLinkResourceSpecification().getId()
					.equals(searchedFailureType.getCommunicationLinkResourceType__NetworkInducedFailureType().getId());
		}

		private Predicate<FailureOccurrenceDescription> describing(SoftwareInducedFailureType searchedFailureType) {
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

		private List<FailureOccurrenceDescription> filterFailureOccurenceDescs(ServiceEffectSpecification root) {
			return EMF_HELPER.getElements(root, ReliabilityPackage.eINSTANCE.getFailureOccurrenceDescription()).stream()
					.map(FailureOccurrenceDescription.class::cast).collect(toList());
		}

	}

	private final static EMFHelper EMF_HELPER = new EMFHelper();

	private final PCMInstance pcmInstance;

	public UncertaintyResolver(PCMInstance pcmInstance) {
		this.pcmInstance = pcmInstance;
	}

	public void resolve(UncertaintyInducedFailureType uncertainty, List<UncertaintyState> values) {
		var probabilityUpdater = new ProbabilityUpdater();
		for (FailureType each : filterFailureTypes(pcmInstance)) {
			if (isRefined(each, uncertainty)) {
				probabilityUpdater.update(each, computeProbabilityOfFailure(uncertainty, values));
			}
		}
	}

	private double computeProbabilityOfFailure(UncertaintyInducedFailureType uncertainty,
			List<UncertaintyState> values) {
		var uncertaintyModel = UncertaintyModelManager.get().findModelFor(uncertainty).orElseThrow();
		return uncertaintyModel.probabilityOfFailureGiven(values);
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

}
