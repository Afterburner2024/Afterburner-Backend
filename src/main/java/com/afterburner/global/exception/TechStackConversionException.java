package com.afterburner.global.exception;

public class TechStackConversionException extends RuntimeException {

	public TechStackConversionException(String message) {
		super(message);
	}

	public TechStackConversionException(String message, Throwable cause) {
		super(message, cause);
	}
}