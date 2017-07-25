package com.cl.ws.info.base;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.cl.ws.base.Money;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class BaseToStringInfo implements Serializable {
	
	private static final long serialVersionUID = -290471646591360603L;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	/**
	 * wan
	 * @param amount
	 * @return
	 */
	public static double getMoneyByw(Money amount) {
		if (amount == null)
			return 0;
		return (amount.getCent() / 1000000.00);
	}
	
	/**
	 * 
	 * 单位：万，保留两位小数
	 * 
	 * @param amount
	 * @return
	 */
	public static String getMoneyByw2(Money amount) {
		if (amount == null)
			return "0";
		double d = amount.getCent() / 1000000.00d;
		DecimalFormat format = new DecimalFormat("#0.00");
		return format.format(d);
	}
}
