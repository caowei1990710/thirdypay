package com.example.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUitil {


    public static String newDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String newDateFront(int minute) {
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, minute);
        Date beforeD = beforeTime.getTime();
        String before5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beforeD);
        return before5;
    }
}
