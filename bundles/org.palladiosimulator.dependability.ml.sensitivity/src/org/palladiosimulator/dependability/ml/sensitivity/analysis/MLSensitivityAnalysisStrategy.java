package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import java.io.File;

import org.palladiosimulator.dependability.ml.sensitivity.prediction.MLModel;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

public interface MLSensitivityAnalysisStrategy {

	public static class MLContext {

		private final MLModel mlModel;
		private final File trainingData;
		private final String fileExt;

		public MLContext(MLModel mlModel, File trainingData) {
			this.mlModel = mlModel;
			this.trainingData = trainingData;
			this.fileExt = retrieveFileExt(trainingData);
		}

		private String retrieveFileExt(File trainingData) {
			String any = Lists.newArrayList(trainingData.listFiles()).get(0).getAbsolutePath();
			return Files.getFileExtension(any);
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

	}

	public GroundProbabilisticNetwork analyseSensitivity(GroundProbabilisticNetwork sensitivityModel,
			MLContext context);
}
