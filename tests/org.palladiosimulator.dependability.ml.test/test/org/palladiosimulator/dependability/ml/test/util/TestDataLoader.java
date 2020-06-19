package org.palladiosimulator.dependability.ml.test.util;

import java.io.File;
import java.nio.file.Paths;

public class TestDataLoader {
	
	private final static String FOLDER = "resource";
	private final static String IMAGE = "TrainingImg.png";
	
	public static File loadTrainingImage() {
		var projectPath = System.getProperty("user.dir");
		var relativeDataPath = Paths.get(FOLDER, IMAGE).toString();
		return Paths.get(projectPath, relativeDataPath).toFile();
	}
}
