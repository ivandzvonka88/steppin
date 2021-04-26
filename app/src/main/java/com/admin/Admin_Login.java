package com.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.steppin.R;
import com.steppin.TempData;
import com.utils.ConnectionDetector;
import com.utils.MyIntent;
import com.utils.MySession;
import com.utils.SetStatusBar;
import com.utils.ToastMsg;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Admin_Login extends AppCompatActivity {
    TextInputEditText edUserName, edPassword;
    String sUserName = "", sPassword = "";
    LinearLayout layLogin;

    FirebaseFirestore db;
    ConnectionDetector cn;
    CheckBox chkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
        SetStatusBar.set(this,R.color.clr_White);
        ImageView imgLeft = (ImageView) findViewById(R.id.imgLeft);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText("Admin Login");
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassword);
        layLogin = (LinearLayout) findViewById(R.id.layLogin);
        chkLogin = (CheckBox) findViewById(R.id.chkLogin);

        db = FirebaseFirestore.getInstance();
        cn = new ConnectionDetector(getApplicationContext());

        layLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserName = edUserName.getText().toString();
                sPassword = edPassword.getText().toString();
                if (!sUserName.equals("")) {
                    if (!sPassword.equals("")) {
                        login();
                    } else {
                        edPassword.setError("Please Enter Your Password");
                        edPassword.requestFocus();
                    }
                } else {
                    edUserName.setError("Please Enter Your Username");
                    edUserName.requestFocus();
                }
            }
        });
    }

    public void login() {
        final ProgressDialog pd = new ProgressDialog(Admin_Login.this);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        Query query = null;
        query = db.collection(Admin.TableName).whereEqualTo(Admin.Key_Username, sUserName);

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        try {
                            if (pd.isShowing()) {
                                pd.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (task.isSuccessful()) {
                            Log.e("GetData", "isSuccessful");
                            Log.e("Data", " => " + task.getResult());
                            Log.e("Data", " => " + task.getException());
                            if (task.getResult().size() == 0) {
                                checkAdmin();
                                ToastMsg.mToastMsg(getApplicationContext(), "!Oops something went wrong, please try again.", 1);
                            }
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.e("GetData", document.getId() + " => " + document.getData());

                                Admin admin = document.toObject(Admin.class);
//

                                if (admin.Username.equals(sUserName) && admin.Password.equals(sPassword)) {
                                    if (admin.IsActive.equals("1")) {
//                                        FirebaseMessaging.getInstance().subscribeToTopic(TempData.NotiAdminChannale);
                                        MySession.set(getApplicationContext(), admin.Username, Admin.Key_Username);
                                        MySession.set(getApplicationContext(), admin.Password, Admin.Key_Password);
                                        MySession.set(getApplicationContext(), admin.UID, Admin.Key_UID);
                                        MySession.set(getApplicationContext(), admin.IsActive, Admin.Key_IsActive);
                                        if (chkLogin.isChecked()) {
                                            MySession.set(getApplicationContext(), "1", Admin.Key_StayLogin);
                                        } else {
                                            MySession.set(getApplicationContext(), "0", Admin.Key_StayLogin);
                                        }
                                        Map<String, Object> user = new HashMap<>();
                                        user.put(Admin.Key_AccessToken, MySession.getAccessToken(getApplicationContext()));
                                        Task mtask = db.collection(Admin.TableName).document(admin.UID).update(user);
                                        goToAdminHome();
                                    } else {
                                        ToastMsg.mToastMsg(getApplicationContext(), "User not allow to use app", 1);
                                    }
                                } else {
                                    ToastMsg.mToastMsg(getApplicationContext(), "Username or password not valid.", 1);
                                }
                            }


                        } else {
                            Log.e("GetData", "Error getting documents. " + task.getException());
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("OnFailureListener", "-" + e.getMessage());
                try {
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void checkAdmin() {
        if (sUserName.toLowerCase().equals("admin") && sPassword.toLowerCase().equals("admin")) {
            final ProgressDialog pd = new ProgressDialog(Admin_Login.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            Query query = null;
            query = db.collection(Admin.TableName);

            query.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            try {
                                if (pd.isShowing()) {
                                    pd.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (task.isSuccessful()) {
                                if (task.getResult().size() == 0) {
                                    addAdmin();
                                }
                            } else {
                                Log.e("GetData", "Error getting documents. " + task.getException());
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("OnFailureListener", "-" + e.getMessage());
                    try {
                        if (pd.isShowing()) {
                            pd.dismiss();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }

    }


    public void addAdmin() {
        final String UID = "" + new Date().getTime();
        Map<String, Object> admin = new HashMap<>();
        admin.put(Admin.Key_UID, UID);
        admin.put(Admin.Key_Username, sUserName);
        admin.put(Admin.Key_Password, sPassword);
        admin.put(Admin.Key_IsActive, "1");
        admin.put(Admin.Key_AccessToken, MySession.getAccessToken(getApplicationContext()));
        final ProgressDialog pd = new ProgressDialog(Admin_Login.this);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        Task task = null;
        try {
            task = db.collection(Admin.TableName).document(UID).set(admin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        task.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                try {
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
                MySession.set(getApplicationContext(), UID, Admin.Key_UID);
                MySession.set(getApplicationContext(), sUserName, Admin.Key_Username);
                MySession.set(getApplicationContext(), sPassword, Admin.Key_Password);
                MySession.set(getApplicationContext(), "1", Admin.Key_IsActive);
                goToAdminHome();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        try {
                            if (pd.isShowing()) {
                                pd.dismiss();
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        ToastMsg.mToastMsg(getApplicationContext(), "" + e.getMessage(), 1);
                        Log.e("Error Add", "-" + e.getMessage());
                    }
                });
    }

    public void goToAdminHome() {
        MyIntent.Goto(Admin_Login.this, Admin_Home.class);
        finish();
        overridePendingTransition(0, 0);
    }

}
