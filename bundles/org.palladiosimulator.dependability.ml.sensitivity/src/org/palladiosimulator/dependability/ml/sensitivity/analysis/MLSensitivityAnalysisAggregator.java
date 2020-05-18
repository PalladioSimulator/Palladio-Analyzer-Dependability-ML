package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import java.util.Map;

import org.palladiosimulator.dependability.ml.sensitivity.transformation.MeasureableProperty;

public abstract class MLSensitivityAnalysisAggregator {
	
	Map<MeasureableProperty, Double> sensitivityValues;
	
}
