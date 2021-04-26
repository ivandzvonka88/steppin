package com.steppin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.admin.Events;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.utils.ConnectionDetector;
import com.utils.MyAsync;
import com.utils.MyIntent;
import com.utils.MySession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Rajneesh on 15-Jan-18.
 */

public class Events_Fragment extends Fragment {

    ArrayList<Events> Data_Array = new ArrayList<>();
    ListView listView;
    TextView txtCity;
    ImageView imgNoData;
    ProgressBar progressBar;
    ConnectionDetector cn;
    FirebaseFirestore db;
    public static Events selectedEvents;

    public Events_Fragment() {
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
        View v = inflater.inflate(R.layout.events_fragment, container, false);
//        listView = (ListView) v.findViewById(R.id.listView);

        listView = v.findViewById(R.id.listView);
        txtCity = v.findViewById(R.id.txtCity);
        txtCity.setText("");
        progressBar = v.findViewById(R.id.progressBar);
        imgNoData = v.findViewById(R.id.imgNoData);
        imgNoData.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        cn = new ConnectionDetector(getActivity());
        db = FirebaseFirestore.getInstance();
        LinearLayout layCity = v.findViewById(R.id.layCity);
        layCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntent.Goto(getActivity(), City_Update_Activity.class);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedEvents=Data_Array.get(position);
                TempData.poss = position;
                MyIntent.Goto(getActivity(), Events_Details.class);
            }
        });
        callSetData();
        return v;
    }

    public void onStart() {
        super.onStart();
        String sCity = MySession.get(getActivity(),MySession.KeyCityNameWithLocation);
        if (sCity.isEmpty()){
            sCity = MySession.get(getActivity(),MySession.Key_CityName);
        }
        if (txtCity.getText().toString().equals("")) {
            txtCity.setText(sCity);
        } else if (!txtCity.getText().toString().equals(sCity)) {
            txtCity.setText(sCity);
            callSetData();
        }
    }

    public void callSetData() {

        Data_Array = new ArrayList<>();
        final ArrayList<Events> mArray = new ArrayList<>();

        if (cn.isConnectingToInternet()) {
            imgNoData.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            Query query = null;
            try {
                String sMili = "" + new Date().getTime();
//                query = db.collection(Order.TableName).whereEqualTo(Order.Key_UID, sUUID).whereEqualTo(Order.Key_CID, sCID).whereGreaterThanOrEqualTo(Order.Key_OID, "" + startDate).whereLessThanOrEqualTo(Order.Key_OID, "" + endDate).orderBy(Order.Key_OID, Query.Direction.DESCENDING);
                query = db.collection(Events.TableName).whereEqualTo(Events.Key_CityID, MySession.get(getActivity(),MySession.Key_CityID)).whereGreaterThan(Events.Key_DateTime, sMili);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Source source = Source.DEFAULT;
            query.get(source).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            // Document found in the offline cache
                            Events eventData = document.toObject(Events.class);
//                            Log.e("EventData", "-" + eventData.Title);
//                            Log.e("DateTime", "-" + eventData.DateTime);
                            if (Long.parseLong(eventData.DateTime)>System.currentTimeMillis()) {
                                mArray.add(eventData);
                            }
                        }

                    } else {

                    }
                    Data_Array = mArray;
                    if (Data_Array.size()==0){
                        imgNoData.setVisibility(View.VISIBLE);
                    }
                    progressBar.setVisibility(View.GONE);
                    Events_Adapter events_adapter = new Events_Adapter(getActivity(), Data_Array);
                    listView.setAdapter(events_adapter);
                }
            });
        }

    }


    public void onDestroy() {
        super.onDestroy();

    }

// try {
//        HashMap<String, String> map = new HashMap<>();
//        map.put(Key_Name, "Event Name");
//        map.put(Key_Details, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
//        map.put(Key_DateTime, "Fri, April 20, 7 PM");
//        map.put(Key_Location, "459 Josiah Prairie Apt. 575");
//        map.put(Key_Price, "$ 25");
//        dataArray.add(map);
//    } catch (Exception e) {
//        e.printStackTrace();
//    }

}
