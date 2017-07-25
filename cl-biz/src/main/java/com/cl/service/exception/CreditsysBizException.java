package com.cl.service.exception;

import com.cl.ws.base.ClResultEnum;

/**
 * 
 * @Filename EstateException.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 */
public class CreditsysBizException extends ApplicationNestException {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 554229467642044021L;
	
	private ClResultEnum resultCode;
	
	private String errorMsg;
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 */
	public CreditsysBizException() {
		super();
	}
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 * @param arg0
	 * @param arg1
	 */
	public CreditsysBizException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 * @param arg0
	 */
	public CreditsysBizException(String arg0) {
		super(arg0);
	}
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 * @param arg0
	 */
	public CreditsysBizException(Throwable arg0) {
		super(arg0);
	}
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 * @param resultCode
	 * @param errorMsg
	 */
	public CreditsysBizException(ClResultEnum resultCode, String errorMsg) {
		super(errorMsg);
		this.resultCode = resultCode;
		this.errorMsg = errorMsg;
	}
	
	public ClResultEnum getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(ClResultEnum resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EstateException [resultCode=");
		builder.append(resultCode);
		builder.append(", errorMsg=");
		builder.append(errorMsg);
		builder.append("]");
		return builder.toString();
	}
	
}
