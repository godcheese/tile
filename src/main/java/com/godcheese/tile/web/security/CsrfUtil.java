package com.godcheese.tile.web.security;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018/2/22 15:11
 */
public class CsrfUtil {

    public static final String CSRF_PARAMETER_NAME = "csrfToken";
    public static final String CSRF_TOKEN_FORM_INPUT_NAME = CSRF_PARAMETER_NAME + "FormInput";

    public static String generateToken(HttpSession httpSession) {
        return generateToken(httpSession, CSRF_PARAMETER_NAME);
    }

    public static String generateToken(HttpSession httpSession, String csrfParameterName) {
        String token;
        token = (String) httpSession.getAttribute(CSRF_PARAMETER_NAME);
        if (token == null) {
            token = UUID.randomUUID().toString();
            token = token.replaceAll("-", "");
            httpSession.setAttribute(csrfParameterName, token);
        }
        return token;
    }

    public static boolean verifyToken(HttpSession httpSession, String token) {
        String httpSessionToken = (String) httpSession.getAttribute(CSRF_PARAMETER_NAME);
        return httpSessionToken != null && token != null && httpSessionToken.equals(token);
    }

    public static String generateTokenFormInput(String csrfParameterName, String token) {
        return "<input type=\"hidden\" name=\"" + csrfParameterName + "\" value=\"" + token + "\" />";
    }

    public static String generateTokenFormInput(String token) {
        return generateTokenFormInput(CSRF_TOKEN_FORM_INPUT_NAME, token);
    }
}
