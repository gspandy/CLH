package com.cl.ws.base;


import javax.validation.ConstraintViolation;
import java.util.Set;

/*
 * 修订记录:
 * qzhanbo@yiji.com 2013-01-22 10:43 创建
 *
 * qzhanbo@yiji.com 2014-01-22 10:43 增加checkWithGroup
 *
 */
/**
 * 提供jsr303校验能力
 * @author qzhanbo@yiji.com
 */
public abstract class OrderBase implements Order {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 统一流水id
     */
    private String gid;

    public String getGid() {
        return gid;
    }


    public void setGid(String gid) {
        this.gid = gid;
    }





}
