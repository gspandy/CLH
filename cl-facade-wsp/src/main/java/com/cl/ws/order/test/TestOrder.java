package com.cl.ws.order.test;

import com.cl.ws.order.base.ProcessOrder;

import java.util.Date;


public class TestOrder extends ProcessOrder {

    private long id;

    private String name;

    private Date rawAddTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }
}
