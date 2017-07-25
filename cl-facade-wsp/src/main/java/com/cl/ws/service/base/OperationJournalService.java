package com.cl.ws.service.base;


import com.cl.ws.info.base.OperationJournalInfo;
import com.cl.ws.order.base.OperationJournalAddOrder;
import com.cl.ws.order.base.OperationJournalQueryOrder;
import com.cl.ws.result.base.OperationJournalServiceResult;
import com.cl.ws.result.base.QueryBaseBatchResult;

import javax.jws.WebService;

/**
 * 
 * @Filename OperationJournalService.java
 * 
 * @Description
 * 
 * @Version 1.0
 *
 */
@WebService
public interface OperationJournalService {
	
	/**
	 * 添加一条操作日志
	 * @param operationJournalAddOrder
	 * @return
	 */
	OperationJournalServiceResult addOperationJournalInfo(OperationJournalAddOrder operationJournalAddOrder);

    QueryBaseBatchResult<OperationJournalInfo> queryOperationJournalInfo(OperationJournalQueryOrder queryOrder);
	
}
