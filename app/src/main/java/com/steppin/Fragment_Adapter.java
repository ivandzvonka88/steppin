package com.steppin;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Rajneesh on 15-Jan-18.
 */

public class Fragment_Adapter extends FragmentPagerAdapter {

    public Fragment_Adapter(FragmentManager fm) {
        super(fm);
    }

    ArrayList<Fragment> Fragment_Array = new ArrayList<>();

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                if (Fragment_Array.size() > 1) {
                    Fragment_Array.set(0, new Events_Fragment());
                } else {
                    Fragment_Array.add(new Events_Fragment());
                }
                return Fragment_Array.get(0);
            case 1:
                if (Fragment_Array.size() > 2) {
                    Fragment_Array.set(1, new Venues_Fragment());
                } else {
                    Fragment_Array.add(new Venues_Fragment());
                }
                return Fragment_Array.get(1);
            case 2:
                if (Fragment_Array.size() > 3) {
                    Fragment_Array.set(2, new Dance_Fragment());
                } else {
                    Fragment_Array.add(new Dance_Fragment());
                }
                return Fragment_Array.get(2);
            case 3:
                if (Fragment_Array.size() > 4) {
                    Fragment_Array.set(3, new Settings_Fragment());
                } else {
                    Fragment_Array.add(new Settings_Fragment());
                }
                return Fragment_Array.get(3);

            default:
                if (Fragment_Array.size() > 1) {
                    Fragment_Array.set(0, new Events_Fragment());
                } else {
                    Fragment_Array.add(new Events_Fragment());
                }
                return Fragment_Array.get(0);
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}