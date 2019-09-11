package com.healthbank.classes;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.healthbank.AddBillingFragment;
import com.healthbank.BillingDetailsFragment;

public class BillingPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public BillingPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new AddBillingFragment();
        } else if (position == 1) {
            return new BillingDetailsFragment();
        }
        return new BillingDetailsFragment();
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Create Bill";
            case 1:
                return "Billing Details";
            default:
                return null;
        }
    }
}