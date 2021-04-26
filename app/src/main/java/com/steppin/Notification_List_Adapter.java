package com.steppin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FCM.Notification_Data;
import com.squareup.picasso.Picasso;
import com.utils.ConvertDate;

import java.util.ArrayList;
import java.util.HashMap;


public class Notification_List_Adapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public Notification_List_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            row = inflater.inflate(R.layout.notification_list_adapter, null);
            holder = new MyHolderView(row);
            row.setTag(holder);
        } else {
            holder = (MyHolderView) row.getTag();
        }

        HashMap<String, String> mData = new HashMap<String, String>();
        mData = data.get(position);
//        String strLocalPath = "" + mData.get(PostItem.Key_LocalPath);
//            Picasso.with(activity).load(f).into(holder.img);
        String title = "" + mData.get(Notification_Data.Key_NotificationTitle);
        String Details = "" + mData.get(Notification_Data.Key_Notification);
        String sImage = "" + mData.get(Notification_Data.Key_NotificationImage);

        holder.txtTitle.setText(title);
        holder.txtDetails.setText(Details);

        if (title.equals("") || title.toLowerCase().equals("null")) {
            holder.layTitle.setVisibility(View.GONE);
        } else {
            holder.layTitle.setVisibility(View.VISIBLE);
        }
        if (Details.equals("") || Details.toLowerCase().equals("null")) {
            holder.txtDetails.setVisibility(View.GONE);
        } else {
            holder.txtDetails.setVisibility(View.VISIBLE);
        }
        if (sImage.equals("") || sImage.toLowerCase().equals("null")) {
            holder.img.setVisibility(View.GONE);
            holder.vei.setVisibility(View.VISIBLE);
//            holder.txtDetails.setVisibility(View.VISIBLE);
        } else {
            Picasso.get().load(sImage).placeholder(R.drawable.temp_image).into(holder.img);
            holder.img.setVisibility(View.VISIBLE);
            holder.vei.setVisibility(View.GONE);
//            holder.txtDetails.setVisibility(View.GONE);
        }
        return row;
    }

    class MyHolderView {
        TextView txtTitle, txtDetails;
        LinearLayout layTitle;
        ImageView img;
        View vei;

        MyHolderView(View v) {

            txtTitle = (TextView) v
                    .findViewById(R.id.txtTitle);
            txtDetails = (TextView) v
                    .findViewById(R.id.txtDetails);
            layTitle = (LinearLayout) v
                    .findViewById(R.id.layTitle);
            img = (ImageView) v.findViewById(R.id.img);
            vei = (View) v.findViewById(R.id.v);


        }

    }
}
