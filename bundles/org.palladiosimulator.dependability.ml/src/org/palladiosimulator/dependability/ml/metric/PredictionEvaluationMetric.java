package org.palladiosimulator.dependability.ml.metric;

import org.palladiosimulator.dependability.ml.model.InputDataLabel;
import org.palladiosimulator.dependability.ml.model.OutputData;

public interface PredictionEvaluationMetric<T, U, V> {

	public T isAccurate(InputDataLabel<U> dataLabel, OutputData<V> output);
}
