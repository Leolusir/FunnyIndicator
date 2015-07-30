package com.leo.funnyindicator.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.leo.funnyindicator.fragments.PagerItem;

/**
 * Created by littleming on 15/7/29.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter{

    private int count;

    public ViewPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Fragment getItem(int i) {
        return new PagerItem();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PagerItem pagerItem = (PagerItem)super.instantiateItem(container, position);

        return pagerItem;
    }
}
