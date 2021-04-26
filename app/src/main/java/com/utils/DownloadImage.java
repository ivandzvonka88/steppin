package com.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImage {

    public static Bitmap DownloagImg(String imageUrl) {
        if (imageUrl.equals("") || imageUrl.equals("null")) {
            return null;
        }
        String StoragePath = URLString.RootImagePathHide + imageUrl;
        File f = new File(StoragePath);
        if (f.exists()) {
            return null;
        }
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            imageUrl = URLString.WebURL2 + imageUrl;
            imageUrl = imageUrl.replaceAll(" ", "%20");
//            Log.e("imageUrl 2", "-"+imageUrl);
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
//            e.printStackTrace();
            return null;
        }catch (Exception e) {
//            e.printStackTrace();
            return null;
        }

    }
    public static Bitmap DownloagImgWithSterch(String imageUrl) {
        if (imageUrl.equals("") || imageUrl.equals("null")) {
            return null;
        }
//        String StoragePath = URLString.RootImagePathHide + imageUrl;

        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            imageUrl = imageUrl.replaceAll(" ", "%20");
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            myBitmap= Bitmap.createScaledBitmap(myBitmap, 350, 490, false);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
