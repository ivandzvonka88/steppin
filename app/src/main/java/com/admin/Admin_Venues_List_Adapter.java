package com.admin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.steppin.R;
import com.utils.ConvertDate;

import java.util.ArrayList;


/**
 * Created by Raj on 4/4/2015.
 * this class use for Post Item selection list
 * this class use for customizing listview
 */
public class Admin_Venues_List_Adapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<Venues> data;


    public Admin_Venues_List_Adapter(Activity a, ArrayList<Venues> d) {
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
            row = inflater.inflate(R.layout.admin_venues_list_adapter, null);
            holder = new MyHolderView(row);
            row.setTag(holder);
        } else {
            holder = (MyHolderView) row.getTag();
        }
        Venues venues = data.get(position);
        String sTitle = "" + venues.Title;
        String sCityName = "" + venues.CityName;
        holder.txt.setText(sTitle);
        holder.txt2.setText(sCityName);

        if (venues.Images != null) {
            if (venues.Images.size() > 0) {
                Picasso.get().load(venues.Images.get(0)).placeholder(R.drawable.temp_image).error(R.drawable.temp_image).into(holder.img);
            }
        }
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Admin_Home) activity).deleteVenues(position);
            }
        });
        return row;
    }

    class MyHolderView {
        ImageView imgDelete, img;
        TextView txt,txt2;

        MyHolderView(View v) {
            img = (ImageView) v.findViewById(R.id.img);
            imgDelete = (ImageView) v.findViewById(R.id.imgDelete);
            txt = (TextView) v.findViewById(R.id.txt);
            txt2 = (TextView) v.findViewById(R.id.txt2);
        }

    }
}
