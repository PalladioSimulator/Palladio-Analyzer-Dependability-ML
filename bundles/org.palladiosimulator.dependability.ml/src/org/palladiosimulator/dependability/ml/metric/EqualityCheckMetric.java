package org.palladiosimulator.dependability.ml.metric;

import org.palladiosimulator.dependability.ml.model.InputDataLabel;
import org.palladiosimulator.dependability.ml.model.OutputData;

public class EqualityCheckMetric<T> implements PredictionEvaluationMetric<Boolean, T, T> {

	@Override
	public Boolean isAccurate(InputDataLabel<T> dataLabel, OutputData<T> output) {
		return output.getResult().equals(dataLabel.getLabel());
	}

}
