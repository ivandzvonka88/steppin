package com.steppin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.admin.City;
import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.utils.ConnectionDetector;
import com.utils.DistanceCalculator;
import com.utils.GetResources;
import com.utils.LocationFinder;
import com.utils.MyAsync;
import com.utils.MyIntent;
import com.utils.MySession;
import com.utils.SetStatusBar;
import com.utils.SingleShotLocationProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class City_Activity extends AppCompatActivity {
    private static final String TAG = "City_Activity";
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 101;
    EditText edSearch;
    ImageView imgBack;
    ArrayList<City> cityList = new ArrayList<>();
    ArrayList<String> cityArrayName = new ArrayList<>();
    ArrayList<String> searchArray = new ArrayList<>();
    TagView tag_group;
    SearchAsync searchAsync;
    String sSearch = "";
    boolean isSelected = false;
    CardView layNext;
    ConnectionDetector cn;
    FirebaseFirestore db;
    String sCityID = "";

    private String msCityName = "";
    private LocationFinder finder;
    private double longitude = 0.0, latitude = 0.0;
    private boolean isUserSelected = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activity);
        SetStatusBar.set(this, R.color.clr_White);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        edSearch = (EditText) findViewById(R.id.edSearch);
        tag_group = (TagView) findViewById(R.id.tag_group);
        layNext = (CardView) findViewById(R.id.layNext);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchAsync = new SearchAsync();
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

        tag_group.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(Tag tag, int position) {
                isUserSelected = true;
                edSearch.setText(tag.text.toString());
            }
        });
        cn = new ConnectionDetector(getApplicationContext());
        db = FirebaseFirestore.getInstance();
        layNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    if (isUserSelected){
                        MySession.set(getApplicationContext(), edSearch.getText().toString(), MySession.Key_CityName);
                        MySession.set(getApplicationContext(), "", MySession.KeyCityNameWithLocation);
                    }else {
                        MySession.set(getApplicationContext(), edSearch.getText().toString(), MySession.KeyCityNameWithLocation);
                        MySession.set(getApplicationContext(), "", MySession.Key_CityName);
                    }
                    MySession.set(getApplicationContext(), sCityID, MySession.Key_CityID);
                    MyIntent.Goto(City_Activity.this, Home.class);
                } else {
                    Snackbar.make(v, "Please Select Your City.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        TempData.Activity_Array.add(this);
        callSetData();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_ACCESS_COARSE_LOCATION);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getLocation(true);
                }},2000);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation(false);
                }
                break;
        }
    }

    private void getLocation(boolean isFromResume) {
        if (!cityArrayName.isEmpty()) {
            finder = new LocationFinder(City_Activity.this);
            if (finder.canGetLocation()) {

                latitude = finder.getLatitude();
                longitude = finder.getLongitude();

                if (longitude == 0.0 && latitude == 0.0){
                    SingleShotLocationProvider.requestSingleUpdate(this,
                            new SingleShotLocationProvider.LocationCallback() {
                                @Override public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                                    latitude = location.latitude;
                                    longitude = location.longitude;
                                    selectCityCase2();
                                }
                            });
                }else {
                    selectCityCase2();
                }
            } else {
                if (isFromResume){
                    finder.showSettingsAlert();
                }
            }
        }
    }

    private void selectCityCase2(){

        if (!cityArrayName.isEmpty()) {

            Geocoder geocoder = new Geocoder(City_Activity.this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String cityName = addresses.get(0).getAddressLine(0);
            Log.e(TAG, "onNewLocationAvailable: 1st Line: " + cityName);
            String stateName = addresses.get(0).getAddressLine(1);
            Log.e(TAG, "onNewLocationAvailable: 2nd Line: " + stateName);
            String countryName = addresses.get(0).getAddressLine(2);
            Log.e(TAG, "onNewLocationAvailable: 3rd Line: " + countryName);

            for (int i = 0; i < cityArrayName.size(); i++) {
                if (cityName.contains(cityArrayName.get(i))) {
                    msCityName = cityArrayName.get(i);
                    edSearch.setText(cityArrayName.get(i));
                    break;
                }
            }
            if (msCityName.isEmpty()) {
                List<Double> distanceList = new ArrayList<>();
                for (int i = 0; i < cityList.size(); i++) {
                    distanceList.add(DistanceCalculator.distance(
                            latitude,
                            longitude,
                            cityList.get(i).getLat(),
                            cityList.get(i).getLng(),
                            "K"));
                }
                int indexOfMinimum = distanceList.indexOf(Collections.min(distanceList));
                edSearch.setText(cityArrayName.get(indexOfMinimum));
            }
        }
    }

    ProgressDialog pd = null;

    public void callSetData() {
        cityList = new ArrayList<>();
        cityArrayName = new ArrayList<>();

        if (cn.isConnectingToInternet()) {
            pd = new ProgressDialog(City_Activity.this);
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
                            cityList.add(cityData);
                            cityArrayName.add(cityData.CityName);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(City_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(City_Activity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSION_ACCESS_COARSE_LOCATION);
                            } else {
                                getLocation(false);
                            }
                        } else {
                            getLocation(false);
                        }

                    }
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
//        if (!dataAsync.isCancelled()) {
//            dataAsync.cancel(true);
//        }
    }

    public void callSearch() {
        if (!isSearching) {
            sSearch = edSearch.getText().toString();
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

    boolean isPending = false, isSearching = false;

    private class SearchAsync extends AsyncTask<Void, Void, Void> {

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
            searchArray = new ArrayList<>();
            isSelected = false;
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < cityArrayName.size(); i++) {
                String s = cityArrayName.get(i);
                if (s.equals(sSearch)) {
                    isSelected = true;
                    sCityID = cityList.get(i).UID;
                    break;
                } else if (s.toLowerCase().contains(sSearch.toLowerCase())) {
                    searchArray.add(s);
                }
            }
            if (!isSelected) {
                if (searchArray.size() == 0) {
                    for (int i = 0; i < cityArrayName.size(); i++) {
                        String s = cityArrayName.get(i);
                        searchArray.add(s);
                    }
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (isSelected) {
                ArrayList<Tag> tags = new ArrayList<>();
                tag_group.addTags(tags);
            } else {
                ArrayList<Tag> tags = new ArrayList<>();
                Tag tag;
                for (int i = 0; i < searchArray.size(); i++) {
//            mStrings[i] = cityArray.get(i);
                    tag = new Tag(searchArray.get(i));
                    tag.radius = 10f;
                    tag.layoutColor = GetResources.getColor(getApplicationContext(), R.color.color1);
                    tag.isDeletable = false;
                    tag.tagTextSize = 14;
                    tag.tagTextColor = GetResources.getColor(getApplicationContext(), R.color.clr_White);
                    tags.add(tag);
                }
                tag_group.addTags(tags);
            }
            isSearching = false;
            if (isPending) {
                isPending = false;
                callSearch();
            }
            if (isSelected) {
                layNext.setCardBackgroundColor(GetResources.getColor(getApplicationContext(), R.color.colorPrimary));
            } else {
                layNext.setCardBackgroundColor(GetResources.getColor(getApplicationContext(), R.color.clr_Gray3));
            }
//            try {
//                if (pd.isShowing()) {
//                    pd.dismiss();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
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
