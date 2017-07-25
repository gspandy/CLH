
package com.cl.ws.base;

import java.io.Serializable;

/**
 *
 * 接口入参对象接口
 */
public interface Order extends Serializable {

    /**
     * 校验入参
     */
    public void check();

    /**
     * 获取统一流水号
     * @return 统一流水号
     */
    String getGid();

    /**
     * 设置统一流水号
     * @param gid 统一流水号
     */
    void setGid(String gid);
}
