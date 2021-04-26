package com.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;

import com.squareup.picasso.Picasso;
import com.steppin.R;


import java.io.File;


/**
 * Created by Raj on 7/24/2015.
 */
public class ImageZoom_Dialog extends Dialog {

    public Context c;
    TouchImageView img_fullscreen;
    String mUrl = "";

    public ImageZoom_Dialog(Context a, String ImageUrl) {
        super(a);
        // TODO Auto-generated constructor stub
        mUrl = ImageUrl;
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fullscreenimage_dialog);
        getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        img_fullscreen = (TouchImageView) findViewById(R.id.img_fullscreen);


        String StoragePath = URLString.RootImagePathHide + URLString.replaceURL(mUrl);
        File f = new File(StoragePath);
        if (f.exists()) {
            Picasso.get().load(f).into(img_fullscreen);
        } else {
            mUrl = mUrl.replaceAll(" ", "%20");
            Picasso.get().load(mUrl).into(img_fullscreen);
        }
    }
}