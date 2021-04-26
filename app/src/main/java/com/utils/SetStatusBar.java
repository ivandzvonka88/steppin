package com.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by Raj on 5/26/2016.
 */
public class SetStatusBar {
    public static void set(Activity activity, int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= 23) {
                window.setStatusBarColor(GetResources.getColor(activity.getApplicationContext(), res));
            } else {
                window.setStatusBarColor(GetResources.getColor(activity.getApplicationContext(), res));
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = activity.getWindow().getDecorView();
            decor.setSystemUiVisibility(decor.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        adjustFontScale(activity);
    }
    public static void adjustFontScale(Activity activity) {
        try {
            Configuration configuration = activity.getResources().getConfiguration();
            Log.e("fontScale", "fontScale: " + configuration.fontScale);
            if (configuration.fontScale > 1) {
                configuration.fontScale = (float) 1;
                DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
                WindowManager wm = (WindowManager) activity.getSystemService(WINDOW_SERVICE);
                wm.getDefaultDisplay().getMetrics(metrics);
                metrics.scaledDensity = configuration.fontScale * metrics.density;
                activity.getBaseContext().getResources().updateConfiguration(configuration, metrics);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
