package com.cl.ws.base;

import java.io.Serializable;

/**
 *
 * @Filename Result.java
 *
 * @Description
 *
 * @Version 1.0
 *
 *
 */
public interface Result extends Serializable {

    public boolean isSuccess();

    public boolean isExecuted();

    public String getMessage();

}
