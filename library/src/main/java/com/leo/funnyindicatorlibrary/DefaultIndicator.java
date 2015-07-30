package com.leo.funnyindicatorlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.funnyindicatorlibrary.Base.BaseIndicator;
import com.leo.funnyindicatorlibrary.Base.DrawableCenterTextView;
import com.leo.funnyindicatorlibrary.Base.IndicatorSelectedListener;
import com.leo.funnyindicatorlibrary.Base.LogHelper;
import com.leo.funnyindicatorlibrary.Base.OnPageChangedListener;

import java.util.List;

/**
 * Created by littleming on 15/7/29.
 */
public class DefaultIndicator extends BaseIndicator {
    private static final String TAG = "DefaultIndicator";

    private final int visibleCount = 5;

    private int normalTextColor;
    private int normalTextSize;

    private int selectedTextColor;
    private int selectedTextSize;

    private int lineColor;

    private int layoutWidth;

    private List<String> tabs;

    private ViewPager viewPager;

    private IndicatorSelectedListener indicatorSelectedListener;
    private OnPageChangedListener onPageChangedListener;

    public DefaultIndicator(Context context) {
        super(context);
    }

    public DefaultIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttribute(context, attrs);
    }

    public DefaultIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttribute(context, attrs);
    }

    private void setAttribute(Context context, AttributeSet attrs){
        if(context == null || attrs == null){
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorLayout);
        normalTextColor = typedArray.getColor(R.styleable.IndicatorLayout_normal_text_color, default_text_normal_color);
        normalTextSize = typedArray.getDimensionPixelSize(R.styleable.IndicatorLayout_normal_text_size, default_text_normal_size);
        selectedTextColor = typedArray.getColor(R.styleable.IndicatorLayout_selected_text_color, default_text_selected_color);
        selectedTextSize = typedArray.getDimensionPixelSize(R.styleable.IndicatorLayout_selected_text_size, default_Text_selected_size);
        lineColor = typedArray.getColor(R.styleable.IndicatorLayout_line_color, default_line_color);
        indicatorLayout.setLineColor(lineColor);
        typedArray.recycle();
    }

    /*
    * this method must call before setTabs(List<String> tabs)
    * */
    public void setLayoutWidth(int layoutWidth){
        this.layoutWidth = layoutWidth;
        indicatorLayout.setLineWidth(layoutWidth / visibleCount);
    }

    public void setTabs(List<String> tabs) {
        if(tabs != null && tabs.size() > 0) {
            if(layoutWidth > 0) {
                this.tabs = tabs;
                indicatorLayout.setTabsCount(tabs.size());
                for (String tab : tabs) {
                    indicatorLayout.addView(generateTextView(tab));
                }
                setItemSelectedEvent();
                resetAllTextViewColor();
                resetAllTextViewSize();
                setSelectedTab(0);
            }else{
                LogHelper.e(TAG, "must call method setLayoutWidth(int layoutWidth) first");
            }
        }
    }

    private View generateTextView(String title){
        DrawableCenterTextView tv = new DrawableCenterTextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.width = layoutWidth / visibleCount;
        lp.setMargins(0, 20, 0, 0);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(normalTextColor);
        tv.setText(title);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, normalTextSize);
        tv.setLayoutParams(lp);
        return tv;
    }

    private void setItemSelectedEvent(){
        for (int i = 0; i < indicatorLayout.getChildCount(); i ++){
            final int position = i;
            View view = indicatorLayout.getChildAt(position);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(indicatorSelectedListener != null)
                        indicatorSelectedListener.onItemSeleted(position);
                }
            });
        }
    }

    private void resetAllTextViewColor(){
        for (int i = 0; i < indicatorLayout.getChildCount(); i ++){
            View view = indicatorLayout.getChildAt(i);
            if(view instanceof TextView){
                TextView textView = (TextView) view;
                textView.setTextColor(normalTextColor);
            }
        }
    }

    private void resetAllTextViewSize(){
        for (int i = 0; i < indicatorLayout.getChildCount(); i ++){
            View view = indicatorLayout.getChildAt(i);
            if(view instanceof TextView){
                TextView textView = (TextView) view;

                textView.setTextSize(normalTextSize);
            }
        }
    }

    private void setSelectedTab(int position){
        View view = indicatorLayout.getChildAt(position);
        if(view instanceof TextView){
            TextView textView = (TextView) view;
            textView.setTextColor(selectedTextColor);
            textView.setTextSize(selectedTextSize);
        }
    }

    //getter and setter
    public void setViewPager(ViewPager viewPager){
        this.viewPager = viewPager;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicatorLayout.scroll(position, positionOffset);
                indicatorLayout.invalidate();
                if(onPageChangedListener != null)
                    onPageChangedListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(final int position) {
                final int itemWidth = indicatorLayout.getWidth() / indicatorLayout.getChildCount();
                DefaultIndicator.this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(position <= 1){
                            DefaultIndicator.this.smoothScrollTo(0, 0);
                        }else if(position > 1 && position < (indicatorLayout.getChildCount() - 3)){
                            DefaultIndicator.this.smoothScrollTo(((position - 2) * itemWidth), 0);
                        }else if(position == indicatorLayout.getChildCount() - 1){
                            // not scroll
                        }else{
                            DefaultIndicator.this.smoothScrollTo((indicatorLayout.getWidth() - ((visibleCount - 1) * itemWidth)), 0);
                        }
                    }
                }, 300);
                resetAllTextViewColor();
                resetAllTextViewSize();
                setSelectedTab(position);
                if(onPageChangedListener != null)
                    onPageChangedListener.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(onPageChangedListener != null)
                    onPageChangedListener.onPageScrollStateChanged(state);
            }
        });
    }

    public void setIndicatorSelectedListener(IndicatorSelectedListener indicatorSelectedListener){
        this.indicatorSelectedListener = indicatorSelectedListener;
    }

    public void setOnPageChangedListener(OnPageChangedListener onPageChangedListener){
        this.onPageChangedListener = onPageChangedListener;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public int getNormalTextColor() {
        return normalTextColor;
    }

    public void setNormalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
    }

    public int getNormalTextSize() {
        return normalTextSize;
    }

    public void setNormalTextSize(int normalTextSize) {
        this.normalTextSize = normalTextSize;
    }

    public int getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public int getSelectedTextSize() {
        return selectedTextSize;
    }

    public void setSelectedTextSize(int selectedTextSize) {
        this.selectedTextSize = selectedTextSize;
    }

    public List<String> getTabs() {
        return tabs;
    }
}
