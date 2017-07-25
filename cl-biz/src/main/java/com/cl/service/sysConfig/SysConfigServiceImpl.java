package com.cl.service.sysConfig;

import com.cl.common.lang.util.BeanCopier;
import com.cl.dal.daointerface.SysConfigDAO;
import com.cl.dal.dataobject.SysConfigDO;
import com.cl.service.base.BeforeProcessInvokeService;
import com.cl.service.base.ClDomainHolder;
import com.cl.service.base.ClTransServiceBase;
import com.cl.service.base.Domain;
import com.cl.ws.base.ClBaseResult;
import com.cl.ws.info.sysConfig.SysConfigInfo;
import com.cl.ws.order.sysConfig.SysConfigOrder;
import com.cl.ws.service.sysConfig.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ClTransServiceBase implements SysConfigService {
    @Autowired
    SysConfigDAO sysConfigDAO;
    @Override
    public SysConfigInfo findSysConf() {
        SysConfigDO conf = null;
        if (conf != null) {
            SysConfigInfo info = new SysConfigInfo();
            BeanCopier.staticCopy(conf, info);
            return info;
        }
        return null;
    }

    @Override
    public ClBaseResult saveSysConf(final SysConfigOrder order) {
        return commonProcess(order, "保存系统配置", new BeforeProcessInvokeService() {

            @Override
            public Domain before() {
                Date now = ClDomainHolder.get().getSysDate();
                SysConfigDO conf = null;
                if (conf == null)
                    conf = new SysConfigDO();
                BeanCopier.staticCopy(order, conf);
                if (conf.getId() == 0) {
                    conf.setRawAddTime(now);
                    sysConfigDAO.insert(conf);
                } else {
                    sysConfigDAO.update(conf);
                }
                return null;
            }
        }, null, null);
    }

    @Override
    protected ClBaseResult createResult() {
        return new ClBaseResult();
    }
}
