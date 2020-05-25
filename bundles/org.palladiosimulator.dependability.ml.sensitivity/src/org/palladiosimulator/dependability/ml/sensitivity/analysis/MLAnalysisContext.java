package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import java.io.File;

import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.dependability.ml.sensitivity.prediction.MLModel;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

public class MLAnalysisContext {

	public static class MLAnalysisContextBuilder {

		private File mlModelLocation = null;
		private File trainingData = null;
		private SensitivityModel sensitivityModel = null;
		
		public MLAnalysisContextBuilder analyseSensitivityOf(File mlModelLocation) {
			this.mlModelLocation = mlModelLocation;
			return this;
		}

		public MLAnalysisContextBuilder trainedWith(File trainingData) {
			this.trainingData = trainingData;
			return this;
		}
		
		public MLAnalysisContextBuilder andCapturedBy(SensitivityModel sensitivityModel) {
			this.sensitivityModel = sensitivityModel;
			return this;
		}

		public MLAnalysisContext build() {
			if (trainingData.isDirectory() == false) {
				MLSensitivityAnalysisException.throwWithMessage("The data location must be a folder.");
			}

			if (mlModelLocation.exists() == false) {
				MLSensitivityAnalysisException.throwWithMessage("The ml model file does not exist.");
			}

			return new MLAnalysisContext(loadModel(), trainingData, retrieveFileExt(), sensitivityModel);
		}

		private MLModel loadModel() {
			// TODO Auto-generated method stub
			return null;
		}

		private String retrieveFileExt() {
			String any = Lists.newArrayList(trainingData.listFiles()).get(0).getAbsolutePath();
			return Files.getFileExtension(any);
		}

	}

	private final MLModel mlModel;
	private final File trainingData;
	private final String fileExt;
	private final SensitivityModel sensitivityModel;

	private MLAnalysisContext(MLModel mlModel, File trainingData, String fileExt, SensitivityModel sensitivityModel) {
		this.mlModel = mlModel;
		this.trainingData = trainingData;
		this.fileExt = fileExt;
		this.sensitivityModel = sensitivityModel;
	}
	
	public static MLAnalysisContextBuilder newBuilder() {
		return new MLAnalysisContextBuilder();
	}

	public MLModel getMLModel() {
		return mlModel;
	}

	public File getTrainingData() {
		return trainingData;
	}

	public String getTrainingDataFileExtension() {
		return fileExt;
	}

	public SensitivityModel getSensitivityModel() {
		return sensitivityModel;
	}
}
