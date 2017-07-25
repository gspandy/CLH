package com.cl.service.base;


import com.cl.common.lang.util.ListUtil;
import com.cl.dal.daointerface.OperationJournalDAO;
import com.cl.dal.dataobject.OperationJournalDO;
import com.cl.ws.base.ClResultEnum;
import com.cl.ws.base.PageComponent;
import com.cl.ws.info.base.OperationJournalInfo;
import com.cl.ws.order.base.OperationJournalAddOrder;
import com.cl.ws.order.base.OperationJournalQueryOrder;
import com.cl.ws.result.base.OperationJournalServiceResult;
import com.cl.ws.result.base.QueryBaseBatchResult;
import com.cl.ws.service.base.OperationJournalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Filename OperationJournalServiceImpl.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 *
 */
@Service("operationJournalService")
public class OperationJournalServiceImpl extends BaseAutowiredDAOService implements
		OperationJournalService {
	private static final Logger logger = LoggerFactory.getLogger(OperationJournalServiceImpl.class);
	
	@Autowired
	OperationJournalDAO operationJournalDAO;
	
	/**
	 * @param operationJournalAddOrder
	 * @return
	 */
	@Override
	public OperationJournalServiceResult addOperationJournalInfo(	OperationJournalAddOrder operationJournalAddOrder) {
		logger.info("进入OperationJournalServiceImpl的addOperationJournalInfo方法!添加一条日志信息,入参: "
					+ operationJournalAddOrder);
		OperationJournalServiceResult operationJournalServiceResult = new OperationJournalServiceResult();
		try {
			//参数校验
			operationJournalAddOrder.check();
			// 参数设置
			OperationJournalDO operationJournalDO = new OperationJournalDO();
			OperationJournalConvertor.convert(operationJournalAddOrder, operationJournalDO);
			operationJournalDO.setRawAddTime(getSysdate());
			// 插入
			long identity = operationJournalDAO.insert(operationJournalDO);
			// 设置返回结果
			operationJournalDO.setIdentity(identity);
			OperationJournalInfo operationJournalInfo = new OperationJournalInfo();
			OperationJournalConvertor.convert(operationJournalDO, operationJournalInfo);
			operationJournalServiceResult.setOperationJournalInfo(operationJournalInfo);
			setResultInfo(operationJournalServiceResult, "添加一条操作日志信息成功!",
					ClResultEnum.EXECUTE_SUCCESS, true);
			logger.info("添加一条操作日志信息成功!resultCode=[ "
						+ operationJournalServiceResult.getCreditsysResultEnum() + " ]");
		} catch (IllegalArgumentException e) {
			setResultInfo(operationJournalServiceResult, e.getMessage(),
					ClResultEnum.INCOMPLETE_REQ_PARAM, false);
			logger.error("添加一条操作日志信息失败!返回结果:[" + operationJournalServiceResult + "]原因：{}",
				e.getMessage());
		} catch (DataAccessException e) {
			setResultInfo(operationJournalServiceResult,
					ClResultEnum.DATABASE_EXCEPTION.getMessage(),
					ClResultEnum.DATABASE_EXCEPTION, false);
			logger.error("添加一条操作日志信息失败!返回结果:[" + operationJournalServiceResult + "]原因：", e);
		} catch (Exception e) {
			setResultInfo(operationJournalServiceResult,
					ClResultEnum.UN_KNOWN_EXCEPTION.getMessage(),
					ClResultEnum.UN_KNOWN_EXCEPTION, false);
			logger.error("添加一条操作日志信息失败!返回结果:[" + operationJournalServiceResult + "]原因：", e);
		}
		return operationJournalServiceResult;
	}
	
	protected void setResultInfo(OperationJournalServiceResult operationJournalResultBase,
									String message, ClResultEnum resultCode, boolean success) {
		if (operationJournalResultBase != null) {
			operationJournalResultBase.setMessage(message);
			operationJournalResultBase.setCreditsysResultEnum(resultCode);
			operationJournalResultBase.setSuccess(success);
		}
	}
	
	@Override
	public QueryBaseBatchResult<OperationJournalInfo> queryOperationJournalInfo(OperationJournalQueryOrder operationJournalQueryOrder) {
		QueryBaseBatchResult<OperationJournalInfo> batchResult = new QueryBaseBatchResult<OperationJournalInfo>();
		try {
			operationJournalQueryOrder.check();
			List<OperationJournalInfo> pageList = new ArrayList<OperationJournalInfo>(
				(int) operationJournalQueryOrder.getPageSize());
			OperationJournalDO operationJournalDO = new OperationJournalDO();
			OperationJournalConvertor.convert(operationJournalQueryOrder, operationJournalDO);
			long totalCount = operationJournalDAO.queryOperationJournalPageListCount(
				operationJournalDO, operationJournalQueryOrder.getOperatorTimeStart(),
				operationJournalQueryOrder.getOperatorTimeEnd());
			PageComponent component = new PageComponent(operationJournalQueryOrder, totalCount);
			List<OperationJournalDO> recordList = operationJournalDAO
				.queryOperationJournalPageList(operationJournalDO,
					operationJournalQueryOrder.getOperatorTimeStart(),
					operationJournalQueryOrder.getOperatorTimeEnd(), component.getFirstRecord(),
					operationJournalQueryOrder.getPageSize());
			if (ListUtil.isNotEmpty(recordList)) {
				for (OperationJournalDO item : recordList) {
					OperationJournalInfo info = new OperationJournalInfo();
					OperationJournalConvertor.convert(item, info);
					pageList.add(info);
				}
			}
			batchResult.initPageParam(component);
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
		} catch (IllegalArgumentException e) {
			batchResult.setSuccess(false);
			batchResult.setCreditsysResultEnum(ClResultEnum.INCOMPLETE_REQ_PARAM);
			batchResult.setMessage(e.getMessage());
		} catch (Exception e) {
			batchResult.setSuccess(false);
			batchResult.setCreditsysResultEnum(ClResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return batchResult;
	}
}
