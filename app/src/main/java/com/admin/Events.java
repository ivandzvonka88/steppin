package com.admin;

import java.util.ArrayList;

/**
 * Created by Raj on 11/25/2016.
 */

public class Events {

    public static final String TableName = "Events";
    public String UID;
    public String Title;
    public String Details;
    public String Location;
    public String Price;
    public String DateTime;
    public String Mobile;
    public String CityID;
    public String CityName;
    public ArrayList<String> Images = new ArrayList<>();

    public static final String Key_UID = "UID";
    public static final String Key_Title = "Title";
    public static final String Key_Details = "Details";
    public static final String Key_Location = "Location";
    public static final String Key_Price = "Price";
    public static final String Key_DateTime = "DateTime";
    public static final String Key_Mobile = "Mobile";
    public static final String Key_CityID = "CityID";
    public static final String Key_CityName = "CityName";
    public static final String Key_Images = "Images";

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Events() {
    }

    public Events(String UID, String Title, String Details, String Location, String Price, String DateTime, String Mobile, String CityID, String CityName, ArrayList<String> Images) {

        this.UID = UID;
        this.Title = Title;
        this.Details = Details;
        this.Location = Location;
        this.Price = Price;
        this.DateTime = DateTime;
        this.Mobile = Mobile;
        this.CityID = CityID;
        this.CityName = CityName;
        this.Images = Images;
    }
}