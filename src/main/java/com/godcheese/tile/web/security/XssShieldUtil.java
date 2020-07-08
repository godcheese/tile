package com.godcheese.tile.web.security;

import com.godcheese.tile.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018/2/19 22:13
 */
public class XssShieldUtil {

    private static final String REGEX = "regex";
    private static final String FLAG = "flag";

    private static List<Pattern> PATTERNS = new ArrayList<>();

    private static List<Map<String, Object>> getXssPatternList() {
        List<Map<String, Object>> patternList = new ArrayList<>();

        Map<String, Object> map = new HashMap<>(2);
        map.put(REGEX, "<(no)?script[^>]*>.*?</(no)?script>");
        map.put(FLAG, Pattern.CASE_INSENSITIVE);
        patternList.add(map);

        map = new HashMap<>(2);
        map.put(REGEX, "eval\\((.*?)\\)");
        map.put(FLAG, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        patternList.add(map);

        map = new HashMap<>(2);
        map.put(REGEX, "expression\\((.*?)\\)");
        map.put(FLAG, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        patternList.add(map);

        map = new HashMap<>(2);
        map.put(REGEX, "(javascript:|vbscript:|view-source:)*");
        map.put(FLAG, Pattern.CASE_INSENSITIVE);
        patternList.add(map);

        map = new HashMap<>(2);
        map.put(REGEX, "<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
        map.put(FLAG, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        patternList.add(map);

        map = new HashMap<>(2);
        map.put(REGEX, "(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*");
        map.put(FLAG, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        patternList.add(map);

        map = new HashMap<>(2);
        map.put(REGEX, "<+\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)+\\s*=+");
        map.put(FLAG, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        patternList.add(map);

        return patternList;
    }

    private static List<Pattern> getPatterns() {
        if (PATTERNS == null) {

            List<Pattern> list = new ArrayList<>();
            String regex;
            Integer flag;

            for (Map<String, Object> map : getXssPatternList()) {
                regex = (String) map.get(REGEX);
                flag = (Integer) map.get(FLAG);
                list.add(Pattern.compile(regex, flag));
            }
            PATTERNS = list;
        }

        return PATTERNS;
    }

    public static String stripXss(String value) {
        if (value != null) {
            try {
                //'+' replace to '%2B'
                value = value.replace("+", "%2B");
                value = URLDecoder.decode(value, "utf-8");
            } catch (UnsupportedEncodingException | IllegalArgumentException e) {
                e.printStackTrace();
            }


            // Avoid null characters
            value = value.replaceAll("\0", "");
            if (StringUtil.isNotBlank(value)) {

                Matcher matcher = null;
                for (Pattern pattern : getPatterns()) {
                    matcher = pattern.matcher(value);

                    // 匹配相关字符串
                    if (matcher.find()) {
                        // 替换已匹配的正则字符串为空
                        value = matcher.replaceAll("");
                    }
                }

                value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//                value = filter(value);

            }
        }
        return value;
    }

//    /**
//     * 过滤特殊字符
//     */
//    private static String filter(String value) {
//        if (value == null) {
//            return null;
//        }
//        StringBuilder result = new StringBuilder(value.length());
//        for (int i=0; i<value.length(); ++i) {
//            switch (value.charAt(i)) {
//                case '<':
//                    result.append("<");
//                    break;
//                case '>':
//                    result.append(">");
//                    break;
//                case '"':
//                    result.append("\"");
//                    break;
//                case '\'':
//                    result.append("'");
//                    break;
//                case '%':
//                    result.append("%");
//                    break;
//                case ';':
//                    result.append(";");
//                    break;
//                case '(':
//                    result.append("(");
//                    break;
//                case ')':
//                    result.append(")");
//                    break;
//                case '&':
//                    result.append("&");
//                    break;
//                case '+':
//                    result.append("+");
//                    break;
//                default:
//                    result.append(value.charAt(i));
//                    break;
//            }
//        }
//        return result.toString();
//    }
}
