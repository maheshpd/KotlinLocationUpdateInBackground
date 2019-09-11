package com.healthbank.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.healthbank.AppointmentsFragment;
import com.healthbank.BookAppointmentFragment;
import com.healthbank.BookAppointmentFragmentv1;

/**
 * Created by it1 on 8/7/2018.
 */
public class BookAppointmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public BookAppointmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            // return new BookAppointmentFragment();
            return new BookAppointmentFragmentv1();
        } else if (position == 1) {
            return new AppointmentsFragment();
        }
        return new BookAppointmentFragment();
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
                return "New Appointment";
            case 1:
                return "Previous Appointments";
            default:
                return null;
        }
    }

}
