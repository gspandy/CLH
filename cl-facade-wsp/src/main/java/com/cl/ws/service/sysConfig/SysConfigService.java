package com.cl.ws.service.sysConfig;

import com.cl.ws.base.ClBaseResult;
import com.cl.ws.info.sysConfig.SysConfigInfo;
import com.cl.ws.order.sysConfig.SysConfigOrder;

public interface SysConfigService {
    /**
     * 查询系统配置
     * @return
     */
    SysConfigInfo findSysConf();

    /**
     * 保存系统配置
     * @param order
     * @return
     */
    ClBaseResult saveSysConf(SysConfigOrder order);
}
