package org.palladiosimulator.dependability.ml.sensitivity.analysis;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityAggregations.MLSensitivityEntry;
import org.palladiosimulator.dependability.ml.sensitivity.builder.ProbabilisticSensitivityModelBuilder;
import org.palladiosimulator.dependability.ml.sensitivity.builder.ProbabilityDistributionBuilder;
import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.MeasurableProperty;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;
import org.palladiosimulator.envdyn.api.entity.bn.BayesianNetwork;
import org.palladiosimulator.envdyn.api.entity.bn.BayesianNetwork.InputValue;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticModel;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;
import org.palladiosimulator.envdyn.environment.staticmodel.ProbabilisticModelRepository;
import org.palladiosimulator.envdyn.environment.templatevariable.TemplateVariableDefinitions;

import com.google.common.collect.Maps;

import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.parser.DefaultParameterParser;
import tools.mdsd.probdist.distributionfunction.ProbabilityDistribution;
import tools.mdsd.probdist.distributionfunction.SimpleParameter;

public class ProbabilisticSensitivityModel extends SensitivityModel {

	private final static String DIST_MODEL_NAME = "ProbabilityDistributions";
	private final static String DIST_MODEL_EXT = "distributionfunction";
	private final static String PROB_MODEL_NAME = "ProbabilisticSensitivityModel";
	private final static String PROB_MODEL_EXT = "staticmodel";
	private final static String TEMPLATE_MODEL_NAME = "ProbabilisticSensitivityModel";
	private final static String TEMPLATE_MODEL_EXT = "templatevariable";

	private final TemplateVariableDefinitions templateVariables;
	private final GroundProbabilisticNetwork probSensitivityModel;

	private BayesianNetwork bayesianNetwork;

	private ProbabilisticSensitivityModel(GroundProbabilisticNetwork probSensitivityModel,
			TemplateVariableDefinitions templateVariables) {
		this.probSensitivityModel = probSensitivityModel;
		this.templateVariables = templateVariables;
		this.bayesianNetwork = null;
	}

	private ProbabilisticSensitivityModel() {
		this(null, null);
	}

	public static ProbabilisticSensitivityModel createFrom(GroundProbabilisticNetwork probSensitivityModel,
			TemplateVariableDefinitions templateVariables) {
		return new ProbabilisticSensitivityModel(probSensitivityModel, templateVariables);
	}

	public static ProbabilisticSensitivityModel createFrom(Set<PropertyMeasure> propertyMeasures) {
		return new ProbabilisticSensitivityModel().deriveFrom(propertyMeasures);
	}

	@Override
	public void setSensitivityValues(Map<MeasurableProperty, Double> sensitivityValues) {
		var propertyName = getGlobalProperty(sensitivityValues).getName();
		var probabilisticModel = findRandomVariableFor(propertyName).getDescriptiveModel();
		probabilisticModel.setDistribution(buildProbabilityDistributionOf(sensitivityValues));
	}

	@Override
	public double getSensitivityValuesOf(MeasurableProperty property) {
		var probabilisticModel = findRandomVariableFor(property.getName()).getDescriptiveModel();
		var param = probabilisticModel.getDistribution().getParams().get(0).getRepresentation();
		if (param instanceof SimpleParameter) {
			return retrieveSensitivityValueOf(property, (SimpleParameter) param).orElse(0.0);
		} else {
			// TODO logging
			return 0.0;
		}
	}

	@Override
	public void setMLSensitivityValues(Map<MLSensitivityEntry, Double> mlSensitivityValues) {
		var probabilisticModel = findMLRandomVariable().getDescriptiveModel();
		probabilisticModel.setDistribution(buildMLProbabilityOfSuccessDistribution(mlSensitivityValues));
	}

	@Override
	public double inferSensitivity(List<MeasurableProperty> properties) {
		if (isNull(bayesianNetwork)) {
			bayesianNetwork = new BayesianNetwork(null, probSensitivityModel);
		}

		var inputs = toInputValues(properties);
		if (isInferenceRequired(inputs)) {
			return bayesianNetwork.infer(inputs);
		}
		return bayesianNetwork.probability(inputs);

	}

	@Override
	public void saveAt(URI location) {
		if (nonNull(location.fileExtension())) {
			// TODO logging
			return;
		}

		saveTemplateVariables(location);
		saveDistributionFunctions(location);
		saveGroundProbabilisticModel(location);
	}

