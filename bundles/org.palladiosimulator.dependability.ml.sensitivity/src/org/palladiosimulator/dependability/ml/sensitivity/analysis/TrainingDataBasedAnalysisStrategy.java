package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityAggregations.SensitivityEntry;
import org.palladiosimulator.dependability.ml.sensitivity.builder.ProbabilityDistributionBuilder;
import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.dependability.ml.sensitivity.iterator.TrainingDataIterator;
import org.palladiosimulator.dependability.ml.sensitivity.iterator.TrainingDataIteratorFactory;
import org.palladiosimulator.dependability.ml.sensitivity.prediction.MLPredictionResult;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.AnalysisTransformation;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.MeasurableProperty;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;

import com.google.common.collect.Lists;

import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.distributionfunction.ProbabilityDistribution;

public class TrainingDataBasedAnalysisStrategy implements MLSensitivityAnalysisStrategy {

	private final AnalysisTransformation analysisTransformation;
	private final Function<MLPredictionResult, Double> incrementalUpdate;

	private TrainingDataBasedAnalysisStrategy(AnalysisTransformation analysisTransformation,
			Function<MLPredictionResult, Double> incrementalUpdate) {
		this.analysisTransformation = analysisTransformation;
		this.incrementalUpdate = incrementalUpdate;
	}

	public static TrainingDataBasedAnalysisStrategy falsePredictionBasedStrategy(
			AnalysisTransformation analysisTransformation) {
		return new TrainingDataBasedAnalysisStrategy(analysisTransformation, r -> r.isExpectedResult() ? 1.0 : 0.0);
	}

	public static TrainingDataBasedAnalysisStrategy confidenceBasedStrategy(
			AnalysisTransformation analysisTransformation) {
		return new TrainingDataBasedAnalysisStrategy(analysisTransformation, r -> r.getPredictionConfidence());
	}

	@Override
	public GroundProbabilisticNetwork analyseSensitivity(GroundProbabilisticNetwork sensitivityModel,
			MLContext context) {
		return complementSensitivityModel(sensitivityModel, computeAggregatedSensitivityValues(context));
	}

	private SensitivityAggregations computeAggregatedSensitivityValues(MLContext context) {
		var sensitivityAggregations = new SensitivityAggregations();

		var dataIterator = createIteratorBy(context);
		while (dataIterator.hasNext()) {
			var data = dataIterator.next();

			var prediction = context.getMLModel().makePrediction(data);
			var properties = analysisTransformation.computeMeasurableProperties(data);

			properties.forEach(prop -> sensitivityAggregations.updateLocal(prop, incrementalUpdate.apply(prediction)));
			sensitivityAggregations.updateGlobal(properties, incrementalUpdate.apply(prediction));
		}

		return sensitivityAggregations;
	}

	private GroundProbabilisticNetwork complementSensitivityModel(GroundProbabilisticNetwork sensitivityModel,
			SensitivityAggregations sensitivityAggregations) {
		for (String each : sensitivityAggregations.getMeasurablePropertyNames()) {
			var sensitivityValues = sensitivityAggregations.filterLocalSensitivityValues(each);
			checkAndHandleCompleteness(each, sensitivityValues);

			var distribution = buildProbabilityDistributionOf(sensitivityValues);

			var probabilisticModel = findRandomVariableFor(each, sensitivityModel).getDescriptiveModel();
			probabilisticModel.setDistribution(distribution);
		}

		var mlSensitivityValues = sensitivityAggregations.getMLSensitivityValues();
		checkAndHandleCompleteness(mlSensitivityValues);

		var mlProbabilisticModel = findMLRandomVariable(sensitivityModel).getDescriptiveModel();
		mlProbabilisticModel.setDistribution(buildMLProbabilityOfSuccessDistribution(sensitivityAggregations));

		return sensitivityModel;
	}

	private void checkAndHandleCompleteness(String propertyName, Map<MeasurableProperty, Double> sensitivityValues) {
		for (CategoricalValue each : retrieveValueSpaceOf(propertyName)) {
			if (containsNoPropertyWith(each, sensitivityValues.keySet())) {
				enrichWithZeroSensitivity(sensitivityValues, new MeasurableProperty(propertyName, each));
			}
		}
	}

