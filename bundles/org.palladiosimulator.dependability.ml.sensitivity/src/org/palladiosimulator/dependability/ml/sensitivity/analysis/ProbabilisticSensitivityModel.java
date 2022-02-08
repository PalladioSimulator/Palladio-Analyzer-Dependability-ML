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
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.SensitivityProperty;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.property.conversion.SensitivityPropertyConventions;
import org.palladiosimulator.envdyn.api.entity.bn.BayesianNetwork;
import org.palladiosimulator.envdyn.api.entity.bn.BayesianNetwork.InputValue;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticModel;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;
import org.palladiosimulator.envdyn.environment.staticmodel.ProbabilisticModelRepository;
import org.palladiosimulator.envdyn.environment.templatevariable.TemplateVariableDefinitions;

import com.google.common.collect.Maps;

import tools.mdsd.probdist.api.entity.CategoricalValue;
import tools.mdsd.probdist.api.entity.Conditionable;
import tools.mdsd.probdist.api.entity.ConditionalProbabilityDistribution;
import tools.mdsd.probdist.api.parser.DefaultParameterParser;
import tools.mdsd.probdist.distributionfunction.Domain;
import tools.mdsd.probdist.distributionfunction.ProbabilityDistribution;
import tools.mdsd.probdist.distributionfunction.SimpleParameter;
import tools.mdsd.probdist.distributionfunction.TabularCPD;

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
	public void setSensitivityValues(Map<SensitivityProperty, Double> sensitivityValues) {
		var propertyName = getGlobalProperty(sensitivityValues);
		var probabilisticModel = findRandomVariableFor(propertyName).getDescriptiveModel();
		probabilisticModel.setDistribution(buildProbabilityDistributionOf(sensitivityValues));
	}

	@Override
	public double getSensitivityValuesOf(SensitivityProperty property) {
		var probabilisticModel = findRandomVariableFor(property).getDescriptiveModel();
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
	public double inferSensitivity(List<SensitivityProperty> properties) {
		if (isNull(bayesianNetwork)) {
			bayesianNetwork = new BayesianNetwork(null, probSensitivityModel);
		}

		var inputs = toInputValues(properties);
		if (isInferenceRequired(inputs)) {
			return bayesianNetwork.infer(inputs); // the Method is not implemented yet.. 
		}
		return bayesianNetwork.probability(inputs);

	}
	
	@Override
	public double conditionalSensitivity(List<SensitivityProperty> properties) {
		var dist = findMLRandomVariable().getDescriptiveModel().getDistribution();
		var paramRep = (TabularCPD) dist.getParams().get(0).getRepresentation();
		var conditionalOutcomeDistribution = new ConditionalProbabilityDistribution(dist, paramRep);
		
		var conditionals = properties.stream()
				.map(each -> new Conditionable.Conditional(Domain.CATEGORY, each.getValue()))
				.collect(toList());
		var outcomeEvent = (CategoricalValue) mlInputValue().value;
		return conditionalOutcomeDistribution.given(conditionals).probability(outcomeEvent);
	}

	@Override
	public void saveAt(java.net.URI location) {
		var uri = URI.createURI(location.toString());

		if (nonNull(uri.fileExtension())) {
			// TODO logging
			return;
		}

		saveTemplateVariables(uri);
		saveDistributionFunctions(uri);
		saveGroundProbabilisticModel(uri);
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

	private List<InputValue> toInputValues(List<SensitivityProperty> properties) {
		var inputs = properties.stream()
				.map(this::toInputValue)
				.collect(toList());
		
		inputs.add(mlInputValue());
		
		return inputs;
	}

	private InputValue mlInputValue() {
		var value = CategoricalValue.create(outcomeMeasure.toString());
		var mlVariable = findMLRandomVariable();
		return InputValue.create(value, mlVariable);
	}

	private InputValue toInputValue(SensitivityProperty property) {
		var value = property.getValue();
		var variable = findRandomVariableFor(property);
		return InputValue.create(value, variable);
	}

	private Optional<Double> retrieveSensitivityValueOf(SensitivityProperty property, SimpleParameter param) {
		var values = param.getValue().split(DefaultParameterParser.SAMPLE_DELIMITER);
		return Stream.of(values)
				.map(parseValues())
				.filter(withValue(property.getValue()))
				.map(v -> Double.valueOf(v[1]))
				.findFirst();
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

	private Predicate<String[]> withValue(CategoricalValue measuredValue) {
		return v -> v[0].equals(measuredValue.toString());
	}

	private void saveTemplateVariables(URI location) {
		var adjustedLocation = location.appendSegment(TEMPLATE_MODEL_NAME)
				.appendFileExtension(TEMPLATE_MODEL_EXT);
		save(templateVariables, adjustedLocation);
	}

	private void saveGroundProbabilisticModel(URI location) {
		var adjustedLocation = location.appendSegment(PROB_MODEL_NAME)
				.appendFileExtension(PROB_MODEL_EXT);
		save(probSensitivityModel.eContainer(), adjustedLocation);
	}

	private void saveDistributionFunctions(URI location) {
		var adjustedLocation = location.appendSegment(DIST_MODEL_NAME)
				.appendFileExtension(DIST_MODEL_EXT);
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
		return probSensitivityModel.getLocalModels().stream()
				.map(GroundProbabilisticModel::getDistribution)
				.collect(toSet());
	}

	private SensitivityProperty getGlobalProperty(Map<SensitivityProperty, Double> sensitivityValues) {
		return sensitivityValues.keySet().iterator().next();
	}

	private GroundRandomVariable findRandomVariableFor(SensitivityProperty property) {
		return probSensitivityModel.getLocalProbabilisticModels().stream()
				.flatMap(each -> each.getGroundRandomVariables().stream())
				.filter(variableOf(property))
				.findFirst()
				.orElseThrow(MLSensitivityAnalysisException.supplierWithMessage(
						String.format("There is no variable for measured property %s", property.getId())));
	}

	public GroundRandomVariable findMLRandomVariable() {
		return probSensitivityModel.getLocalProbabilisticModels().stream()
				.flatMap(each -> each.getGroundRandomVariables().stream())
				.filter(variablesWithParents())
				.findFirst()
				.orElseThrow(MLSensitivityAnalysisException
						.supplierWithMessage("No random variable for ml prediction was found."));
	}

	// Assuming that the ML prediction random variable is the only one with parents.
	private Predicate<GroundRandomVariable> variablesWithParents() {
		return v -> v.getDependenceStructure().isEmpty() == false;
	}

	private Predicate<GroundRandomVariable> variableOf(SensitivityProperty property) {
		return v -> SensitivityPropertyConventions.areSemanticallyEqual(v, property);
	}

	private ProbabilityDistribution buildProbabilityDistributionOf(Map<SensitivityProperty, Double> sensitivityValues) {
		return ProbabilityDistributionBuilder.buildProbabilityDistributionFor(getGlobalProperty(sensitivityValues))
				.withSimpleParameterDerivedFrom(sensitivityValues)
				.build();
	}

	private ProbabilityDistribution buildMLProbabilityOfSuccessDistribution(
			Map<MLSensitivityEntry, Double> sensitivityValues) {
		return ProbabilityDistributionBuilder.buildProbabilityDistributionForMLVariable()
				.withTabularParameterDerivedFrom(sensitivityValues)
				.build();
	}

}
