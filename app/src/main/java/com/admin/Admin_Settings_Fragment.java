package com.admin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.steppin.R;
import com.utils.MyIntent;
import com.utils.MySession;

/**
 * Created by Rajneesh on 15-Jan-18.
 */

public class Admin_Settings_Fragment extends Fragment {

    RelativeLayout layNotification, layLocations, layAdminUser, layLogout;
    TextView txtVersion;

    public Admin_Settings_Fragment() {
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
        View v = inflater.inflate(R.layout.admin_settings_fragment, container, false);

        layNotification = v.findViewById(R.id.layNotification);
        layLocations = v.findViewById(R.id.layLocations);
        layLogout = v.findViewById(R.id.layLogout);
        layAdminUser = v.findViewById(R.id.layAdminUser);
        txtVersion = v.findViewById(R.id.txtVersion);
        layLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntent.Goto(getActivity(), City_List.class);
            }
        });
        layAdminUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyDialog dialog = new VerifyDialog(getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                dialog.show();
            }
        });
        layNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntent.Goto(getActivity(), Send_Notification.class);
            }
        });
        layLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Logout");
                alert.setMessage("Are you sure do you want to Logout?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MySession.logout(getActivity());
                        getActivity().finish();
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
    public class VerifyDialog extends Dialog {

        public Context c;
        LinearLayout layLogin;
        EditText edPassword;

        public VerifyDialog(Activity a) {
            super(a, R.style.MYDIALOG);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            setContentView(R.layout.admin_verify);

            edPassword = (EditText) findViewById(R.id.edPassword);
            layLogin = (LinearLayout) findViewById(R.id.layLogin);

            layLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edPassword.getText().toString().equals(MySession.get(getActivity(), Admin.Key_Password))) {
                        MyIntent.Goto(getActivity(), AdminList.class);
                        dismiss();
                    }else{
                        edPassword.setError("Please Enter Your Valid Password");
                        edPassword.requestFocus();
                    }
                }
            });
        }


    }

}