	private void checkAndHandleCompleteness(Map<SensitivityEntry, Double> mlSensitivityValues) {
		for (List<MeasurableProperty> each : analysisTransformation.computePropertyMeasureValueSpace()) {
			var entry = SensitivityEntry.from(each);
			if (containsNoPropertyWith(entry, mlSensitivityValues.keySet())) {
				enrichWithZeroSensitivity(mlSensitivityValues, entry);
			}
		}
	}

	private boolean containsNoPropertyWith(CategoricalValue value, Set<MeasurableProperty> recordedProperties) {
		return recordedProperties.stream().noneMatch(prop -> prop.getMeasuredValue().equals(value));
	}

	private boolean containsNoPropertyWith(SensitivityEntry entry, Set<SensitivityEntry> mlSensitivityValues) {
		return mlSensitivityValues.contains(entry) == false;
	}

	private void enrichWithZeroSensitivity(Map<MeasurableProperty, Double> recordedProperties,
			MeasurableProperty zeroSensitivityProperty) {
		recordedProperties.put(zeroSensitivityProperty, 0.0);
	}

	private void enrichWithZeroSensitivity(Map<SensitivityEntry, Double> mlSensitivityValues,
			SensitivityEntry zeroSensitivityProperty) {
		mlSensitivityValues.put(zeroSensitivityProperty, 0.0);
	}

	private Set<CategoricalValue> retrieveValueSpaceOf(String propertyName) {
		var propertyMeasure = analysisTransformation.findPropertyMeasureWith(propertyName)
				.orElseThrow(MLSensitivityAnalysisException.supplierWithMessage(
						String.format("There is no property measure for property %s", propertyName)));
		return propertyMeasure.getValueSpace();
	}

	private GroundRandomVariable findRandomVariableFor(String propertyName,
			GroundProbabilisticNetwork sensitivityModel) {
		return sensitivityModel.getLocalProbabilisticModels().stream()
				.flatMap(each -> each.getGroundRandomVariables().stream()).filter(variableOf(propertyName)).findFirst()
				.orElseThrow(MLSensitivityAnalysisException.supplierWithMessage(
						String.format("There is no variable for measured property %s", propertyName)));
	}

	private GroundRandomVariable findMLRandomVariable(GroundProbabilisticNetwork sensitivityModel) {
		return sensitivityModel.getLocalProbabilisticModels().stream()
				.flatMap(each -> each.getGroundRandomVariables().stream()).filter(variableWithParents()).findFirst()
				.orElseThrow(MLSensitivityAnalysisException
						.supplierWithMessage("No random variable for ml prediction was found."));
	}

	// Assuming that the ML prediction random variable is the only one with parents.
	private Predicate<GroundRandomVariable> variableWithParents() {
		return v -> v.getDependenceStructure().isEmpty() == false;
	}

	// Assuming that by convention the name of the property corresponds to the name
	// of the template variable.
	private Predicate<GroundRandomVariable> variableOf(String propertyName) {
		return v -> v.getInstantiatedTemplate().getEntityName().equals(propertyName);
	}

	private TrainingDataIterator createIteratorBy(MLContext context) {
		String fileExt = context.getTrainingDataFileExtension();
		File trainingData = context.getTrainingData();
		return TrainingDataIteratorFactory.get().createIteratorFor(fileExt, trainingData)
				.orElseThrow(MLSensitivityAnalysisException
						.supplierWithMessage(String.format("There is no supported iterator for %s", fileExt)));
	}

	private ProbabilityDistribution buildProbabilityDistributionOf(Map<MeasurableProperty, Double> sensitivityValues) {
		var property = Lists.newArrayList(sensitivityValues.keySet()).get(0);
		return ProbabilityDistributionBuilder.buildProbabilityDistributionFor(property)
				.withSimpleParameterDerivedFrom(sensitivityValues).build();
	}

	public ProbabilityDistribution buildMLProbabilityOfSuccessDistribution(SensitivityAggregations aggegations) {
		return ProbabilityDistributionBuilder.buildProbabilityDistributionForMLVariable()
				.withTabularParameterDerivedFrom(aggegations.getMLSensitivityValues()).build();
	}

}
