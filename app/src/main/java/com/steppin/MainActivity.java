package com.steppin;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.admin.Admin;
import com.admin.Admin_Home;
import com.admin.Admin_Login;
import com.admin.City;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.utils.MyIntent;
import com.utils.MySession;
import com.utils.SetStatusBar;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CardView layStart;
ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetStatusBar.set(this, R.color.clr_White);
        TempData.Activity_Array = new ArrayList<>();
        layStart = (CardView) findViewById(R.id.layStart);
        img = (ImageView) findViewById(R.id.img);
        layStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntent.Goto(MainActivity.this, City_Activity.class);
            }
        });

        TempData.Activity_Array.add(this);
        if (BuildConfig.FLAVOR == "steppinadmin") {
            Log.e("UID","-"+MySession.get(getApplicationContext(), Admin.Key_UID));
            Log.e("StayLogin","-"+MySession.get(getApplicationContext(), Admin.Key_StayLogin));
            if (MySession.get(getApplicationContext(), Admin.Key_UID).equals("")){
                MyIntent.Goto(MainActivity.this, Admin_Login.class);
            }else{
                if (MySession.get(getApplicationContext(),Admin.Key_StayLogin).equals("1")){
                    MyIntent.Goto(MainActivity.this, Admin_Home.class);
                }else {
                    MyIntent.Goto(MainActivity.this, Admin_Login.class);
                }
            }
            finish();

        } else if (BuildConfig.FLAVOR == "steppin") {
            callStart();
        }

    }

    public void callStart() {
        MySession.set(getApplicationContext(),"",MySession.Key_CityName);
        if (MySession.get(getApplicationContext(), MySession.KeyCityNameWithLocation).equals("")){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //add your code here
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            YoYo.with(Techniques.FadeIn).withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                    img.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    YoYo.with(Techniques.SlideInUp).withListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {

                                            layStart.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {

                                        }
                                    }).duration(500)
                                            .playOn(layStart);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            }).duration(500)
                                    .playOn(img);
                        }
                    });
                }
            }, 500);
        }else{
            YoYo.with(Techniques.FadeIn).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                    img.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).duration(500)
                    .playOn(img);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //add your code here
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyIntent.Goto(MainActivity.this, Home.class);
                        }
                    });
                }
            }, 2000);
        }

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
