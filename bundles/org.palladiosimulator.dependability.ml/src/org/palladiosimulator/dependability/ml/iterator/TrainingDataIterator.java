package org.palladiosimulator.dependability.ml.iterator;

import java.io.File;
import java.util.Iterator;

import org.palladiosimulator.dependability.ml.model.InputData;
import org.palladiosimulator.dependability.ml.model.InputDataLabel;
import org.palladiosimulator.dependability.ml.util.Tuple;

public abstract class TrainingDataIterator<InType extends InputData<?>, LabelType extends InputDataLabel<?>>
		implements Iterator<Tuple<InType, LabelType>> {

	private final Iterator<Tuple<InType, LabelType>> innerIterator;

	protected TrainingDataIterator(File trainingDataLocation) {
		this.innerIterator = load(trainingDataLocation);
	}

	@Override
	public boolean hasNext() {
		return innerIterator.hasNext();
	}

	@Override
	public Tuple<InType, LabelType> next() {
		return innerIterator.next();
	}

	protected abstract Iterator<Tuple<InType, LabelType>> load(File trainingDataLocation);

}
