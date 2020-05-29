package org.palladiosimulator.dependability.ml.model.access;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Base64;

import org.palladiosimulator.dependability.ml.exception.DependableMLException;
import org.palladiosimulator.dependability.ml.model.InputData;

public class HttpModelAccessor implements TrainedModelAccessor<InputData<?>, String> {

	private static final String PREDICTION_ROUTE = "/predict";
	private static final String SUPPORTED_SCHEME = "http";
	private static final int TIME_OUT_DURATION = 2;

	private HttpClient client = null;
	private URI remoteModelURI = null;

	@Override
	public boolean canAccess(URI modelURI) {
		return modelURI.getScheme().equals(SUPPORTED_SCHEME);
	}

	@Override
	public void load(URI modelURI) {
		client = HttpClient.newHttpClient();
		remoteModelURI = adjustQueryUri(modelURI);
	}

	@Override
	public String query(InputData<?> inputData) {
		var request = HttpRequest.newBuilder(remoteModelURI)
				.header("Content-Type", "application/json")
				.timeout(Duration.ofMinutes(TIME_OUT_DURATION))
				.POST(BodyPublishers.ofString(prepareForSending(inputData)))
				.build();

		try {
			return client.send(request, BodyHandlers.ofString()).body();
		} catch (IOException | InterruptedException e) {
			throw DependableMLException
					.supplierWithMessageAndCause("Something went wrong during communication with remote model.", e)
					.get();
		}
	}

	private URI adjustQueryUri(URI modelURI) {
		var adjustedUri = modelURI.toString().concat(PREDICTION_ROUTE);
		try {
			return new URI(adjustedUri);
		} catch (URISyntaxException e) {
			throw DependableMLException.supplierWithMessageAndCause(
					String.format("The syntax of the adjusted uri %s is not valid.", adjustedUri), e).get();
		}
	}

	private String prepareForSending(InputData<?> inputData) {
		var body = "";
		var value = inputData.getValue();

		if (value instanceof String) {
			body = (String) value;
		} else if (value instanceof File) {
			body = encode((File) value);
		} else {
			DependableMLException.throwWithMessage("The input data format is not supported.");
		}

		return jsonify(body);
	}

	private String encode(File fileToEncode) {
		String base64Image = "";
		try (FileInputStream imageInFile = new FileInputStream(fileToEncode)) {
			byte imageData[] = new byte[(int) fileToEncode.length()];
			imageInFile.read(imageData);
			base64Image = Base64.getEncoder().encodeToString(imageData);
		} catch (IOException e) {
			DependableMLException.throwWithMessageAndCause("Something went wrong during file encoding.", e);
		}
		return base64Image;
	}

	private String jsonify(String value) {
		return String.format("{\"value\": %s}", value);
	}

}
