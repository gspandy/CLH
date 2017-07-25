package com.cl.service.base;

import com.cl.common.log.Logger;
import com.cl.common.log.LoggerFactory;
import com.cl.dal.daointerface.ExtraDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 
 * @Filename BaseAutowiredDAOService.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 */
public class BaseAutowiredDAOService  {
	@Autowired
	ExtraDAO extraDAO;
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
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
