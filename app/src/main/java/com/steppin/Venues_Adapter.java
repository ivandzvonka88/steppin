package com.steppin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.admin.Events;
import com.admin.Venues;
import com.squareup.picasso.Picasso;
import com.utils.ConvertDate;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Raj on 4/4/2015.
 * this class use for Post Item selection list
 * this class use for customizing listview
 */
public class Venues_Adapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<Venues> data;


    public Venues_Adapter(Activity a, ArrayList<Venues> d) {
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
            row = inflater.inflate(R.layout.venues_adapter, null);
            holder = new MyHolderView(row);
            row.setTag(holder);
        } else {
            holder = (MyHolderView) row.getTag();
        }



        Venues venues = data.get(position);
        String sTitle = "" + venues.Title;
//        String sCityName = "" + events.CityName;
//        String sDate = ConvertDate.getMiliToFullDate(events.DateTime);
        holder.txt.setText(sTitle);

        if (venues.Images != null) {
            if (venues.Images.size() > 0) {
                Picasso.get().load(venues.Images.get(0)).placeholder(R.drawable.temp_image).error(R.drawable.temp_image).into(holder.img);
            }
        }


        return row;
    }


    class MyHolderView {
        ImageView img;
        TextView txt;

        MyHolderView(View v) {

            img = (ImageView) v.findViewById(R.id.img);
            txt = (TextView) v.findViewById(R.id.txt);


        }

    }
}
