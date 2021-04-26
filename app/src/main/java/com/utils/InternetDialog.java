package com.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/*
 * Created by Raj on 4/4/2015.
 * this class use as a Dialog Box
 * this showing internet warning.
 */
public class InternetDialog {

    public static void showGPSDisabledAlertToUser(String Msg,
                                                  final Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setMessage(Msg).setCancelable(false);
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(
                                android.provider.Settings.ACTION_SETTINGS);
                        context.startActivity(callGPSSettingIntent);
                    }
                });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}
