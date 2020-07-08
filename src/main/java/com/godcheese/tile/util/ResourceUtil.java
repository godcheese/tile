package com.godcheese.tile.util;


import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-04-16
 */
public final class ResourceUtil {

    public static URL getResource(String resourceFile) {
        resourceFile = FileUtil.filterFileSeparator(resourceFile);
        return ResourceUtil.class.getClassLoader().getResource(resourceFile);
    }

    public static URL getFxml(String fxmlName) {
        String suffix = ".fxml";
        if (!fxmlName.toLowerCase().endsWith(suffix)) {
            fxmlName = fxmlName + suffix;
        }
        String resourceFile = "fxml" + File.separator + fxmlName;
        return getResource(resourceFile);
    }

    public static InputStream getResourceAsStream(String resourceFile) {
        resourceFile = FileUtil.filterFileSeparator(resourceFile);
        return ResourceUtil.class.getClassLoader().getResourceAsStream(resourceFile);
    }
}
