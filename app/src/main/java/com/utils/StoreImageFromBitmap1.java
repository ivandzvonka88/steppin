package com.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StoreImageFromBitmap1 {

    public static boolean storeImage(Bitmap imageData, String ImagePath) {
        // get path to external storage (SD card)
        String StoragePath;

        StoragePath = URLString.RootImagePathHide + ImagePath;
        int iIndex = StoragePath.lastIndexOf("/");
        String FPath = StoragePath.substring(0, iIndex);
        String ImageName = StoragePath.substring(iIndex + 1,
                StoragePath.length());
        String ImgExtention = ImageName.substring(ImageName.lastIndexOf("."),
                ImageName.length());
//        Log.i("Path", FPath);
//        Log.i("ImageName", ImageName);
//        Log.i("Extention", ImgExtention);
        File sdStorageDir = new File(FPath);
        // create storage directories, if they don't exist
        if (!sdStorageDir.exists()) {
            sdStorageDir.mkdirs();
        }

        try {
            String filePath1 = sdStorageDir.toString() + "/" + ImageName;
            FileOutputStream fileOutputStream1 = new FileOutputStream(filePath1);

            BufferedOutputStream bos1 = new BufferedOutputStream(
                    fileOutputStream1);

            // choose another format if PNG doesn't suit you
            if (!ImgExtention.equals(".png")) {
                imageData.compress(CompressFormat.JPEG, 100, bos1);
            } else {
                imageData.compress(CompressFormat.PNG, 100, bos1);
            }
            bos1.flush();
            bos1.close();

            return true;
        } catch (FileNotFoundException e) {

            Log.e("Error", e.toString());
            return false;
        } catch (IOException e) {

            Log.e("Error", e.toString());
            return false;
        }

    }

    public static boolean storeImageScale(Bitmap imageData, String ImagePath) {
        // get path to external storage (SD card)
        String StoragePath;
        imageData = resizeBitmap(imageData,1000);
        StoragePath = URLString.RootImagePathHide + ImagePath;
        int iIndex = StoragePath.lastIndexOf("/");
        String FPath = StoragePath.substring(0, iIndex);
        String ImageName = StoragePath.substring(iIndex + 1,
                StoragePath.length());
        String ImgExtention = ImageName.substring(ImageName.lastIndexOf("."),
                ImageName.length());
        Log.i("Path", FPath);
        Log.i("ImageName", ImageName);
        Log.i("Extention", ImgExtention);
        File sdStorageDir = new File(FPath);
        // create storage directories, if they don't exist
        if (!sdStorageDir.exists()) {
            sdStorageDir.mkdirs();
        }

        try {
            String filePath1 = sdStorageDir.toString() + "/" + ImageName;
            FileOutputStream fileOutputStream1 = new FileOutputStream(filePath1);

            BufferedOutputStream bos1 = new BufferedOutputStream(
                    fileOutputStream1);

            // choose another format if PNG doesn't suit you
            if (!ImgExtention.equals(".png")) {
                imageData.compress(CompressFormat.JPEG, 100, bos1);
            } else {
                imageData.compress(CompressFormat.PNG, 100, bos1);
            }
            bos1.flush();
            bos1.close();

            return true;
        } catch (FileNotFoundException e) {

            Log.e("Error", e.toString());
            return false;
        } catch (IOException e) {

            Log.e("Error", e.toString());
            return false;
        }

    }

    public static Bitmap resizeBitmap(final Bitmap temp, final int size) {
        if (size > 0) {
            int width = temp.getWidth();
            int height = temp.getHeight();
            float ratioBitmap = (float) width / (float) height;
            int finalWidth = size;
            int finalHeight = size;
            if (ratioBitmap < 1) {
                finalWidth = (int) ((float) size * ratioBitmap);
            } else {
                finalHeight = (int) ((float) size / ratioBitmap);
            }
            return Bitmap.createScaledBitmap(temp, finalWidth, finalHeight, true);
        } else {
            return temp;
        }
    }
}
