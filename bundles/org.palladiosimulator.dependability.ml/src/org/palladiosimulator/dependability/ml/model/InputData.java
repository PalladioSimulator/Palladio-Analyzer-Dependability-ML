package org.palladiosimulator.dependability.ml.model;

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.palladiosimulator.dependability.ml.exception.DependableMLException;

public class InputData {

	private final Map<String, Object> values;

	@SafeVarargs
	public InputData(Map.Entry<String, Object>... values) {
		this.values = Stream.of(values).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public Set<String> getKeys() {
		return values.keySet();
	}
	
	public Object getValueOf(String key) {
		return Optional.ofNullable(values.get(key)).orElseThrow(
				DependableMLException.supplierWithMessage(String.format("There is no element for key %s", key)));
	}

}
