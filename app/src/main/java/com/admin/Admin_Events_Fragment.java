package com.admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import com.FCM.Notification_Data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.steppin.R;
import com.steppin.TempData;
import com.utils.ConnectionDetector;
import com.utils.MyAsync;
import com.utils.MyIntent;
import com.utils.MyKeyboard;
import com.utils.MySession;
import com.utils.ToastMsg;
import com.utils.URLString;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rajneesh on 15-Jan-18.
 */

public class Admin_Events_Fragment extends Fragment {
    FloatingActionButton fabAdd;
    ListView listView;

    public static ArrayList<Events> Temp_Array = new ArrayList<>();
    ArrayList<Events> Data_Array = new ArrayList<>();
    EditText edSearch;
    String sSearch = "";
    ProgressBar progressBar;
    ConnectionDetector cn;
    FirebaseFirestore db;
    SearchAsync searchAsync;
    Admin_Events_List_Adapter admin_events_list_adapter;
    public static Events lastSelectedEvents;
    public static boolean isRefresh = false;
    RemoveAsync removeAsync;
    String sCityID = "";
    String sCityName = "";
    RelativeLayout layCity;
    TextView txtCity;
    ImageView imgCity, imgNoData;

    public Admin_Events_Fragment() {
        // Required empty public constructor
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
        View v = inflater.inflate(R.layout.admin_events_fragment, container, false);

        fabAdd = v.findViewById(R.id.fabAdd);
        listView = v.findViewById(R.id.listView);
        progressBar = v.findViewById(R.id.progressBar);
        edSearch = v.findViewById(R.id.edSearch);
        layCity = v.findViewById(R.id.layCity);
        txtCity = v.findViewById(R.id.txtCity);
        imgCity = v.findViewById(R.id.imgCity);
        imgNoData = v.findViewById(R.id.imgNoData);
        imgNoData.setVisibility(View.GONE);
        isRefresh = false;
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyKeyboard.hide(getActivity());
                lastSelectedEvents = new Events();
                lastSelectedEvents.UID = "";
                lastSelectedEvents.Title = "";
                lastSelectedEvents.Details = "";
                lastSelectedEvents.Price = "";
                lastSelectedEvents.Mobile = "";
                lastSelectedEvents.Location = "";
                lastSelectedEvents.DateTime = "";
                lastSelectedEvents.CityID = "";
                lastSelectedEvents.CityName = "";
                lastSelectedEvents.Images = new ArrayList<>();
                MyIntent.Goto(getActivity(), Add_Events.class);
            }
        });
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                callSearch();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyKeyboard.hide(getActivity());
                lastSelectedEvents = Data_Array.get(position);
                MyIntent.Goto(getActivity(), Add_Events.class);
            }
        });
        layCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), City_List.class);
                getActivity().startActivityForResult(intent, Admin_Home.eventCode);
            }
        });
        imgCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sCityID.equals("")) {
                    sCityID = "";
                    sCityName = "";
                    txtCity.setText("All City");
                    changeCity();
                }
            }
        });
        Temp_Array = new ArrayList<>();
        lastSelectedEvents = new Events();
        progressBar.setVisibility(View.GONE);
        cn = new ConnectionDetector(getActivity());
        db = FirebaseFirestore.getInstance();
        searchAsync = new SearchAsync();
        removeAsync = new RemoveAsync();
        callSetData();
        return v;
    }

    public void changeCity() {
        callSetData();
    }

    public void onStart() {
        super.onStart();
        if (isRefresh) {
            callSearch();
        }

    }

    public void callSetData() {

        Data_Array = new ArrayList<>();
        Temp_Array = new ArrayList<>();
        final ArrayList<Events> mArray = new ArrayList<>();

        if (cn.isConnectingToInternet()) {
            imgNoData.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            Query query = null;
            try {
                if (sCityID.equals("")) {
                    query = db.collection(Events.TableName).orderBy(Events.Key_UID, Query.Direction.DESCENDING);
                } else {
                    query = db.collection(Events.TableName).whereEqualTo(Events.Key_CityID, sCityID).orderBy(Events.Key_UID, Query.Direction.DESCENDING);
                }

//            if (TempData.User_Type.equals(TempData.User_CUSTOMER)) {
//                query = db.collection(User.TableName).whereEqualTo(User.Key_UserType, TempData.User_CUSTOMER);
//            } else {
//                query = db.collection(User.TableName).whereGreaterThanOrEqualTo(User.Key_UserType, TempData.User_SALESMAN);
//            }
            } catch (Exception e) {
                e.printStackTrace();
            }
// Source can be CACHE, SERVER, or DEFAULT.
            Source source = Source.SERVER;
//            if (cn.isConnectingToInternet()) {
//                source = Source.SERVER;
//            } else {
//                source = Source.CACHE;
//            }
            query.get(source).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            // Document found in the offline cache
                            Events eventData = document.toObject(Events.class);
//                            Log.e("EventData", "-" + eventData.Title);
//                            Log.e("DateTime", "-" + eventData.DateTime);
                            mArray.add(eventData);
                        }

                    } else {

                    }
                    Temp_Array = mArray;
                    progressBar.setVisibility(View.GONE);
                    callSearch();
                }
            });
        } else {
            callSearch();
        }

    }


    public void onDestroy() {
        super.onDestroy();
        if (!searchAsync.isCancelled()) {
            searchAsync.cancel(true);
        }
    }

    boolean isPending = false, isSearching = false;

    public void callSearch() {

        if (!isSearching) {
            isSearching = true;
            if (!searchAsync.isCancelled()) {
                searchAsync.cancel(true);
            }
            searchAsync = new SearchAsync();
            MyAsync.callAsync(searchAsync);
        } else {
            isPending = true;
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
            imgNoData.setVisibility(View.GONE);
            Data_Array = new ArrayList<>();
            sSearch = edSearch.getText().toString().toLowerCase();
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int j = 0; j < Temp_Array.size(); j++) {
                Events events = Temp_Array.get(j);
                String s = events.Title + " " + events.Details + " " + events.Location + " " + events.Mobile + " " + events.Price + " " + events.CityName;
                if (s.toLowerCase().contains(sSearch.toLowerCase()) || sSearch.equals("")) {
                    Data_Array.add(events);
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
            admin_events_list_adapter = new Admin_Events_List_Adapter(getActivity(), Data_Array);
            admin_events_list_adapter.notifyDataSetChanged();
            listView.setAdapter(admin_events_list_adapter);
            isSearching = false;
            if (isPending) {
                isPending = false;
                callSearch();
            } else {
                if (Data_Array.size() == 0) {
                    imgNoData.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    public void callDelete(final int Poss) {
        db.collection(Events.TableName).document(Data_Array.get(Poss).UID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Data_Array.remove(Poss);
                try {
                    admin_events_list_adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                callRemove();
                ToastMsg.mToastMsg(getActivity(), "Successfully deleted.", 1);
            }
        });

    }

    public void callRemove() {
        if (!removeAsync.isCancelled()) {
            removeAsync.cancel(true);
        }
        removeAsync = new RemoveAsync();
        MyAsync.callAsync(removeAsync);
    }

    private class RemoveAsync extends AsyncTask<Void, Void, Void> {

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
                Events events = Temp_Array.get(j);
                if (events.UID.equals(lastSelectedEvents.UID)) {
                    Temp_Array.remove(j);
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (Data_Array.size() == 0) {
                imgNoData.setVisibility(View.VISIBLE);
            }
        }
    }



}
