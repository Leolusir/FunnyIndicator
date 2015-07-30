package com.leo.funnyindicator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.leo.funnyindicator.adapters.ViewPagerAdapter;
import com.leo.funnyindicatorlibrary.Base.IndicatorSelectedListener;
import com.leo.funnyindicatorlibrary.Base.OnPageChangedListener;
import com.leo.funnyindicatorlibrary.DefaultIndicator;

import java.util.ArrayList;
import java.util.List;

public class DefaultActivity extends AppCompatActivity implements OnPageChangedListener, IndicatorSelectedListener{
    private DefaultIndicator indicator;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        indicator = (DefaultIndicator)findViewById(R.id.indicator);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        List<String> titles = new ArrayList<>();
        for(int i = 0; i < 12 ; i++){
            titles.add("Tab - " + i);
        }
        indicator.setLayoutWidth(getResources().getDisplayMetrics().widthPixels);
        indicator.setTabs(titles);
        indicator.setViewPager(viewPager);
        indicator.setIndicatorSelectedListener(this);
        indicator.setOnPageChangedListener(this);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), titles.size()));
    }

    @Override
    public void onItemSeleted(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
