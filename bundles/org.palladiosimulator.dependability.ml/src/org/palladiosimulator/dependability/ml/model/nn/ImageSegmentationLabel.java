package org.palladiosimulator.dependability.ml.model.nn;

import java.io.File;

import org.palladiosimulator.dependability.ml.model.InputDataLabel;

public class ImageSegmentationLabel extends InputDataLabel {

	public ImageSegmentationLabel(File label) {
		super(label);
	}
	
	public File getSegmentationImgLabel() {
		return (File) getLabel();
	}

}
