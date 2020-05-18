package org.palladiosimulator.dependability.ml.sensitivity.exception;

public class MLSensitivityAnalysisException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6621095744843509202L;

	private MLSensitivityAnalysisException(String message) {
		super(message);
	}

	private MLSensitivityAnalysisException(String message, Throwable cause) {
		super(message, cause);
	}

	public static void throwWithMessage(String message) {
		throw new MLSensitivityAnalysisException(message);
	}

	public static void throwWithMessageAndCause(String message, Throwable cause) {
		throw new MLSensitivityAnalysisException(message, cause);
	}

}
