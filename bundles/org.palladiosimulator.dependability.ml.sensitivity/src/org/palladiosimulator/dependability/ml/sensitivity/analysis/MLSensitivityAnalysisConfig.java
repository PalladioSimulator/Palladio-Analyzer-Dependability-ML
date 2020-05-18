package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import java.io.File;
import java.util.Optional;

import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;

public class MLSensitivityAnalysisConfig {

	public static class MLSensitivityAnalysisConfigBuilder {

		private File dataLocation = null;
		private File mlModelLocation = null;
		private GroundProbabilisticNetwork sensitivityModel = null;

		public MLSensitivityAnalysisConfigBuilder withMLModel(File mlModelLocation) {
			this.mlModelLocation = mlModelLocation;
			return this;
		}

		public MLSensitivityAnalysisConfigBuilder trainedWith(File dataLocation) {
			this.dataLocation = dataLocation;
			return this;
		}

		public MLSensitivityAnalysisConfigBuilder andOptionalSensitivityModel(
				GroundProbabilisticNetwork sensitivityModel) {
			this.sensitivityModel = sensitivityModel;
			return this;
		}

		public MLSensitivityAnalysisConfig build() {
			if (dataLocation.isDirectory() == false) {
				MLSensitivityAnalysisException.throwWithMessage("The data location must be a folder.");
			}

			if (mlModelLocation.exists() == false) {
				MLSensitivityAnalysisException.throwWithMessage("The ml model file does not exist.");
			}

			return new MLSensitivityAnalysisConfig(dataLocation, mlModelLocation, sensitivityModel);
		}

	}

	private final File dataLocation;
	private final File mlModelLocation;
	private final Optional<GroundProbabilisticNetwork> sensitivityModel;

	private MLSensitivityAnalysisConfig(File dataLocation, File mlModelLocation,
			GroundProbabilisticNetwork sensitivityModel) {
		this.dataLocation = dataLocation;
		this.mlModelLocation = mlModelLocation;
		this.sensitivityModel = Optional.ofNullable(sensitivityModel);
	}

	public File getDataLocation() {
		return dataLocation;
	}

	public Optional<GroundProbabilisticNetwork> getSensitivityModel() {
		return sensitivityModel;
	}

	public File getMLModelLocation() {
		return mlModelLocation;
	}
}
