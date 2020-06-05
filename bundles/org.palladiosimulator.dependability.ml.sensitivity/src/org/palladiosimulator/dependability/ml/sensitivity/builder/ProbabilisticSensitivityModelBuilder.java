package org.palladiosimulator.dependability.ml.sensitivity.builder;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticModel;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;
import org.palladiosimulator.envdyn.environment.staticmodel.ProbabilisticModelRepository;
import org.palladiosimulator.envdyn.environment.staticmodel.StaticmodelFactory;
import org.palladiosimulator.envdyn.environment.templatevariable.DependenceRelation;
import org.palladiosimulator.envdyn.environment.templatevariable.DependenceType;
import org.palladiosimulator.envdyn.environment.templatevariable.Relation;
import org.palladiosimulator.envdyn.environment.templatevariable.TemplateFactor;
import org.palladiosimulator.envdyn.environment.templatevariable.TemplateVariable;
import org.palladiosimulator.envdyn.environment.templatevariable.TemplateVariableDefinitions;
import org.palladiosimulator.envdyn.environment.templatevariable.TemplatevariableFactory;

import com.google.common.collect.Lists;

import tools.mdsd.probdist.distributiontype.ProbabilityDistributionSkeleton;
import tools.mdsd.probdist.model.basic.loader.BasicDistributionTypesLoader;

public class ProbabilisticSensitivityModelBuilder {

	private final static String MULTINOMIAL_DIST_SKELETON = "MultinomialDistribution";
	private final static String ML_TEMPLATE_NAME = "MLPredictionSensitivityTemplate";
	private final static String ML_VARIABLE_NAME = "MLPredictionSensitivity";
	private final static String ML_FACTOR_NAME = "MLPredictionSensitivityFactor";
	private final static String FACTOR_SUFFIX = "Factor";
	private final static String VARIABLE_NAME_SUFFIX = "Sensitivity";
	private final static String ML_NETWORK_NAME = "MLPredictionSensitivityModel";
	private final static String INSTANTIATION_FACTOR_SUFFIX = "Model";

	private final TemplatevariableFactory templateFactory = TemplatevariableFactory.eINSTANCE;
	private final StaticmodelFactory staticModelFactory = StaticmodelFactory.eINSTANCE;

	private final ProbabilisticModelRepository networkRepo;
	private final TemplateVariableDefinitions templateDefinitions;

	private TemplateVariable mlTemplate = null;
	private GroundRandomVariable mlVariable = null;

	private ProbabilisticSensitivityModelBuilder() {
		this.networkRepo = staticModelFactory.createProbabilisticModelRepository();
		this.templateDefinitions = templateFactory.createTemplateVariableDefinitions();

		buildInitialStructure();
	}
	
	public static ProbabilisticSensitivityModelBuilder get() {
		return new ProbabilisticSensitivityModelBuilder();
	}

	public ProbabilisticSensitivityModelBuilder addSensitivityFactor(PropertyMeasure propertyMeasure) {
		var propertyName = propertyMeasure.getPropertyName();

		var template = buildTemplateWith(propertyName);
		addToTemplateDefinitions(template);
		adjustMLFactorScope(template);

		var factor = buildTemplateFactor(template, propertyName.concat(FACTOR_SUFFIX));
		addToTemplateDefinitions(factor);

		var dependencyRelation = buildDependencyRelationToMLTemplate(template);
		addToTemplateDefinitions(dependencyRelation);
		adjustMLDependencyStructure(dependencyRelation);

		var groundVariable = instantiateTemplate(template, propertyName.concat(VARIABLE_NAME_SUFFIX));
		addToGroundNetwork(groundVariable);

		var instantiatedFactor = instantiateFactor(factor, propertyName.concat(INSTANTIATION_FACTOR_SUFFIX));
		addToGroundNetwork(instantiatedFactor);

		groundVariable.setDescriptiveModel(instantiatedFactor);
		
		return this;
	}

	public List<EObject> build() {
		return Lists.newArrayList(networkRepo, templateDefinitions);
	}

	private DependenceRelation buildDependencyRelationToMLTemplate(TemplateVariable source) {
		var dependencyRelation = templateFactory.createDependenceRelation();
		dependencyRelation.setType(DependenceType.DIRECTED);
		dependencyRelation.setSource(source);
		dependencyRelation.setTarget(mlTemplate);
		return dependencyRelation;
	}

