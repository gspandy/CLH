package com.cl.dal.ibatis;

import com.cl.common.lang.DateUtil;
import com.cl.common.lang.util.StringUtil;
import com.cl.dal.daointerface.ExtraDAO;
import com.cl.dal.dataobject.SysSeqDO;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Filename IbatisExtraDAO.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 *
 */
public class IbatisExtraDAO extends SqlMapClientDaoSupport implements ExtraDAO {
	
	/**
	 * @return
	 */
	@Override
	public Date getSysdate() {
		return (Date) getSqlMapClientTemplate().queryForObject("MS-COMMON-GET-SYSDATE");
	}
	
	/**
	 * @param name
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public long insertDateSeq(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't insert a null data object into db.");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatDay(date));
		paramMap.put("rawAddTime", date);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-INSERT", paramMap);
	}
	
	@Override
	public long getNextDateSeq(String name, Date date, long cacheNumber) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't update a null data object into db.");
		}
		if (cacheNumber <= 0)
			cacheNumber = 1;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatDay(date));
		paramMap.put("cacheNumber", cacheNumber);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-UPDATE", paramMap);
	}
	
	@Override
	public boolean selectDateSeq(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatDay(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return false;
		}
		return true;
	}
	
	@Override
	public long getNextSeq(String name) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't insert a null data object into db.");
		}
		
		SysSeqDO sysSeqDO = new SysSeqDO();
		sysSeqDO.setName(name);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-SEQ-INSERT", sysSeqDO);
	}
	
}
