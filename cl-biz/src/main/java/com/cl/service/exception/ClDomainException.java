/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.cl.service.exception;


/**
 * 
 * @Filename EstateDomainException.java
 * 
 * @Description 领域模型异常
 * 
 * @Version 1.0
 *
 */
public class ClDomainException extends ApplicationNestException {

	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -2527668261623906864L;

	private ClDomainResultEnum domainResult;

	private String errorMsg;

	/**
	 * 构建一个<code>EstateDomainException.java</code>
	 */
	public ClDomainException() {
		super();
	}

	/**
	 * 构建一个<code>EstateDomainException.java</code>
	 * @param domainResult
	 * @param errorMsg
	 */
	public ClDomainException(ClDomainResultEnum domainResult, String errorMsg) {
		super(errorMsg);
		this.domainResult = domainResult;
		this.errorMsg = errorMsg;
	}

	/**
	 * 构建一个<code>EstateDomainException.java</code>
	 * @param message
	 */
	public ClDomainException(String message) {
		super(message);
	}

	/**
	 * 构建一个<code>EstateDomainException.java</code>
	 * @param cause
	 */
	public ClDomainException(Throwable cause) {
		super(cause);
	}

	public ClDomainResultEnum getDomainResult() {
		return domainResult;
	}

	public void setDomainResult(ClDomainResultEnum domainResult) {
		this.domainResult = domainResult;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @return
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EstateDomainException [domainResult=");
		builder.append(domainResult);
		builder.append(", errorMsg=");
		builder.append(errorMsg);
		builder.append("]");
		return builder.toString();
	}

}
