package com.steppin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.FCM.Notification_Data;
import com.admin.Admin_Events_Fragment;
import com.admin.Admin_Home;
import com.utils.MyAsync;
import com.utils.SetStatusBar;
import com.utils.ToastMsg;

import java.util.ArrayList;
import java.util.HashMap;

public class Notification_Activity extends AppCompatActivity {

    TextView txtTitle;
    ListView listView;
    ArrayList<HashMap<String, String>> Noti_Array = new ArrayList<>();
    ReadAsync readAsync;
ImageView imgNoData;
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        SetStatusBar.set(this, R.color.clr_White);
        ImageView imgLeft = (ImageView) findViewById(R.id.imgLeft);
        ImageView imgRight = (ImageView) findViewById(R.id.imgRight);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText("Notifications");
        imgRight.setImageResource(R.drawable.delete_icon_main);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        imgNoData = (ImageView) findViewById(R.id.imgNoData);
        readAsync = new ReadAsync();
        setData();

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Notification_Activity.this);
                alert.setTitle("Clear Notification");
                alert.setMessage("Are you sure do you want to clear all notification?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Notification_Data.clearNoti(getApplicationContext());
                        setData();
                        ToastMsg.mToastMsg(getApplicationContext(), "Cleared all notifications", 1);
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
        });
    }

    public void onDestroy() {
        super.onDestroy();
        if (!readAsync.isCancelled()) {
            readAsync.cancel(true);
        }
    }

    public void seReadData() {
        if (!readAsync.isCancelled()) {
            readAsync.cancel(true);
        }
        readAsync = new ReadAsync();
        MyAsync.callAsync(readAsync);
    }

    public void setData() {
        try {
            listView.setAdapter(null);
            Noti_Array = new ArrayList<>();
            Noti_Array = Notification_Data.getNotiArray(getApplicationContext());
            if (Noti_Array.size() == 0) {
                imgNoData.setVisibility(View.VISIBLE);
            } else {
                imgNoData.setVisibility(View.GONE);
            }
            Notification_List_Adapter notification_list_adapter = new Notification_List_Adapter(Notification_Activity.this, Noti_Array);
            notification_list_adapter.notifyDataSetChanged();
            listView.setAdapter(notification_list_adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        seReadData();

    }

    private class ReadAsync extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {


            Notification_Data.readNotiArray(getApplicationContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
        }

    }

    public void back() {

        finish();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        back();
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
