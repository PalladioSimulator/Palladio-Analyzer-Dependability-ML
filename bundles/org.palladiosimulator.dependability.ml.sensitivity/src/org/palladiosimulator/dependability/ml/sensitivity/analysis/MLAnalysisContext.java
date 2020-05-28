package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.net.URI;

import org.palladiosimulator.dependability.ml.model.InputData;
import org.palladiosimulator.dependability.ml.model.InputDataLabel;
import org.palladiosimulator.dependability.ml.model.TrainedModel;
import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;

public class MLAnalysisContext {

	public static class MLAnalysisContextBuilder {

		private URI mlModelLocation = null;
		private Class<? extends TrainedModel<InputData<?>, InputDataLabel<?>>> modelType = null;
		private File trainingData = null;
		private SensitivityModel sensitivityModel = null;

		public MLAnalysisContextBuilder analyseSensitivityOf(URI mlModelLocation) {
			this.mlModelLocation = mlModelLocation;
			return this;
		}

		public MLAnalysisContextBuilder withType(
				Class<? extends TrainedModel<InputData<?>, InputDataLabel<?>>> modelType) {
			this.modelType = modelType;
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
			requireNonNull(mlModelLocation, "The model location has to be specified.");
			requireNonNull(trainingData, "The training data location has to be specified.");
			requireNonNull(sensitivityModel, "The sensitivity model has to be specified.");
			requireNonNull(modelType, "The model type has to be specified.");

			if (trainingData.exists() == false) {
				MLSensitivityAnalysisException.throwWithMessage("The data location does not exist.");
			}

			if (mlModelLocation.isAbsolute() == false) {
				MLSensitivityAnalysisException.throwWithMessage("The ml model uri has no scheme.");
			}

			TrainedModel<InputData<?>, InputDataLabel<?>> trainedModel = null;
			try {
				trainedModel = modelType.getDeclaredConstructor().newInstance();
				trainedModel.loadModel(mlModelLocation);
			} catch (Exception e) {
				MLSensitivityAnalysisException.throwWithMessageAndCause("The ml model could not be build.", e);
			}

			return new MLAnalysisContext(trainedModel, trainingData, sensitivityModel);
		}

	}

	private final TrainedModel<InputData<?>, InputDataLabel<?>> mlModel;
	private final File trainingData;
	private final SensitivityModel sensitivityModel;

	private MLAnalysisContext(TrainedModel<InputData<?>, InputDataLabel<?>> mlModel, File trainingData,
			SensitivityModel sensitivityModel) {
		this.mlModel = mlModel;
		this.trainingData = trainingData;
		this.sensitivityModel = sensitivityModel;
	}

	public static MLAnalysisContextBuilder newBuilder() {
		return new MLAnalysisContextBuilder();
	}

	public TrainedModel<InputData<?>, InputDataLabel<?>> getMLModel() {
		return mlModel;
	}

	public File getTrainingData() {
		return trainingData;
	}

	public SensitivityModel getSensitivityModel() {
		return sensitivityModel;
	}
}
