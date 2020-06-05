package org.palladiosimulator.dependability.ml.sensitivity.builder;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

import java.util.Map;

import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityAggregations.MLSensitivityEntry;
import org.palladiosimulator.dependability.ml.sensitivity.analysis.SensitivityModel.MLOutcomeMeasure;
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
	private final static DistributionfunctionFactory FACTORY = DistributionfunctionFactory.eINSTANCE;

	private ProbabilityDistributionSkeleton skeleton = null;
	Map<MeasurableProperty, Double> propertySensitivityValues = null;
	Map<MLSensitivityEntry, Double> mlSensitivityValues = null;
	private boolean isConfidenceBased = false;
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
		this.propertySensitivityValues = sensitivityValues;
		return this;
	}

	public ProbabilityDistributionBuilder withTabularParameterDerivedFrom(
			Map<MLSensitivityEntry, Double> sensitivityValues) {
		this.mlSensitivityValues = sensitivityValues;
		return this;
	}

	public ProbabilityDistributionBuilder withConfidenceOutcomeMeasure() {
		this.isConfidenceBased = true;
		return this;
	}

	public ProbabilityDistribution build() {
		checkValidity();

		ParamRepresentation paramRep;
		if (isConfidenceBased) {
			paramRep = buildTabularParam();
		} else {
			paramRep = buildSimpleParam(parseToSampleSpace(propertySensitivityValues));
		}

		var defaultParameter = buildDefaultParameterWith(paramRep);
		return buildDistribution(defaultParameter);
	}

	private void checkValidity() {
		if (isConfidenceBased) {
			requireNonNull(mlSensitivityValues, "The ml sensitivity values must be specified.");
		} else {
			requireNonNull(propertySensitivityValues, "The property sensitivity values must be specified.");
		}
	}

	private TabularCPD buildTabularParam() {
		var tabularCPD = FACTORY.createTabularCPD();
		for (MLSensitivityEntry eachEntry : mlSensitivityValues.keySet()) {
			var tabularCPDEntry = FACTORY.createTabularCPDEntry();
			for (String eachSignatureComponent : eachEntry.getSignatureComponents()) {
				tabularCPDEntry.getConditonals().add(eachSignatureComponent);
			}

			String param;
			if (isConfidenceBased) {
				param = parseAsMLConfidenceParam(mlSensitivityValues.get(eachEntry));
			} else {
				param = parseAsProbOfSuccessParam(mlSensitivityValues.get(eachEntry));
			}

			tabularCPDEntry.setEntry(buildSimpleParam(param));
		}
		return tabularCPD;
	}

	private SimpleParameter buildSimpleParam(String stringRepresentation) {
		var param = FACTORY.createSimpleParameter();
		param.setType(ParameterType.SAMPLESPACE);
		param.setValue(stringRepresentation);
		return param;
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

	private String parseToSampleSpace(Map<MeasurableProperty, Double> sensitivityValues) {
		Map<String, String> sampleSpace = sensitivityValues.entrySet().stream()
				.collect(toMap(entry -> entry.getKey().getMeasuredValue().get(), entry -> entry.getValue().toString()));
		return parseToString(sampleSpace);
	}

	private String parseAsProbOfSuccessParam(Double probOfSuccess) {
		Map<String, String> sampleSpace = Maps.newHashMap();
		sampleSpace.put(MLOutcomeMeasure.SUCCESS.toString(), probOfSuccess.toString());
		sampleSpace.put(MLOutcomeMeasure.FAIL.toString(), Double.valueOf(1 - probOfSuccess).toString());
		return parseToString(sampleSpace);
	}

	private String parseAsMLConfidenceParam(Double confidence) {
		Map<String, String> sampleSpace = Maps.newHashMap();
		sampleSpace.put(MLOutcomeMeasure.CONFIDENCE.toString(), confidence.toString());
		return parseToString(sampleSpace);
	}

	private String parseToString(Map<String, String> sampleSpace) {
		var builder = new StringBuilder();
		for (String eachCategory : sampleSpace.keySet()) {
			builder.append(String.format("{%1s,%2s};", eachCategory, sampleSpace.get(eachCategory)));
		}

		builder.deleteCharAt(builder.length() - 1);

		return builder.toString();
	}

}
