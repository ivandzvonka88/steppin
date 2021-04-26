package com.admin;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.steppin.R;
import com.utils.ConnectionDetector;
import com.utils.MyAsync;
import com.utils.SetStatusBar;
import com.utils.ToastMsg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class City_List extends AppCompatActivity {
    ListView listView;
    FloatingActionButton fabAdd;
    ArrayList<HashMap<String, String>> Temp_Array = new ArrayList<>();
    ArrayList<HashMap<String, String>> Data_Array = new ArrayList<>();
    HashMap<String, String> selectedMap = new HashMap<>();
    boolean isRemove = false;
    int selectedPoss = 0;
    ConnectionDetector cn;
    FirebaseFirestore db;
    City_List_Adapter city_list_adapter;
    EditText edSearch;
    SearchAsync searchAsync;
    RemoveEditAsync removeEditAsync;
    String strSearch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list);
        SetStatusBar.set(this, R.color.clr_White);
        ImageView imgLeft = (ImageView) findViewById(R.id.imgLeft);
        ImageView imgRight = (ImageView) findViewById(R.id.imgRight);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText("City List");
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cn = new ConnectionDetector(getApplicationContext());
        db = FirebaseFirestore.getInstance();
        listView = (ListView) findViewById(R.id.listView);
        edSearch = (EditText) findViewById(R.id.edSearch);
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);


        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                strSearch = edSearch.getText().toString();
                callSearch();
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cn.isConnectingToInternet()) {
                    UserDialog dialog = new UserDialog(City_List.this, false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(true);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    dialog.show();
                } else {
                    ToastMsg.mToastMsg(getApplicationContext(), "Please start your internet.", 1);
                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(City.Key_UID, Data_Array.get(i).get(City.Key_UID));
                returnIntent.putExtra(City.Key_CityName, Data_Array.get(i).get(City.Key_CityName));
                setResult(RESULT_OK, returnIntent);
                finish();

            }
        });

        removeEditAsync = new RemoveEditAsync();
        searchAsync = new SearchAsync();
        callSetData();
    }

    ArrayList<HashMap<String, String>> mArray = new ArrayList<>();

    ProgressDialog pd = null;

    public void callSetData() {
        mArray = new ArrayList<>();
        Temp_Array = new ArrayList<>();
        callSearch();

        if (cn.isConnectingToInternet()) {
            pd = new ProgressDialog(City_List.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(true);
            pd.setCanceledOnTouchOutside(false);
            pd.show();


            Query query = null;
            try {
                query = db.collection(City.TableName).orderBy(City.Key_CityName, Query.Direction.ASCENDING);
//            if (TempData.User_Type.equals(TempData.User_CUSTOMER)) {
//                query = db.collection(User.TableName).whereEqualTo(User.Key_UserType, TempData.User_CUSTOMER);
//            } else {
//                query = db.collection(User.TableName).whereGreaterThanOrEqualTo(User.Key_UserType, TempData.User_SALESMAN);
//            }
            } catch (Exception e) {
                e.printStackTrace();
            }
// Source can be CACHE, SERVER, or DEFAULT.
            Source source = Source.DEFAULT;
            if (cn.isConnectingToInternet()) {
                source = Source.SERVER;
            } else {
                source = Source.CACHE;
            }
            query.get(source).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            // Document found in the offline cache
                            City cityData = document.toObject(City.class);
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(City.Key_UID, cityData.UID);
                            map.put(City.Key_CityName, cityData.CityName);
                            mArray.add(map);
                        }

                    } else {

                    }
                    Temp_Array = mArray;
                    callSearch();
                    try {
                        if (pd != null) {
                            if (pd.isShowing()) {
                                pd.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public void onDestroy() {
        super.onDestroy();
        if (!searchAsync.isCancelled()) {
            searchAsync.cancel(true);
        }
        if (!removeEditAsync.isCancelled()) {
            removeEditAsync.cancel(true);
        }
    }

    public void callSearch() {
        if (!searchAsync.isCancelled()) {
            searchAsync.cancel(true);
        }
        searchAsync = new SearchAsync();
        MyAsync.callAsync(searchAsync);
    }

    public void callRemoveEdit() {
        if (!removeEditAsync.isCancelled()) {
            removeEditAsync.cancel(true);
        }
        removeEditAsync = new RemoveEditAsync();
        MyAsync.callAsync(removeEditAsync);
    }

    private class RemoveEditAsync extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;
        String Result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            pd = new ProgressDialog(SearchEvent.this);
//            pd.setMessage("Please Wait...");
//            pd.setCancelable(false);
//            pd.setCanceledOnTouchOutside(false);
//            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {


            for (int j = 0; j < Temp_Array.size(); j++) {
                HashMap<String, String> map = Temp_Array.get(j);

                if (selectedMap.get(City.Key_UID).equals(map.get(City.Key_UID))) {
                    if (isRemove) {
                        Temp_Array.remove(j);
                    } else {
                        Temp_Array.set(j, selectedMap);
                    }
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
//
//            try {
//                if (pd.isShowing()) {
//                    pd.dismiss();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    private class SearchAsync extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;
        String Result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            pd = new ProgressDialog(SearchEvent.this);
//            pd.setMessage("Please Wait...");
//            pd.setCancelable(false);
//            pd.setCanceledOnTouchOutside(false);
//            pd.show();
            Data_Array = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {

            strSearch = strSearch.toLowerCase();
            strSearch = strSearch.replaceAll("\\s+", "");
            strSearch = strSearch.replaceAll("[\n\r]", "");
            strSearch = strSearch.replaceAll(" ", "");
            strSearch = strSearch.replaceAll("\r", "");
            for (int j = 0; j < Temp_Array.size(); j++) {
                HashMap<String, String> map = Temp_Array.get(j);
                String sS = map.toString().toLowerCase();
                sS = sS.replaceAll("\\s+", "");
                sS = sS.replaceAll("[\n\r]", "");
                sS = sS.replaceAll(" ", "");
                sS = sS.replaceAll("\r", "");
                if (sS.contains(strSearch.toLowerCase()) || strSearch.equals("")) {
                    Data_Array.add(map);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
//
//            try {
//                if (pd.isShowing()) {
//                    pd.dismiss();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            city_list_adapter = new City_List_Adapter(City_List.this, Data_Array);
            city_list_adapter.notifyDataSetChanged();
            listView.setAdapter(city_list_adapter);
        }
    }

    public void callEditData(int Poss) {
        isRemove = false;
        selectedPoss = Poss;
        if (cn.isConnectingToInternet()) {
            UserDialog userDialog = new UserDialog(City_List.this, true);
            userDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            userDialog.setCanceledOnTouchOutside(true);
            userDialog.setCancelable(true);
            userDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            userDialog.show();
        } else {
            ToastMsg.mToastMsg(getApplicationContext(), "Please start your internet.", 1);
        }

    }

    public void callDeleteData(final int Poss) {
        isRemove = true;
        selectedPoss = Poss;
        selectedMap = Data_Array.get(Poss);
        if (cn.isConnectingToInternet()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(City_List.this);
            alert.setTitle("Delete");
            alert.setMessage("Are you sure do you want to delete this city?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.collection(City.TableName).document(Data_Array.get(Poss).get(City.Key_UID)).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Data_Array.remove(Poss);
                            try {
                                city_list_adapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            callRemoveEdit();
                            ToastMsg.mToastMsg(getApplicationContext(), "Successfully deleted.", 1);
                        }
                    });
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
        } else {
            ToastMsg.mToastMsg(getApplicationContext(), "Please start your internet.", 1);
        }

    }

    public class UserDialog extends Dialog {

        public Context c;
        TextView txtTitleDialog, txtAdd;
        TextInputEditText edCityName;
        String sUID = "", sCityName = "";
        LinearLayout layCancel, layAdd;
        boolean isUpdate = false;

        public UserDialog(Activity a, boolean isUpdate) {
            super(a, R.style.MYDIALOG);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.isUpdate = isUpdate;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            setContentView(R.layout.add_city_dialog);

            layAdd = (LinearLayout) findViewById(R.id.layAdd);
            layCancel = (LinearLayout) findViewById(R.id.layCancel);
            edCityName = findViewById(R.id.edCityName);
            txtTitleDialog = (TextView) findViewById(R.id.txtTitleDialog);
            txtAdd = (TextView) findViewById(R.id.txtAdd);

            layCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            if (isUpdate) {
                txtTitleDialog.setText("Update City");
                txtAdd.setText("Update now");
            } else {
                txtTitleDialog.setText("Add City");
                txtAdd.setText("Add Now");
            }
            if (isUpdate) {
                edCityName.setText(Data_Array.get(selectedPoss).get(City.Key_CityName));
                sUID = Data_Array.get(selectedPoss).get(City.Key_UID);
            } else {
                sUID = "" + new Date().getTime();
            }
            layAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sCityName = edCityName.getText().toString();

                    if (!sCityName.equals("")) {
                        Map<String, Object> city = new HashMap<>();
                        city.put(City.Key_UID, sUID);
                        city.put(City.Key_CityName, sCityName);

                        final ProgressDialog pd = new ProgressDialog(City_List.this);
                        pd.setMessage("Please Wait...");
                        pd.setCancelable(false);
                        pd.setCanceledOnTouchOutside(false);
                        pd.show();

                        Task task = null;
                        try {
                            task = db.collection(City.TableName).document(sUID).set(city);
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
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put(City.Key_UID, sUID);
                                map.put(City.Key_CityName, sCityName);
                                if (isUpdate) {
                                    Data_Array.set(selectedPoss, map);
                                    selectedMap = map;
                                    ToastMsg.mToastMsg(getApplicationContext(), "Successfully Updated", 1);
                                    callRemoveEdit();
                                } else {
                                    Data_Array.add(map);
                                    Temp_Array.add(map);
                                    ToastMsg.mToastMsg(getApplicationContext(), "Successfully Added", 1);
                                }
                                try {
                                    city_list_adapter.notifyDataSetChanged();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                dismiss();
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

                    } else {
                        edCityName.setError("Please Enter City");
                        edCityName.requestFocus();
                    }
                }
            });


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
