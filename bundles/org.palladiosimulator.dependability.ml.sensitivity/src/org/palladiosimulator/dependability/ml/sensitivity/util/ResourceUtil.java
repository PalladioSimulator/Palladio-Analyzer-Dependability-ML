package org.palladiosimulator.dependability.ml.sensitivity.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

public class ResourceUtil {

	public final static String RESOURCE_PLATFORM_URI = "platform:/resource/";

	public static File asFile(String path) {
		return new File(formatPath(path));
	}

	public static java.net.URI asURI(String path) {
		if (isValidURL(path)) {
			return asURL(path).get();
		}
		return java.net.URI.create(formatPath(path));
	}

	private static Optional<java.net.URI> asURL(String path) {
		try {
			return Optional.of(new URL(path).toURI());
		} catch (MalformedURLException | URISyntaxException e) {
			return Optional.empty();
		}
	}

	private static boolean isValidURL(String path) {
		if (path.startsWith(RESOURCE_PLATFORM_URI)) {
			return false;
		}
		return asURL(path).isPresent();
	}

	private static String formatPath(String path) {
		if (path.startsWith(RESOURCE_PLATFORM_URI)) {
			var resolvedPath = new Path(URI.createURI(path).toPlatformString(true));
			var file = ResourcesPlugin.getWorkspace().getRoot().getFile(resolvedPath);
			path = file.getRawLocation().toString();
		} else {
			path = Path.fromOSString(path).toString();
		}
		return path;
	}

}
