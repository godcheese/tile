package com.godcheese.tile.util;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-25
 */
public class ArrayUtil {

    public static boolean isBlank(Object[] o) {
        return o == null || o.length <= 0;
    }

    public static boolean isNotBlank(Object[] o) {
        return !isBlank(o);
    }
}
