package org.palladiosimulator.dependability.ml.sensitivity.api;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.net.URI;
import java.util.List;
import static java.util.Objects.isNull;

import org.palladiosimulator.dependability.ml.model.TrainedModel;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.MLSensitivityAnalysisStrategy;
import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;
import org.palladiosimulator.dependability.ml.sensitivity.util.ResourceUtil;

import com.google.common.collect.Lists;

public class SensitivityAnalysisConfig {

	public static class SensitivityAnalysisConfigBuilder {

		private String trainedModel = null;
		private String trainDataLocation = null;
		private String trainedModelURI = null;
		private String analysisStrategy = null;
		private String sensModelStoringLocation = null;
		private List<String> sensitivityProperties = Lists.newArrayList();

		public SensitivityAnalysisConfigBuilder modelToAnalyse(String trainedModel) {
			this.trainedModel = trainedModel;
			return this;
		}

		public SensitivityAnalysisConfigBuilder locatedAt(String trainedModelURI) {
			this.trainedModelURI = trainedModelURI;
			return this;
		}

		public SensitivityAnalysisConfigBuilder andTrainedWith(String trainDataLocation) {
			this.trainDataLocation = trainDataLocation;
			return this;
		}

		public SensitivityAnalysisConfigBuilder analyseProperties(List<String> sensitivityProperties) {
			this.sensitivityProperties.addAll(sensitivityProperties);
			return this;
		}

		public SensitivityAnalysisConfigBuilder withStrategy(String analysisStrategy) {
			this.analysisStrategy = analysisStrategy;
			return this;
		}
		
		public SensitivityAnalysisConfigBuilder storeSensitivityModelAt(String sensModelStoringLocation) {
			this.sensModelStoringLocation = sensModelStoringLocation;
			return this;
		}

		public SensitivityAnalysisConfig build() {
			var trainedModel = retrieveTrainedModel();
			var trainDataLocation = convertTrainDataLocation();
			var trainedModelFile = convertTrainedModelFile();
			var sensModelStoringLocation = convertSensitivityModelStoringLocation();
			var analysisStrategy = retrieveAnalysisStrategy();
			var propertyMeasures = retrievePropertyMeasures();
			return new SensitivityAnalysisConfig(trainedModel, trainDataLocation, trainedModelFile, analysisStrategy,
					propertyMeasures, sensModelStoringLocation);
		}

		private URI convertSensitivityModelStoringLocation() {
			checkValidity(sensModelStoringLocation);
			
			return ResourceUtil.asURI(sensModelStoringLocation);
		}

		private TrainedModel retrieveTrainedModel() {
			checkValidity(trainedModel);

			return MLSensitivityAnalyser.findAnalysableModelWith(trainedModel)
					.orElseThrow(MLSensitivityAnalysisException
							.supplierWithMessage(String.format("There is no ml model with name: %s", trainedModel)));
		}

		private File convertTrainDataLocation() {
			checkValidity(trainDataLocation);

			var location = ResourceUtil.asFile(trainDataLocation);
			if (location.exists() == false) {
				MLSensitivityAnalysisException.throwWithMessage(
						String.format("The training data location is not properly formatted: %s", trainDataLocation));
			}
			return location;
		}

		private URI convertTrainedModelFile() {
			checkValidity(trainedModelURI);

			return ResourceUtil.asURI(trainedModelURI);
		}

		private MLSensitivityAnalysisStrategy retrieveAnalysisStrategy() {
			checkValidity(analysisStrategy);

			return MLSensitivityAnalyser.findAnalysisStrategyWith(analysisStrategy)
					.orElseThrow(MLSensitivityAnalysisException.supplierWithMessage(
							String.format("There is no strategy with name: %s", analysisStrategy)));
		}

		private List<PropertyMeasure> retrievePropertyMeasures() {
			if (sensitivityProperties.isEmpty()) {
				MLSensitivityAnalysisException.throwWithMessage("At least one property must be specified.");
			}

			return sensitivityProperties.stream().map(name -> retrievePropertyMeasure(name)).collect(toList());
		}

		private PropertyMeasure retrievePropertyMeasure(String name) {
			checkValidity(name);

			return MLSensitivityAnalyser.findAnalysablePropertyMeasureWith(name)
					.orElseThrow(MLSensitivityAnalysisException
							.supplierWithMessage(String.format("There is no property measure with name: %s", name)));
		}

		private void checkValidity(String value) {
			if (Boolean.logicalOr(isNull(value), value.isBlank())) {
				MLSensitivityAnalysisException
						.throwWithMessage(String.format("Value %s is not properly specified.", value));
			}
		}

	}

	private final TrainedModel trainedModel;
	private final File trainDataLocation;
	private final URI sensModelStoringLocation;
	private final URI trainedModelURI;
	private final MLSensitivityAnalysisStrategy analysisStrategy;
	private final List<PropertyMeasure> propertyMeasures;

	private SensitivityAnalysisConfig(TrainedModel trainedModel, File trainDataLocation, URI trainedModelURI,
			MLSensitivityAnalysisStrategy analysisStrategy, List<PropertyMeasure> propertyMeasures, URI sensModelStoringLocation) {
		this.trainedModel = trainedModel;
		this.trainDataLocation = trainDataLocation;
		this.trainedModelURI = trainedModelURI;
		this.analysisStrategy = analysisStrategy;
		this.propertyMeasures = propertyMeasures;
		this.sensModelStoringLocation = sensModelStoringLocation;
	}

	public static SensitivityAnalysisConfigBuilder newBuilder() {
		return new SensitivityAnalysisConfigBuilder();
	}

	public TrainedModel getTrainedModel() {
		return trainedModel;
	}

	public File getTrainDataLocation() {
		return trainDataLocation;
	}

	public URI getTrainedModelURI() {
		return trainedModelURI;
	}

	public MLSensitivityAnalysisStrategy getAnalysisStrategy() {
		return analysisStrategy;
	}

	public List<PropertyMeasure> getPropertyMeasures() {
		return propertyMeasures;
	}
	
	public URI getSensitivityModelStoringLocation() {
		return sensModelStoringLocation;
	}

}
