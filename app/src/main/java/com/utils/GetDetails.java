package com.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 * Created by Raj on 4/4/2015.
 * this class use for get device details.
 */
public class GetDetails {
    public static final String PREFERENCES_UID = "UID";
    public static final String Share_UID = "UID";
    public static SharedPreferences sharedpreferences_UID;

    //this method return unique ID
    public static String getUID(Context context) {

        String sIMEI = GetDetails.getIMEI(context);
        String sMacAddress = GetDetails.getMacAddress(context);
        String sAndroidId = GetDetails.getAndroidId(context);
        String sID = "";
        if (sID.equals("") || sID.toLowerCase().equals("null") || sID.contains("02:00:00")) {
            sID = sIMEI;
        }
        if (sID.equals("") || sID.toLowerCase().equals("null") || sID.contains("02:00:00")) {
            sID = sAndroidId;
        }
        if (sID.equals("") || sID.toLowerCase().equals("null") || sID.contains("02:00:00")) {
            sID = sMacAddress;
        }

        return sID;
    }

    //this method return device MANUFACTURER
    public static String getMANUFACTURER() {

        String Uid = "MANUFACTURER: " + android.os.Build.MANUFACTURER;
        return Uid;
    }
    //this method return device MANUFACTURER
    public static String getMANUFACTURER1() {

        String Uid = " " + android.os.Build.MANUFACTURER;
        return Uid;
    }

    //this method return device APILEVEL
    public static String getAPILEVEL() {
        String Uid = "APILEVEL: " + android.os.Build.VERSION.SDK_INT;
        return Uid;
    }

    //this method return device deatls
    public static String getDEVICE() {
        String Uid = "Device: " + android.os.Build.DEVICE + "   Model: " + android.os.Build.MODEL;
        return Uid;
    }
    //this method return device deatls
    public static String getDEVICE1() {
        String Uid = " " + android.os.Build.DEVICE;
        return Uid;
    }

    public static String getAllDetails() {
        String Uid = getMANUFACTURER() + "  " + getDEVICE() + "  " + getAPILEVEL();
        return Uid;
    }

    //this method return device IMEI
    public static String getIMEI(Context context) {
        String Uid = "";
        try {
            TelephonyManager mngr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Uid = "" + mngr.getDeviceId();
        } catch (Exception e) {
            // TODO: handle exception
            Uid = "";
        }

        return Uid;
    }

    //this method return device Account
    public static String getAccount(Context context) {
        String Uid = "";

        try {
            Account[] accounts = AccountManager.get(context).getAccountsByType(
                    "com.google");
            for (Account account : accounts) {
                // Log.e("Account", account.name + " : " + account.type);
                Uid = account.name;
            }
        } catch (Exception e) {
            // TODO: handle exception
            Uid = "";
        }
        return Uid;
    }

    //this method return device MacAddress
    public static String getMacAddress(Context context) {
        String Uid = "";
        try {
            WifiManager m_wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            Uid = m_wm.getConnectionInfo().getMacAddress();

        } catch (Exception e) {
            // TODO: handle exception
            Uid = "";
        }
        return Uid;
    }

    //this method return device AndroidId
    public static String getAndroidId(Context context) {
        String Uid = "";
        try {
            Uid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            // TODO: handle exception
            Uid = "";
        }
        return Uid;
    }

    //this method return current date and time.
    public static String getDateTimeYMD() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }

    //this method return current date.
    public static String getDateYMD() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }

    public static String getDateDMY() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }

    //this method return current date and time.
    public static String getDateTimeDMY() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }
}
