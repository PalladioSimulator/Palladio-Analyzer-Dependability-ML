package org.palladiosimulator.dependability.ml.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.palladiosimulator.dependability.ml.exception.DependableMLException;
import org.palladiosimulator.dependability.ml.iterator.TrainingDataIterator;
import org.palladiosimulator.dependability.ml.model.InputData;
import org.palladiosimulator.dependability.ml.model.InputDataLabel;
import org.palladiosimulator.dependability.ml.model.MLPredictionResult;
import org.palladiosimulator.dependability.ml.model.TrainedModel;
import org.palladiosimulator.dependability.ml.model.access.HttpModelAccessor;
import org.palladiosimulator.dependability.ml.model.nn.ImageInputData;
import org.palladiosimulator.dependability.ml.test.util.TestDataLoader;
import org.palladiosimulator.dependability.ml.util.Tuple;

public class RemoteTrainedModel {

	private final static boolean IS_EXPECTED = true;
	private final static String TEST_URI = "http://localhost:5000";

	private static class TrainedTestModel implements TrainedModel {

		private final HttpModelAccessor remoteTrainedModel = new HttpModelAccessor();

		@Override
		public void loadModel(URI modelURI) {
			if (remoteTrainedModel.canAccess(modelURI) == false) {
				fail("The uri is not valid for this context.");
			}
			remoteTrainedModel.load(modelURI);
		}

		@Override
		public MLPredictionResult makePrediction(Tuple<InputData, InputDataLabel> dataTuple) {
			var prediction = remoteTrainedModel.query(dataTuple.getFirst());
			return new MLPredictionResult(IS_EXPECTED);
		}

		@Override
		public TrainingDataIterator getTrainingDataIteratorBy(File dataLocation) {
			fail("This method is not subject of testing in this case.");
			return null;
		}

	}

	private final TrainedTestModel model = new TrainedTestModel();

	private URI modelURI;
	private ImageInputData inData;

	@Test
	public void test() {
		givenModelURIAndInputData();

		var successfullyLoaded = whenLoadingTheModel();
		thenLoadingWasSuccessfull(successfullyLoaded);

		var result = whenQueryingTheModel();
		thenModelPredictionIsExpected(result);
	}

	private void givenModelURIAndInputData() {
		try {
			modelURI = new URI(TEST_URI);
		} catch (URISyntaxException e) {
			fail("The specified uri is not valid.");
		}
		inData = new ImageInputData(TestDataLoader.loadTrainingImage());
	}
	
	private boolean whenLoadingTheModel() {
		model.loadModel(modelURI);
		return true;
	}
	
	private MLPredictionResult whenQueryingTheModel() {
		MLPredictionResult result = null;
		try {
			result = model.makePrediction(Tuple.of(inData, null));
		} catch (DependableMLException e) {
			fail(e.getMessage());
		}
		return result;
	}

	private void thenLoadingWasSuccessfull(boolean successfullyLoaded) {
		assertTrue(successfullyLoaded);
	}
	
	private void thenModelPredictionIsExpected(MLPredictionResult result) {
		assertTrue(result.isExpectedResult() == IS_EXPECTED);
	}

}
