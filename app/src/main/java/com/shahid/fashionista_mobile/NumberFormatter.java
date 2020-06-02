package com.shahid.fashionista_mobile;

public class NumberFormatter {
    public static int getRatingPercentage(int count, int total) {
        if (count == 0 || total == 0) {
            return 0;
        }
        return count / total * 100;
    }

    public static String getIntAsString(int value) {
        return String.valueOf(value);
    }
}
