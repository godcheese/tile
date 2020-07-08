package com.godcheese.tile.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-04-29
 */
public class RegexUtil {

    public static boolean isNumber(String input) {
        String regex = "^[0-9]*$";
        return isMatch(regex, input);
    }

    public static boolean isLetter(String input) {
        String regex = "^[A-Za-z]*$";
        return isMatch(regex, input);
    }

    public static boolean isLowerCaseLetter(String input) {
        String regex = "^[a-z]*$";
        return isMatch(regex, input);
    }

    public static boolean isUpperCaseLetter(String input) {
        String regex = "^[A-Za-b]*$";
        return isMatch(regex, input);
    }

    public static boolean isChinese(String input) {
        String regex = "^[\u4e00-\u9fa5]*$";
        return isMatch(regex, input);
    }

    public static boolean isMatch(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    /**
     * 中国手机号码验证，可靠性较强
     * +8618869988800
     *
     * @param mobilePhone
     * @return
     */
    public static boolean isChineseMobilePhone(String[] sectionNumber, String mobilePhone) {
        String regex;
        String nationalId = "+86";
        for (String sn : sectionNumber) {
            if (sn.length() == 3) {
                regex = "\\" + nationalId + sn + "[0-9]{8}";
                if (isMatch(regex, mobilePhone)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 国际手机号通用验证，可靠性较低
     *
     * @param nationalId
     * @param minLength
     * @param maxLength
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumber(String nationalId, int minLength, int maxLength, String phoneNumber) {
        String plus = "+";
        if (!nationalId.startsWith(plus)) {
            nationalId = plus + nationalId;
        }
        String regex = nationalId + "[0-9]" + "{" + minLength + "," + maxLength + "}";
        return isMatch(regex, phoneNumber);
    }

    public static Matcher getMacther(String input, String regex, int flags) {
        Pattern pattern = Pattern.compile(regex, flags);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher;
        }
        return null;
    }

    public static String getGroup(String input, int groupIndex, String regex, int flags) {
        return getMacther(input, regex, flags).group(groupIndex);
    }
}
