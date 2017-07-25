package com.cl.service.exception;


/**
 * 
 * @Filename ExceptionFactory.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 */
public class ExceptionDomainFactory {
	
	public static ClDomainException newEstateDomainException(ClDomainResultEnum resultCode,
																	String errorMsg) {
		return new ClDomainException(resultCode, errorMsg);
	}
}
