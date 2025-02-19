package org.palladiosimulator.dependability.reliability.uncertainty.solver.tests.util;

import org.palladiosimulator.analyzer.workflow.jobs.EventsTransformationJob;
import org.palladiosimulator.analyzer.workflow.jobs.LoadMiddlewareConfigurationIntoBlackboardJob;
import org.palladiosimulator.analyzer.workflow.jobs.LoadPCMModelsIntoBlackboardJob;
import org.palladiosimulator.analyzer.workflow.jobs.ValidatePCMModelsJob;
import org.palladiosimulator.solver.core.runconfig.PCMSolverWorkflowRunConfiguration;

import de.uka.ipd.sdq.workflow.jobs.ICompositeJob;
import de.uka.ipd.sdq.workflow.jobs.SequentialBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.mdsd.blackboard.MDSDBlackboard;

public class PCMTestInstanceBuilderJob extends SequentialBlackboardInteractingJob<MDSDBlackboard> implements ICompositeJob {

	public PCMTestInstanceBuilderJob(PCMSolverWorkflowRunConfiguration config) {
		super(false);

		this.myBlackboard = new MDSDBlackboard();

		this.addJob(new LoadPCMModelsIntoBlackboardJob(config));
		this.addJob(new LoadMiddlewareConfigurationIntoBlackboardJob(config));
		this.addJob(new ValidatePCMModelsJob(config));
		this.add(new EventsTransformationJob(config.getStoragePluginID(), config.getEventMiddlewareFile(), false));
	}
	
}
