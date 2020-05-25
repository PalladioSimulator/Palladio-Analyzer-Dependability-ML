package org.palladiosimulator.dependability.ml.sensitivity.iterator;

import java.io.File;
import java.util.Iterator;

import org.palladiosimulator.dependability.ml.sensitivity.prediction.InputData;

import com.google.common.collect.Lists;

public abstract class TrainingDataIterator implements Iterator<InputData> {

	private final Iterator<File> trainingDataIterator;

	protected TrainingDataIterator(File trainingDataLocation) {
		this.trainingDataIterator = Lists.newArrayList(trainingDataLocation.listFiles()).iterator();
	}

	@Override
	public boolean hasNext() {
		return trainingDataIterator.hasNext();
	}

	@Override
	public InputData next() {
		return asInputData(trainingDataIterator.next());
	}

	protected abstract InputData asInputData(File trainingData);

}
