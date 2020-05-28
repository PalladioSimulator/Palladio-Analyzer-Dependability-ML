package org.palladiosimulator.dependability.ml.exception;

import com.google.common.base.Supplier;

public class DependableMLException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1828285516912518265L;

	private DependableMLException(String message) {
		super(message);
	}

	private DependableMLException(String message, Throwable cause) {
		super(message, cause);
	}

	public static void throwWithMessage(String message) {
		throw new DependableMLException(message);
	}

	public static void throwWithMessageAndCause(String message, Throwable cause) {
		throw new DependableMLException(message, cause);
	}

	public static Supplier<DependableMLException> supplierWithMessage(String message) {
		return () -> new DependableMLException(message);
	}

	public static Supplier<DependableMLException> supplierWithMessageAndCause(String message, Throwable cause) {
		return () -> new DependableMLException(message, cause);
	}

}
