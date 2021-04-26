package com.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Raj on 10/4/2015.
 * this class use Displaying message
 */
public class ToastMsg {
    //this class call for Toast Message.
    public static void mToastMsg(Context context, String msg, int val) {

        if (val == 1) {
            Toast toast = Toast.makeText(context,
                    msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);
            toast.show();
        } else {
            Toast toast = Toast.makeText(context,
                    msg, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);
            toast.show();
        }


    }

//    //this class call for Snak Bar Message.
//    public static void mSnakMsg(View view, String msg, int val) {
//        if (val == 1) {
//            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
//        } else {
//            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
//        }
//
//
//    }
}
