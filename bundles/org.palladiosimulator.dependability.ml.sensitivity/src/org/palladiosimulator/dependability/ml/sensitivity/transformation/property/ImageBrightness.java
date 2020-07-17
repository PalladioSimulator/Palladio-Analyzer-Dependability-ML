package org.palladiosimulator.dependability.ml.sensitivity.transformation.property;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.palladiosimulator.dependability.ml.model.InputData;
import org.palladiosimulator.dependability.ml.model.nn.ImageInputData;
import org.palladiosimulator.dependability.ml.sensitivity.exception.MLSensitivityAnalysisException;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.MeasurableProperty;
import org.palladiosimulator.dependability.ml.sensitivity.transformation.PropertyMeasure;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import tools.mdsd.probdist.api.entity.CategoricalValue;

public class ImageBrightness implements PropertyMeasure {

	private final static int HIGH_BRIGHTNESS_THRESHOLD = 175;
	private final static int MEDIUM_BRIGHTNESS_THRESHOLD = 75;

	private static enum BrightnessCategory {
		HIGH("High"), MEDIUM("Medium"), LOW("Low");

		private final MeasurableProperty value;

		private BrightnessCategory(String name) {
			this.value = new MeasurableProperty(PROPERTY_NAME, CategoricalValue.create(name));
		}

		public CategoricalValue getMeasuredValue() {
			return value.getMeasuredValue();
		}

		public MeasurableProperty asMeasurableProperty() {
			return value;
		}

		public static Set<CategoricalValue> getValueSpace() {
			return Sets.newHashSet(HIGH.getMeasuredValue(), MEDIUM.getMeasuredValue(), LOW.getMeasuredValue());
		}
	}

	private final static String PROPERTY_NAME = "Image brightness measure";

	@Override
	public MeasurableProperty apply(InputData inputData) {
		if (isApplicableTo(inputData) == false) {
			MLSensitivityAnalysisException.throwWithMessage("Input data is not applicable to propertymeasure");
		}

		return computeBrightness((ImageInputData) inputData);
	}

	@Override
	public Boolean isApplicableTo(InputData inputData) {
		return inputData instanceof ImageInputData;
	}

	@Override
	public Set<CategoricalValue> getValueSpace() {
		return BrightnessCategory.getValueSpace();
	}

	@Override
	public String getPropertyName() {
		return PROPERTY_NAME;
	}

	private MeasurableProperty computeBrightness(ImageInputData inputData) {
		var img = readImageToProcess(inputData);

		var grayscaleHist = computeGrayScaleHist(img);
		var meanGrayScale = computeMeanGrayScaleValue(grayscaleHist);
		if (isLowBrightness(meanGrayScale)) {
			return BrightnessCategory.LOW.asMeasurableProperty();
		} else if (isMediumBrightness(meanGrayScale)) {
			return BrightnessCategory.MEDIUM.asMeasurableProperty();
		} else {
			return BrightnessCategory.HIGH.asMeasurableProperty();
		}
	}

	private Map<Integer, Integer> computeGrayScaleHist(BufferedImage img) {
		Map<Integer, Integer> grayscaleHist = Maps.newHashMap();
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				var grayscale = computeGrayscaleValue(img.getRGB(x, y));
				grayscaleHist.merge(grayscale, 1, (v1, v2) -> v1 + v2);
			}
		}
		return grayscaleHist;
	}

	private int computeGrayscaleValue(int pixel) {
		var r = (pixel >> 16) & 0xff;
		var g = (pixel >> 8) & 0xff;
		var b = pixel & 0xff;
		return (int) ((0.33 * b) + (0.56 * g) + (0.11 * r));
	}

	private int computeMeanGrayScaleValue(Map<Integer, Integer> grayscaleHist) {
		var totalCount = grayscaleHist.values().stream().reduce((v1, v2) -> v1 + v2).get();

		var meanGrayScale = 0.0;
		for (int eachGrayscaleValue : grayscaleHist.keySet()) {
			var relativeGrayscaleCount = grayscaleHist.get(eachGrayscaleValue);
			var relativeFrequency = (double) relativeGrayscaleCount / totalCount;
			meanGrayScale += eachGrayscaleValue * relativeFrequency;
		}
		return (int) meanGrayScale;
	}

	private boolean isMediumBrightness(int meanGrayScale) {
		return Boolean.logicalAnd(meanGrayScale >= MEDIUM_BRIGHTNESS_THRESHOLD,
				meanGrayScale < HIGH_BRIGHTNESS_THRESHOLD);
	}

	private boolean isLowBrightness(int meanGrayScale) {
		return meanGrayScale < MEDIUM_BRIGHTNESS_THRESHOLD;
	}

	private BufferedImage readImageToProcess(ImageInputData inputData) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(inputData.getImage());
		} catch (IOException e) {
			MLSensitivityAnalysisException
					.throwWithMessageAndCause("Something rent wrong during brithness computation.", e);
		}
		return img;
	}

}
