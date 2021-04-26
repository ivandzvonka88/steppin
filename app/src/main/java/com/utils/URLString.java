package com.utils;

import android.os.Environment;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Raj on 10/4/2015.
 * this class use for set Server URL.
 */
public class URLString {


    public static final String RootPath = Environment.getExternalStorageDirectory() + "/Steppin";
    public static final String RootImagePathHide = Environment.getExternalStorageDirectory() + "/Steppin/.Image";
    public static final String Download = "/Steppin/Download";
    //    public static final String WebURL = "";
    public static final String WebURL = "";
    public static final String WebURL2 = "";

    public static String replaceURL(String s) {
        String sURL = s;

        sURL = sURL.replace(WebURL, "");
        sURL = sURL.replace(WebURL2, "");

        return sURL;
    }
}
