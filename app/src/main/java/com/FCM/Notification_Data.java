package com.FCM;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Raj on 2/3/2016.
 */
public class Notification_Data {
    public static final String PREFERENCES_Notification = "Notification";
    public static final String Share_Notification = "Notification";
    public static SharedPreferences sharedpreferences_Notification;


    public static ArrayList<HashMap<String, String>> Noti_Array;
    public static final String Key_Time = "Time";
    public static final String Key_Notification = "Notification";
    public static final String Key_NotificationTitle = "NotificationTitle";
    public static final String Key_NotificationImage = "NotificationImage";
    public static final String Key_type = "type";
    public static final String Key_id = "id";
    public static final String Key_IsRead = "IsRead";

    public static void clearAll(Context context) {
        try {
            sharedpreferences_Notification = context.getSharedPreferences(PREFERENCES_Notification,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences_Notification.edit();
            editor.clear().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void addNotiArray(Context context, ArrayList<HashMap<String, String>> Item_Array) {
        ArrayList<HashMap<String, String>> mArray = getNotiArray(context);

        for (int i = 0; i < mArray.size(); i++) {
            Item_Array.add(mArray.get(i));
        }
        String r = arrayList_to_json_string(Item_Array);
        sharedpreferences_Notification = context.getSharedPreferences(PREFERENCES_Notification,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences_Notification.edit();
        editor.putString(Share_Notification, r);
        editor.commit();
    }

    public static void readNotiArray(Context context) {
        ArrayList<HashMap<String, String>> mArray = getNotiArray(context);

        for (int i = 0; i < mArray.size(); i++) {
            HashMap<String, String> map = mArray.get(i);
            String isRead = "";
            try {
                isRead = "" + map.get(Key_IsRead);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!isRead.equals("1")) {
                map.put(Key_IsRead, "1");
                mArray.set(i, map);
            }
        }
        String r = arrayList_to_json_string(mArray);
        sharedpreferences_Notification = context.getSharedPreferences(PREFERENCES_Notification,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences_Notification.edit();
        editor.putString(Share_Notification, r);
        editor.commit();
    }

    public static int unReadNotiArray(Context context) {
        ArrayList<HashMap<String, String>> mArray = getNotiArray(context);
        int unRead = 0;
        for (int i = 0; i < mArray.size(); i++) {
            HashMap<String, String> map = mArray.get(i);
            String isRead = "";
            try {
                isRead = "" + map.get(Key_IsRead);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!isRead.equals("1")) {
                unRead++;
            }
        }
        if (unRead > 99) {
            unRead = 99;
        }
        return unRead;
    }



    public static void clearNoti(Context context) {
        sharedpreferences_Notification = context.getSharedPreferences(PREFERENCES_Notification, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences_Notification.edit();
        editor.putString(Share_Notification, "");
        editor.commit();
    }


    public static ArrayList<HashMap<String, String>> getNotiArray(Context context) {
        sharedpreferences_Notification = context.getSharedPreferences(PREFERENCES_Notification, Context.MODE_PRIVATE);
        String s = "";
        if (sharedpreferences_Notification.contains(Share_Notification)) {
            s = sharedpreferences_Notification.getString(Share_Notification, "");
        } else {
            s = "";
        }
        ArrayList<HashMap<String, String>> Item_Array = new ArrayList<>();
        if (!s.equals("") && !s.equals("null")) {
            try {
                JSONArray array = new JSONArray(s);
                HashMap<String, String> item = null;

                for (int i = 0; i < array.length(); i++) {
                    JSONObject ary = array.getJSONObject(i);
                    Iterator<String> it = ary.keys();
                    item = new HashMap<String, String>();
                    while (it.hasNext()) {
                        String key = it.next();
                        item.put(key, (String) ary.get(key));
                    }
                    Item_Array.add(item);
                }
            } catch (JSONException e) {
            }
        }

        return Item_Array;

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
