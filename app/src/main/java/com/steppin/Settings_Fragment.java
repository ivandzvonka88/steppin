package com.steppin;

import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.utils.MyAsync;
import com.utils.MyIntent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rajneesh on 15-Jan-18.
 */

public class Settings_Fragment extends Fragment {

    DataAsync dataAsync;
    RelativeLayout layNotification, layLocations;
    TextView txtVersion;

    public Settings_Fragment() {
        // Required empty public constructor
    }


    public void onStart() {
        super.onStart();

    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if (isVisibleToUser) {

        } else {

        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.settings_fragment, container, false);
//        listView = (ListView) v.findViewById(R.id.listView);
        layNotification = v.findViewById(R.id.layNotification);
        layLocations = v.findViewById(R.id.layLocations);
        txtVersion = v.findViewById(R.id.txtVersion);

        layNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntent.Goto(getActivity(), Settings_Activity.class);
            }
        });
        layLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntent.Goto(getActivity(), City_Update_Activity.class);
            }
        });
        setVersion();
        return v;
    }


    public void setVersion(){
        PackageInfo pinfo = null;
        try {
            pinfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//        int versionNumber = pinfo.versionCode;
        String versionName = pinfo.versionName;
        txtVersion.setText("App version: " + versionName);

    }
    public void onDestroy() {
        super.onDestroy();
//        if (!dataAsync.isCancelled()) {
//            dataAsync.cancel(true);
//        }
    }

    public void callData() {
        if (!dataAsync.isCancelled()) {
            dataAsync.cancel(true);
        }
        dataAsync = new DataAsync();
        MyAsync.callAsync(dataAsync);
    }

    private class DataAsync extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;
        String Result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pd = new ProgressDialog(getActivity());
//            pd.setMessage("Please Wait...");
//            pd.setCancelable(false);
//            pd.setCanceledOnTouchOutside(false);
//            pd.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
//            try {
//                if (pd.isShowing()) {
//                    pd.dismiss();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    public void setSongAdapter() {

    }
}
