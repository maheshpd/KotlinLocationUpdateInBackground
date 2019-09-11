package com.healthbank.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.healthbank.PrescriptionFragment;
import com.healthbank.VitalFragment;


/**
 * Created by it1 on 1/4/2018.
 */

public class PrescriptionPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public PrescriptionPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PrescriptionFragment();
        } else if (position == 1) {
            return new VitalFragment();
        } else if (position == 2) {
            return new PrescriptionFragment();
        } else {
            return new PrescriptionFragment();
            //  return new AppointmentFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "Rx";
            case 1:
                return "Vitals";
            case 2:
                return "Notes";
            case 3:
                return "Fees";
            default:
                return null;
        }
    }

}
