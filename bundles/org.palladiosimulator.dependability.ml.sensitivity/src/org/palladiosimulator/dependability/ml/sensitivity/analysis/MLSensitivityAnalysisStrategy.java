package org.palladiosimulator.dependability.ml.sensitivity.analysis;

public interface MLSensitivityAnalysisStrategy {

	public SensitivityModel analyseSensitivity(MLAnalysisContext context);
}
