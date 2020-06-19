package org.palladiosimulator.dependability.ml.sensitivity.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.palladiosimulator.dependability.ml.iterator.TrainingDataIterator;
import org.palladiosimulator.dependability.ml.model.InputData;
import org.palladiosimulator.dependability.ml.model.InputDataLabel;
import org.palladiosimulator.dependability.ml.model.MLPredictionResult;
import org.palladiosimulator.dependability.ml.model.OutputData;
import org.palladiosimulator.dependability.ml.model.PrimitiveInputData;
import org.palladiosimulator.dependability.ml.model.SimpleInputDataLabel;
import org.palladiosimulator.dependability.ml.model.TrainedModel;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.MLAnalysisContext;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.MLSensitivityAnalysis;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.ProbabilisticSensitivityModel;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityModel;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.TrainingDataBasedAnalysisStrategy;
import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.MeasurableProperty;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;
import org.palladiosimulator.dependability.ml.util.Tuple;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import tools.mdsd.probdist.api.apache.supplier.MultinomialDistributionSupplier;
import tools.mdsd.probdist.api.apache.util.DistributionTypeModelUtil;
import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.factory.ProbabilityDistributionFactory;
import tools.mdsd.probdist.model.basic.loader.BasicDistributionTypesLoader;

public class MLSensitivityAnalysisTest {

	private File dummyFile;
	private MLAnalysisContext context;
	private Set<PropertyMeasure> propertyMeasures;
	private MLSensitivityAnalysis sensitivityAnalysis;
	private SensitivityModel result;

	private static class TrainedModelMock implements TrainedModel {

		private static class TrainingDataIteratorMock extends TrainingDataIterator {

