package com.healthbank.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.healthbank.PatientlistFragment;
import com.healthbank.RecentAppointmentFragment;

/**
 * Created by it1 on 1/4/2018.
 */

public class fragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public fragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return PatientlistFragment.newInstance(true);
        } else if (position == 1) {
            return RecentAppointmentFragment.newInstance(true);
        } else if (position == 2) {
            return RecentAppointmentFragment.newInstance(false);
        } else {
            return PatientlistFragment.newInstance(true);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return "TODAY";
            case 2:
                return "RECENT";
            case 0:
                return "ALL";
            default:
                return null;
        }
    }
}
