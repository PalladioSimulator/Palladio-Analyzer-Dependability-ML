package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.IProgressMonitor;
import org.palladiosimulator.analyzer.workflow.blackboard.PCMResourceSetPartition;
import org.palladiosimulator.analyzer.workflow.jobs.LoadPCMModelsIntoBlackboardJob;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.markov.ReliabilityPredictionResultPerScenario;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

import com.google.common.collect.Lists;

import de.uka.ipd.sdq.workflow.jobs.CleanupFailedException;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;

public class ReliabilityPredictionResultVisualizationJob extends ReliabilityPredictionRunJob {

	public ReliabilityPredictionResultVisualizationJob(ReliabilityPredictionContext context) {
		super(context);
	}

	@Override
	public void execute(IProgressMonitor monitor) throws JobFailedException, UserCanceledException {
		var usageModel = ((PCMResourceSetPartition) getBlackboard()
				.getPartition(LoadPCMModelsIntoBlackboardJob.PCM_MODELS_PARTITION_ID))
				.getUsageModel();
		usageModel.getUsageScenario_UsageModel().forEach(this::visualize);
	}

	private void visualize(UsageScenario scenario) {
		var result = context.result;

		System.out.println(String.format("Scenario: %s", scenario.getEntityName()));

		var maxCharNum = result.filterPredictionResultsFor(scenario).stream()
				.mapToInt(each -> asString(each.getUncertainties()).length())
				.max()
				.getAsInt();

		String leftAlignFormat = "| %-".concat(Integer.toString(maxCharNum)).concat("s | %-15f | %-15f | %-20f | %-20f | %-20f |%n");
		String lines = "-".repeat(maxCharNum - "Uncertainty".length() + 1);
		String blanks = " ".repeat(maxCharNum - "Uncertainty".length() + 1);
		
		System.out.format("+------------%s+-----------------+-----------------+----------------------+----------------------+----------------------+%n", lines);
		System.out.format("| Uncertainty%s| Prob of Success | Prob of Failure | Cond Prob of Success | Cond Prob of Failure | Prob of Uncertanties |%n", blanks);
		System.out.format("+------------%s+-----------------+-----------------+----------------------+----------------------+----------------------+%n", lines);
		var iterator = sortByUncertainties(result.filterPredictionResultsFor(scenario));
		while (iterator.hasNext()) {
			var next = iterator.next();
			System.out.format(leftAlignFormat, asString(next.getUncertainties()),
					result.getProbabilityOfSuccess(scenario), result.getProbabilityOfFailure(scenario),
					next.getConditionalProbabilityOfSuccess(), next.getConditionalProbabilityOfFailure(),
					next.getProbabilityOfUncertainty());
		}
		System.out.format("+------------%s+-----------------+-----------------+----------------------+----------------------+----------------------+%n", lines);
	}

	private String asString(List<UncertaintyState> uncertainties) {
		return String.join(",", uncertainties.stream()
				.map(each -> each.getValue().get())
				.collect(toList()));
	}
	
	private Iterator<ReliabilityPredictionResultPerScenario> sortByUncertainties(Set<ReliabilityPredictionResultPerScenario> unsorted) {
		var sorted = new TreeSet<ReliabilityPredictionResultPerScenario>(new Comparator<ReliabilityPredictionResultPerScenario>() {

			@Override
			public int compare(ReliabilityPredictionResultPerScenario r1, ReliabilityPredictionResultPerScenario r2) {
				var strUncertainties1 = asString(r1.getUncertainties());
				var strUncertainties2 = asString(r2.getUncertainties());
				var uncertainties = Lists.newArrayList(strUncertainties1, strUncertainties2);
				
				Collections.sort(uncertainties);
				
				return uncertainties.get(0).equals(strUncertainties1) ? -1 : 1;
			}
		
		});
		
		sorted.addAll(unsorted);
		
		return sorted.iterator();
//		Iterator iterator = treeSet.descendingIterator();
//		while(iterator.hasNext()) {
//		    String element = (String) iterator.next();
//		    System.out.println(element);
//		}
	}

	@Override
	public void cleanup(IProgressMonitor monitor) throws CleanupFailedException {
		// Nothing to do here
	}

	@Override
	public String getName() {
		return ReliabilityPredictionResultVisualizationJob.class.getName();
	}

}
