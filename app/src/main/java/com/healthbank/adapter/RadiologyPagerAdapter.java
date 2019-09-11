package com.healthbank.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.healthbank.AddLabFragment;
import com.healthbank.AddRadilogyFragment;
import com.healthbank.RadiologyFragment;

/**
 * Created by it1 on 8/20/2018.
 */

public class RadiologyPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public RadiologyPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new AddRadilogyFragment();
        } else if (position == 1) {
            return new RadiologyFragment();
        }
        return new AddLabFragment();
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "New Radiology";
            case 1:
                return "Previous Radiology";
            default:
                return null;
        }
    }

}
