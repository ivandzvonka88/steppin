package com.steppin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FCM.Notification_Data;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.utils.GetResources;
import com.utils.MyIntent;
import com.utils.MySession;
import com.utils.SetStatusBar;

public class Home extends AppCompatActivity {
    private static final String TAG = "Home";
    ViewPager viewPager;
    LinearLayout layEvents, layVenues, layDance, laySettings;
    ImageView imgEvents, imgVenues, imgDance, imgSettings;
    TextView txtEvents, txtVenues, txtDance, txtSettings;
    CardView CardNoti;
    View vNoti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        final AdView adView = findViewById(R.id.adView);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
            }
        });

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                adView.setVisibility(View.VISIBLE);
                Log.e(TAG, "onAdLoaded: ");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                adView.setVisibility(View.GONE);
                Log.e(TAG, "onAdFailedToLoad: " + errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.e(TAG, "onAdOpened: ");
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.e(TAG, "onAdClicked: ");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.e(TAG, "onAdLeftApplication: ");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Log.e(TAG, "onAdClosed: ");
            }
        });

        SetStatusBar.set(this,R.color.clr_White);
        layEvents = findViewById(R.id.layEvents);
        layVenues = findViewById(R.id.layVenues);
        layDance = findViewById(R.id.layDance);
        laySettings = findViewById(R.id.laySettings);
        imgEvents = findViewById(R.id.imgEvents);
        imgVenues = findViewById(R.id.imgVenues);
        imgDance = findViewById(R.id.imgDance);
        imgSettings = findViewById(R.id.imgSettings);
        txtEvents = findViewById(R.id.txtEvents);
        txtVenues = findViewById(R.id.txtVenues);
        txtDance = findViewById(R.id.txtDance);
        txtSettings = findViewById(R.id.txtSettings);
        viewPager = findViewById(R.id.viewPager);
        CardNoti = findViewById(R.id.CardNoti);
        vNoti = findViewById(R.id.vNoti);
        vNoti.setVisibility(View.GONE);

        Fragment_Adapter fragment_adapter = new Fragment_Adapter(getSupportFragmentManager());
        viewPager.setAdapter(fragment_adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        layEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0, true);
            }
        });
        layVenues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1, true);
            }
        });
        layDance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2, true);
            }
        });
        laySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3, true);
            }
        });
        CardNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntent.Goto(Home.this,Notification_Activity.class);
            }
        });
        String KEY = "";
        try {
            Bundle b = getIntent().getExtras();
            if (b!=null){
                KEY = ""+b.getString(Notification_Data.Key_type);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (KEY.equals(MySession.ShareVenuesNotification)){
            setSelection(1);
        }else if (KEY.equals(MySession.ShareDanceNotification)){
            setSelection(2);
        }else{
            setSelection(0);
        }

        callStart();
        subscribeChannel();

    }
public void subscribeChannel(){

    if (BuildConfig.FLAVOR == "steppinadmin") {
        if (!MySession.get(getApplicationContext(),TempData.NotiChannaleAdmin).equals("1")){
            FirebaseMessaging.getInstance().subscribeToTopic(TempData.NotiChannaleAdmin);
            MySession.set(getApplicationContext(),"1",TempData.NotiChannaleAdmin);
        }
    } else if (BuildConfig.FLAVOR == "steppin") {
        if (!MySession.get(getApplicationContext(),TempData.NotiChannaleClients).equals("1")){
            FirebaseMessaging.getInstance().subscribeToTopic(TempData.NotiChannaleClients);
            MySession.set(getApplicationContext(),"1",TempData.NotiChannaleClients);
        }
    }
}
    public void onStart() {
        super.onStart();
        if (Notification_Data.unReadNotiArray(getApplicationContext())>0){
            vNoti.setVisibility(View.VISIBLE);
        }else{
            vNoti.setVisibility(View.GONE);
        }
    }
    public void setSelection(int Poss) {
        if (Poss == 0) {
            imgEvents.setImageResource(R.drawable.events_2);
            imgVenues.setImageResource(R.drawable.venues_1);
            imgDance.setImageResource(R.drawable.daince_1);
            imgSettings.setImageResource(R.drawable.settings_1);
            txtEvents.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_Black));
            txtVenues.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_subTitle));
            txtDance.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_subTitle));
            txtSettings.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_subTitle));
        } else if (Poss == 1) {
            imgEvents.setImageResource(R.drawable.events_1);
            imgVenues.setImageResource(R.drawable.venues_2);
            imgDance.setImageResource(R.drawable.daince_1);
            imgSettings.setImageResource(R.drawable.settings_1);
            txtEvents.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_subTitle));
            txtVenues.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_Black));
            txtDance.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_subTitle));
            txtSettings.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_subTitle));
        }
        if (Poss == 2) {
            imgEvents.setImageResource(R.drawable.events_1);
            imgVenues.setImageResource(R.drawable.venues_1);
            imgDance.setImageResource(R.drawable.daince_2);
            imgSettings.setImageResource(R.drawable.settings_1);
            txtEvents.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_subTitle));
            txtVenues.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_subTitle));
            txtDance.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_Black));
            txtSettings.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_subTitle));
        }
        if (Poss == 3) {
            imgEvents.setImageResource(R.drawable.events_1);
            imgVenues.setImageResource(R.drawable.venues_1);
            imgDance.setImageResource(R.drawable.daince_1);
            imgSettings.setImageResource(R.drawable.settings_2);
            txtEvents.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_subTitle));
            txtVenues.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_subTitle));
            txtDance.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_subTitle));
            txtSettings.setTextColor(GetResources.getColor(getApplicationContext(),R.color.clr_Black));
        }
    }

    public void callStart() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //add your code here
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (int i = 0; i < TempData.Activity_Array.size(); i++) {
                                try {
                                    TempData.Activity_Array.get(i).finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, 800);
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
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
