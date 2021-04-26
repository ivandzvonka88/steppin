package com.admin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.steppin.R;

import java.util.ArrayList;
import java.util.HashMap;


public class City_List_Adapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public City_List_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // this method count array size.
    // this is return size array
    public int getCount() {
        return data.size();
    }

    // this method through get item position or this method return item position.
    public Object getItem(int position) {
        return position;
    }

    //this method return item ID.
    public long getItemId(int position) {
        return position;
    }

    // this method through getting custom view
    // this method through set item data.
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MyHolderView holder = null;
        if (row == null) {
            row = inflater.inflate(R.layout.city_list_adapter, null);
            holder = new MyHolderView(row);
            row.setTag(holder);
        } else {
            holder = (MyHolderView) row.getTag();
        }

        HashMap<String, String> mData = new HashMap<String, String>();
        mData = data.get(position);

        String sCityName = "" + mData.get(City.Key_CityName);
        if (sCityName.toLowerCase().equals("null")) {
            sCityName = "";
        }

        holder.txt.setText(sCityName);
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((City_List) activity).callEditData(position);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((City_List) activity).callDeleteData(position);
            }
        });
        return row;
    }

    class MyHolderView {
        ImageView imgEdit, imgDelete;
        TextView txt;


        MyHolderView(View v) {


            txt = (TextView) v
                    .findViewById(R.id.txt);
            imgEdit = (ImageView) v
                    .findViewById(R.id.imgEdit);
            imgDelete = (ImageView) v
                    .findViewById(R.id.imgDelete);

        }

    }
}
