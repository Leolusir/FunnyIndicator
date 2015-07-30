package com.leo.funnyindicator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.leo.funnyindicator.adapters.ViewPagerAdapter;
import com.leo.funnyindicatorlibrary.TravelHeadColorIndicator;

import java.util.ArrayList;
import java.util.List;

public class FunnyHeadActivity extends AppCompatActivity {
    private TravelHeadColorIndicator travelHeadColorIndicator;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funny_head);

        List<String> titles = new ArrayList<>();
        titles.add("小世界");
        titles.add("大梦想");
        titles.add("依然范特西");
        titles.add("叶惠美");
        titles.add("阿里巴巴");
        titles.add("好多好吃的");
        titles.add("我太TM帅了");
        titles.add("呵呵");

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.CYAN);
        colors.add(Color.LTGRAY);
        colors.add(Color.YELLOW);
        colors.add(Color.DKGRAY);

        travelHeadColorIndicator = (TravelHeadColorIndicator) findViewById(R.id.travel_color_view);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), titles.size()));
        travelHeadColorIndicator.setViewPager(viewPager);
        travelHeadColorIndicator.setTitles(titles);
        travelHeadColorIndicator.setColors(colors);
        travelHeadColorIndicator.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_funny_head, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
