package com.admin;

/**
 * Created by Raj on 11/25/2016.
 */

public class Admin {

    public static final String TableName = "Admin";
    public String UID;
    public String Username;
    public String Password;
    public String IsActive;
    public String AccessToken;

    public static final String Key_UID = "UID";
    public static final String Key_Username= "Username";
    public static final String Key_Password = "Password";
    public static final String Key_IsActive = "IsActive";
    public static final String Key_AccessToken = "AccessToken";
    public static final String Key_StayLogin = "StayLogin";

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Admin() {
    }

    public Admin(String UID, String Username, String Password, String IsActive, String AccessToken) {

        this.UID = UID;
        this.Username = Username;
        this.Password = Password;
        this.IsActive = IsActive;
        this.AccessToken = AccessToken;
    }
}