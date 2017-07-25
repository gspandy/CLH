package com.cl.common.log;

/**
 * 提供变长参数的支持 <br/>
 * <h3>Usage Examples</h3>
 * 
 * <pre>
 *  private static final Logger	logger	= LoggerFactory.getLogger(AsyncMailTaskServiceImpl.class);
 *  
 *  //支持变长参数
 *  logger.info("a{}b{}c{}", "a", "b", "c", "d");
 *  //支持在输出异常时的变长参数支持，注意异常对象要放在最后
 *  logger.error("a{}.b{}.c{}", "hello", "a", ex); 
 *  </pre>
 * 
 * 
 * @Filename Logger.java
 * 
 * @Description
 * 
 * @Version 1.0
 *
 */
public interface Logger extends org.slf4j.Logger {
	/**
	 * 可以支持任意长度的参数，如果有要打印异常，请把异常对象放到最后
	 * @param format
	 * @param arg
	 * @see org.slf4j.Logger#trace(String, Object[])
	 */
	public void trace(String format, Object... arg);

	/**
	 * 可以支持任意长度的参数，如果有要打印异常，请把异常对象放到最后
	 * @param format
	 * @param arg
	 * @see org.slf4j.Logger#debug(String, Object[])
	 */
	public void debug(String format, Object... arg);

	/**
	 * 可以支持任意长度的参数，如果有要打印异常，请把异常对象放到最后
	 * @param format
	 * @param arg
	 * @see org.slf4j.Logger#info(String, Object[])
	 */
	public void info(String format, Object... arg);

	/**
	 * 可以支持任意长度的参数，如果有要打印异常，请把异常对象放到最后
	 * @param format
	 * @param arg
	 * @see org.slf4j.Logger#warn(String, Object[])
	 */
	public void warn(String format, Object... arg);

	/**
	 * 可以支持任意长度的参数，如果有要打印异常，请把异常对象放到最后
	 * @param format
	 * @param arg
	 * @see org.slf4j.Logger#error(String, Object[])
	 */
	public void error(String format, Object... arg);
}
