package com.healthbank.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.healthbank.AddLabFragment;
import com.healthbank.LabFragment;

public class LabPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public LabPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new AddLabFragment();
        } else if (position == 1) {
            return new LabFragment();
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
                return "New Lab Tests";
            case 1:
                return "Previous Lab Tests";
            default:
                return null;
        }
    }

}