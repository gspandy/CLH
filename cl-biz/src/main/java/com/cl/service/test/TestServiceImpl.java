package com.cl.service.test;


import com.cl.common.lang.util.BeanCopier;
import com.cl.dal.dataobject.TestDO;
import com.cl.service.base.BeforeProcessInvokeService;
import com.cl.service.base.ClDomainHolder;
import com.cl.service.base.ClTransServiceBase;
import com.cl.service.base.Domain;
import com.cl.service.convert.UnBoxingConverter;
import com.cl.ws.base.ClBaseResult;
import com.cl.ws.order.test.TestOrder;
import com.cl.ws.service.test.TestService;
import org.springframework.stereotype.Service;


import java.util.Date;

@Service("testService")
public class TestServiceImpl extends ClTransServiceBase implements TestService {
    @Override
    public ClBaseResult add(final TestOrder order) {
        return commonProcess(order, "测试一下", new BeforeProcessInvokeService() {
            @Override
            public Domain before() {
                final Date now= ClDomainHolder.get().getSysDate();
                TestDO testDO=new TestDO();
                BeanCopier.staticCopy(order,testDO, UnBoxingConverter.getInstance());
                testDO.setRawAddTime(now);
                testDAO.insert(testDO);
                return null;
            }
        },null,null);
    }

    @Override
    protected ClBaseResult createResult() {
        return new ClBaseResult();
    }
}
