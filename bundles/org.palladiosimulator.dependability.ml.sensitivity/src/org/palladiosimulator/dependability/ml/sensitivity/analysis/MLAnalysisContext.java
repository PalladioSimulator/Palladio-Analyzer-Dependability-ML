package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import static java.util.Objects.requireNonNull;

import java.io.File;

import org.palladiosimulator.dependability.ml.model.TrainedModel;
import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;

public class MLAnalysisContext {

	public static class MLAnalysisContextBuilder {

		TrainedModel trainedModel;
		private File trainingData = null;
		private SensitivityModel sensitivityModel = null;

		public MLAnalysisContextBuilder analyseSensitivityOf(TrainedModel trainedModel) {
			this.trainedModel = trainedModel;
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
			requireNonNull(trainedModel, "The ML model to analyse must be specified.");
			requireNonNull(trainingData, "The training data location has to be specified.");
			requireNonNull(sensitivityModel, "The sensitivity model has to be specified.");

			if (trainingData.exists() == false) {
				MLSensitivityAnalysisException.throwWithMessage("The data location does not exist.");
			}

			return new MLAnalysisContext(trainedModel, trainingData, sensitivityModel);
		}

	}

	private final TrainedModel trainedModel;
	private final File trainingData;
	private final SensitivityModel sensitivityModel;

	private MLAnalysisContext(TrainedModel trainedModel, File trainingData, SensitivityModel sensitivityModel) {
		this.trainedModel = trainedModel;
		this.trainingData = trainingData;
		this.sensitivityModel = sensitivityModel;
	}

	public static MLAnalysisContextBuilder newBuilder() {
		return new MLAnalysisContextBuilder();
	}

	public TrainedModel getMLModel() {
		return trainedModel;
	}

	public File getTrainingData() {
		return trainingData;
	}

	public SensitivityModel getSensitivityModel() {
		return sensitivityModel;
	}
}
