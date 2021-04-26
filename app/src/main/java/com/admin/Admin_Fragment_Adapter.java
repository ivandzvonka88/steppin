package com.admin;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.steppin.Dance_Fragment;
import com.steppin.Events_Fragment;
import com.steppin.Settings_Fragment;
import com.steppin.Venues_Fragment;

import java.util.ArrayList;

/**
 * Created by Rajneesh on 15-Jan-18.
 */

public class Admin_Fragment_Adapter extends FragmentPagerAdapter {

    public Admin_Fragment_Adapter(FragmentManager fm) {
        super(fm);
    }

    ArrayList<Fragment> Fragment_Array = new ArrayList<>();

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                if (Fragment_Array.size() > 1) {
                    Fragment_Array.set(0, new Admin_Events_Fragment());
                } else {
                    Fragment_Array.add(new Admin_Events_Fragment());
                }
                return Fragment_Array.get(0);
            case 1:
                if (Fragment_Array.size() > 2) {
                    Fragment_Array.set(1, new Admin_Venues_Fragment());
                } else {
                    Fragment_Array.add(new Admin_Venues_Fragment());
                }
                return Fragment_Array.get(1);
            case 2:
                if (Fragment_Array.size() > 3) {
                    Fragment_Array.set(2, new Admin_Dance_Fragment());
                } else {
                    Fragment_Array.add(new Admin_Dance_Fragment());
                }
                return Fragment_Array.get(2);
            case 3:
                if (Fragment_Array.size() > 4) {
                    Fragment_Array.set(3, new Admin_Settings_Fragment());
                } else {
                    Fragment_Array.add(new Admin_Settings_Fragment());
                }
                return Fragment_Array.get(3);

            default:
                if (Fragment_Array.size() > 1) {
                    Fragment_Array.set(0, new Admin_Events_Fragment());
                } else {
                    Fragment_Array.add(new Admin_Events_Fragment());
                }
                return Fragment_Array.get(0);
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}