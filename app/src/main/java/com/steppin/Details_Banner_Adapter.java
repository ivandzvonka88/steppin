package com.steppin;

/**
 * Created by Raj on 4/10/2015.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class Details_Banner_Adapter extends PagerAdapter {

    private Activity _activity;

    private ArrayList<String> _imagePaths;
    private LayoutInflater inflater;


    // constructor
    public Details_Banner_Adapter(Activity activity,
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
        ImageView img;
        TextView txt;
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.details_banner_adapter,
                container, false);
        img = (ImageView) viewLayout.findViewById(R.id.img);

        Picasso.get().load(_imagePaths.get(position)).placeholder(R.drawable.temp_image).error(R.drawable.temp_image).into(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZoomDialog dialog = new ZoomDialog(_activity, position);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                dialog.show();
            }
        });
        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

    public class ZoomDialog extends Dialog {

        public Context c;
        ViewPager viewPagerBanner;
        ImageView imgPreIcon, imgNextIcon;
        int poss;

        public ZoomDialog(Activity a, int poss) {
            super(a, R.style.MYDIALOG);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.poss = poss;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            setContentView(R.layout.gallery_image_dialog);

            viewPagerBanner = (ViewPager) findViewById(R.id.viewPagerBanner);
            imgPreIcon = (ImageView) findViewById(R.id.imgPreIcon);
            imgNextIcon = (ImageView) findViewById(R.id.imgNextIcon);
            viewPagerBanner.setOffscreenPageLimit(0);

            if (_imagePaths.size() == 1) {
                imgNextIcon.setVisibility(View.GONE);
                imgPreIcon.setVisibility(View.GONE);
            } else {
                if (poss == 0) {
                    imgPreIcon.setVisibility(View.GONE);
                } else if (poss == _imagePaths.size() - 1) {
                    imgNextIcon.setVisibility(View.GONE);
                }
                viewPagerBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (position == 0) {
                            imgPreIcon.setVisibility(View.GONE);
                            imgNextIcon.setVisibility(View.VISIBLE);
                        } else {
                            imgPreIcon.setVisibility(View.VISIBLE);
                            if (position == _imagePaths.size() - 1) {
                                imgNextIcon.setVisibility(View.GONE);
                            } else {
                                imgNextIcon.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            }
            Gallery_image_zoom_Adapter gallery_image_zoom_adapter = new Gallery_image_zoom_Adapter(_activity, _imagePaths);
            gallery_image_zoom_adapter.notifyDataSetChanged();
            viewPagerBanner.setAdapter(gallery_image_zoom_adapter);

            viewPagerBanner.setCurrentItem(poss, true);

            imgPreIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viewPagerBanner.getCurrentItem() != 0) {
                        poss = viewPagerBanner.getCurrentItem() - 1;
                        viewPagerBanner.setCurrentItem(poss, true);
                    }
                }
            });
            imgNextIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viewPagerBanner.getCurrentItem() != _imagePaths.size() - 1) {
                        poss = viewPagerBanner.getCurrentItem() + 1;
                        viewPagerBanner.setCurrentItem(poss, true);
                    }
                }
            });
        }


    }

}