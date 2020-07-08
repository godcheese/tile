package com.godcheese.tile.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class ObjectUtil {

    public static Map<String, Object> toMap(Object o) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(o.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String key = propertyDescriptor.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = propertyDescriptor.getReadMethod();
            Object value = getter != null ? getter.invoke(o) : null;
            map.put(key, value);
        }
        return map;
    }
}
