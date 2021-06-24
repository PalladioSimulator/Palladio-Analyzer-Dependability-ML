package org.palladiosimulator.dependability.reliability.uncertainty.precondition;

import java.util.function.Predicate;
import java.util.stream.Stream;

import org.palladiosimulator.dependability.reliability.uncertainty.ActiveComponentPrecondition;
import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalPrecondition;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.solver.models.PCMInstance;

public class ActiveComponentChecker implements ArchitecturalPreconditionChecker {

	protected ActiveComponentChecker() {
		
	}
	
	@Override
	public boolean isApplicable(ArchitecturalPrecondition precondition) {
		return precondition instanceof ActiveComponentPrecondition;
	}

	@Override
	public boolean isFulfilled(PCMInstance pcmModel, ArchitecturalPrecondition precondition) {
		if (isNotApplicable(precondition)) {
			return false;
		}

		var requiredActiveComponent = ActiveComponentPrecondition.class.cast(precondition).getRequiredActiveComponent();
		return getInstantiatedComponents(pcmModel).anyMatch(isInstantiated(requiredActiveComponent));
	}

	private Predicate<AssemblyContext> isInstantiated(InterfaceProvidingRequiringEntity requiredActiveComponent) {
		return assContext -> assContext.getEncapsulatedComponent__AssemblyContext().getId()
				.equals(requiredActiveComponent.getId());
	}

	private Stream<AssemblyContext> getInstantiatedComponents(PCMInstance pcmModel) {
		return pcmModel.getSystem().getAssemblyContexts__ComposedStructure().stream();
	}

}
