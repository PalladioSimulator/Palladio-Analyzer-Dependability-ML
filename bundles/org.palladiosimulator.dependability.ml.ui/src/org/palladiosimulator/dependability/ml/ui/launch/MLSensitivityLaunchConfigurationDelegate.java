package org.palladiosimulator.dependability.ml.ui.launch;

import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.ANALYSIS_STRATEGY_ATTR;
import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.DEFAULT_STR_ATTR;
import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.ML_MODEL_ATTR;
import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.ML_MODEL_LOCATION_ATTR;
import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.ML_TRAIN_DATA_LOCATION_ATTR;
import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.SENSITIVITY_MODEL_STORING_LOCATION_ATTR;
import static org.palladiosimulator.dependability.ml.ui.launch.MLSensitivityLaunchAttributes.SENSITIVITY_PROP_ATTR;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.palladiosimulator.dependability.ml.sensitivity.api.MLSensitivityAnalyser;
import org.palladiosimulator.dependability.ml.sensitivity.api.SensitivityAnalysisConfig;

import com.google.common.collect.Lists;

import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.api.factory.ProbabilityDistributionFactory;
import tools.mdsd.probdist.api.random.NoSeedProvider;

public class MLSensitivityLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

    @Override
    public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
            throws CoreException {
        var trainedModel = configuration.getAttribute(ML_MODEL_ATTR, DEFAULT_STR_ATTR);
        var trainedModelFile = configuration.getAttribute(ML_MODEL_LOCATION_ATTR, DEFAULT_STR_ATTR);
        var trainDataLocation = configuration.getAttribute(ML_TRAIN_DATA_LOCATION_ATTR, DEFAULT_STR_ATTR);
        var analysisStrategy = configuration.getAttribute(ANALYSIS_STRATEGY_ATTR, DEFAULT_STR_ATTR);
        var sensModelStoringLocation = configuration.getAttribute(SENSITIVITY_MODEL_STORING_LOCATION_ATTR,
                DEFAULT_STR_ATTR);
        List<String> sensitivityProperties = Lists.newArrayList();
        for (String each : configuration.getAttributes()
            .keySet()) {
            if (each.startsWith(SENSITIVITY_PROP_ATTR)) {
                sensitivityProperties.add(configuration.getAttribute(each, ""));
            }
        }

        var config = SensitivityAnalysisConfig.newBuilder()
            .modelToAnalyse(trainedModel)
            .locatedAt(trainedModelFile)
            .andTrainedWith(trainDataLocation)
            .analyseProperties(sensitivityProperties)
            .withStrategy(analysisStrategy)
            .storeSensitivityModelAt(sensModelStoringLocation)
            .build();

        ProbabilityDistributionFactory defaultProbabilityDistributionFactory = new ProbabilityDistributionFactory(
                new NoSeedProvider());
        IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory = defaultProbabilityDistributionFactory;

        MLSensitivityAnalyser.analyseAndSave(config, probabilityDistributionFactory);
    }

}
