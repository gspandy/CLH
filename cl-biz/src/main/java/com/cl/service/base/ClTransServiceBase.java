package com.cl.service.base;

import com.cl.dal.daointerface.ExtraDAO;
import com.cl.dal.daointerface.TestDAO;
import com.cl.service.exception.ClDomainException;
import com.cl.service.exception.CreditsysBizException;
import com.cl.service.security.ShiroSessionUtils;
import com.cl.service.session.SessionLocal;
import com.cl.ws.base.ClBaseResult;
import com.cl.ws.base.ClResultEnum;
import com.cl.service.exception.ExceptionFactory;
import com.cl.ws.base.Order;
import com.cl.ws.order.base.OperationJournalAddOrder;
import com.cl.ws.service.base.OperationJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;

public abstract class ClTransServiceBase extends ClServiceBase {
	
	@Autowired
	OperationJournalService operationJournalService;
	@Autowired
	protected ExtraDAO extraDAO;
	@Autowired
	protected TransactionTemplate transactionTemplate;
	@Autowired
	protected  TestDAO testDAO;
	protected static final String CONTEXT_RESULT_KEY = "result";
	
	protected void checkOrder(Order order) {
		logger.info("[order={}]", order);
		
		if (null == order) {
			throw ExceptionFactory.newClException(ClResultEnum.EXECUTE_FAILURE,
				"order must not be null");
		}
		
		try {
			order.check();
		} catch (IllegalArgumentException ex) {
			throw ExceptionFactory.newClException(ClResultEnum.INCOMPLETE_REQ_PARAM,
				"请求参数异常--" + ex.getLocalizedMessage());
			
		}
	}
	
