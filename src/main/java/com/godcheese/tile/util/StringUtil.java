package com.godcheese.tile.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class StringUtil {


    /**
     * 类似C#的字符串占位符格式化输出，{0}表示第一个参数，{1}表示第二个参数，依次类推
     *
     * @param string String
     * @param args   Object...
     * @return String
     */
    public static String format(String string, Object... args) {
        int i = 0;
        for (Object arg : args) {
            String regex = "\\{([" + i + "]+)\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(string);
            if (matcher.find()) {
                string = matcher.replaceAll(arg.toString());
            }
            i++;
        }
        return string;
    }

    public static Object ifNullReturnEmpty(Object o) {
        return isNull(o) ? "" : o;
    }

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isBlank(String str) {
        return isNullOrEmpty(str);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static List<String> splitAsList(String str, String delimiter) {
        List<String> list = new ArrayList<>();
        if (isNotBlank(str)) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, delimiter);
            while (stringTokenizer.hasMoreElements()) {
                list.add(String.valueOf(stringTokenizer.nextElement()));
            }
        }
        return list;
    }

    public static List<Long> splitAsLongList(String str, String delimiter) {
        List<Long> list = new ArrayList<>();
        List<String> stringList = splitAsList(str, delimiter);
        if (stringList != null && !stringList.isEmpty()) {
            for (String s : stringList) {
                list.add(new Long(s));
            }
        }
        return list;
    }

    public static List<Integer> asIntegerList(List<String> stringList) {
        List<Integer> integerList = new ArrayList<>();
        if (ListUtil.isNotBlank(stringList)) {
            for (String str : stringList) {
                if (StringUtil.isNotBlank(str)) {
                    integerList.add(Integer.valueOf(str));
                }
            }
        }
        return integerList;
    }

    public static String isNullReturnEmpty(String str) {
        if (str != null) {
            return str;
        }
        return "";
    }

    public static String firstLowerCase(String word) {
        String first = word.substring(0, 1).toLowerCase();
        return first + word.substring(1);
    }

    public static String firstUpperCase(String word) {
        String first = word.substring(0, 1).toUpperCase();
        return first + word.substring(1);
    }

    public static String firstUpperCase(String replace, String words) {
        words = firstUpperCase(words);
        String[] wordsArray = words.split(replace);
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : wordsArray) {
            stringBuilder.append(firstUpperCase(word));
        }
        return stringBuilder.toString();
    }

    public static String camelToUnderline(String words) {
        final String underline = "_";
        int length = words.length();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char ch = words.charAt(i);
            if (Character.isUpperCase(ch)) {
                stringBuilder.append(underline);
                stringBuilder.append(Character.toLowerCase(ch));
            } else {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }

    public static String underlineToCamel(String words) {
        final String underline = "_";
        int length = words.length();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char ch = words.charAt(i);
            if (underline.equalsIgnoreCase(String.valueOf(ch))) {
                int ii = i + 1;
                if (ii <= (length - 1)) {
                    char ch2 = words.charAt(ii);
                    if (Character.isLetter(ch2)) {
                        stringBuilder.append(Character.toUpperCase(ch2));
                    }
                }
            } else {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }
}
