package com.godcheese.tile.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class DateUtil {

    public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private DateUtil() {
    }

    public static Date newDate() {
        return new Date();
    }

    public static String format(String format, long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(timestamp));
    }

    public static String format(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String getNow(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(DateUtil.newDate());
    }

    public static String getNowDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(DateUtil.newDate());
    }

    public static String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(DateUtil.newDate());
    }

    public static String getNowDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT_PATTERN);
        return simpleDateFormat.format(DateUtil.newDate());
    }


    public static String calendarPlus(String startDate, String format, int calendarField, int plus) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(calendarPlus(simpleDateFormat.parse(startDate), calendarField, plus));
    }

    public static Date calendarPlus(Date startDate, int calendarField, int plus) {
        Date date = startDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // plus 为增加的时间，可以改变的
        calendar.add(calendarField, plus);
        date = calendar.getTime();
        return date;
    }
}
