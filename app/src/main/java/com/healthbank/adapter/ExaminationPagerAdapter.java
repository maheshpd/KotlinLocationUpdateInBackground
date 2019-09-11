package com.healthbank.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.healthbank.AddExaminationFragment;
import com.healthbank.AddLabFragment;
import com.healthbank.ExaminationFragment;

/**
 * Created by it1 on 8/9/2018.
 */

public class ExaminationPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public ExaminationPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new AddExaminationFragment();
        } else if (position == 1) {
            return new ExaminationFragment();
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
                return "New Examination";
            case 1:
                return "Previous Examination";
            default:
                return null;
        }
    }

}
