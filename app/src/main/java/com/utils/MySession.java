package com.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.admin.Admin;
import com.admin.Admin_Home;
import com.admin.Admin_Login;
import com.steppin.BuildConfig;
import com.steppin.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Raj on 10/6/2015.
 * this class use for store User Details in Session.
 * this class also returns user Details when required.
 */
public class MySession {
    public static final String PREFERENCES_SessionMain = "SessionMain";
    public static final String PREFERENCES_SessionAdmin = "SessionAdmin";

    public static final String ShareAccessTokenTopic = "AccessTokenTopic";
    public static final String ShareAccessToken = "AccessToken";
    public static final String ShareEventsNotification = "N1";
    public static final String ShareVenuesNotification = "N2";
    public static final String ShareDanceNotification = "N3";
    public static final String ShareNewsNotification = "N4";
    public static final String Key_CityID = "CityID";
    public static final String Key_CityName = "CityName";
    public static final String KeyCityNameWithLocation = "KeyCityNameWithLocation";
    public static SharedPreferences sharedpreferences_SessionMain;
    public static SharedPreferences sharedpreferences_SessionAdmin;
    public static Context mContext = null;

    public static void logout(Context context) {
        mContext = context;
        if (BuildConfig.FLAVOR == "steppinadmin") {
            sharedpreferences_SessionAdmin = context.getSharedPreferences(PREFERENCES_SessionAdmin,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences_SessionAdmin.edit();
            editor.clear();
            editor.commit();
        } else if (BuildConfig.FLAVOR == "steppin") {
            sharedpreferences_SessionMain = context.getSharedPreferences(PREFERENCES_SessionMain,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences_SessionMain.edit();
            editor.clear();
            editor.commit();
        }
    }

    public static void setAccessToken(Context context, String str) {
        mContext = context;
        if (BuildConfig.FLAVOR == "steppinadmin") {
            sharedpreferences_SessionAdmin = context.getSharedPreferences(PREFERENCES_SessionAdmin,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences_SessionAdmin.edit();
            editor.putString(ShareAccessToken, str);
            editor.commit();
        } else if (BuildConfig.FLAVOR == "steppin") {
            sharedpreferences_SessionMain = context.getSharedPreferences(PREFERENCES_SessionMain,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences_SessionMain.edit();
            editor.putString(ShareAccessToken, str);
            editor.commit();
        }
    }

    public static void set(Context context, String str, String key) {
        mContext = context;
        if (BuildConfig.FLAVOR == "steppinadmin") {
            sharedpreferences_SessionAdmin = context.getSharedPreferences(PREFERENCES_SessionAdmin,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences_SessionAdmin.edit();
            editor.putString(key, str);
            editor.commit();
        } else if (BuildConfig.FLAVOR == "steppin") {
            sharedpreferences_SessionMain = context.getSharedPreferences(PREFERENCES_SessionMain,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences_SessionMain.edit();
            editor.putString(key, str);
            editor.commit();
        }
    }


    public static void setAccessTokenTopic(Context context, String str) {
        mContext = context;
        if (BuildConfig.FLAVOR == "steppinadmin") {
            sharedpreferences_SessionAdmin = context.getSharedPreferences(PREFERENCES_SessionAdmin,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences_SessionAdmin.edit();
            editor.putString(ShareAccessTokenTopic, str);
            editor.commit();
        } else if (BuildConfig.FLAVOR == "steppin") {
            sharedpreferences_SessionMain = context.getSharedPreferences(PREFERENCES_SessionMain,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences_SessionMain.edit();
            editor.putString(ShareAccessTokenTopic, str);
            editor.commit();
        }
    }

    public static String get(Context context, String key) {
        mContext = context;
        String s = "";
        if (BuildConfig.FLAVOR == "steppinadmin") {
            sharedpreferences_SessionAdmin = context.getSharedPreferences(PREFERENCES_SessionAdmin, Context.MODE_PRIVATE);
            if (sharedpreferences_SessionAdmin.contains(key)) {
                s = sharedpreferences_SessionAdmin.getString(key, "");
            } else {
                s = "";
            }
        } else if (BuildConfig.FLAVOR == "steppin") {
            sharedpreferences_SessionMain = context.getSharedPreferences(PREFERENCES_SessionMain,
                    Context.MODE_PRIVATE);
            if (sharedpreferences_SessionMain.contains(key)) {
                s = sharedpreferences_SessionMain.getString(key, "");
            } else {
                s = "";
            }
        }
        return s;
    }

    public static boolean getNoti(Context context, String key) {
        mContext = context;
        String s = "";
        if (BuildConfig.FLAVOR == "steppinadmin") {
            sharedpreferences_SessionAdmin = context.getSharedPreferences(PREFERENCES_SessionAdmin,
                    Context.MODE_PRIVATE);

            if (sharedpreferences_SessionAdmin.contains(key)) {
                s = sharedpreferences_SessionAdmin.getString(key, "");
            } else {
                s = "";
            }
        } else if (BuildConfig.FLAVOR == "steppin") {
            sharedpreferences_SessionMain = context.getSharedPreferences(PREFERENCES_SessionMain,
                    Context.MODE_PRIVATE);

            if (sharedpreferences_SessionMain.contains(key)) {
                s = sharedpreferences_SessionMain.getString(key, "");
            } else {
                s = "";
            }
        }

        if (s.equals("0")) {
            return false;
        } else {
            return true;
        }
    }

    public static String getAccessToken(Context context) {
        mContext = context;
        String s = "";
        if (BuildConfig.FLAVOR == "steppinadmin") {
            sharedpreferences_SessionAdmin = context.getSharedPreferences(PREFERENCES_SessionAdmin,
                    Context.MODE_PRIVATE);
            if (sharedpreferences_SessionAdmin.contains(ShareAccessToken)) {
                s = sharedpreferences_SessionAdmin.getString(ShareAccessToken, "");
            } else {
                s = "";
            }
        } else if (BuildConfig.FLAVOR == "steppin") {
            sharedpreferences_SessionMain = context.getSharedPreferences(PREFERENCES_SessionMain,
                    Context.MODE_PRIVATE);
            if (sharedpreferences_SessionMain.contains(ShareAccessToken)) {
                s = sharedpreferences_SessionMain.getString(ShareAccessToken, "");
            } else {
                s = "";
            }
        }

        return s;
    }

    public static String getAccessTokenTopic(Context context) {
        mContext = context;
        String s = "";
        if (BuildConfig.FLAVOR == "steppinadmin") {
            sharedpreferences_SessionAdmin = context.getSharedPreferences(PREFERENCES_SessionAdmin,
                    Context.MODE_PRIVATE);
            if (sharedpreferences_SessionAdmin.contains(ShareAccessTokenTopic)) {
                s = sharedpreferences_SessionAdmin.getString(ShareAccessTokenTopic, "");
            } else {
                s = "";
            }
        } else if (BuildConfig.FLAVOR == "steppin") {
            sharedpreferences_SessionMain = context.getSharedPreferences(PREFERENCES_SessionMain,
                    Context.MODE_PRIVATE);
            if (sharedpreferences_SessionMain.contains(ShareAccessTokenTopic)) {
                s = sharedpreferences_SessionMain.getString(ShareAccessTokenTopic, "");
            } else {
                s = "";
            }
        }

        return s;
    }

    public static String arrayList_to_json_string(ArrayList<HashMap<String, String>> list) {
        JSONArray json_arr = new JSONArray();
        for (Map<String, String> map : list) {
            JSONObject json_obj = new JSONObject();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                try {
                    json_obj.put(key, value);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            json_arr.put(json_obj);
        }
        return json_arr.toString();
    }

}
