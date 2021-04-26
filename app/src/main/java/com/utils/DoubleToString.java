package com.utils;

import java.text.DecimalFormat;

/**
 * Created by Raj on 12/7/2015.
 */
public class DoubleToString {

    public static String removeAfterDot(String str) {

        String s = str.substring(str.lastIndexOf(".") + 1, str.length());
        if (s.equals("0") || s.equals("00")) {
            str = str.substring(0, str.lastIndexOf("."));
        }
        return str;
    }

    public static double convertTwo(double x) {

        DecimalFormat df = new DecimalFormat("#.##");
        String dx = df.format(x);
        x = Double.valueOf(dx);
        return x;
    }

    public static String convertTwo(String s) {
        double x = 0;
        try {
            x = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return s;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(x);

    }

    public static long round(double x) {

        long m = Math.round(x);
        return m;
    }
}
