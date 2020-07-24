package com.godcheese.tile.util;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2020-06-07
 */
public class CamelCaseToUnderScoreCaseUtil {

    private static final char UNDER_SCORE_SEPARATOR_CHAR = '_';

    /**
     * 驼峰转成下划线
     *
     * @param camelCaseString
     * @return
     */
    public static String toUnderScoreCase(String camelCaseString) {
        int length = camelCaseString.length();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char ch = camelCaseString.charAt(i);
            if (Character.isUpperCase(camelCaseString.charAt(i))) {
                if (i == 0) {
                    stringBuilder.append(Character.toLowerCase(camelCaseString.charAt(i)));
                } else {
                    stringBuilder.append(UNDER_SCORE_SEPARATOR_CHAR).append(Character.toLowerCase(camelCaseString.charAt(i)));
                }
            } else {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 下划线转成驼峰
     *
     * @param underScoreCaseString
     * @return
     */
    public static String toCamelCase(String underScoreCaseString) {
        int length = underScoreCaseString.length();
        StringBuilder stringBuilder = new StringBuilder(length);
        boolean nextUpperCase = false;
        for (int i = 0; i < length; i++) {
            char ch = underScoreCaseString.charAt(i);
            if (underScoreCaseString.charAt(i) == UNDER_SCORE_SEPARATOR_CHAR) {
                nextUpperCase = true;
            } else if (nextUpperCase) {
                stringBuilder.append(Character.toUpperCase(ch));
                nextUpperCase = false;
            } else {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }
}