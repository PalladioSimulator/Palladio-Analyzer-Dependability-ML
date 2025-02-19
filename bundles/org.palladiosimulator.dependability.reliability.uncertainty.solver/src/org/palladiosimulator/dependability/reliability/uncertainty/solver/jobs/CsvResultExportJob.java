package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import static org.palladiosimulator.dependability.reliability.uncertainty.solver.util.PredictionResultFormatting.asString;
import static org.palladiosimulator.dependability.reliability.uncertainty.solver.util.PredictionResultFormatting.sortByUncertainties;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.palladiosimulator.analyzer.workflow.core.blackboard.PCMResourceSetPartition;
import org.palladiosimulator.analyzer.workflow.jobs.LoadPCMModelsIntoBlackboardJob;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

import com.google.common.collect.Lists;

import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;

public class CsvResultExportJob extends ReliabilityPredictionRunJob {

	private final static Logger LOGGER = Logger.getLogger(CsvResultExportJob.class.getName());

	private final static String CSV_DELIMITER = ";";

	public CsvResultExportJob(ReliabilityPredictionContext context) {
		super(context);
	}

	@Override
	public void execute(IProgressMonitor monitor) throws JobFailedException, UserCanceledException {
		var exportFile = context.exportLocation.appendSegment("ReliabilityPredictionResults.csv");
		try {
			var output = new ExtensibleURIConverterImpl().createOutputStream(exportFile);
			
			appendHeader(output);
			appendResults(output);
			
			output.close();
		} catch (IOException e) {
			LOGGER.debug("Reliability prediction results could not be written.", e);
		} 

	}

	private void appendHeader(OutputStream output) throws IOException {
		var uncertaintyColumn = "Uncertainty";
		var probOfSuccessColumn = "Prob of Success";
		var probOfFailureColumn = "Prob of Failure";
		var condProbOfSuccessColumn = "Cond Prob of Success";
		var condProbOfFailureColumn = "Cond Prob of Failure";
		var probOfUncertaintyColumn = "Prob of Uncertainties";
		var header = Lists.newArrayList(uncertaintyColumn, probOfSuccessColumn, probOfFailureColumn,
				condProbOfSuccessColumn, condProbOfFailureColumn, probOfUncertaintyColumn);

		writeRow(output, header);
	}
	
	private void appendResults(OutputStream output) throws IOException {
		var usageModel = ((PCMResourceSetPartition) getBlackboard()
				.getPartition(LoadPCMModelsIntoBlackboardJob.PCM_MODELS_PARTITION_ID))
				.getUsageModel();
		for (UsageScenario each : usageModel.getUsageScenario_UsageModel()) {
			appendRow(each, output);
		}
	}

	private void appendRow(UsageScenario scenario, OutputStream output) throws IOException {
		var result = context.result; 
		
		var iterator = sortByUncertainties(result.filterPredictionResultsFor(scenario));
		while (iterator.hasNext()) {
			var next = iterator.next();
			
			List<String> values = Lists.newArrayList();
			
			var uncertainties = asString(next.getUncertainties());
			values.add(uncertainties);
			
			var probOfSuccess = result.getProbabilityOfSuccess(scenario);
			values.add(Double.toString(probOfSuccess));
			
			var probOfFailure = result.getProbabilityOfFailure(scenario);
			values.add(Double.toString(probOfFailure));
			
			var condProbOfSuccess = next.getConditionalProbabilityOfSuccess();
			values.add(Double.toString(condProbOfSuccess));
			
			var condProbOfFailure = next.getConditionalProbabilityOfFailure();
			values.add(Double.toString(condProbOfFailure));
			
			var probOfUncertainties = next.getProbabilityOfUncertainty();
			values.add(Double.toString(probOfUncertainties));
			
			writeRow(output, values);
		}
	}
	
	private void writeRow(OutputStream output, List<String> values) throws IOException {
		var row = String.join(CSV_DELIMITER, values).concat("\n");
		output.write(row.getBytes());
	}

}
