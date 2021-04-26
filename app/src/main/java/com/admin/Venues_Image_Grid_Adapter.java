package com.admin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.steppin.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Venues_Image_Grid_Adapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<HashMap<String, Object>> data;

    public Venues_Image_Grid_Adapter(Activity a, ArrayList<HashMap<String, Object>> d) {
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
            row = inflater.inflate(R.layout.image_adapter, null);
            holder = new MyHolderView(row);
            row.setTag(holder);
        } else {
            holder = (MyHolderView) row.getTag();
        }
        String s= data.get(position).get("P").toString();
        if (s.contains("https")||s.contains("http")){
            Picasso.get().load(s).placeholder(R.drawable.temp_image).error(R.drawable.temp_image).into(holder.img);
        }else{
            Uri sPath =(Uri) data.get(position).get("P");
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), sPath);
                holder.img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



//        File f = new File(sPath);
//        Picasso.get().load(f).into(holder.img);

        holder.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Add_Venues) activity).deleteImage(position);
            }
        });
        return row;
    }

    class MyHolderView {
        ImageView img, imgClose;

        MyHolderView(View v) {


            img = (ImageView) v
                    .findViewById(R.id.img);
            imgClose = (ImageView) v
                    .findViewById(R.id.imgClose);

        }

    }
}
