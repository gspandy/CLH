package com.cl.service.base;

import com.cl.common.log.Logger;
import com.cl.common.log.LoggerFactory;
import com.cl.service.exception.CreditsysBizException;
import com.cl.ws.base.ClBaseResult;
import com.cl.ws.base.ClResultEnum;
import com.cl.ws.base.Money;
import org.springframework.transaction.TransactionStatus;

public class ClServiceBase  {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected final static Money ZERO = Money.zero();
	
	protected void setClException(TransactionStatus status, ClBaseResult result,
								   CreditsysBizException eex, String errorMessage) {
		try {
			status.setRollbackOnly();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		result.setSuccess(false);
		result.setCreditsysResultEnum(eex.getResultCode());
		result.setMessage(errorMessage);
		logger
			.error(eex.getMessage() + " ==errMesaage=" + eex.getErrorMsg() + " result={}", result);
	}

	
	protected void setUnknownException(ClBaseResult result, Exception ex) {
		logger.error(ex.getMessage(), ex);
		result.setSuccess(false);
		result.setCreditsysResultEnum(ClResultEnum.UN_KNOWN_EXCEPTION);
	}
	
	protected void setUnknownException(ClBaseResult result, Throwable ex) {
		logger.error(ex.getMessage(), ex);
		result.setSuccess(false);
		result.setCreditsysResultEnum(ClResultEnum.UN_KNOWN_EXCEPTION);
	}
	
	protected void setDbException(TransactionStatus status, ClBaseResult result, Exception e) {
		logger.error(e.getMessage(), e);
		status.setRollbackOnly();
		result.setCreditsysResultEnum(ClResultEnum.DATABASE_EXCEPTION);
		result.setSuccess(false);
	}
	
	protected void setDbException(TransactionStatus status, ClBaseResult result, Throwable e) {
		logger.error(e.getMessage(), e);
		status.setRollbackOnly();
		result.setCreditsysResultEnum(ClResultEnum.DATABASE_EXCEPTION);
		result.setSuccess(false);
	}
}
