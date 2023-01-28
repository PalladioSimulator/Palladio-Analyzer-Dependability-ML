package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;

import de.uka.ipd.sdq.workflow.jobs.SequentialBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.mdsd.blackboard.MDSDBlackboard;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionRegistry;

public class RootReliabilityPredictionRunJob extends SequentialBlackboardInteractingJob<MDSDBlackboard> {
	
	public RootReliabilityPredictionRunJob(PCMSolverWorkflowRunConfiguration config, String uncertaintyModel,
			String explorationStrategy, Optional<URI> exportLocation, IProbabilityDistributionRegistry probabilityDistributionRegistry) {		
		var context = new ReliabilityPredictionContext(config, uncertaintyModel, explorationStrategy);
		
		addJob(new ReliabilityPredictionExecutionJob(context, probabilityDistributionRegistry));
		addJob(new ReliabilityPredictionResultVisualizationJob(context));
		
		if (exportLocation.isPresent()) {
			context.exportLocation = exportLocation.get();
			addJob(new CsvResultExportJob(context));
		}
	}

}
