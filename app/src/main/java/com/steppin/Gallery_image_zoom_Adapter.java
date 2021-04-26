package com.steppin;

/**
 * Created by Raj on 4/10/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;
import com.utils.TouchImageView;

import java.util.ArrayList;
import java.util.HashMap;


public class Gallery_image_zoom_Adapter extends PagerAdapter {

    private Activity _activity;

    private ArrayList<String> _imagePaths;

    private LayoutInflater inflater;


    // constructor
    public Gallery_image_zoom_Adapter(Activity activity,
                                      ArrayList<String> imagePaths) {

        this._activity = activity;
        this._imagePaths = imagePaths;

    }

    @Override
    public int getCount() {


        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.gallaery_zoom_image_adapter,
                container, false);
        TouchImageView img_fullscreen = (TouchImageView) viewLayout.findViewById(R.id.img_fullscreen);


        Picasso.get().load(_imagePaths.get(position)).placeholder(R.drawable.temp_image).error(R.drawable.temp_image).into(img_fullscreen);

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }


}