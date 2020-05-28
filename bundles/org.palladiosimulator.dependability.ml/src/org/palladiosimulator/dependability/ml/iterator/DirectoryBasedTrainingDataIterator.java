package org.palladiosimulator.dependability.ml.iterator;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.palladiosimulator.dependability.ml.model.InputData;
import org.palladiosimulator.dependability.ml.model.InputDataLabel;
import org.palladiosimulator.dependability.ml.util.Tuple;

import com.google.common.collect.Lists;

public abstract class DirectoryBasedTrainingDataIterator<InType extends InputData<?>, LabelType extends InputDataLabel<?>>
		extends TrainingDataIterator<InType, LabelType> {

	public DirectoryBasedTrainingDataIterator(File trainingDataLocation) {
		super(trainingDataLocation);
	}

	@Override
	protected Iterator<Tuple<InType, LabelType>> load(File trainingDataLocation) {
		var trainData = Lists.newArrayList(trainingDataLocation.listFiles());
		return arrangeTrainingData(trainData);
	}

	protected abstract Iterator<Tuple<InType, LabelType>> arrangeTrainingData(List<File> trainData);

}
