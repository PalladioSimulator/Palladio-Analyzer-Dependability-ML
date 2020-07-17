package org.palladiosimulator.dependability.ml.model.nn;

import static java.util.stream.Collectors.partitioningBy;

import java.io.File;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.palladiosimulator.dependability.ml.exception.DependableMLException;
import org.palladiosimulator.dependability.ml.iterator.DirectoryBasedTrainingDataIterator;
import org.palladiosimulator.dependability.ml.model.InputData;
import org.palladiosimulator.dependability.ml.model.InputDataLabel;
import org.palladiosimulator.dependability.ml.model.MLPredictionResult;
import org.palladiosimulator.dependability.ml.model.OutputData;
import org.palladiosimulator.dependability.ml.model.TrainedModel;
import org.palladiosimulator.dependability.ml.model.access.HttpModelAccessor;
import org.palladiosimulator.dependability.ml.util.Tuple;

import com.google.common.collect.Lists;

public class MaskRCNN implements TrainedModel {

	private final static String MODEL_NAME = "Mask R-CNN";
	private final static String CLASS_1_PREDICTION = "class1";
	private final static String CLASS_2_PREDICTION = "class2";

	public static class MaskRCNNTrainingDataIterator extends DirectoryBasedTrainingDataIterator {

		private final static String LABEL_POSTFIX = "label.txt";
		
		public MaskRCNNTrainingDataIterator(File trainingDataLocation) {
			super(trainingDataLocation);
		}

		@Override
		protected Iterator<Tuple<InputData, InputDataLabel>> arrangeTrainingData(List<File> trainData) {
			var partitionedData = trainData.stream().collect(partitioningBy(isTrainingData()));
			var trainDataSplit = partitionedData.get(true);
			var labelDataSplit = partitionedData.get(false);
			if (trainDataSplit.size() != labelDataSplit.size()) {
				DependableMLException.throwWithMessage("The number of training and label data is unequal");
			}

			List<Tuple<InputData, InputDataLabel>> arrangedData = Lists.newArrayList();
			for (int i = 0; i < trainDataSplit.size(); i++) {
				var inputData = new ImageInputData(trainDataSplit.get(i));
				var label = new ImageSegmentationLabel(labelDataSplit.get(i));
				arrangedData.add(Tuple.of(inputData, label));
			}
			return arrangedData.iterator();
		}

		private Predicate<File> isTrainingData() {
			return file -> file.getName().endsWith(LABEL_POSTFIX) == false;
		}

	}

	private final HttpModelAccessor remoteTrainedModel = new HttpModelAccessor();

	@Override
	public MaskRCNNTrainingDataIterator getTrainingDataIteratorBy(File dataLocation) {
		return new MaskRCNNTrainingDataIterator(dataLocation);
	}

	@Override
	public void loadModel(URI modelURI) {
		if (remoteTrainedModel.canNotAccess(modelURI)) {
			DependableMLException.throwWithMessage(String.format("The model %s could not be loaded.", modelURI));
		}
		remoteTrainedModel.load(modelURI);
	}

	@Override
	public MLPredictionResult makePrediction(Tuple<InputData, InputDataLabel> dataTuple) {
		var prediction = remoteTrainedModel.query(dataTuple.getFirst());
		return parsePredictionResult(prediction);
	}

	private MLPredictionResult parsePredictionResult(List<OutputData> predictions) {
		var randomizedExpectation = new Random(System.currentTimeMillis()).nextInt(2) == 1 ? true : false;
		var result =  new MLPredictionResult(randomizedExpectation);
		result.getPredictions().addAll(predictions);
		return result;
	}

	@Override
	public String getName() {
		return MODEL_NAME;
	}

}
