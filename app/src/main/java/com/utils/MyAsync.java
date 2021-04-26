package com.utils;

import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by Raj on 10/5/2015.
 * this class use for calling AsyncTask.
 */
public class MyAsync {
    public static void callAsync(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }
    public static void callAsync2(AsyncTask<String, String, String> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }
}
