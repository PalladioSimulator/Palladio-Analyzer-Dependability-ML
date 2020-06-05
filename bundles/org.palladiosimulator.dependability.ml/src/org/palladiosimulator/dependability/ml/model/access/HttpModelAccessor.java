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
import java.util.List;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.nonNull;
import java.util.function.Function;

import org.palladiosimulator.dependability.ml.exception.DependableMLException;
import org.palladiosimulator.dependability.ml.model.InputData;
import org.palladiosimulator.dependability.ml.model.OutputData;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

public class HttpModelAccessor implements TrainedModelAccessor<InputData, OutputData> {

	private static int HTTP_OK = 200;
	private static final String PREDICTION_ROUTE = "/predict";
	private static final String SUPPORTED_SCHEME = "http";
	private static final int TIME_OUT_DURATION = 2;

	private URI remoteModelURI;
	
	private final HttpClient client;
	private final Function<String, List<OutputData>> jsonParser;

	public HttpModelAccessor(Function<String, List<OutputData>> jsonParser) {
		this.remoteModelURI = null;
		this.client = HttpClient.newHttpClient();
		this.jsonParser = nonNull(jsonParser) ? jsonParser : defaultJsonParser();
	}
	
	public HttpModelAccessor() {
		this(null);
	}
	
	private Function<String, List<OutputData>> defaultJsonParser() {
		return json -> {
			var outData = new Gson().fromJson(json, OutputData[].class);
			return Lists.newArrayList(outData);
		};
	}

	@Override
	public boolean canAccess(URI modelURI) {
		return modelURI.getScheme().equals(SUPPORTED_SCHEME);
	}

	@Override
	public void load(URI modelURI) {
		remoteModelURI = adjustQueryUri(modelURI);
	}

	@Override
	public List<OutputData> query(InputData inputData) {
		requireNonNull(remoteModelURI, "The model must be loaded first.");
		
		var request = HttpRequest.newBuilder(remoteModelURI)
				.header("Content-Type", "application/json")
				.timeout(Duration.ofMinutes(TIME_OUT_DURATION))
				.POST(BodyPublishers.ofString(jsonify(inputData)))
				.build();

		try {
			var response = client.send(request, BodyHandlers.ofString());
			if (response.statusCode() != HTTP_OK) {
				DependableMLException.throwWithMessage("Querying of remote model was not successfull.");
			}
			return readFromJson(response.body());
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

	private String jsonify(InputData inputData) {
		var jsonMessage = new StringBuilder();
		for (String each : inputData.getKeys()) {
			var value = inputData.getValueOf(each);
			jsonMessage.append(jsonify(each, value));
		}
		
		jsonMessage.deleteCharAt(jsonMessage.length() - 1);
		
		return jsonMessage.toString();
	}
	
	private String jsonify(String key, Object value) {
		if (value instanceof Number) {
			return String.format("{\"%1s\": %2s},", key, value);
		} else if (value instanceof File) {
			return String.format("{\"%1s\": \"%2s\"},", key, encode((File) value));
		} else {
			return String.format("{\"%1s\": \"%2s\"},", key, value);
		}
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
	
	private List<OutputData> readFromJson(String json) {
		return jsonParser.apply(json);
	}

}
