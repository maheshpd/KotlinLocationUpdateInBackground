package com.healthbank.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.healthbank.AddEyetestDataFragment;
import com.healthbank.AddRXFragment;
import com.healthbank.EyetestDataFragment;

public class EyeSpecialistAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public EyeSpecialistAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new AddEyetestDataFragment();
        } else if (position == 1) {
            return new EyetestDataFragment();
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
        switch (position) {
            case 0:
                return "New ";
            case 1:
                return "Previous ";
            default:
                return "New ";
        }
    }
}
