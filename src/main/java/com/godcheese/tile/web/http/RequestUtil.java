package com.godcheese.tile.web.http;


import javax.servlet.http.HttpServletRequest;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-03-29
 */
public class RequestUtil {

//    /**
//     * getQueryString
//     * @param request
//     * @param name
//     * @return
//     */
//    public static String getQueryString(HttpServletRequest request, String name){
//        String queryString = request.getQueryString();
//        if(StringUtil.isNotBlank(queryString)){
//            List<String> stringList = StringUtil.splitAsList(queryString,"&");
//            if(ListUtil.isNotBlank(stringList)) {
//                for (String s : stringList) {
//                    if (StringUtil.isNotBlank(s)) {
//                        String[] sArray = s.split("=");
//                        if (ArrayUtil.isNotBlank(sArray)) {
//                            if (sArray[0].equals(name)) {
//                                return sArray[1];
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }


//    /**
//     * getQueryString
//     * @param request
//     * @param name
//     * @return
//     */
//    public static String getQueryString(ServletRequest request, String name){
//        return getQueryString((HttpServletRequest) request, name);
//    }


    /**
     * 判断是否为 Ajax 请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(ajaxFlag);
    }
}
