package com.chantra.lampscrap.balancing.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by phearom on 7/2/16.
 */
public class DateUtils {
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateAndTime = sdf.format(new Date());
        return currentDateAndTime;
    }

    public static String formatDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        SimpleDateFormat sdf1 = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf1.format(c.getTime());
    }
}
