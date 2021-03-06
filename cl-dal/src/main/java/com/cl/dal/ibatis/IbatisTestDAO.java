
package com.cl.dal.ibatis;

import com.cl.dal.dataobject.TestDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.cl.dal.daointerface.TestDAO;


// auto generated imports
import org.springframework.dao.DataAccessException;

/**
 * An ibatis based implementation of dao interface <tt>com.cl.dal.daointerface.TestDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/test.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 

public class IbatisTestDAO extends SqlMapClientDaoSupport implements TestDAO {
	/**
	 *  Insert one <tt>TestDO</tt> object to DB table <tt>test</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into test(id,name,raw_add_time) values (?, ?, ?)</tt>
	 *
	 *	@param test
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(TestDO test) throws DataAccessException {
    	if (test == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-TEST-INSERT", test);

        return test.getId();
    }

}