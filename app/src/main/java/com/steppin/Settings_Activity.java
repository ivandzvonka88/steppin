package com.steppin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.admin.Admin;
import com.utils.MySession;
import com.utils.SetStatusBar;

public class Settings_Activity extends AppCompatActivity {
    RelativeLayout layEvents, layVenues, layDance, layNews;
    Switch swtcEvents, swtcVenues, swtcDance, swtcNews;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        SetStatusBar.set(this, R.color.clr_White);
        imgBack = findViewById(R.id.imgBack);
        layEvents = findViewById(R.id.layEvents);
        layVenues = findViewById(R.id.layVenues);
        layDance = findViewById(R.id.layDance);
        layNews = findViewById(R.id.layNews);
        swtcEvents = findViewById(R.id.swtcEvents);
        swtcVenues = findViewById(R.id.swtcVenues);
        swtcDance = findViewById(R.id.swtcDance);
        swtcNews = findViewById(R.id.swtcNews);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (MySession.getNoti(getApplicationContext(), MySession.ShareEventsNotification)) {
            swtcEvents.setChecked(true);
        } else {
            swtcEvents.setChecked(false);
        }
        if (MySession.getNoti(getApplicationContext(), MySession.ShareDanceNotification)) {
            swtcDance.setChecked(true);
        } else {
            swtcDance.setChecked(false);
        }
        if (MySession.getNoti(getApplicationContext(), MySession.ShareVenuesNotification)) {
            swtcVenues.setChecked(true);
        } else {
            swtcVenues.setChecked(false);
        }
        if (MySession.getNoti(getApplicationContext(), MySession.ShareNewsNotification)) {
            swtcNews.setChecked(true);
        } else {
            swtcNews.setChecked(false);
        }
        layEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swtcEvents.isChecked()) {
                    swtcEvents.setChecked(false);
                    MySession.set(getApplicationContext(), "0", MySession.ShareEventsNotification);
                } else {
                    MySession.set(getApplicationContext(), "1", MySession.ShareEventsNotification);
                    swtcEvents.setChecked(true);
                }
            }
        });
        layVenues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swtcVenues.isChecked()) {
                    swtcVenues.setChecked(false);
                    MySession.set(getApplicationContext(), "0", MySession.ShareVenuesNotification);
                } else {
                    MySession.set(getApplicationContext(), "1", MySession.ShareVenuesNotification);
                    swtcVenues.setChecked(true);
                }
            }
        });
        layDance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swtcDance.isChecked()) {
                    swtcDance.setChecked(false);
                    MySession.set(getApplicationContext(), "0", MySession.ShareDanceNotification);
                } else {
                    MySession.set(getApplicationContext(), "1", MySession.ShareDanceNotification);
                    swtcDance.setChecked(true);
                }
            }
        });
        layNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swtcNews.isChecked()) {
                    swtcNews.setChecked(false);
                    MySession.set(getApplicationContext(), "0", MySession.ShareNewsNotification);
                } else {
                    MySession.set(getApplicationContext(), "1", MySession.ShareNewsNotification);
                    swtcNews.setChecked(true);
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
