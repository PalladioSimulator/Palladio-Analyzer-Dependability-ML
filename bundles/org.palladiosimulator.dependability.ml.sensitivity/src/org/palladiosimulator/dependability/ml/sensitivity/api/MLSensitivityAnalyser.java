package org.palladiosimulator.dependability.ml.sensitivity.api;

import static java.util.stream.Collectors.toSet;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.palladiosimulator.dependability.ml.model.TrainedModel;
import org.palladiosimulator.dependability.ml.model.nn.MaskRCNN;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.MLAnalysisContext;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.MLSensitivityAnalysis;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.MLSensitivityAnalysisStrategy;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.ProbabilisticSensitivityModel;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityModel;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.TrainingDataBasedAnalysisStrategy;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.property.ImageBrightness;

import com.google.common.collect.Sets;

import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.factory.IProbabilityDistributionFactory;
import tools.mdsd.probdist.api.random.ISeedProvider;

public class MLSensitivityAnalyser {

    private static final Set<MLSensitivityAnalysisStrategy> ANALYSIS_STRATEGY_REGISTRY = Sets.newHashSet();
    static {
        ANALYSIS_STRATEGY_REGISTRY.add(TrainingDataBasedAnalysisStrategy.accuracyBasedStrategy());
        ANALYSIS_STRATEGY_REGISTRY.add(TrainingDataBasedAnalysisStrategy.confidenceBasedStrategy());
    }

    private static final Set<PropertyMeasure> PROPERTY_MEASURE_REGISTRY = Sets.newHashSet();
    static {
        PROPERTY_MEASURE_REGISTRY.add(ImageBrightness.get());
    }

    private static final Set<TrainedModel> ANALYSABLE_MODEL_REGISTRY = Sets.newHashSet();
    static {
        ANALYSABLE_MODEL_REGISTRY.add(new MaskRCNN());
    }

    protected static Optional<TrainedModel> findAnalysableModelWith(String name) {
        return ANALYSABLE_MODEL_REGISTRY.stream()
            .filter(modelWith(name))
            .findFirst();
    }

    public static Optional<PropertyMeasure> findAnalysablePropertyMeasureWith(String id) {
        return PROPERTY_MEASURE_REGISTRY.stream()
            .filter(propertyWith(id))
            .findFirst();
    }

    protected static Optional<MLSensitivityAnalysisStrategy> findAnalysisStrategyWith(String name) {
        return ANALYSIS_STRATEGY_REGISTRY.stream()
            .filter(strategyWith(name))
            .findFirst();
    }

    public static void registerAnalysableProperties(PropertyMeasure property) {
        PROPERTY_MEASURE_REGISTRY.add(property);
    }

    public static void registerAnalysableModel(TrainedModel model) {
        ANALYSABLE_MODEL_REGISTRY.add(model);
    }

    public static void registerAnalysisStrategy(MLSensitivityAnalysisStrategy strategy) {
        ANALYSIS_STRATEGY_REGISTRY.add(strategy);
    }

    public static Set<PropertyMeasure> getAnalysablePropertyMeasures() {
        return PROPERTY_MEASURE_REGISTRY;
    }

    public static Set<String> getAnalysableModelNames() {
        return ANALYSABLE_MODEL_REGISTRY.stream()
            .map(TrainedModel::getName)
            .collect(toSet());
    }

    public static Set<String> getAnalysisStrategyNames() {
        return ANALYSIS_STRATEGY_REGISTRY.stream()
            .map(MLSensitivityAnalysisStrategy::getName)
            .collect(toSet());
    }

    public static SensitivityModel analyse(SensitivityAnalysisConfig config,
            IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
            Optional<ISeedProvider> seedProvider) {
        var analysisBuilder = MLSensitivityAnalysis.newBuilder()
            .withSensitivityAnalysisStrategy(config.getAnalysisStrategy());
        for (PropertyMeasure each : config.getPropertyMeasures()) {
            analysisBuilder.addPropertyMeasure(each);
        }
        var sensitivityAnalysis = analysisBuilder.build();

        var sensitivityModel = ProbabilisticSensitivityModel.createFrom(Sets.newHashSet(config.getPropertyMeasures()),
                probabilityDistributionFactory, seedProvider);

        var context = MLAnalysisContext.newBuilder()
            .analyseSensitivityOf(config.getTrainedModel())
            .trainedWith(config.getTrainDataLocation())
            .andCapturedBy(sensitivityModel)
            .build();

        context.getMLModel()
            .loadModel(config.getTrainedModelURI());

        return sensitivityAnalysis.analyseSensitivity(context);
    }

    public static void analyseAndSave(SensitivityAnalysisConfig config,
            IProbabilityDistributionFactory<CategoricalValue> probabilityDistributionFactory,
            Optional<ISeedProvider> seedProvider) {
        analyse(config, probabilityDistributionFactory, seedProvider)
            .saveAt(config.getSensitivityModelStoringLocation());
    }

    private static Predicate<PropertyMeasure> propertyWith(String queriedId) {
        return prop -> prop.getId()
            .equals(queriedId);
    }

    private static Predicate<TrainedModel> modelWith(String queriedName) {
        return model -> model.getName()
            .equals(queriedName);
    }

    private static Predicate<MLSensitivityAnalysisStrategy> strategyWith(String queriedName) {
        return strategy -> strategy.getName()
            .equals(queriedName);
    }

}
