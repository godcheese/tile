package com.godcheese.tile.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class JsonUtil {

    private static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("GMT+8");
    private static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final Locale DEFAULT_LOCALE = Locale.CHINA;

    private JsonUtil() {
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return toJson(object, DEFAULT_TIME_ZONE, DEFAULT_DATE_FORMAT_PATTERN);
    }

    public static String toJson(Object object, TimeZone timeZone) throws JsonProcessingException {
        return toJson(object, timeZone, DEFAULT_DATE_FORMAT_PATTERN);
    }

    public static String toJson(Object object, TimeZone timeZone, String dateFormatPattern) throws JsonProcessingException {
        return getObjectMapper(timeZone, dateFormatPattern, DEFAULT_LOCALE).writeValueAsString(object);
    }

    public static <T> T toObject(String json, Class<T> clazz) throws IOException {
        return toObject(json, clazz, DEFAULT_TIME_ZONE, DEFAULT_DATE_FORMAT_PATTERN);
    }

    public static <T> T toObject(String json, Class<T> clazz, TimeZone timeZone) throws IOException {
        return toObject(json, clazz, timeZone, DEFAULT_DATE_FORMAT_PATTERN);
    }

    public static <T> T toObject(String json, Class<T> clazz, TimeZone timeZone, String dateFormatPattern) throws IOException {
        return getObjectMapper(timeZone, dateFormatPattern, DEFAULT_LOCALE).readValue(json, clazz);
    }

    public static ObjectMapper getObjectMapper(TimeZone timeZone, String dateFormatPattern, Locale locale) {
        ObjectMapper objectMapper = new ObjectMapper();
        TimeZone userTimeZone = TimeZone.getTimeZone(System.getProperty("user.timezone"));
        if (!timeZone.hasSameRules(userTimeZone)) {
            objectMapper.setTimeZone(timeZone);
        }
        objectMapper.setDateFormat((new SimpleDateFormat(dateFormatPattern)));
        objectMapper.setLocale(locale);
        return objectMapper;
    }
}
