package com.healthbank.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.healthbank.AddRXFragment;
import com.healthbank.RxFragment;

/**
 * Created by it1 on 8/10/2018.
 */
public class RxPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public RxPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new AddRXFragment();
        } else if (position == 1) {
            return new RxFragment();
        }
        return new AddRXFragment();
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
                return "New Rx";
            case 1:
                return "Previous Rx";
            default:
                return "New Rx";
        }
    }

}