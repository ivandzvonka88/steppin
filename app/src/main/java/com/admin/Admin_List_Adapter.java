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


public class Admin_List_Adapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public Admin_List_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            row = inflater.inflate(R.layout.admin_list_adapter, null);
            holder = new MyHolderView(row);
            row.setTag(holder);
        } else {
            holder = (MyHolderView) row.getTag();
        }

        HashMap<String, String> mData = new HashMap<String, String>();
        mData = data.get(position);

        String Name = "" + mData.get(Admin.Key_Username);
        if (Name.toLowerCase().equals("null")) {
            Name = "";
        }
        String Password = "" + mData.get(Admin.Key_Password);
        if (Password.toLowerCase().equals("null")) {
            Password = "";
        }

        holder.txtName.setText(Name);
        holder.txtPassword.setText(Password);


        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AdminList) activity).callEditData(position);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AdminList) activity).callDeleteData(position);
            }
        });
        return row;
    }

    class MyHolderView {
        ImageView imgEdit, imgDelete;
        TextView txtName, txtPassword;


        MyHolderView(View v) {


            txtPassword = (TextView) v
                    .findViewById(R.id.txtPassword);
            txtName = (TextView) v
                    .findViewById(R.id.txtName);
            imgEdit = (ImageView) v
                    .findViewById(R.id.imgEdit);
            imgDelete = (ImageView) v
                    .findViewById(R.id.imgDelete);

        }

    }
}
