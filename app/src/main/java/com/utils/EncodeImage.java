package com.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;


/**
 * Created by Raj on 8/20/2015.
 * this class use for get device size
 */
public class EncodeImage {

    //this method return boolean value.
    public static String Encode(Context c, String Path) {
        Bitmap bitmap = ShrinkBitmap(Path, 300, 300);
        Log.e("Encode Path", "-" + Path);
        File f = new File(Path);
        Log.e("Image", "-" + Path);
        if (f.exists()) {
            Log.e("Image", "Available");
        } else {
            Log.e("Image", "Not Available");
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        String image_str = "";
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byte_arr = stream.toByteArray();
            image_str = Base64.encodeBytes(byte_arr);
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("Error 1 ", e.toString());
            image_str = "";
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                byte[] byte_arr = stream.toByteArray();
                image_str = Base64.encodeBytes(byte_arr);
            } catch (Exception e2) {
                // TODO: handle exception
                image_str = "";
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                    byte[] byte_arr = stream.toByteArray();
                    image_str = Base64.encodeBytes(byte_arr);
                } catch (Exception e3) {
                    // TODO: handle exception
                    image_str = "";
                    try {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                        byte[] byte_arr = stream.toByteArray();
                        image_str = Base64.encodeBytes(byte_arr);
                    } catch (Exception e4) {
                        image_str = "";
                        // TODO: handle exception
                        ToastMsg.mToastMsg(c, "Oops! Image Too Large", 1);
                    }

                }

            }

        }
        return image_str;
    }

    public static Bitmap ShrinkBitmap(String file, int width, int height) {

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }
}
