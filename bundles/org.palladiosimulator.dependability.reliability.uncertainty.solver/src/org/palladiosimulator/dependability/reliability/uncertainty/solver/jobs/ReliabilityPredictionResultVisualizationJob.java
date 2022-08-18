package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import static org.palladiosimulator.dependability.reliability.uncertainty.solver.util.PredictionResultFormatting.asString;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.util.PredictionResultFormatting.sortByUncertainties;

import org.eclipse.core.runtime.IProgressMonitor;
import org.palladiosimulator.analyzer.workflow.blackboard.PCMResourceSetPartition;
import org.palladiosimulator.analyzer.workflow.jobs.LoadPCMModelsIntoBlackboardJob;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

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

	@Override
	public void cleanup(IProgressMonitor monitor) throws CleanupFailedException {
		// Nothing to do here
	}

	@Override
	public String getName() {
		return ReliabilityPredictionResultVisualizationJob.class.getName();
	}

}
