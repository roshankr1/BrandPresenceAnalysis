package com.example.shubzz99.prototype;

/**
 * Created by shubzz99 on 28/5/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeFragment();
        } else if (position == 1){
            return new FilterFragment();
        } else {
            return new TrendingFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    //for title of tabs
    @Override
    public CharSequence getPageTitle(int position)
    {
        if (position == 0) {
            return "Home";
        } else if (position == 1){
            return "Filters";
        } else {
            return "Trending";
        }
    }
}
