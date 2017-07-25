package com.cl.dal.daointerface;

import org.springframework.dao.DataAccessException;

import java.util.Date;

/**
 * 
 * @Filename ExtraDAO.java
 * 
 * @Description 手动写的sql
 * 
 * @Version 1.0
 * 
 *
 */
public interface ExtraDAO {
	
	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public Date getSysdate();
	
	/**
	 * 获取Seq
	 * 
	 * @param name
	 * @return
	 * @throws DataAccessException
	 */
	public long getNextSeq(String name) throws DataAccessException;
	
	/**
	 * @param name
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	boolean selectDateSeq(String name, Date date) throws DataAccessException;
	
	/**
	 * @param name
	 * @param date
	 * @param cacheNumber
	 * @return
	 * @throws DataAccessException
	 */
	long getNextDateSeq(String name, Date date, long cacheNumber) throws DataAccessException;
	
	/**
	 * @param name
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	long insertDateSeq(String name, Date date) throws DataAccessException;
	
}
