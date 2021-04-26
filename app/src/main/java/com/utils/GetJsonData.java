package com.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Rajneesh on 15-Nov-17.
 */

public class GetJsonData {

    public static ArrayList<HashMap<String, String>> getData(String Result, String obj) {
        ArrayList<HashMap<String, String>> array = new ArrayList<>();

        Result = Result.replaceAll(".0000","");
        try {
            JSONObject jsonObject;
            jsonObject = new JSONObject(Result);
            if (jsonObject != null) {
                if (jsonObject.has(obj)) {
                    JSONArray jsonarray = jsonObject
                            .getJSONArray(obj);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobjData = jsonarray
                                .getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        Iterator<String> iter = jsonobjData.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                Object value = jsonobjData.get(key);
                                key = key.replaceAll(":", "");
//                                Log.e("-" + key, ":" + value + "=");
                                map.put(key, value.toString());
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        array.add(map);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }


        return array;
    }

    public static ArrayList<HashMap<String, String>> getData(String Result, String obj, ArrayList<HashMap<String, String>> array) {


        try {
            JSONObject jsonObject;
            jsonObject = new JSONObject(Result);
            if (jsonObject != null) {
                if (jsonObject.has(obj)) {
                    JSONArray jsonarray = jsonObject
                            .getJSONArray(obj);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobjData = jsonarray
                                .getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        Iterator<String> iter = jsonobjData.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                Object value = jsonobjData.get(key);
                                map.put(key, value.toString());
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        array.add(map);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        return array;
    }

    public static ArrayList<HashMap<String, String>> getData(String Result, String obj, String myKey) {
        ArrayList<HashMap<String, String>> array = new ArrayList<>();
        try {
            JSONObject jsonObject;
            jsonObject = new JSONObject(Result);
            if (jsonObject != null) {
                if (jsonObject.has(obj)) {
                    JSONArray jsonarray = jsonObject
                            .getJSONArray(obj);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobjData = jsonarray
                                .getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        Iterator<String> iter = jsonobjData.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                Object value = jsonobjData.get(key);
                                Log.e("-" + key, "-" + value.toString());
                                map.put(myKey, value.toString());
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        array.add(map);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }


        return array;
    }
    public static ArrayList<HashMap<String, String>> getData1(String Result, String obj, String myKey) {
        ArrayList<HashMap<String, String>> array = new ArrayList<>();
        try {
            JSONObject jsonObject;
            jsonObject = new JSONObject(Result);
            if (jsonObject != null) {
                if (jsonObject.has(obj)) {
                    JSONArray jsonarray = jsonObject
                            .getJSONArray(obj);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobjData = jsonarray
                                .getJSONObject(i);
                        Iterator<String> iter = jsonobjData.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                HashMap<String, String> map = new HashMap<String, String>();
                                Object value = jsonobjData.get(key);
                                Log.e("-" + key, "-" + value.toString());
                                map.put(myKey, value.toString());
                                array.add(map);
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }


        return array;
    }

    public static ArrayList<HashMap<String, String>> getData(String Result, String obj, String myKey, ArrayList<HashMap<String, String>> array) {


        try {
            JSONObject jsonObject;
            jsonObject = new JSONObject(Result);
            if (jsonObject != null) {
                if (jsonObject.has(obj)) {
                    JSONArray jsonarray = jsonObject
                            .getJSONArray(obj);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobjData = jsonarray
                                .getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();

                        Iterator<String> iter = jsonobjData.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                Object value = jsonobjData.get(key);
                                map.put(myKey, value.toString());
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        array.add(map);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        return array;
    }

    public static ArrayList<String> getDataSingle(String Result, String obj) {
        ArrayList<String> array = new ArrayList<>();
        try {
            JSONObject jsonObject;
            jsonObject = new JSONObject(Result);
            if (jsonObject != null) {
                if (jsonObject.has(obj)) {
                    JSONArray jsonarray = jsonObject
                            .getJSONArray(obj);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobjData = jsonarray
                                .getJSONObject(i);
                        Iterator<String> iter = jsonobjData.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                Object value = jsonobjData.get(key);
//                                HashMap<String, String> map = new HashMap<String, String>();
//                                map.put(key, value.toString());
                                array.add(value.toString());
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }


        return array;
    }

    public static ArrayList<String> getDataSingle(String Result, String obj, ArrayList<String> array) {


        try {
            JSONObject jsonObject;
            jsonObject = new JSONObject(Result);
            if (jsonObject != null) {
                if (jsonObject.has(obj)) {
                    JSONArray jsonarray = jsonObject
                            .getJSONArray(obj);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobjData = jsonarray
                                .getJSONObject(i);
                        Iterator<String> iter = jsonobjData.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                Object value = jsonobjData.get(key);
//                                HashMap<String, String> map = new HashMap<String, String>();
//                                map.put(key, value.toString());
                                array.add(value.toString());
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        return array;
    }

}
