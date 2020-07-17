package org.palladiosimulator.dependability.ml.ui.launch;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;

public class MLSensitivityLaunchTabGroup extends AbstractLaunchConfigurationTabGroup {

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		setTabs(new MLSensitivityLaunchTab());
	}

}
