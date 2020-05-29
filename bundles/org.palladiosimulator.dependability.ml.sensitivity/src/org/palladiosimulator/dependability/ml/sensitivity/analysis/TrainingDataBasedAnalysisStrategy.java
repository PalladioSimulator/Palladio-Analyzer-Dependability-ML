package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.palladiosimulator.dependability.ml.model.MLPredictionResult;
import org.palladiosimulator.dependability.ml.model.OutputData;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityAggregations.SensitivityEntry;
import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.AnalysisTransformation;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.MeasurableProperty;

import tools.mdsd.probdist.api.entity.CategoricalValue;

public class TrainingDataBasedAnalysisStrategy implements MLSensitivityAnalysisStrategy {

	private final Function<MLPredictionResult, Double> incrementalUpdate;

	private TrainingDataBasedAnalysisStrategy(Function<MLPredictionResult, Double> incrementalUpdate) {
		this.incrementalUpdate = incrementalUpdate;
	}

	public static TrainingDataBasedAnalysisStrategy falsePredictionBasedStrategy() {
		return new TrainingDataBasedAnalysisStrategy(r -> r.isExpectedResult() ? 1.0 : 0.0);
	}

	public static TrainingDataBasedAnalysisStrategy confidenceBasedStrategy() {
		return new TrainingDataBasedAnalysisStrategy(r -> {
			var numberOfPredictions = r.getPredictions().size();
			var sumOfPredictions = r.getPredictions().stream().map(OutputData::getPredictionConfidence)
					.reduce(Double::sum).get();
			return sumOfPredictions / numberOfPredictions;
		});
	}

	@Override
	public SensitivityModel analyseSensitivity(MLAnalysisContext context) {
		var sensitivityModel = context.getSensitivityModel();
		var sensitivityValues = computeAggregatedSensitivityValues(context);
		return complementSensitivityModel(sensitivityModel, sensitivityValues);
	}

	private SensitivityAggregations computeAggregatedSensitivityValues(MLAnalysisContext context) {
		var mlModel = context.getMLModel();
		var dataIterator = mlModel.getTrainingDataIteratorBy(context.getTrainingData());
		var sensitivityAggregations = new SensitivityAggregations();

		while (dataIterator.hasNext()) {
			var dataTuple = dataIterator.next();

			var<MeasurableProperty> properties = MLSensitivityAnalysis.getAnalysisTransformation()
					.computeMeasurableProperties(dataTuple.getFirst());
			properties.forEach(sensitivityAggregations::updatePropertySensitivity);

			var newValue = incrementalUpdate.apply(mlModel.makePrediction(dataTuple));
			sensitivityAggregations.updateMLSensitivity(SensitivityEntry.from(properties), newValue);
		}

		return sensitivityAggregations;
	}

	private SensitivityModel complementSensitivityModel(SensitivityModel sensitivitiyModel,
			SensitivityAggregations sensitivityAggregations) {
		for (String each : sensitivityAggregations.getMeasurablePropertyNames()) {
			var<MeasurableProperty, Double> sensitivityValues = sensitivityAggregations
					.filterLocalSensitivityValues(each);
			checkAndHandleCompleteness(each, sensitivityValues);

			sensitivitiyModel.setSensitivityValues(sensitivityValues);
		}

		var<SensitivityEntry, Double> mlSensitivityValues = sensitivityAggregations.getMLSensitivityValues();
		checkAndHandleCompleteness(mlSensitivityValues);

		sensitivitiyModel.setMLSensitivityValues(mlSensitivityValues);

		return sensitivitiyModel;
	}

	private void checkAndHandleCompleteness(String propertyName, Map<MeasurableProperty, Double> sensitivityValues) {
		for (CategoricalValue each : retrieveValueSpaceOf(propertyName)) {
			if (containsNoPropertyWith(each, sensitivityValues.keySet())) {
				enrichWithZeroSensitivity(sensitivityValues, new MeasurableProperty(propertyName, each));
			}
		}
	}

	private void checkAndHandleCompleteness(Map<SensitivityEntry, Double> mlSensitivityValues) {
		AnalysisTransformation transformation = MLSensitivityAnalysis.getAnalysisTransformation();
		for (List<MeasurableProperty> each : transformation.computePropertyMeasureValueSpace()) {
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
		var propertyMeasure = MLSensitivityAnalysis.getAnalysisTransformation().findPropertyMeasureWith(propertyName)
				.orElseThrow(MLSensitivityAnalysisException.supplierWithMessage(
						String.format("There is no property measure for property %s", propertyName)));
		return propertyMeasure.getValueSpace();
	}

}
