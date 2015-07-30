package com.leo.funnyindicator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.leo.funnyindicator.adapters.ViewPagerAdapter;
import com.leo.funnyindicatorlibrary.Base.IndicatorSelectedListener;
import com.leo.funnyindicatorlibrary.Base.OnPageChangedListener;
import com.leo.funnyindicatorlibrary.TravelTextColorIndicator;

import java.util.ArrayList;
import java.util.List;

public class TravelColorActivity extends AppCompatActivity implements OnPageChangedListener, IndicatorSelectedListener{
    private TravelTextColorIndicator travelTextColorIndicator;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_color);

        List<String> tabs = new ArrayList<>();
        for(int i = 0; i < 12 ; i ++){
            tabs.add("Tab - " + i);
        }

        travelTextColorIndicator = (TravelTextColorIndicator)findViewById(R.id.indicator);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), tabs.size()));
        travelTextColorIndicator.setViewPager(viewPager);
        travelTextColorIndicator.setLayoutWidth(getResources().getDisplayMetrics().widthPixels);
        travelTextColorIndicator.setTabs(tabs);
        travelTextColorIndicator.setIndicatorSelectedListener(this);
        travelTextColorIndicator.setOnPageChangedListener(this);
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
