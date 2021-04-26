package com.steppin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;
import com.utils.ConverPIXtoDPI;
import com.utils.ConvertDate;
import com.utils.MyViewPagerSize;
import com.utils.SetStatusBar;
import com.utils.ToastMsg;

import java.lang.reflect.Method;

public class Events_Details extends AppCompatActivity {
    ImageView img, imgBack, imgShare;
    CardView layCall;
    TextView txtEventsName, txtTime, txtDetails, txtLocation, txtPrice;
    MyViewPagerSize viewPager;
    WormDotsIndicator worm_dots_indicator;
    RelativeLayout layBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_details);
        SetStatusBar.set(this, R.color.clr_White);
        img = findViewById(R.id.img);
        layCall = findViewById(R.id.layCall);
        imgBack = findViewById(R.id.imgBack);
        imgShare = findViewById(R.id.imgShare);
        txtEventsName = findViewById(R.id.txtEventsName);
        txtTime = findViewById(R.id.txtTime);
        txtDetails = findViewById(R.id.txtDetails);
        txtLocation = findViewById(R.id.txtLocation);
        txtPrice = findViewById(R.id.txtPrice);
        viewPager = findViewById(R.id.viewPager);
        layBanner = findViewById(R.id.layBanner);
        worm_dots_indicator = findViewById(R.id.worm_dots_indicator);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        layCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + Events_Fragment.selectedEvents.Mobile));
                startActivity(intent);
            }
        });
        ViewTreeObserver vto = layBanner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    layBanner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    layBanner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                int width = layBanner.getMeasuredWidth();
                int height = layBanner.getMeasuredHeight();
                float m = width / 4;
                m = m * 2.5f;
                layBanner.getLayoutParams().height = Math.round(m);
            }
        });
        setData();
    }

    public void setData() {


//        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT, pixels);
//        rel_btn.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        scrollpopap.setLayoutParams(rel_btn);
//        scrollpopap.setScrollingEnabled(false);
        Details_Banner_Adapter banner_adapter = new Details_Banner_Adapter(this, Events_Fragment.selectedEvents.Images);
        viewPager.setAdapter(banner_adapter);
        worm_dots_indicator.setViewPager(viewPager);


        txtEventsName.setText(Events_Fragment.selectedEvents.Title);
        txtTime.setText(ConvertDate.getMiliToFullDateTime(Events_Fragment.selectedEvents.DateTime));
        txtDetails.setText(Events_Fragment.selectedEvents.Details);
        txtLocation.setText(Events_Fragment.selectedEvents.Location);
        String sMobile = "" + Events_Fragment.selectedEvents.Mobile;
        if (sMobile.equals("") || sMobile.toLowerCase().equals("null")) {
            layCall.setVisibility(View.GONE);
        } else {
            layCall.setVisibility(View.VISIBLE);
        }

        float f = 0;
        try {
            f = Float.parseFloat(Events_Fragment.selectedEvents.Price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (f > 0) {
            txtPrice.setText("$ " + Events_Fragment.selectedEvents.Price);
        } else {
            txtPrice.setText("Free");
        }

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = "";
                try {
                    s = getData(s,Events_Fragment.selectedEvents.Title);
                    s = getData(s,Events_Fragment.selectedEvents.Details);
                    s = getData(s,"Mobile: "+Events_Fragment.selectedEvents.Mobile);
                    s = getData(s,"Location: "+Events_Fragment.selectedEvents.Location);
                    s = getData(s,"City: "+Events_Fragment.selectedEvents.CityName);
                    s = getData(s,"Price: $"+Events_Fragment.selectedEvents.Price);
                    s = getData(s,"DateTime: "+ConvertDate.getMiliToFullDateTime(Events_Fragment.selectedEvents.DateTime));
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastMsg.mToastMsg(getApplicationContext(),"Error: "+e.getMessage().toString(),1);
                }
                try {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, s);
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    if (sendIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(sendIntent);
                    }else{
                        ToastMsg.mToastMsg(getApplicationContext(),"Error2: "+"No Activity Found.",1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastMsg.mToastMsg(getApplicationContext(),"Error: "+e.getMessage().toString(),1);
                }
            }
        });
        callBanner();
    }

    public String getData(String s, String s2) {
        if (!s2.equals("") && !s2.equals("null")) {
            if (s.equals("")) {
                s = s2;
            } else {
                s = s + "\n" + s2;
            }
        }
        return s;
    }

    public void callBanner() {
        if (Events_Fragment.selectedEvents.Images.size() > 1) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //add your code here
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (viewPager.getCurrentItem() == Events_Fragment.selectedEvents.Images.size() - 1) {
                                viewPager.setCurrentItem(0, true);
                            } else {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                            }
                            callBanner();
                        }
                    });
                }
            }, 3000);
        } else {
            worm_dots_indicator.setVisibility(View.GONE);
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

    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
