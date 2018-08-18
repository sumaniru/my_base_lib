package com.lhd.base.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by arutoria on 2017/6/5.
 */

public class VPAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mlist;
    private boolean shouldTitle = false;
    private String[] titles;

    public VPAdapter(FragmentManager fm, List<Fragment> mlist) {
        this(fm, mlist, null);
    }

    public VPAdapter(FragmentManager fm, List<Fragment> mlist, String[] titles) {
        super(fm);
        if (titles != null) {
            this.titles = titles;
            shouldTitle = true;
        }
        this.mlist = mlist;
    }

    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (shouldTitle)
            return titles[position];
        return super.getPageTitle(position);
    }

}
