package com.testcode.gameofthrones.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.testcode.gameofthrones.fragments.GoTHousesListFragment;
import com.testcode.gameofthrones.fragments.GoTListFragment;

/**
 * Created by Fabian on 07/12/2016.
 */

public class SelectionsPagerAdapter extends FragmentPagerAdapter {
    FragmentManager mfm;
    public SelectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mfm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new GoTListFragment();
        } else {
            return new GoTHousesListFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Characters";
            case 1:
                return "Houses";
        }
        return null;
    }
}
