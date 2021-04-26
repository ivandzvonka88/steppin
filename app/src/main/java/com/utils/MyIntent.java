package com.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Raj on 10/4/2015.
 * this class use for Intent
 */
public class MyIntent {
    //this method call when Use Intent Without Bundle.
    public static void Goto(Context context, Class mClass) {
        Intent intent = new Intent(context, mClass);
        context.startActivity(intent);
    }

    //this method call when Use Intent with Bundle.
    public static void Goto(Context context, Class mClass, Bundle bundle) {
        Intent intent = new Intent(context, mClass);
        Bundle b = bundle;
        intent.putExtras(b);
        context.startActivity(intent);
    }
}
