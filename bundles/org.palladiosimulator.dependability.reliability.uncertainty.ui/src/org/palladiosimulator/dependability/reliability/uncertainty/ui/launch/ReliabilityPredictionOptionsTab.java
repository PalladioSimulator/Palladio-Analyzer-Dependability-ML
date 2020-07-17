package org.palladiosimulator.dependability.reliability.uncertainty.ui.launch;

import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.DEFAULT_ATTR;
import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.EXPLORATION_STRATEGY_ATTR;
import static org.palladiosimulator.dependability.reliability.uncertainty.ui.launch.UncertaintyBasedReliabilityPredictionAttributes.UNCERTAINTY_MODEL_ATTR;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.api.UncertaintyBasedReliabilityPredictor;

import de.uka.ipd.sdq.workflow.launchconfig.tabs.TabHelper;

public class ReliabilityPredictionOptionsTab extends AbstractLaunchConfigurationTab {

	private final static String[] RESTRICTIONS = new String[] { "*.uncertainty" };

	private Text uncertaintyModelLocation;
	private Combo supportedStrategiesCombobox;

	private final ModifyListener modifyListener = new ModifyListener() {

		public void modifyText(ModifyEvent e) {
			setDirty(true);
			updateLaunchConfigurationDialog();
		}
	};

	@Override
	public void createControl(Composite parent) {
		var container = createRootContainer(parent);

		createUncertaintyModelLocationText(container);

		createSupportedExplorationStrategiesGroup(container);
	}

	private Composite createRootContainer(Composite parent) {
		var container = new Composite(parent, SWT.NONE);
		setControl(container);
		GridLayoutFactory.swtDefaults().applyTo(container);
		return container;
	}

	private void createUncertaintyModelLocationText(Composite container) {
		uncertaintyModelLocation = new Text(container, SWT.SINGLE | SWT.BORDER);
		TabHelper.createFileInputSection(container, modifyListener, "Uncertainty model location", RESTRICTIONS,
				uncertaintyModelLocation, getShell(), DEFAULT_ATTR);
	}

	private void createSupportedExplorationStrategiesGroup(Composite container) {
		var mlModelsGroup = new Group(container, SWT.NONE);
		mlModelsGroup.setText("State space exploration strategy selection");
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(mlModelsGroup);

		var mlModelLabel = new Label(mlModelsGroup, SWT.NONE);
		mlModelLabel.setText("Supported strategies:");
		GridDataFactory.swtDefaults().applyTo(mlModelLabel);

		supportedStrategiesCombobox = new Combo(mlModelsGroup, SWT.READ_ONLY);
		supportedStrategiesCombobox.setItems(getStrategyNames());
		supportedStrategiesCombobox.setSize(400, 200);
	}

	private String[] getStrategyNames() {
		var strategies = UncertaintyBasedReliabilityPredictor.getSupportedStrategies();
		var strategiesAsArray = new String[strategies.size()];
		return strategies.toArray(strategiesAsArray);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		supportedStrategiesCombobox.select(0);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(EXPLORATION_STRATEGY_ATTR,
				supportedStrategiesCombobox.getItem(supportedStrategiesCombobox.getSelectionIndex()));
		configuration.setAttribute(UNCERTAINTY_MODEL_ATTR, uncertaintyModelLocation.getText());
	}

	@Override
	public String getName() {
		return "Uncertainty based reliability prediction options";
	}

}
