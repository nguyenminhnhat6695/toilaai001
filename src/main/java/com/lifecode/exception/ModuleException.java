package com.lifecode.exception;

public class ModuleException extends Exception {

	private static final long serialVersionUID = -6595017418039191769L;

	public ModuleException(String message) {
		super(message);
	}

	public ModuleException(String message, Throwable cause) {
		super(message, cause);
	}
}
