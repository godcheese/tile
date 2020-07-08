package com.godcheese.tile.util;

import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-03-29
 */
public class ListUtil {

    public static boolean isBlank(List o) {
        return o == null || o.size() <= 0;
    }

    public static boolean isNotBlank(List o) {
        return !isBlank(o);
    }
}
