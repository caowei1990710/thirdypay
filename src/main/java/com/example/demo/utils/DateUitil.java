package com.example.demo.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUitil {

    private final static String yyyyMMddHHmmss ="yyyy-MM-dd HH:mm:ss";

    public static String newDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(yyyyMMddHHmmss);
        return sdf.format(date);
    }

    public static String newDateFront(int minute) {
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, minute);
        Date beforeD = beforeTime.getTime();
        String before5 = new SimpleDateFormat(yyyyMMddHHmmss).format(beforeD);
        return before5;
    }

    /**
     * 字符串时间的前后分钟计时间点 yyyy-MM-dd HH:mm:ss
     */
    public static String rollMinute(String dateStr, int minute) {
        return new SimpleDateFormat(yyyyMMddHHmmss).format(new Date(getDate(dateStr).getTime() + minute * 60 * 1000));
    }

    /**
     * 字符串时间的前后分钟计时间点 yyyy-MM-dd HH:mm:ss
     */
    public static String rollMinute(Date date, int minute) {
        return new SimpleDateFormat(yyyyMMddHHmmss).format(date.getTime() + minute * 60 * 1000);
    }


    /**
     * 字符串时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(yyyyMMddHHmmss);
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(dateStr, pos);
        return date;
    }

    public static String getDateStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(yyyyMMddHHmmss);
        return sdf.format(date);
    }

}