	private void adjustMLFactorScope(TemplateVariable template) {
		for (TemplateFactor each : templateDefinitions.getFactors()) {
			if (each.getScope().contains(mlTemplate)) {
				each.getScope().add(template);
				return;
			}
		}

		MLSensitivityAnalysisException.throwWithMessage("There is no ML variable template.");
	}

	private void adjustMLDependencyStructure(DependenceRelation relation) {
		mlVariable.getDependenceStructure().add(relation);
	}

	private void buildInitialStructure() {
		mlTemplate = buildMLTemplate();
		addToTemplateDefinitions(mlTemplate);

		var mlFactor = buildMLFactor(mlTemplate);
		addToTemplateDefinitions(mlFactor);

		buildAndAddGroundProbabilisticNetwork();

		mlVariable = instantiateTemplate(mlTemplate, ML_VARIABLE_NAME);
		addToGroundNetwork(mlVariable);

		var mlInstantiatedFactor = instantiateFactor(mlFactor, ML_NETWORK_NAME);
		addToGroundNetwork(mlInstantiatedFactor);

		mlVariable.setDescriptiveModel(mlInstantiatedFactor);
	}

	private GroundProbabilisticModel instantiateFactor(TemplateFactor mlFactor, String name) {
		var groundModel = staticModelFactory.createGroundProbabilisticModel();
		groundModel.setEntityName(name);
		groundModel.setInstantiatedFactor(mlFactor);
		return groundModel;
	}

	private TemplateVariable buildMLTemplate() {
		return buildTemplateWith(ML_TEMPLATE_NAME);
	}

	private TemplateVariable buildTemplateWith(String name) {
		var mlTemplate = templateFactory.createTemplateVariable();
		mlTemplate.setEntityName(name);
		return mlTemplate;
	}

	private TemplateFactor buildMLFactor(TemplateVariable mlTemplate) {
		return buildTemplateFactor(mlTemplate, ML_FACTOR_NAME);
	}

	private TemplateFactor buildTemplateFactor(TemplateVariable mlTemplate, String name) {
		var mlFactor = templateFactory.createProbabilisticTemplateFactor();
		mlFactor.setDistributionFamily(multinomialDistributionFamily());
		mlFactor.setEntityName(name);
		mlFactor.getScope().add(mlTemplate);
		return mlFactor;
	}

	private void addToTemplateDefinitions(TemplateVariable template) {
		templateDefinitions.getVariables().add(template);
	}

	private void addToTemplateDefinitions(TemplateFactor factor) {
		templateDefinitions.getFactors().add(factor);
	}

	private void addToTemplateDefinitions(Relation relation) {
		templateDefinitions.getRelation().add(relation);
	}

	private void buildAndAddGroundProbabilisticNetwork() {
		var groundNetwork = staticModelFactory.createGroundProbabilisticNetwork();
		groundNetwork.setEntityName(ML_NETWORK_NAME);

		networkRepo.getModels().add(groundNetwork);
	}

	private GroundRandomVariable instantiateTemplate(TemplateVariable template, String name) {
		var randomVariable = staticModelFactory.createGroundRandomVariable();
		randomVariable.setEntityName(name);
		randomVariable.setInstantiatedTemplate(template);
		return randomVariable;
	}

	private ProbabilityDistributionSkeleton multinomialDistributionFamily() {
		return BasicDistributionTypesLoader.loadRepository().getDistributionFamilies().stream()
				.filter(each -> each.getEntityName().equals(MULTINOMIAL_DIST_SKELETON)).findFirst().get();
	}

	private void addToGroundNetwork(GroundRandomVariable groundVariable) {
		var localModel = staticModelFactory.createLocalProbabilisticNetwork();
		localModel.getGroundRandomVariables().add(groundVariable);

		networkRepo.getModels().get(0).getLocalProbabilisticModels().add(localModel);
	}

	private void addToGroundNetwork(GroundProbabilisticModel instantiatedFactor) {
		networkRepo.getModels().get(0).getLocalModels().add(instantiatedFactor);
	}

}
