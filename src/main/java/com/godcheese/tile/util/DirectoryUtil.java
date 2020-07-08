package com.godcheese.tile.util;

import java.io.File;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class DirectoryUtil {

    public static boolean delete(File file) {
        return file.delete();
    }

    public static boolean createDirectory(File directory) {
        return directory.mkdirs();
    }
}
