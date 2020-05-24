package com.lifecode.exception;

public class BusinessException extends ModuleException {

	private static final long serialVersionUID = -7591691603848908733L;
	
	private String errorCode;
	
	public BusinessException(String message) {
		super(message);
		this.errorCode = "-1";
	}

	public BusinessException(String message, Object errorCode) {
		super(message);
		this.errorCode = (String)errorCode;
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = "-1";
	}
	
	/**
	 * 오류코드
	 */
	public String getErrorCode() {
		return errorCode;
	}
}
