package org.palladiosimulator.dependability.ml.model.nn;

import static java.util.stream.Collectors.partitioningBy;

import java.io.File;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

import org.palladiosimulator.dependability.ml.exception.DependableMLException;
import org.palladiosimulator.dependability.ml.iterator.DirectoryBasedTrainingDataIterator;
import org.palladiosimulator.dependability.ml.model.MLPredictionResult;
import org.palladiosimulator.dependability.ml.model.OutputData;
import org.palladiosimulator.dependability.ml.model.TrainedModel;
import org.palladiosimulator.dependability.ml.model.access.HttpModelAccessor;
import org.palladiosimulator.dependability.ml.util.Tuple;

import com.google.common.collect.Lists;

public class MaskRCNN implements TrainedModel<ImageInputData, ImageSegmentationLabel> {

	public static class MaskRCNNTrainingDataIterator
			extends DirectoryBasedTrainingDataIterator<ImageInputData, ImageSegmentationLabel> {

		public MaskRCNNTrainingDataIterator(File trainingDataLocation) {
			super(trainingDataLocation);
		}

		@Override
		protected Iterator<Tuple<ImageInputData, ImageSegmentationLabel>> arrangeTrainingData(List<File> trainData) {
			var partitionedData = trainData.stream().collect(partitioningBy(f -> ImageInputData.isTrainingData(f)));
			var trainDataSplit = partitionedData.get(true);
			var labelDataSplit = partitionedData.get(false);
			if (trainDataSplit.size() != labelDataSplit.size()) {
				DependableMLException.throwWithMessage("The number of training and label data is unequal");
			}

			List<Tuple<ImageInputData, ImageSegmentationLabel>> arrangedData = Lists.newArrayList();
			for (int i = 0; i < trainDataSplit.size(); i++) {
				var inputData = new ImageInputData(trainDataSplit.get(i));
				var label = new ImageSegmentationLabel(labelDataSplit.get(i));
				arrangedData.add(Tuple.of(inputData, label));
			}
			return arrangedData.iterator();
		}
	}

	private final HttpModelAccessor remoteTrainedModel = new HttpModelAccessor();

	@Override
	public MaskRCNNTrainingDataIterator getTrainingDataIteratorBy(File dataLocation) {
		return new MaskRCNNTrainingDataIterator(dataLocation);
	}

	@Override
	public void loadModel(URI modelURI) {
		if (remoteTrainedModel.canAccess(modelURI) == false) {
			DependableMLException.throwWithMessage(String.format("The model %s could not be loaded.", modelURI));
		}
		remoteTrainedModel.load(modelURI);
	}

	@Override
	public MLPredictionResult makePrediction(Tuple<ImageInputData, ImageSegmentationLabel> dataTuple) {
		var prediction = remoteTrainedModel.query(dataTuple.getFirst());
		return parsePredictionResult(prediction);
	}

	private MLPredictionResult parsePredictionResult(List<OutputData> predictions) {
		// TODO Auto-generated method stub
		return null;
	}

}
