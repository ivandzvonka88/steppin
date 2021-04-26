package com.steppin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import com.utils.GetResources;
import com.utils.MyAsync;
import com.utils.MyIntent;
import com.utils.MySession;
import com.utils.SetStatusBar;

import java.util.ArrayList;
import java.util.HashMap;

public class City_Update_Activity extends AppCompatActivity {
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
String sCityID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_update_activity);
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
        cn = new ConnectionDetector(getApplicationContext());
        db = FirebaseFirestore.getInstance();
        tag_group.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(Tag tag, int position) {
                edSearch.setText(tag.text.toString());
            }
        });
        layNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    MySession.set(getApplicationContext(),edSearch.getText().toString(),MySession.Key_CityName);
                    MySession.set(getApplicationContext(),"",MySession.KeyCityNameWithLocation);
                    MySession.set(getApplicationContext(),sCityID,MySession.Key_CityID);
                    finish();
                } else {
                    Snackbar.make(v, "Please Select Your City.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        callSetData();
    }

    ProgressDialog pd = null;

    public void callSetData() {
        cityList = new ArrayList<>();
        cityArrayName = new ArrayList<>();

        if (cn.isConnectingToInternet()) {
            pd = new ProgressDialog(City_Update_Activity.this);
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

                    } else {

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
                    sCityID=cityList.get(i).UID;
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
                    tag.tagTextColor =GetResources.getColor(getApplicationContext(), R.color.clr_White);
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
