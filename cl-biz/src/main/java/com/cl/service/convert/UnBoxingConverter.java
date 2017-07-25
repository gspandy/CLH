package com.cl.service.convert;


import com.cl.ws.base.Money;
import net.sf.cglib.core.Converter;

public class UnBoxingConverter implements Converter {
    public static UnBoxingConverter getInstance() {
        return new UnBoxingConverter();
    }
    @SuppressWarnings("rawtypes")
    @Override
    public Object convert(Object value, Class target, Object context) {
        String tagetName = target.getName();
        if ("byte".equals(tagetName)) {
            tagetName = "java.lang.Byte";
        } else if ("short".equals(tagetName)) {
            tagetName = "java.lang.Short";
        } else if ("int".equals(tagetName)) {
            tagetName = "java.lang.Integer";
        } else if ("long".equals(tagetName)) {
            tagetName = "java.lang.Long";
        } else if ("float".equals(tagetName)) {
            tagetName = "java.lang.Float";
        } else if ("double".equals(tagetName)) {
            tagetName = "java.lang.Double";
        } else if ("boolean".equals(tagetName)) {
            tagetName = "java.lang.Boolean";
        } else if ("char".equals(tagetName)) {
            tagetName = "java.lang.Character";
        }
        if (value == null) {
            return null;
        } else if (tagetName.equals(value.getClass().getName())) {
            return value;
        } else if (!(value instanceof Money)
                && "com.cl.ws.base.Money".equals(tagetName)) {
            return new Money(0, 0);
        } else {
            return null;
        }
    }
}
