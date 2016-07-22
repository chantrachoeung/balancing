package com.chantra.lampscrap.balancing.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by phearom on 7/2/16.
 */
public class DateUtils {
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String currentDateAndTime = sdf.format(new Date());
        return currentDateAndTime;
    }

    public static Date getDate(String date) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.parse(date);
    }

    public static String formatDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date1 = new Date();
        try {
            date1 = getDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date1);
    }
}
