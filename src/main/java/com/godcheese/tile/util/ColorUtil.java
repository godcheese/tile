package com.godcheese.tile.util;

import java.awt.*;
import java.util.Random;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-01-10
 */
public class ColorUtil {

    private static final int MAX_COLOR = 255;

    /**
     * 获取随机 RGB 颜色
     *
     * @param minColor
     * @param maxColor
     * @return Color
     */
    public static Color getRandomRGBColor(int minColor, int maxColor) {
        int red, green, blue;
        Random random = new Random();
        if (minColor > MAX_COLOR) {
            minColor = MAX_COLOR;
        }
        if (maxColor > MAX_COLOR) {
            maxColor = MAX_COLOR;
        }
        red = RandomUtil.nextInt(random, minColor, maxColor);
        green = RandomUtil.nextInt(random, minColor, maxColor);
        blue = RandomUtil.nextInt(random, minColor, maxColor);
        return new Color(red, green, blue);
    }

    /**
     * 获取随机 RGB 颜色
     *
     * @return Color
     */
    public static Color getRandomRGBColor() {
        int red, green, blue;
        Random random = new Random();
        red = RandomUtil.nextInt(random, 0, MAX_COLOR);
        green = RandomUtil.nextInt(random, 0, MAX_COLOR);
        blue = RandomUtil.nextInt(random, 0, MAX_COLOR);
        return new Color(red, green, blue);
    }

    /**
     * 16 进制颜色值转成 RGB 颜色
     *
     * @param hexString 16 进制颜色值
     * @return RGB Color
     */
    public static Color getRGBColorByHexString(String hexString) {
        final int hexColorLength = 7;
        if (hexString.length() == hexColorLength) {
            String hex1 = hexString.substring(1, 3);
            String hex2 = hexString.substring(3, 5);
            String hex3 = hexString.substring(5, 7);
            int red = Integer.parseInt(hex1, 16);
            int green = Integer.parseInt(hex2, 16);
            int blue = Integer.parseInt(hex3, 16);
            return new Color(red, green, blue);
        }
        return null;
    }

    /**
     * RGB 颜色转成 16 进制颜色值
     *
     * @param color RGB Color
     * @return 16 进制颜色值
     */
    public static String getHexStringByRGBColor(Color color) {
        String red = Integer.toHexString(color.getRed());
        String green = Integer.toHexString(color.getGreen());
        String blue = Integer.toHexString(color.getBlue());
        return "#" + (red + green + blue).toUpperCase();
    }
}