	@Override
	public ProbabilisticSensitivityModel deriveFrom(Set<PropertyMeasure> propertyMeasures) {
		var builder = ProbabilisticSensitivityModelBuilder.get();
		propertyMeasures.forEach(builder::addSensitivityFactor);
		var models = builder.build();

		ProbabilisticModelRepository groundNetworkRepo = getModelOfType(ProbabilisticModelRepository.class, models);
		TemplateVariableDefinitions templateVariables = getModelOfType(TemplateVariableDefinitions.class, models);
		return new ProbabilisticSensitivityModel(groundNetworkRepo.getModels().get(0), templateVariables);
	}

	@SuppressWarnings("unchecked")
	private <T> T getModelOfType(Class<T> modelType, List<EObject> models) {
		for (EObject each : models) {
			if (modelType.isInstance(each)) {
				return (T) each;
			}
		}
		return null;
	}

	private boolean isInferenceRequired(List<InputValue> inputs) {
		return (bayesianNetwork.getGroundVariables().size()) > inputs.size();
	}

	private List<InputValue> toInputValues(List<MeasurableProperty> properties) {
		var inputs = properties.stream().map(this::toInputValue).collect(toList());

		var mlVar = findMLRandomVariable();
		inputs.add(InputValue.create(CategoricalValue.create(outcomeMeasure.toString()), mlVar));

		return inputs;
	}

	private InputValue toInputValue(MeasurableProperty property) {
		var value = CategoricalValue.create(property.toString());
		var variable = findRandomVariableFor(property.getName());
		return InputValue.create(value, variable);
	}

	private Optional<Double> retrieveSensitivityValueOf(MeasurableProperty property, SimpleParameter param) {
		var values = param.getValue().split(DefaultParameterParser.SAMPLE_DELIMITER);
		return Stream.of(values).map(parseValues()).filter(withValue(property.toString()))
				.map(v -> Double.valueOf(v[1])).findFirst();
	}

	private Function<String, String[]> parseValues() {
		return value -> {
			return new StringBuilder(value)
					.deleteCharAt(0)
					.deleteCharAt(value.length() - 2)
					.toString()
					.split(DefaultParameterParser.PAIR_DELIMITER);
		};
	}

	private Predicate<String[]> withValue(String measuredValue) {
		return v -> v[0].equals(measuredValue);
	}

	private void saveTemplateVariables(URI location) {
		var adjustedLocation = location.appendSegment(TEMPLATE_MODEL_NAME).appendFileExtension(TEMPLATE_MODEL_EXT);
		save(templateVariables, adjustedLocation);
	}

	private void saveGroundProbabilisticModel(URI location) {
		var adjustedLocation = location.appendSegment(PROB_MODEL_NAME).appendFileExtension(PROB_MODEL_EXT);
		save(probSensitivityModel.eContainer(), adjustedLocation);
	}

	private void saveDistributionFunctions(URI location) {
		var adjustedLocation = location.appendSegment(DIST_MODEL_NAME).appendFileExtension(DIST_MODEL_EXT);
		var root = ProbabilityDistributionBuilder.mergeToSingleRepository(retrieveDistributions());
		save(root, adjustedLocation);
	}

	private void save(EObject root, URI adjustedLocation) {
		try {
			var resourceToSave = new ResourceSetImpl().createResource(adjustedLocation);
			resourceToSave.getContents().add(root);
			resourceToSave.save(Maps.newHashMap());
		} catch (IOException e) {
			MLSensitivityAnalysisException
					.throwWithMessageAndCause(String.format("Model %s could not be safed", adjustedLocation), e);
		}
	}
	
	private Set<ProbabilityDistribution> retrieveDistributions() {
		return probSensitivityModel.getLocalModels().stream().map(GroundProbabilisticModel::getDistribution)
				.collect(toSet());
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

	private ProbabilityDistribution buildMLProbabilityOfSuccessDistribution(
			Map<MLSensitivityEntry, Double> sensitivityValues) {
		var builder = ProbabilityDistributionBuilder.buildProbabilityDistributionForMLVariable()
				.withTabularParameterDerivedFrom(sensitivityValues);
		if (outcomeMeasure == MLOutcomeMeasure.CONFIDENCE) {
			builder.withConfidenceOutcomeMeasure();
		}
		return builder.build();
	}

}
