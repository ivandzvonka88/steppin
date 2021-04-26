package com.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * Created by Raj on 4/4/2015.
 * this class use for converting InputStream to String Format
 */
public class ConvertToString {
    //this method Convert InputStream to String.
    public static String convertDate(InputStream inputStream) {
        try {
            if (inputStream != null) {
                StringBuilder sb = new StringBuilder();
                String line;
                try {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(inputStream, "UTF-8"));
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                } finally {
                    inputStream.close();
                }

                return sb.toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

}
