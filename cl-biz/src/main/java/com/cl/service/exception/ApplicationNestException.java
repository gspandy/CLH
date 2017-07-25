/**
 * www.yiji.com Inc.
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.cl.service.exception;

/**
 * @Filename ApplicationNestException.java
 *
 * @Description 应用内部异常总接口,该异常子类，可以打印出详细的异常链路信息.
 *
 * @Version 1.0
 */
public class ApplicationNestException extends RuntimeException {

    private static final long serialVersionUID = -1369013612167105010L;

    public ApplicationNestException() {
    }

    public ApplicationNestException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public ApplicationNestException(String arg0) {
        super(arg0);
    }

    public ApplicationNestException(Throwable arg0) {
        super(arg0);
    }

    @Override
    public String getMessage() {
        Throwable cause = getCause();
        String msg = super.getMessage();
        if (cause != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getStackTrace()[0]).append("\t");
            if (msg != null) {
                sb.append(msg).append("; ");
            }
            sb.append("内联异常信息：").append(cause);
            return sb.toString();
        } else {
            return msg;
        }
    }
}
