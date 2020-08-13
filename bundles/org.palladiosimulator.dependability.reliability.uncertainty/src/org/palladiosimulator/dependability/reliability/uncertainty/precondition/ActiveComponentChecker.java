package org.palladiosimulator.dependability.reliability.uncertainty.precondition;

import java.util.function.Predicate;
import java.util.stream.Stream;

import org.palladiosimulator.dependability.reliability.uncertainty.ActiveComponentPrecondition;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.solver.models.PCMInstance;

public class ActiveComponentChecker implements ArchitecturalPreconditionChecker<ActiveComponentPrecondition> {

	@Override
	public boolean isFulfilled(ActiveComponentPrecondition precondition, PCMInstance pcmModel) {
		return getInstantiatedComponents(pcmModel).anyMatch(isInstantiated(precondition.getRequiredActiveComponent()));
	}

	private Predicate<AssemblyContext> isInstantiated(BasicComponent requiredActiveComponent) {
		return assContext -> assContext.getEncapsulatedComponent__AssemblyContext().getId()
				.equals(requiredActiveComponent.getId());
	}

	private Stream<AssemblyContext> getInstantiatedComponents(PCMInstance pcmModel) {
		return pcmModel.getSystem().getAssemblyContexts__ComposedStructure().stream();
	}

}
