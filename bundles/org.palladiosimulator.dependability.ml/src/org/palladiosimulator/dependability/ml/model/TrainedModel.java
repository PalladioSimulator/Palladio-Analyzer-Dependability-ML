package org.palladiosimulator.dependability.ml.model;

import java.io.File;
import java.net.URI;

import org.palladiosimulator.dependability.ml.iterator.TrainingDataIterator;
import org.palladiosimulator.dependability.ml.util.Tuple;

public interface TrainedModel<InType extends InputData<?>, LabelType extends InputDataLabel<?>> {
	
	public MLPredictionResult makePrediction(Tuple<InType, LabelType> dataTuple);
	
	public void loadModel(URI modelLocation);

	public TrainingDataIterator<InType, LabelType> getTrainingDataIteratorBy(File dataLocation);
}
