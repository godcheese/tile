package com.godcheese.tile.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-03-02
 */
public class ClassUtil {

    public static List<Method> getMethods(Class<?> clazz) {
        Method[] method = clazz.getMethods();
        return new LinkedList<>(Arrays.asList(method));
    }

    public static Map<String, List<Class>> getMethodsAndParameterTypes(Class<?> clazz) {
        Map<String, List<Class>> methodsAndParameterTypes = new HashMap<>(6);
        List<Method> methods = getMethods(clazz);
        for (Method method : methods) {
            List<Class> parameterTypes = new LinkedList<>(Arrays.asList(method.getParameterTypes()));
            methodsAndParameterTypes.put(method.getName(), parameterTypes);
        }
        return methodsAndParameterTypes;
    }

    public static Object invokeMethod(Class<?> clazz, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Method method = clazz.getMethod(methodName);
        return method.invoke(clazz.newInstance());
    }

    public static Object invokeMethod(Class<?> clazz, String methodName, Object arg, Class<?> parameterType) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        return invokeMethod(clazz, methodName, new Object[]{arg}, new Class<?>[]{parameterType});
    }

    public static Object invokeMethod(Class<?> clazz, String methodName, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Method method = clazz.getMethod(methodName, parameterTypes);
        return method.invoke(clazz.newInstance(), args);
    }
}
