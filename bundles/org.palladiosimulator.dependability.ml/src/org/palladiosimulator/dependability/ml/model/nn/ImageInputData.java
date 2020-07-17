package org.palladiosimulator.dependability.ml.model.nn;

import java.io.File;
import java.util.Map;

import org.palladiosimulator.dependability.ml.model.InputData;

public class ImageInputData extends InputData {

	private final static String KEY = "Img";
	
	public ImageInputData(File value) {
		super(Map.entry(KEY, value));
	}
	
	public File getImage() {
		return (File) getValueOf(KEY);
	}

}
