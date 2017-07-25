/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.cl.ws.result.base;

import com.cl.ws.base.ClBaseResult;
import com.cl.ws.info.base.OperationJournalInfo;

/**
 * 
 * @Filename OperationJournalServiceResult.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 *
 */
public class OperationJournalServiceResult extends ClBaseResult {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 778793500429870690L;
	
	private OperationJournalInfo operationJournalInfo;
	
	public OperationJournalInfo getOperationJournalInfo() {
		return operationJournalInfo;
	}
	
	public void setOperationJournalInfo(OperationJournalInfo operationJournalInfo) {
		this.operationJournalInfo = operationJournalInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OperationJournalServiceResult [operationJournalInfo=");
		builder.append(operationJournalInfo);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
