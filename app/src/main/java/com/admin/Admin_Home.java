package com.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.steppin.BuildConfig;
import com.steppin.R;
import com.steppin.TempData;
import com.steppin.Venues_Fragment;
import com.utils.GetResources;
import com.utils.MySession;
import com.utils.SetStatusBar;
import com.utils.ToastMsg;

public class Admin_Home extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout layEvents, layVenues, layDance, laySettings;
    ImageView imgEvents, imgVenues, imgDance, imgSettings;
    TextView txtEvents, txtVenues, txtDance, txtSettings;
    Admin_Fragment_Adapter fragment_adapter;
    boolean isFirst = true;
    public static final int eventCode = 111, venuesCode = 222, danceCode = 333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
        SetStatusBar.set(this, R.color.clr_White);
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
        fragment_adapter = new Admin_Fragment_Adapter(getSupportFragmentManager());
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
        setSelection(0);
    }

    public void setSelection(int Poss) {
        if (Poss == 0) {
            imgEvents.setImageResource(R.drawable.events_2);
            imgVenues.setImageResource(R.drawable.venues_1);
            imgDance.setImageResource(R.drawable.daince_1);
            imgSettings.setImageResource(R.drawable.settings_1);
            txtEvents.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_Black));
            txtVenues.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_subTitle));
            txtDance.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_subTitle));
            txtSettings.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_subTitle));
        } else if (Poss == 1) {
            imgEvents.setImageResource(R.drawable.events_1);
            imgVenues.setImageResource(R.drawable.venues_2);
            imgDance.setImageResource(R.drawable.daince_1);
            imgSettings.setImageResource(R.drawable.settings_1);
            txtEvents.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_subTitle));
            txtVenues.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_Black));
            txtDance.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_subTitle));
            txtSettings.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_subTitle));
        }
        if (Poss == 2) {
            imgEvents.setImageResource(R.drawable.events_1);
            imgVenues.setImageResource(R.drawable.venues_1);
            imgDance.setImageResource(R.drawable.daince_2);
            imgSettings.setImageResource(R.drawable.settings_1);
            txtEvents.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_subTitle));
            txtVenues.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_subTitle));
            txtDance.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_Black));
            txtSettings.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_subTitle));
        }
        if (Poss == 3) {
            imgEvents.setImageResource(R.drawable.events_1);
            imgVenues.setImageResource(R.drawable.venues_1);
            imgDance.setImageResource(R.drawable.daince_1);
            imgSettings.setImageResource(R.drawable.settings_2);
            txtEvents.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_subTitle));
            txtVenues.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_subTitle));
            txtDance.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_subTitle));
            txtSettings.setTextColor(GetResources.getColor(getApplicationContext(), R.color.clr_Black));
        }
        subscribeChannel();
    }

    public void subscribeChannel() {

        if (BuildConfig.FLAVOR == "steppinadmin") {
            if (!MySession.get(getApplicationContext(), TempData.NotiChannaleAdmin).equals("1")) {
                FirebaseMessaging.getInstance().subscribeToTopic(TempData.NotiChannaleAdmin);
                MySession.set(getApplicationContext(), "1", TempData.NotiChannaleAdmin);
            }
        } else if (BuildConfig.FLAVOR == "steppin") {
            if (!MySession.get(getApplicationContext(), TempData.NotiChannaleClients).equals("1")) {
                FirebaseMessaging.getInstance().subscribeToTopic(TempData.NotiChannaleClients);
                MySession.set(getApplicationContext(), "1", TempData.NotiChannaleClients);
            }
        }
    }

    public void onStart() {
        super.onStart();
        if (isFirst) {
            isFirst = false;
        } else {

        }

    }

    public void deleteEvents(final int poss) {
        AlertDialog.Builder alert = new AlertDialog.Builder(Admin_Home.this);
        alert.setTitle("Delete");
        alert.setMessage("Are you sure do you want to delete?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Admin_Events_Fragment fragment = (Admin_Events_Fragment) fragment_adapter.Fragment_Array.get(0);
                fragment.callDelete(poss);
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    public void deleteVenues(final int poss) {
        AlertDialog.Builder alert = new AlertDialog.Builder(Admin_Home.this);
        alert.setTitle("Delete");
        alert.setMessage("Are you sure do you want to delete?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Admin_Venues_Fragment fragment = (Admin_Venues_Fragment) fragment_adapter.Fragment_Array.get(1);
                fragment.callDelete(poss);
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    public void deleteDance(final int poss) {
        AlertDialog.Builder alert = new AlertDialog.Builder(Admin_Home.this);
        alert.setTitle("Delete");
        alert.setMessage("Are you sure do you want to delete?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Admin_Dance_Fragment fragment = (Admin_Dance_Fragment) fragment_adapter.Fragment_Array.get(2);
                fragment.callDelete(poss);
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("requestCode", "-" + requestCode);
        if (requestCode == eventCode && resultCode == RESULT_OK) {
            Admin_Events_Fragment fragment = (Admin_Events_Fragment) fragment_adapter.Fragment_Array.get(0);
            fragment.sCityID = data.getStringExtra(City.Key_UID);
            fragment.sCityName = data.getStringExtra(City.Key_CityName);
            fragment.txtCity.setText(fragment.sCityName);
            fragment.changeCity();
        } else if (requestCode == venuesCode && resultCode == RESULT_OK) {
            Admin_Venues_Fragment fragment = (Admin_Venues_Fragment) fragment_adapter.Fragment_Array.get(1);
            fragment.sCityID = data.getStringExtra(City.Key_UID);
            fragment.sCityName = data.getStringExtra(City.Key_CityName);
            fragment.txtCity.setText(fragment.sCityName);
            fragment.changeCity();
        } else if (requestCode == danceCode && resultCode == RESULT_OK) {
            Admin_Dance_Fragment fragment = (Admin_Dance_Fragment) fragment_adapter.Fragment_Array.get(2);
            fragment.sCityID = data.getStringExtra(City.Key_UID);
            fragment.sCityName = data.getStringExtra(City.Key_CityName);
            fragment.txtCity.setText(fragment.sCityName);
            fragment.changeCity();
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

    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
