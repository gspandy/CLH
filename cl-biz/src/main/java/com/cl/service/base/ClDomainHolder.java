package com.cl.service.base;

import com.cl.ws.base.Order;

import java.io.Serializable;

/**
 * @Filename EstateHolder.java
 *
 * @Description 独立线程持有
 *

 */
public class ClDomainHolder implements Serializable {

    private static final long						serialVersionUID	= -6099836956384599949L;

    private static ThreadLocal<ClDomainContext<?>>	contextLocal		= new ThreadLocal<ClDomainContext<?>>();

    public ClDomainHolder() {
    }

    /**
     * 获取上下文
     * @return Returns the ContractContext
     */
    @SuppressWarnings("unchecked")
    public static <T extends Order> ClDomainContext<T> get() {
        return (ClDomainContext<T>) contextLocal.get();
    }

    /**
     * 赋予上下文
     */
    public static <T extends Order> void set(ClDomainContext<T> context) {
        contextLocal.set(context);
    }

    /**
     * 清理充值上下文
     */
    public static void clear() {
        contextLocal.set(null);
    }
}
