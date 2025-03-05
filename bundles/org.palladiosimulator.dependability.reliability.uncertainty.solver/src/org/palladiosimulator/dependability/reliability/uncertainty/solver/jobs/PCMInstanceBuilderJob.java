package org.palladiosimulator.dependability.reliability.uncertainty.solver.jobs;

import org.palladiosimulator.analyzer.workflow.core.jobs.LoadPCMModelsIntoBlackboardJob;
import org.palladiosimulator.analyzer.workflow.jobs.EventsTransformationJob;
import org.palladiosimulator.analyzer.workflow.jobs.LoadMiddlewareConfigurationIntoBlackboardJob;
import org.palladiosimulator.analyzer.workflow.jobs.ValidatePCMModelsJob;
import org.palladiosimulator.solver.core.runconfig.PCMSolverWorkflowRunConfiguration;

import de.uka.ipd.sdq.workflow.jobs.ICompositeJob;
import de.uka.ipd.sdq.workflow.jobs.SequentialBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.mdsd.blackboard.MDSDBlackboard;

public class PCMInstanceBuilderJob extends SequentialBlackboardInteractingJob<MDSDBlackboard> implements ICompositeJob {

	public PCMInstanceBuilderJob(PCMSolverWorkflowRunConfiguration config) {
		super(false);

		this.addJob(new LoadPCMModelsIntoBlackboardJob(config));
		this.addJob(new LoadMiddlewareConfigurationIntoBlackboardJob(config));
		//this.add(new EventsTransformationJob(config.getStoragePluginID(), config.getEventMiddlewareFile(), false));
	}

	public PCMInstanceBuilderJob(PCMSolverWorkflowRunConfiguration config, MDSDBlackboard blackboard) {
		super(false);

		this.myBlackboard = blackboard;

		this.addJob(new LoadMiddlewareConfigurationIntoBlackboardJob(config));
		this.addJob(new ValidatePCMModelsJob(config));
		this.add(new EventsTransformationJob(config.getStoragePluginID(), config.getEventMiddlewareFile(), false));
	}
}
