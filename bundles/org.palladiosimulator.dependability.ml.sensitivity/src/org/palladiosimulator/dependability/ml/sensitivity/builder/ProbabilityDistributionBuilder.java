package org.palladiosimulator.dependability.ml.sensitivity.builder;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

import java.util.Map;

import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityAggregations.SensitivityEntry;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.MeasurableProperty;

import com.google.common.collect.Maps;

import tools.mdsd.probdist.distributionfunction.DistributionfunctionFactory;
import tools.mdsd.probdist.distributionfunction.ParamRepresentation;
import tools.mdsd.probdist.distributionfunction.Parameter;
import tools.mdsd.probdist.distributionfunction.ParameterType;
import tools.mdsd.probdist.distributionfunction.ProbabilityDistribution;
import tools.mdsd.probdist.distributionfunction.SimpleParameter;
import tools.mdsd.probdist.distributionfunction.TabularCPD;
import tools.mdsd.probdist.distributiontype.ParameterSignature;
import tools.mdsd.probdist.distributiontype.ProbabilityDistributionSkeleton;
import tools.mdsd.probdist.model.basic.loader.BasicDistributionTypesLoader;

public class ProbabilityDistributionBuilder {

	private final static String MULTINOMIAL_DIST_SKELETON = "MultinomialDistribution";
	private final static String DIST_NAME_SUFFIX = "SensitivityDistribution";
	private final static String ML_DIST_NAME = "MLPrediction";
	private final static String ML_SUCCESS = "Success";
	private final static String ML_FAIL = "Fail";
	private final static DistributionfunctionFactory FACTORY = DistributionfunctionFactory.eINSTANCE;

	private ProbabilityDistributionSkeleton skeleton = null;
	private SimpleParameter simpleParam = null;
	private TabularCPD tabularParam = null;
	private String name = null;

	private ProbabilityDistributionBuilder(String name) {
		this.name = new StringBuilder(name).append(DIST_NAME_SUFFIX).toString();
		this.skeleton = BasicDistributionTypesLoader.loadRepository().getDistributionFamilies().stream()
				.filter(each -> each.getEntityName().equals(MULTINOMIAL_DIST_SKELETON)).findFirst().get();
	}

	public static ProbabilityDistributionBuilder buildProbabilityDistributionFor(MeasurableProperty property) {
		return new ProbabilityDistributionBuilder(property.getName());
	}

	public static ProbabilityDistributionBuilder buildProbabilityDistributionForMLVariable() {
		return new ProbabilityDistributionBuilder(ML_DIST_NAME);
	}

	public ProbabilityDistributionBuilder withSimpleParameterDerivedFrom(
			Map<MeasurableProperty, Double> sensitivityValues) {
		this.simpleParam = buildSimpleParam(parseToSampleSpace(sensitivityValues));
		return this;
	}

	public ProbabilityDistributionBuilder withTabularParameterDerivedFrom(
			Map<SensitivityEntry, Double> sensitivityValues) {
		this.tabularParam = buildTabularParam(sensitivityValues);
		return this;
	}

	private TabularCPD buildTabularParam(Map<SensitivityEntry, Double> sensitivityValues) {
		var tabularCPD = FACTORY.createTabularCPD();
		for (SensitivityEntry eachEntry : sensitivityValues.keySet()) {
			var tabularCPDEntry = FACTORY.createTabularCPDEntry();
			for (String eachSignatureComponent : eachEntry.getSignatureComponents()) {
				tabularCPDEntry.getConditonals().add(eachSignatureComponent);
			}

			var probOfSuccess = sensitivityValues.get(eachEntry);
			var simpleParam = buildSimpleParam(parseToSampleSpace(probOfSuccess));
			tabularCPDEntry.setEntry(simpleParam);
		}
		return tabularCPD;
	}

	public ProbabilityDistribution build() {
		var defaultParameter = buildDefaultParameterWith(getParamRepresentation());
		return buildDistribution(defaultParameter);
	}

	private ParamRepresentation getParamRepresentation() {
		if (isNull(tabularParam)) {
			requireNonNull(simpleParam, "There is no parameter representation specification.");
			return simpleParam;
		} else {
			requireNonNull(tabularParam, "There is no parameter representation specification.");
			return tabularParam;
		}
	}
	
	private ProbabilityDistribution buildDistribution(Parameter defaultParameter) {
		var distribution = FACTORY.createProbabilityDistribution();
		distribution.setInstantiated(skeleton);
		distribution.setEntityName(name);
		distribution.getParams().add(defaultParameter);
		return distribution;
	}
	
	private Parameter buildDefaultParameterWith(ParamRepresentation paramRep) {
		var defaultParameter = FACTORY.createParameter();
		defaultParameter.setInstantiated(getEventProbabilityStructure());
		defaultParameter.setRepresentation(paramRep);
		return defaultParameter;
	}

	private ParameterSignature getEventProbabilityStructure() {
		return skeleton.getParamStructures().get(0);
	}

	private SimpleParameter buildSimpleParam(String stringRepresentation) {
		var param = FACTORY.createSimpleParameter();
		param.setType(ParameterType.SAMPLESPACE);
		param.setValue(stringRepresentation);
		return param;
	}

	private String parseToSampleSpace(Map<MeasurableProperty, Double> sensitivityValues) {
		Map<String, String> sampleSpace = sensitivityValues.entrySet().stream()
				.collect(toMap(entry -> entry.getKey().getName(), entry -> entry.getValue().toString()));
		return parseToString(sampleSpace);
	}

	private String parseToSampleSpace(Double probOfSuccess) {
		Map<String, String> sampleSpace = Maps.newHashMap();
		sampleSpace.put(ML_SUCCESS, probOfSuccess.toString());
		sampleSpace.put(ML_FAIL, Double.valueOf(1 - probOfSuccess).toString());
		return parseToString(sampleSpace);
	}

	private String parseToString(Map<String, String> sampleSpace) {
		var builder = new StringBuilder();
		for (String eachCategory : sampleSpace.keySet()) {
			builder.append(String.format("{%1s,%2s};", eachCategory, sampleSpace.get(eachCategory)));
		}
		var parsedString = builder.toString();
		return parsedString.substring(0, parsedString.length() - 2);
	}

}
