package com.godcheese.tile.util;

import java.util.Random;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class RandomUtil {

    public static final String NUMBER = "0123456789";
    public static final String LOWER_CASE_LETTER = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPER_CASE_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBER_LETTER = NUMBER + LOWER_CASE_LETTER + UPPER_CASE_LETTER;

    public static int nextInt(Random random, int min, int max) {
        return min + random.nextInt(max - min);
    }

    public static int nextInt(int min, int max) {
        return nextInt(new Random(), min, max);
    }

    public static int nextInt(int max) {
        return nextInt(new Random(), 0, max);
    }

    public static String randomString(int length, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int stringLength = string.length();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(string.charAt(random.nextInt(stringLength)));
        }
        return stringBuilder.toString();
    }
}
