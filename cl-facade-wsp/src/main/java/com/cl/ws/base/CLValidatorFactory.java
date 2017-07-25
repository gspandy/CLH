package com.cl.ws.base;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 获取Validator，保证单例<br/>
 * <p>
 * You should usually not instantiate more than one factory; factory creation is
 * a costly process. Also, the factory also acts as a cache for the available
 * validation constraints.
 * </p>
 *
 * @Description
 *
 * @Version 1.0
 *
 */
public enum CLValidatorFactory {
    INSTANCE {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        @Override
        public Validator getValidator() {
            return factory.getValidator();
        }
    };
    public abstract Validator getValidator();
}
