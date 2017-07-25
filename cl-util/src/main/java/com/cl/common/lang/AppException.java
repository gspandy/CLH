package com.cl.common.lang;

/**
 * @Filename AppException.java
 * @Description
 * @Version 1.0

 */
public class AppException extends RuntimeException {
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	public AppException() {
		super();
	}
	
	public AppException(String message) {
		super(message);
	}
	
	public AppException(Throwable cause) {
		super(cause);
	}
	
	public AppException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AppException(String message, Throwable cause, boolean enableSuppression,
						boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
