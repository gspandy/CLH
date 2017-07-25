package com.cl.ws.base;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;

/**
 * 
 * @Filename MoneyTypeHandlerCallback.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 

 * 
 */
public class MoneyTypeHandlerCallback implements TypeHandlerCallback {

	@Override
	public void setParameter(ParameterSetter setter, Object parameter)
			throws SQLException {
		setter.setLong(((Money) parameter).getCent());
	}

	@Override
	public Object getResult(ResultGetter getter) throws SQLException {
		Money money = new Money();
		money.setCent(Long.valueOf(getter.getLong()));
		return money;
	}

	@Override
	public Object valueOf(String s) {
		return null;
	}

}
