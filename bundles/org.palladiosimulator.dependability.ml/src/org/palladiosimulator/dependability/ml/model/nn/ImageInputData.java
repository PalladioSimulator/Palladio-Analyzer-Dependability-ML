package org.palladiosimulator.dependability.ml.model.nn;

import java.io.File;
import java.util.Map;

import org.palladiosimulator.dependability.ml.model.InputData;

public class ImageInputData extends InputData {

	private final static String TRAIN_DATA_PREFIX = "";
	private final static String KEY = "Img";
	
	public ImageInputData(File value) {
		super(Map.entry(KEY, value));
	}
	
	public static boolean isTrainingData(File file) {
		return file.getName().startsWith(TRAIN_DATA_PREFIX);
	}
	
	public File getImage() {
		return (File) getValueOf(KEY);
	}

}
