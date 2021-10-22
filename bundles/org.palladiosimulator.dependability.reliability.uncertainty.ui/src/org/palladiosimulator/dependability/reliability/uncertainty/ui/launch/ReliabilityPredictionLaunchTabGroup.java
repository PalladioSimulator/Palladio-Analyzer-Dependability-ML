package org.palladiosimulator.dependability.reliability.uncertainty.ui.launch;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.palladiosimulator.analyzer.workflow.runconfig.FileNamesInputTab;
import org.palladiosimulator.architecturaltemplates.jobs.config.ATExtensionTab;
import org.palladiosimulator.reliability.solver.runconfig.OptionsTab;
import org.palladiosimulator.reliability.solver.runconfig.ReliabilityConfigurationTab;

import de.uka.ipd.sdq.workflow.launchconfig.tabs.DebugEnabledCommonTab;

public class ReliabilityPredictionLaunchTabGroup extends AbstractLaunchConfigurationTabGroup {

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		setTabs(new FileNamesInputTab(), 
				new ReliabilityPredictionOptionsTab(), 
				new ReliabilityConfigurationTab(), 
				new ATExtensionTab(),
				new OptionsTab(), 
				new DebugEnabledCommonTab());
	}

}