	protected ClBaseResult commonProcess(final Order order,
										 final String processBizName,
										 final CheckBeforeProcessService checkBeforeProcessService,
										 final BeforeProcessInvokeService beforeProcessInvokeService,
										 final ProcessInvokeService processInvokeService,
										 final AfterProcessInvokeService successProcessInvokeService) {
		logger.info("-进入{} " + this.getClass().getName()
					+ "  commonProcess processBizName={} order={} ", processBizName, order);
		ClBaseResult result = createResult();
		final Date nowDate = getSysdate();
		boolean isClear = false;
		if (ClDomainHolder.get() == null) {
			ClDomainHolder.set(new ClDomainContext<Order>(nowDate, order, null));
			isClear = true;
		}
		try {
			
			checkOrder(order);
			if (checkBeforeProcessService != null)
				checkBeforeProcessService.check();
			result = transactionTemplate.execute(new TransactionCallback<ClBaseResult>() {
				
				@Override
				public ClBaseResult doInTransaction(TransactionStatus status) {
					ClBaseResult result = createResult();
					try {
						// 激活领域模型
						Domain domain = null;
						ClDomainHolder.get().addAttribute(CONTEXT_RESULT_KEY, result);
						if (beforeProcessInvokeService != null) {
							domain = beforeProcessInvokeService.before();
							logger.info("beforeProcessInvokeService.before():" + domain);
						}
						
						if (domain != null) {
							ClDomainHolder.get().setDomain(domain);
						}
						if (processInvokeService != null) {
							processInvokeService.process(domain);
							logger.info("processInvokeService.process():" + domain);
						}
						
						if (result.getCreditsysResultEnum() == ClResultEnum.UN_KNOWN_EXCEPTION) {
							result.setSuccess(true);
						}
						//addOperationJournalInfo(processBizName, processBizName, order.toString());
					} catch (CreditsysBizException eex) {
						setClException(status, result, eex, eex.getErrorMsg());
						
					} catch (ClDomainException e) {
						//setClDomainException(status, result, e, e.getErrorMsg());
					} catch (Exception e) {
						setDbException(status, result, e);
					} catch (Throwable e) {
						setDbException(status, result, e);
					}
					
					return result;
				}
			});
		} catch (CreditsysBizException eex) {
			logger.error(eex.getErrorMsg(), eex);
			result.setSuccess(false);
			result.setCreditsysResultEnum(eex.getResultCode());
			result.setMessage(processBizName + "异常[" + eex.getErrorMsg() + "]");
			
		} catch (Exception ex) {
			setUnknownException(result, ex);
		} catch (Throwable e) {
			setUnknownException(result, e);
		}
		if (result.isSuccess()) {
			try {
				if (successProcessInvokeService != null) {
					successProcessInvokeService.after(ClDomainHolder.get().getDomain());
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
			}
		}
		if (isClear) {
			ClDomainHolder.clear();
		}
		logger.info("-处理结束{}  commonProcess processBizName={} result={} ", this.getClass()
			.getName(), processBizName, result);
		return result;
	}
	
	protected ClBaseResult commonProcess(final Order order,
												final String processBizName,
												final BeforeProcessInvokeService beforeProcessInvokeService,
												final ProcessInvokeService processInvokeService,
												final AfterProcessInvokeService successProcessInvokeService) {
		return commonProcess(order, processBizName, null, beforeProcessInvokeService,
			processInvokeService, successProcessInvokeService);
	}
	
	protected ClBaseResult checkProcess(final Order order, final String processBizName,
												final ProcessInvokeService processInvokeService) {
		logger.info("-进入{} " + this.getClass().getName()
					+ "  commonProcess processBizName={} order={} ", processBizName, order);
		ClBaseResult result = null;
		final Date nowDate = getSysdate();
		ClDomainHolder.set(new ClDomainContext<Order>(nowDate, order, null));
		try {
			checkOrder(order);
			result = createResult();
			processInvokeService.process(null);
			result.setSuccess(true);
			return result;
		} catch (CreditsysBizException eex) {
			logger.error(eex.getLocalizedMessage(), eex);
			result.setSuccess(false);
			result.setCreditsysResultEnum(eex.getResultCode());
			result.setMessage(processBizName + "异常[" + eex.getErrorMsg() + "]");
		} catch (Exception ex) {
			setUnknownException(result, ex);
		} catch (Throwable e) {
			setUnknownException(result, e);
		}
		ClDomainHolder.clear();
		logger.info("-进入{} " + this.getClass().getName()
					+ "  commonProcess processBizName={} result={} ", processBizName, result);
		return result;
	}
	
	protected void addOperationJournalInfo(String permissionName, String operationContent,
											String memo) {
		try {
			OperationJournalAddOrder order = new OperationJournalAddOrder();
			order.setMemo(memo);
			SessionLocal local = ShiroSessionUtils.getSessionLocal();
			if (local == null || local.getUserId() == null) {
				order.setOperatorId(-1);
				order.setOperatorName("系统自动");
				order.setOperatorIp("127.0.0.1");
			} else {
				order.setOperatorId(local.getUserId());
				order.setOperatorName(local.getRealName());
				if (local.getRealName() == null) {
					order.setOperatorName(local.getUserName());
				}
				order.setOperatorIp(local.getRemoteAddr());
			}
			if (ClDomainHolder.get() != null) {
				OperationJournalAddOrder tempOrder = (OperationJournalAddOrder) ClDomainHolder
					.get().getAttribute("loginLogOrder");
				if (tempOrder != null) {
					order = tempOrder;
				}

			}
			order.setBaseModuleName("");
			order.setPermissionName(permissionName);
			order.setOperationContent(operationContent);

			operationJournalService.addOperationJournalInfo(order);
		} catch (Exception e) {
			logger.error("添加操作日志失败,失败原因：{}", e.getMessage(), e);
		}
	}
	

	protected ClBaseResult simpleSaveTemplate(String processBizName, String paramNames,
														ProcessInvokeService processInvokeService) {
		ClBaseResult baseResult = createResult();
		try {
			processInvokeService.process(null);
			baseResult.setSuccess(true);
			//addOperationJournalInfo(processBizName, processBizName, paramNames);
		} catch (CreditsysBizException eex) {
			setClException(null, baseResult, eex, eex.getErrorMsg());
			
		} catch (Exception e) {
			setUnknownException(baseResult, e);
		} catch (Throwable e) {
			setUnknownException(baseResult, e);
		}
		return baseResult;
	}
	
	protected abstract ClBaseResult createResult();

	/**
	 * @return Date
	 */
	protected Date getSysdate() {
		try {
			Date sysDate = extraDAO.getSysdate();
			logger.info("系统时间：sysDate=" + sysDate);
			return sysDate;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return new Date();
	}
}
