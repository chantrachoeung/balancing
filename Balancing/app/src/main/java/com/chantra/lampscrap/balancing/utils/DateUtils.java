package com.chantra.lampscrap.balancing.utils;

import java.text.SimpleDateFormat;
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
}
