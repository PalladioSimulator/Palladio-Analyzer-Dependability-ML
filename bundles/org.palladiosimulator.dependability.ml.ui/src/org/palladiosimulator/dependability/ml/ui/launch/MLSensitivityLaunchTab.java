package org.palladiosimulator.dependability.ml.ui.launch;

import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.ANALYSIS_STRATEGY_ATTR;
import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.DEFAULT_STR_ATTR;
import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.ML_MODEL_ATTR;
import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.ML_MODEL_LOCATION_ATTR;
import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.ML_MODEL_LOCATION_ATTR_DEFAULT;
import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.ML_TRAIN_DATA_LOCATION_ATTR;
import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.SENSITIVITY_PROP_ATTR;
import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.SENSITIVITY_MODEL_STORING_LOCATION_ATTR;

import java.util.List;
import java.util.Set;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.palladiosimulator.dependability.ml.sensitivity.api.MLSensitivityAnalyser;

import com.google.common.collect.Lists;

import de.uka.ipd.sdq.workflow.launchconfig.tabs.TabHelper;

public class MLSensitivityLaunchTab extends AbstractLaunchConfigurationTab {

	private final static String NAME = "ML Sensitivity Analysis";
	private final static String NONE_ITEM = "None";
	private final static String[] RESTRICTIONS = new String[] { "*.*"};

	private final ModifyListener modifyListener = new ModifyListener() {

		public void modifyText(ModifyEvent e) {
			setDirty(true);
			updateLaunchConfigurationDialog();
		}
	};

	private Text modelLocationText;
	private Text trainingDataLocationText;
	private Text sensModelStoringLocation;
	private Combo supportedModelsCombobox;
	private Combo strategyCombobox;
	private List<Button> propertyCheckboxes;

	@Override
	public void createControl(Composite parent) {

		var container = createRootContainer(parent);

		createModelLocationText(container);

		createTrainDataLocationText(container);

		createMLSupportedModelsGroup(container);

		createSensitivityPropertyGroup(container);

		createStrategyGroup(container);
	}
	
	private Composite createRootContainer(Composite parent) {
		var container = new Composite(parent, SWT.NONE);
		setControl(container);
		GridLayoutFactory.swtDefaults().applyTo(container);
		return container;
	}

	private void createModelLocationText(Composite container) {
		modelLocationText = new Text(container, SWT.SINGLE | SWT.BORDER);
		TabHelper.createFileInputSection(container, modifyListener, "ML model location", RESTRICTIONS,
				modelLocationText, getShell(), ML_MODEL_LOCATION_ATTR_DEFAULT);
	}
	
	private void createTrainDataLocationText(Composite container) {
		trainingDataLocationText = new Text(container, SWT.SINGLE | SWT.BORDER);
		TabHelper.createFileInputSection(container, modifyListener, "ML training data location", RESTRICTIONS,
				trainingDataLocationText, getShell(), DEFAULT_STR_ATTR);
	}

	private void createMLSupportedModelsGroup(Composite container) {
		var mlModelsGroup = new Group(container, SWT.NONE);
		mlModelsGroup.setText("ML model selection for sensitivity analysis");
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(mlModelsGroup);

		var mlModelLabel = new Label(mlModelsGroup, SWT.NONE);
		mlModelLabel.setText("Supported models:");
		GridDataFactory.swtDefaults().applyTo(mlModelLabel);

		supportedModelsCombobox = new Combo(mlModelsGroup, SWT.READ_ONLY);
		supportedModelsCombobox.setItems(getAnalysableModelNames());
		supportedModelsCombobox.setSize(400, 200);
	}

	private void createSensitivityPropertyGroup(Composite container) {
		var sensitivityPropertiesGroup = new Group(container, SWT.NONE);
		sensitivityPropertiesGroup.setText("Analysable Sensitivity Properties");
		GridLayoutFactory.swtDefaults().numColumns(3).applyTo(sensitivityPropertiesGroup);

		propertyCheckboxes = Lists.newArrayList();
		for (String each : MLSensitivityAnalyser.getAnalysablePropertyNames()) {
			var propButton = new Button(sensitivityPropertiesGroup, SWT.CHECK);
			propButton.setSelection(false);
			propButton.setText(each);

			propertyCheckboxes.add(propButton);
		}
	}

	private void createStrategyGroup(Composite container) {
		var strategyGroup = new Group(container, SWT.NONE);
		strategyGroup.setText("Sensitivity Analysis Strategy");
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(strategyGroup);

		var strategyLabel = new Label(strategyGroup, SWT.NONE);
		strategyLabel.setText("Supported strategies:");
		GridDataFactory.swtDefaults().applyTo(strategyLabel);

		strategyCombobox = new Combo(strategyGroup, SWT.READ_ONLY);
		strategyCombobox.setItems(getAnalysisStrategyNames());
		strategyCombobox.setSize(400, 200);
		
		sensModelStoringLocation = new Text(container, SWT.SINGLE | SWT.BORDER);
		TabHelper.createFileInputSection(container, modifyListener, "Storing location of sensitivity model", RESTRICTIONS,
				sensModelStoringLocation, getShell(), SENSITIVITY_MODEL_STORING_LOCATION_ATTR);
	}

	private String[] getAnalysisStrategyNames() {
		return enrichWithNoneItem(MLSensitivityAnalyser.getAnalysisStrategyNames());
	}
	
	private String[] getAnalysableModelNames() {
		return enrichWithNoneItem(MLSensitivityAnalyser.getAnalysableModelNames());
	}
	
	private String[] enrichWithNoneItem(Set<String> items) {
		var enrichedItems = new String[items.size() + 1];
		
		enrichedItems[0] = NONE_ITEM;
		
		var itemList = Lists.newArrayList(items);
		for (int i = 0; i < itemList.size(); i++) {
			enrichedItems[i + 1] = itemList.get(i);
		}
		
		return enrichedItems;
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {

	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		supportedModelsCombobox.select(0);
		strategyCombobox.select(0);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(ML_MODEL_LOCATION_ATTR, modelLocationText.getText());
		configuration.setAttribute(ML_TRAIN_DATA_LOCATION_ATTR, trainingDataLocationText.getText());
		configuration.setAttribute(SENSITIVITY_MODEL_STORING_LOCATION_ATTR, sensModelStoringLocation.getText());
		configuration.setAttribute(ANALYSIS_STRATEGY_ATTR,
				strategyCombobox.getItem(strategyCombobox.getSelectionIndex()));
		configuration.setAttribute(ML_MODEL_ATTR,
				supportedModelsCombobox.getItem(supportedModelsCombobox.getSelectionIndex()));
		for (Button each : propertyCheckboxes) {
			if ((boolean) each.getSelection()) {
				var attr = SENSITIVITY_PROP_ATTR.concat(each.getText());
				configuration.setAttribute(attr, each.getText());
			}
		}
	}

	@Override
	public String getName() {
		return NAME;
	}

}
