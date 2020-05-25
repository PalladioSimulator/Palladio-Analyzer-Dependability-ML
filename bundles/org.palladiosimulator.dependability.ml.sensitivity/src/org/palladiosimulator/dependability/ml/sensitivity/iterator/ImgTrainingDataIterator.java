package org.palladiosimulator.dependability.ml.sensitivity.iterator;

import java.io.File;

import org.palladiosimulator.dependability.ml.sensitivity.prediction.InputData;

public class ImgTrainingDataIterator extends TrainingDataIterator {

	public ImgTrainingDataIterator(File trainingDataLocation) {
		super(trainingDataLocation);
	}

	@Override
	protected InputData asInputData(File trainingData) {
		return null;
	}


}
