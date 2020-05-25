package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityAggregations.SensitivityEntry;
import org.palladiosimulator.dependability.ml.sensitivity.builder.ProbabilityDistributionBuilder;
import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.MeasurableProperty;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;

import tools.mdsd.probdist.distributionfunction.ProbabilityDistribution;

public class ProbabilisticSensitivityModel implements SensitivityModel {

	private final GroundProbabilisticNetwork probSensitivityModel;

	public ProbabilisticSensitivityModel(GroundProbabilisticNetwork probSensitivityModel) {
		this.probSensitivityModel = probSensitivityModel;
	}

	@Override
	public void setSensitivityValues(Map<MeasurableProperty, Double> sensitivityValues) {
		var propertyName = getGlobalProperty(sensitivityValues).getName();
		var probabilisticModel = findRandomVariableFor(propertyName).getDescriptiveModel();
		probabilisticModel.setDistribution(buildProbabilityDistributionOf(sensitivityValues));
	}

	@Override
	public void setMLSensitivityValues(Map<SensitivityEntry, Double> mlSensitivityValues) {
		var probabilisticModel = findMLRandomVariable().getDescriptiveModel();
		probabilisticModel.setDistribution(buildMLProbabilityOfSuccessDistribution(mlSensitivityValues));
	}
	
	@Override
	public SensitivityModel deriveFrom(Set<PropertyMeasure> propertyMeasures) {
		// TODO Auto-generated method stub
		return null;
	}

	private MeasurableProperty getGlobalProperty(Map<MeasurableProperty, Double> sensitivityValues) {
		return sensitivityValues.keySet().iterator().next();
	}

	private GroundRandomVariable findRandomVariableFor(String propertyName) {
		return probSensitivityModel.getLocalProbabilisticModels().stream()
				.flatMap(each -> each.getGroundRandomVariables().stream()).filter(variableOf(propertyName)).findFirst()
				.orElseThrow(MLSensitivityAnalysisException.supplierWithMessage(
						String.format("There is no variable for measured property %s", propertyName)));
	}

	private GroundRandomVariable findMLRandomVariable() {
		return probSensitivityModel.getLocalProbabilisticModels().stream()
				.flatMap(each -> each.getGroundRandomVariables().stream()).filter(variablesWithParents()).findFirst()
				.orElseThrow(MLSensitivityAnalysisException
						.supplierWithMessage("No random variable for ml prediction was found."));
	}

	// Assuming that the ML prediction random variable is the only one with parents.
	private Predicate<GroundRandomVariable> variablesWithParents() {
		return v -> v.getDependenceStructure().isEmpty() == false;
	}

	// Assuming that by convention the name of the property corresponds to the name
	// of the template variable.
	private Predicate<GroundRandomVariable> variableOf(String propertyName) {
		return v -> v.getInstantiatedTemplate().getEntityName().equals(propertyName);
	}

	private ProbabilityDistribution buildProbabilityDistributionOf(Map<MeasurableProperty, Double> sensitivityValues) {
		return ProbabilityDistributionBuilder.buildProbabilityDistributionFor(getGlobalProperty(sensitivityValues))
				.withSimpleParameterDerivedFrom(sensitivityValues).build();
	}

	public ProbabilityDistribution buildMLProbabilityOfSuccessDistribution(
			Map<SensitivityEntry, Double> sensitivityValues) {
		return ProbabilityDistributionBuilder.buildProbabilityDistributionForMLVariable()
				.withTabularParameterDerivedFrom(sensitivityValues).build();
	}

}
