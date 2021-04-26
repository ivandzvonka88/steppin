package com.admin;

/**
 * Created by Raj on 11/25/2016.
 */

public class City {

    public static final String TableName = "City";
    public String UID;
    public String CityName;
    public double lat;
    public double lng;

    public static final String Key_UID = "UID";
    public static final String Key_CityName = "CityName";
    public static final String Key_LAT = "lat";
    public static final String Key_LONG = "lng";

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public City() {
    }

    public City(String UID, String cityName, double lat, double lng) {
        this.UID = UID;
        this.CityName = cityName;
        this.lat = lat;
        this.lng = lng;
    }

    public City(String UID, String CityName) {
        this.UID = UID;
        this.CityName = CityName;
    }


    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }


}