package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.palladiosimulator.dependability.ml.model.MLPredictionResult;
import org.palladiosimulator.dependability.ml.model.OutputData;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityAggregations.MLSensitivityEntry;
import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.AnalysisTransformation;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.SensitivityProperty;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure.MeasurableSensitivityProperty;

import com.google.common.collect.Lists;

public class TrainingDataBasedAnalysisStrategy implements MLSensitivityAnalysisStrategy {

	private final static String CONFIDENCE_STRATEGY_NAME = "Confidence based training data analysis strategy";
	private final static String ACCURACY_STRATEGY_NAME = "Accuracy based training data analysis strategy";

	private final String strategyName;
	private final Function<MLPredictionResult, Double> incrementalUpdate;

	private TrainingDataBasedAnalysisStrategy(Function<MLPredictionResult, Double> incrementalUpdate,
			String strategyName) {
		this.incrementalUpdate = incrementalUpdate;
		this.strategyName = strategyName;
	}

	public static TrainingDataBasedAnalysisStrategy accuracyBasedStrategy() {
		return new TrainingDataBasedAnalysisStrategy(r -> r.isExpectedResult() ? 1.0 : 0.0, ACCURACY_STRATEGY_NAME);
	}

	public static TrainingDataBasedAnalysisStrategy confidenceBasedStrategy() {
		return new TrainingDataBasedAnalysisStrategy(r -> {
			var numberOfPredictions = r.getPredictions().size();
			var sumOfPredictions = r.getPredictions().stream()
					.map(OutputData::getPredictionConfidence)
					.reduce(Double::sum)
					.get();
			return sumOfPredictions / numberOfPredictions;
		}, CONFIDENCE_STRATEGY_NAME);
	}

	@Override
	public String getName() {
		return strategyName;
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

			var properties = MLSensitivityAnalysis.getAnalysisTransformation()
					.computeMeasurableProperties(dataTuple.getFirst());
			var predictionAccuracy = incrementalUpdate.apply(mlModel.makePrediction(dataTuple));
			sensitivityAggregations.record(properties, predictionAccuracy);
		}

		return sensitivityAggregations;
	}

	private SensitivityModel complementSensitivityModel(SensitivityModel sensitivitiyModel,
			SensitivityAggregations sensitivityAggregations) {
		for (String each : sensitivityAggregations.getMeasurablePropertyIds()) {
			var sensitivityValues = sensitivityAggregations.getPropertySensitivityValues(each);
			complementPropertyValuesIfNecessary(sensitivityValues);

			var result = sensitivityValues.entrySet().stream()
					.collect(toMap(e -> (SensitivityProperty) e.getKey(), Map.Entry::getValue));
			sensitivitiyModel.setSensitivityValues(result);
		}

		var mlSensitivityValues = sensitivityAggregations.getMLSensitivityValues();
		complementSensitivityValuesIfNecessary(mlSensitivityValues);

		sensitivitiyModel.setMLSensitivityValues(mlSensitivityValues);

		return sensitivitiyModel;
	}

	private void complementPropertyValuesIfNecessary(Map<MeasurableSensitivityProperty, Double> sensitivityValues) {
		var measure = retrievePropertyMeasureBy(reduceToSingleProperty(sensitivityValues));
		for (MeasurableSensitivityProperty each : measure.getMeasurablePropertySpace()) {
			if (containsNoPropertyWith(each, sensitivityValues.keySet())) {
				enrichWithZeroProbability(sensitivityValues, each);
			}
		}
	}

	private void complementSensitivityValuesIfNecessary(Map<MLSensitivityEntry, Double> mlSensitivityValues) {
		AnalysisTransformation transformation = MLSensitivityAnalysis.getAnalysisTransformation();
		for (List<MeasurableSensitivityProperty> each : transformation.computeMeasurableSpace()) {
			// The list of each is immutable; which causes an exception during sorting.
			var entry = MLSensitivityEntry.from(Lists.newArrayList(each));
			if (containsNoPropertyWith(entry, mlSensitivityValues.keySet())) {
				enrichWithMaxEntropy(mlSensitivityValues, entry);
			}
		}
	}

	private boolean containsNoPropertyWith(MeasurableSensitivityProperty property, Set<MeasurableSensitivityProperty> recordedProperties) {
		return recordedProperties.stream().noneMatch(prop -> prop.equals(property));
	}

	private boolean containsNoPropertyWith(MLSensitivityEntry entry, Set<MLSensitivityEntry> mlSensitivityValues) {
		return mlSensitivityValues.contains(entry) == false;
	}

	private void enrichWithZeroProbability(Map<MeasurableSensitivityProperty, Double> recordedProperties,
			MeasurableSensitivityProperty zeroSensitivityProperty) {
		recordedProperties.put(zeroSensitivityProperty, 0.0);
	}

	private void enrichWithMaxEntropy(Map<MLSensitivityEntry, Double> mlSensitivityValues,
			MLSensitivityEntry zeroSensitivityProperty) {
		mlSensitivityValues.put(zeroSensitivityProperty, 0.5);
	}
	
	private MeasurableSensitivityProperty reduceToSingleProperty(Map<MeasurableSensitivityProperty, Double> sensitivityValues) {
		return sensitivityValues.keySet().iterator().next();
	}

	private PropertyMeasure retrievePropertyMeasureBy(MeasurableSensitivityProperty property) {
		return MLSensitivityAnalysis.getAnalysisTransformation().findPropertyMeasureWith(property.getId())
				.orElseThrow(MLSensitivityAnalysisException.supplierWithMessage(
						String.format("There is no property measure for property %s", property.getId())));
	}

}
