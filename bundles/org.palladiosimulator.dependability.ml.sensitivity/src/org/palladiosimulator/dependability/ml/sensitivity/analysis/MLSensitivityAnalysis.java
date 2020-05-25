package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.AnalysisTransformation;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;

import com.google.common.collect.Sets;

public class MLSensitivityAnalysis {

	public static class MLSensitivityAnalysisBuilder {

		private Set<PropertyMeasure> propertyMeasures = Sets.newHashSet();
		private MLSensitivityAnalysisStrategy analysisStrategy = null;

		public MLSensitivityAnalysisBuilder withSensitivityAnalysisStrategy(
				MLSensitivityAnalysisStrategy analysisStrategy) {
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

			MLSensitivityAnalysis.analysisTransformation = new AnalysisTransformation(propertyMeasures);
			return new MLSensitivityAnalysis(analysisStrategy);

		}

	}
	
	private static AnalysisTransformation analysisTransformation;

	private final MLSensitivityAnalysisStrategy analysisStrategy;

	private MLSensitivityAnalysis(MLSensitivityAnalysisStrategy analysisStrategy) {
		this.analysisStrategy = analysisStrategy;
	}

	public static MLSensitivityAnalysisBuilder newBuilder() {
		return new MLSensitivityAnalysisBuilder();
	}

	public static AnalysisTransformation getAnalysisTransformation() {
		return analysisTransformation;
	}
	
	public SensitivityModel analyseSensitivity(MLAnalysisContext context) {
		return analysisStrategy.analyseSensitivity(context);
	}

}
