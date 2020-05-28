package org.palladiosimulator.dependability.ml.model.access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import org.palladiosimulator.dependability.ml.exception.DependableMLException;
import org.palladiosimulator.dependability.ml.model.InputData;

public class HttpModelAccessor implements TrainedModelAccessor<InputData<?>, String> {

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
		remoteModelURI = modelURI;
	}

	@Override
	public String query(InputData<?> inputData) {
		var request = HttpRequest.newBuilder().uri(remoteModelURI).timeout(Duration.ofMinutes(TIME_OUT_DURATION))
				.POST(toBody(inputData)).build();

		try {
			return client.send(request, BodyHandlers.ofString()).body();
		} catch (IOException | InterruptedException e) {
			throw DependableMLException
					.supplierWithMessageAndCause("Something went wrong during communication with remote model.", e)
					.get();
		}
	}

	private BodyPublisher toBody(InputData<?> inputData) {
		var value = inputData.getValue();
		if (value instanceof String) {
			return BodyPublishers.ofString((String) value);
		} else if (value instanceof File) {
			try {
				return BodyPublishers.ofFile(((File) value).toPath());
			} catch (FileNotFoundException e) {
				DependableMLException.throwWithMessageAndCause("The input data file could not found.", e);
			}
		}

		throw DependableMLException.supplierWithMessage("The input data format is not supported.").get();
	}

}
