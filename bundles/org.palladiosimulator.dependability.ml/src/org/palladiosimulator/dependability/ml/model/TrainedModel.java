package org.palladiosimulator.dependability.ml.model;

import java.io.File;
import java.net.URI;

import org.palladiosimulator.dependability.ml.iterator.TrainingDataIterator;
import org.palladiosimulator.dependability.ml.util.Tuple;

public interface TrainedModel {

	public MLPredictionResult makePrediction(Tuple<InputData, InputDataLabel> dataTuple);

	public void loadModel(URI modelLocation);

	public TrainingDataIterator getTrainingDataIteratorBy(File dataLocation);
	
	public String getName();
}
