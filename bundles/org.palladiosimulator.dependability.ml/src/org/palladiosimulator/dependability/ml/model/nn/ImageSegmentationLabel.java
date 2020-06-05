package org.palladiosimulator.dependability.ml.model.nn;

import java.io.File;

import org.palladiosimulator.dependability.ml.model.InputDataLabel;

public class ImageSegmentationLabel extends InputDataLabel {

	private static final String LABEL_DATA_PREFIX = "";
	
	public ImageSegmentationLabel(File label) {
		super(label);
	}
	
	public static boolean isDataLabel(File file) {
		return file.getName().startsWith(LABEL_DATA_PREFIX);
	}
	
	public File getSegmentationImgLabel() {
		return (File) getLabel();
	}

}
