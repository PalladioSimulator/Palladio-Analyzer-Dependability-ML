package org.palladiosimulator.dependability.ml.sensitivity.iterator;

import static java.util.Objects.isNull;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TrainingDataIteratorFactory {

	private static TrainingDataIteratorFactory factoryInstance = null;

	private final Map<String, Class<? extends TrainingDataIterator>> iteratorRegistry;

	private TrainingDataIteratorFactory() {
		this.iteratorRegistry = Maps.newHashMap();
		addDefaultIterators();
	}

	public static TrainingDataIteratorFactory get() {
		if (isNull(factoryInstance)) {
			factoryInstance = new TrainingDataIteratorFactory();
		}
		return factoryInstance;
	}

	private void addDefaultIterators() {
		this.iteratorRegistry.put(".img", ImgTrainingDataIterator.class);
	}

	public void register(String fileExtension, Class<? extends TrainingDataIterator> iteratorClass) {
		iteratorRegistry.put(fileExtension, iteratorClass);
	}

	@SuppressWarnings("unchecked")
	public <T extends TrainingDataIterator> Optional<T> createIteratorFor(String fileExtension, File trainingData) {
		Class<T> iterator = (Class<T>) iteratorRegistry.get(fileExtension);
		if (isNull(iterator)) {
			return Optional.empty();
		}

		T iteratorInstance = null;
		try {
			iteratorInstance = iterator.getConstructor(File.class).newInstance(trainingData);
		} catch (Exception e) {
			MLSensitivityAnalysisException.throwWithMessageAndCause(
					String.format("Something went wrond in the iterator factory: %s", e.getMessage()), e);
		}
		return Optional.of(iteratorInstance);
	}
	
	public List<String> getSupportedFileExtensions() {
		return Lists.newArrayList(iteratorRegistry.keySet());
	}

}
