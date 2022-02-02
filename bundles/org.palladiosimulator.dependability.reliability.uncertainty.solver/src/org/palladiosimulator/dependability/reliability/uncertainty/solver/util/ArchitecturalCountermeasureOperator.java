package org.palladiosimulator.dependability.reliability.uncertainty.solver.util;

import static java.util.stream.Collectors.groupingBy;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.util.ArchitecturalPreconditionUtil.allPreconditionsFulfilled;

import java.util.List;

import org.palladiosimulator.dependability.reliability.uncertainty.ArchitecturalCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.GlobalUncertaintyCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyInducedFailureType;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintyRepository;
import org.palladiosimulator.dependability.reliability.uncertainty.UncertaintySpecificCountermeasure;
import org.palladiosimulator.dependability.reliability.uncertainty.improvement.UncertaintyImprovementCalculator;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.dependability.reliability.uncertainty.util.UncertaintySwitch;
import org.palladiosimulator.solver.models.PCMInstance;

import com.google.common.collect.Lists;

import tools.mdsd.probdist.api.entity.CategoricalValue;

public class ArchitecturalCountermeasureOperator {

	private final PCMInstance pcmModel;
	private final UncertaintyRepository uncertaintyRepo;

	private ArchitecturalCountermeasureOperator(PCMInstance pcmModel, UncertaintyRepository uncertaintyRepo) {
		this.pcmModel = pcmModel;
		this.uncertaintyRepo = uncertaintyRepo;
	}

	public static ArchitecturalCountermeasureOperator createOperatorFor(PCMInstance pcmModel,
			UncertaintyRepository uncertaintyRepo) {
		return new ArchitecturalCountermeasureOperator(pcmModel, uncertaintyRepo);
	}

	public List<UncertaintyState> apply(List<UncertaintyState> stateTuple) {
		if (uncertaintyRepo.getArchitecturalCountermeasures().size() == 0) {
			return stateTuple;
		}
		
		var improved = Lists.newArrayList(stateTuple);
		
		determineApplicableAndOrderedCountermeasures().forEach(c -> apply(c, improved));
		
		return improved;
	}

	private void apply(ArchitecturalCountermeasure countermeasure, List<UncertaintyState> stateTuple) {
		new UncertaintySwitch<Void>() {

			@Override
			public Void caseUncertaintySpecificCountermeasure(UncertaintySpecificCountermeasure countermeasure) {
				var applicableState = stateTuple.stream()
						.filter(each -> each.instantiates(countermeasure.getTargetUncertainty()))
						.findFirst();
				if (applicableState.isEmpty()) {
					return null;
				}

				var oldState = applicableState.get();
				stateTuple.remove(oldState);

				var improvedValue = computeImprovmenet(countermeasure, oldState);
				var improvedState = oldState.newValuedStateWith(improvedValue);
				stateTuple.add(improvedState);
				return null;
			};

			private CategoricalValue computeImprovmenet(UncertaintySpecificCountermeasure countermeasure,
					UncertaintyState state) {
				var improvement = countermeasure.getUncertaintyImprovement();
				return UncertaintyImprovementCalculator.get().calculate(improvement, state.getValue());
			}

			@Override
			public Void caseGlobalUncertaintyCountermeasure(GlobalUncertaintyCountermeasure countermeasure) {
				for (UncertaintyInducedFailureType each : uncertaintyRepo.getUncertaintyInducedFailureTypes()) {
					if (each.getId().equals(countermeasure.getId()) == false) {
						continue;
					}
					
					var improvedModel = countermeasure.getImprovedUncertaintyModel();
					if (each.getUncertaintyModel().getId().equals(improvedModel.getId())) {
						return null;
					} else {
						each.setUncertaintyModel(improvedModel);
					}
				}
				return null;
			};

		}.doSwitch(countermeasure);
	}

	private List<ArchitecturalCountermeasure> determineApplicableAndOrderedCountermeasures() {
		var partitioned = uncertaintyRepo.getArchitecturalCountermeasures().stream()
				.filter(c -> allPreconditionsFulfilled(c, pcmModel))
				.filter(c -> allPreconditionsFulfilled(c.getAppliedFailureType(), pcmModel))
				.collect(groupingBy(UncertaintySpecificCountermeasure.class::isInstance));

		var ordered = partitioned.get(true);
		ordered.addAll(partitioned.get(false));

		return ordered;
	}

}
