package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.AnalysisTransformation;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;

import com.google.common.collect.Sets;

public class MLSensitivityAnalysis {

	public static class MLSensitivityAnalysisBuilder {

		private Set<PropertyMeasure> propertyMeasures = Sets.newHashSet();
		private TrainingDataBasedAnalysisStrategy analysisStrategy = null;

		public MLSensitivityAnalysisBuilder withSensitivityAnalysisStrategy(
				TrainingDataBasedAnalysisStrategy analysisStrategy) {
			this.analysisStrategy = analysisStrategy;
			return this;
		}

		public MLSensitivityAnalysisBuilder addPropertyMeasure(PropertyMeasure propertyMeasure) {
			this.propertyMeasures.add(propertyMeasure);
			return this;
		}

		public MLSensitivityAnalysis build() {
			requireNonNull(analysisStrategy, "There has to be a sensitive analysis strategy specified.");
			if (propertyMeasures.isEmpty()) {
				MLSensitivityAnalysisException.throwWithMessageAndCause(
						"There hast to be at least one property measure.", new IllegalArgumentException());
			}

			return new MLSensitivityAnalysis(analysisStrategy, new AnalysisTransformation(propertyMeasures));

		}

	}

	private final TrainingDataBasedAnalysisStrategy analysisStrategy;
	private final AnalysisTransformation analysisTransformation;

	private MLSensitivityAnalysis(TrainingDataBasedAnalysisStrategy analysisStrategy,
			AnalysisTransformation analysisTransformation) {
		this.analysisStrategy = analysisStrategy;
		this.analysisTransformation = analysisTransformation;
	}

	public static MLSensitivityAnalysisBuilder newBuilder() {
		return new MLSensitivityAnalysisBuilder();
	}

	public GroundProbabilisticNetwork run(MLSensitivityAnalysisConfig config) {
		GroundProbabilisticNetwork sensitivityModel = config.getSensitivityModel().orElse(deriveFromPropertyMeasures());
		return sensitivityModel;
	}

	private GroundProbabilisticNetwork deriveFromPropertyMeasures() {
		// TODO Auto-generated method stub
		return null;
	}

}
