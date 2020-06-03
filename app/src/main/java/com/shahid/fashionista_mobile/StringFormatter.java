package com.shahid.fashionista_mobile;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringFormatter {
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd");

    public static String getDayOfWeek(Date date) {
        return format.format(date).toUpperCase();
    }

    public static String getFormattedId(String id) {
        String replaced = id.replaceAll("-", "").toUpperCase();
        return "ORDER #" + replaced.substring(replaced.length() - 5);
    }

    public static String getFormattedPrice(Double price) {
        return "$" + String.format(Locale.getDefault(), "%.2f", price);
    }

    public static String getFirstCharacter(String name) {
        return String.valueOf(name.charAt(0));
    }
}
