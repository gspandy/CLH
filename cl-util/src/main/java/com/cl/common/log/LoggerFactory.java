
package com.cl.common.log;

/**
 * 提供生成变长参数的logger工厂
 * 
 * @Filename LoggerFactory.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 */
public final class LoggerFactory {
	
	public static Logger getLogger(String name) {
		org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(name);
		return new LoggerImpl(logger);
	}
	
	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}
}
