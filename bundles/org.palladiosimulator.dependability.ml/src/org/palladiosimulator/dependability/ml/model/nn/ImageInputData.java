package org.palladiosimulator.dependability.ml.model.nn;

import java.io.File;

import org.palladiosimulator.dependability.ml.model.InputData;

public class ImageInputData extends InputData<File> {

	private final static String TRAIN_DATA_PREFIX = "";
	
	public ImageInputData(File value) {
		super(value);
	}
	
	public static boolean isTrainingData(File file) {
		return file.getName().startsWith(TRAIN_DATA_PREFIX);
	}

}