			private final static List<Tuple<InputData, InputDataLabel>> TRAIN_DATA = Lists.newArrayList();
			static {
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(1), SimpleInputDataLabel.numericalLabel(1)));
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(2), SimpleInputDataLabel.numericalLabel(2)));
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(3), SimpleInputDataLabel.numericalLabel(3)));
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(4), SimpleInputDataLabel.numericalLabel(4)));
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(5), SimpleInputDataLabel.numericalLabel(5)));
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(6), SimpleInputDataLabel.numericalLabel(6)));
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(7), SimpleInputDataLabel.numericalLabel(7)));
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(8), SimpleInputDataLabel.numericalLabel(8)));
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(9), SimpleInputDataLabel.numericalLabel(9)));
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(10), SimpleInputDataLabel.numericalLabel(10)));
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(11), SimpleInputDataLabel.numericalLabel(11)));
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(12), SimpleInputDataLabel.numericalLabel(12)));
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(13), SimpleInputDataLabel.numericalLabel(13)));
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(14), SimpleInputDataLabel.numericalLabel(14)));
				TRAIN_DATA.add(Tuple.of(PrimitiveInputData.numericalValue(15), SimpleInputDataLabel.numericalLabel(15)));
			}

			protected TrainingDataIteratorMock(File trainingDataLocation) {
				super(trainingDataLocation);
			}

			@Override
			protected Iterator<Tuple<InputData, InputDataLabel>> load(File trainingDataLocation) {
				return TRAIN_DATA.iterator();
			}
		}

		@Override
		public MLPredictionResult makePrediction(Tuple<InputData, InputDataLabel> dataTuple) {
			MLPredictionResult result;
			
			var value = asNumerical(dataTuple.getFirst()).getValue().intValue();
			if (Boolean.logicalOr(value <= 5, value > 10)) {
				result = new MLPredictionResult(determineRandomly());
				result.getPredictions().add(new OutputData("0.5", ""));
			} else {
				result = new MLPredictionResult(true);
				result.getPredictions().add(new OutputData("1.0", ""));
			}

			return result;
		}

		private boolean determineRandomly() {
			return new Random(System.currentTimeMillis()).nextInt(2) == 1;
		}

		@Override
		public void loadModel(URI modelLocation) {

		}

		@Override
		public TrainingDataIterator getTrainingDataIteratorBy(File dataLocation) {
			return new TrainingDataIteratorMock(dataLocation);
		}

	}

	@Before
	public void setUp() throws Exception {
		DistributionTypeModelUtil.get(BasicDistributionTypesLoader.loadRepository());
		ProbabilityDistributionFactory.get().register(new MultinomialDistributionSupplier());
		
		dummyFile = new File(System.getProperty("user.dir"));

		var trainingDataStrategy = TrainingDataBasedAnalysisStrategy.confidenceBasedStrategy();

		var simpleUpperBoundMeasure = new PropertyMeasure() {

			private final CategoricalValue trueValue = CategoricalValue.create("TRUE");
			private final CategoricalValue falseValue = CategoricalValue.create("FALSE");

			@Override
			public String getPropertyName() {
				return "SimpleUpperBoundMeasure";
			}

			@Override
			public MeasurableProperty apply(InputData inputData) {
				var value = asNumerical(inputData).getValue().intValue();
				if (value > 10) {
					return new MeasurableProperty(getPropertyName(), trueValue);
				}
				return new MeasurableProperty(getPropertyName(), falseValue);
			}

			@Override
			public Set<CategoricalValue> getValueSpace() {
				return Sets.newHashSet(trueValue, falseValue);
			}
		};
		var simpleLowerBoundMeasure = new PropertyMeasure() {

			private final CategoricalValue trueValue = CategoricalValue.create("TRUE");
			private final CategoricalValue falseValue = CategoricalValue.create("FALSE");

			@Override
			public String getPropertyName() {
				return "SimpleLowerBoundMeasure";
			}

			@Override
			public MeasurableProperty apply(InputData inputData) {
				var value = asNumerical(inputData).getValue().intValue();
				if (value <= 5) {
					return new MeasurableProperty(getPropertyName(), trueValue);
				}
				return new MeasurableProperty(getPropertyName(), falseValue);
			}

			@Override
			public Set<CategoricalValue> getValueSpace() {
				return Sets.newHashSet(trueValue, falseValue);
			}
		};
		propertyMeasures = Sets.newHashSet(simpleLowerBoundMeasure, simpleUpperBoundMeasure);

		sensitivityAnalysis = MLSensitivityAnalysis.newBuilder().withSensitivityAnalysisStrategy(trainingDataStrategy)
				.addPropertyMeasure(simpleUpperBoundMeasure).addPropertyMeasure(simpleLowerBoundMeasure).build();
	}
	
	@SuppressWarnings("unchecked")
	private static PrimitiveInputData<Number> asNumerical(InputData input) {
		return (PrimitiveInputData<Number>) input;
	}

	@Test
	public void test() {
		givenMLContext();
		whenAnalysingSensitivity();
		thenSensitivityModelIsValid();
	}

	private void givenMLContext() {
		context = MLAnalysisContext.newBuilder().analyseSensitivityOf(new TrainedModelMock()).trainedBy(dummyFile)
				.andCapturedBy(ProbabilisticSensitivityModel.createFrom(propertyMeasures)).build();
	}

	private void whenAnalysingSensitivity() {
		result = sensitivityAnalysis.analyseSensitivity(context);
		
//		var location = Paths.get(System.getProperty("user.dir"), "model").toFile();
//		result.saveAt(org.eclipse.emf.common.util.URI.createFileURI(location.getAbsolutePath()));
	}

	private void thenSensitivityModelIsValid() {
		assertValidSensitivityValuesOfAll(propertyMeasures);
		assertValidMLSensitivityValue();
	}

	private void assertValidMLSensitivityValue() {
		var firstMeasure = Lists.newArrayList(propertyMeasures).get(0);
		var secondMeasure = Lists.newArrayList(propertyMeasures).get(1);
		for (CategoricalValue eachValOfFirst : firstMeasure.getValueSpace()) {
			for (CategoricalValue eachValOfSecond : secondMeasure.getValueSpace()) {
				var properties = Lists.newArrayList(
						new MeasurableProperty(firstMeasure.getPropertyName(), eachValOfFirst),
						new MeasurableProperty(secondMeasure.getPropertyName(), eachValOfSecond));
				var successSensitvity = -1.0;
				try {
					successSensitvity = result.inferSensitivity(properties);
				} catch (MLSensitivityAnalysisException e) {
					fail(String.format("Something went wrong during inference, see: %s", e.getMessage()));
				}

				assertValueIsInRange(successSensitvity);
			}
		}
	}

	private void assertValidSensitivityValuesOfAll(Set<PropertyMeasure> propertyMeasures) {
		for (PropertyMeasure eachMeasure : propertyMeasures) {
			Map<MeasurableProperty, Double> sensitivityValues = Maps.newHashMap();
			for (CategoricalValue eachValue : eachMeasure.getValueSpace()) {
				try {
					var prop = new MeasurableProperty(eachMeasure.getPropertyName(), eachValue);
					var sensitivityValue = result.getSensitivityValuesOf(prop);
					sensitivityValues.put(prop, sensitivityValue);
				} catch (MLSensitivityAnalysisException e) {
					fail(String.format("Property %1s could not be found, see: %2s", eachMeasure.getPropertyName(),
							e.getMessage()));
				}
			}
			assertValidValues(sensitivityValues);
		}
	}

	private void assertValidValues(Map<MeasurableProperty, Double> sensitivityValues) {
		for (MeasurableProperty each : sensitivityValues.keySet()) {
			assertValueIsInRange(sensitivityValues.get(each));
		}
		assertSumsUpToOne(sensitivityValues.values());
	}

	private void assertValueIsInRange(Double value) {
		assertTrue(Boolean.logicalOr(value >= 0, value <= 1));
	}

	private void assertSumsUpToOne(Collection<Double> values) {
		assertTrue(values.stream().reduce(Double::sum).orElse(Double.NaN) == 1);
	}

}
