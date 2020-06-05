package org.palladiosimulator.dependability.ml.iterator;

import java.io.File;
import java.util.Iterator;

import org.palladiosimulator.dependability.ml.model.InputData;
import org.palladiosimulator.dependability.ml.model.InputDataLabel;
import org.palladiosimulator.dependability.ml.util.Tuple;

public abstract class TrainingDataIterator implements Iterator<Tuple<InputData, InputDataLabel>> {

	private final Iterator<Tuple<InputData, InputDataLabel>> innerIterator;

	protected TrainingDataIterator(File trainingDataLocation) {
		this.innerIterator = load(trainingDataLocation);
	}

	@Override
	public boolean hasNext() {
		return innerIterator.hasNext();
	}

	@Override
	public Tuple<InputData, InputDataLabel> next() {
		return innerIterator.next();
	}

	protected abstract Iterator<Tuple<InputData, InputDataLabel>> load(File trainingDataLocation);

}
