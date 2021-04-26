package com.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by Raj on 5/10/2015.
 * this class use for converting pixel to dpi.
 */
public class ConverPIXtoDPI {
    // this methos use for converting pixel to dpi.
    public static final String Type_date = "date";
    public static final String Type_int = "int";
    public static final String Type_decimal = "decimal";
    public static final String Type_unitdecimal = "unitdecimal";


    public static boolean isTablet(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches >= 6.5) {
            return true;
        } else {
            return false;
        }
    }

    public static int PIXtoDPI(Context c, int Pixel) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Pixel, c.getResources().getDisplayMetrics());
    }

    //this method return display density name.
    public static String getDensityName(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            return "xxxhdpi";
        }
        if (density >= 3.0) {
            return "xxhdpi";
        }
        if (density >= 2.0) {
            return "xhdpi";
        }
        if (density >= 1.5) {
            return "hdpi";
        }
        if (density >= 1.0) {
            return "mdpi";
        }
        return "ldpi";
    }

    public static int getDPToPX1(Context context, int dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());


        return px;
    }

    public static int getDPToPXType(Context context, int dp, String type) {
        int mWidth = dp;
//
//        if (mWidth <= 4) {
//            mWidth = dp;
//        } else
//
//        {
////            mWidth = mWidth + 200;
////            if (type.toLowerCase().contains(Type_date)) {
////                mWidth = 100;
////            } else if (type.toLowerCase().contains(Type_int)) {
////                mWidth = 100;
////            } else if (type.toLowerCase().contains(Type_decimal)) {
////                mWidth = 100;
////            } else if (type.toLowerCase().contains(Type_unitdecimal)) {
////                mWidth = 100;
////            } else {
//            if (mWidth <= 10) {
//                mWidth = 60;
//            } else if (mWidth <= 20) {
//                mWidth = 70;
//            } else if (mWidth <= 30) {
//                mWidth = 80;
//            } else if (mWidth <= 40) {
//                mWidth = 90;
//            } else if (mWidth <= 50) {
//                mWidth = 100;
//            } else if (mWidth <= 60) {
//                mWidth = 110;
//            } else if (mWidth <= 70) {
//                mWidth = 120;
//            }
////            }
//        }


        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mWidth, context.getResources().getDisplayMetrics());


        return px;
    }
}
