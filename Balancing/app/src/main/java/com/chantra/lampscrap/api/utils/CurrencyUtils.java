package com.chantra.lampscrap.api.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by phearom on 7/18/16.
 */
public class CurrencyUtils {
    public static String currentFormat(double price) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        return numberFormat.format(price);
    }
}
