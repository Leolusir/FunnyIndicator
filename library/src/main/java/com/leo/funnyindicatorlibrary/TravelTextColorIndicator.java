package com.leo.funnyindicatorlibrary;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.leo.funnyindicatorlibrary.Base.BaseIndicator;
import com.leo.funnyindicatorlibrary.Base.IndicatorSelectedListener;
import com.leo.funnyindicatorlibrary.Base.LogHelper;
import com.leo.funnyindicatorlibrary.Base.OnPageChangedListener;
import com.leo.funnyindicatorlibrary.Base.TravelColorTextView;

import java.util.List;

/**
 * Created by littleming on 15/7/30.
 */
public class TravelTextColorIndicator extends BaseIndicator{

    private static final String TAG = "TravelColorIndicator";

    private final int visibleCount = 5;

    private int normalTextColor = Color.BLUE;

    private int activeTextColor = Color.RED;

    private int textSize = 40;

    private int lineColor = activeTextColor;

    private int layoutWidth;

    private List<String> tabs;

    private ViewPager viewPager;

    private IndicatorSelectedListener indicatorSelectedListener;
    private OnPageChangedListener onPageChangedListener;

    private int lastPosition;

    public TravelTextColorIndicator(Context context) {
        super(context);
    }

    public TravelTextColorIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TravelTextColorIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
                    indicatorLayout.addView(generateTravelColorTextView(tab));
                }
                setItemSelectedEvent();
                resetAllTextViewColor();
                setSelectedTab(0);
            }else{
                LogHelper.e(TAG, "must call method setLayoutWidth(int layoutWidth) first");
            }
        }
    }

    private View generateTravelColorTextView(String title){
        TravelColorTextView travelColorTextView = new TravelColorTextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.width = layoutWidth / visibleCount;
        lp.setMargins(0, 20, 0, 0);
        travelColorTextView.setText(title);
        travelColorTextView.setTextSize(textSize);
        travelColorTextView.setNormalTextColor(normalTextColor);
        travelColorTextView.setActiveTextColor(activeTextColor);
        travelColorTextView.setLayoutParams(lp);

        return travelColorTextView;
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
            if(view instanceof TravelColorTextView){
                TravelColorTextView travelColorTextView = (TravelColorTextView) view;
                travelColorTextView.setStatus(TravelColorTextView.STATUS_NORMAL);
                travelColorTextView.setTextColor(normalTextColor);
                travelColorTextView.invalidate();
            }
        }
    }

    private void setSelectedTab(int position){
        View view = indicatorLayout.getChildAt(position);
        if(view instanceof TravelColorTextView){
            TravelColorTextView travelColorTextView = (TravelColorTextView) view;
            travelColorTextView.setStatus(TravelColorTextView.STATUS_NORMAL);
            travelColorTextView.setTextColor(activeTextColor);
            travelColorTextView.invalidate();
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
                if(position == lastPosition && positionOffset > 0){
                    travelToRight(position, position + 1, positionOffset);
                }else if(position < lastPosition && positionOffset > 0){
                    travelToLeft(position, position + 1, positionOffset);
                }
                if(onPageChangedListener != null)
                    onPageChangedListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(final int position) {
                final int itemWidth = indicatorLayout.getWidth() / indicatorLayout.getChildCount();
                TravelTextColorIndicator.this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(position <= 1){
                            TravelTextColorIndicator.this.smoothScrollTo(0, 0);
                        }else if(position > 1 && position < (indicatorLayout.getChildCount() - 3)){
                            TravelTextColorIndicator.this.smoothScrollTo(((position - 2) * itemWidth), 0);
                        }else if(position == indicatorLayout.getChildCount() - 1){
                            // not scroll
                        }else{
                            TravelTextColorIndicator.this.smoothScrollTo((indicatorLayout.getWidth() - ((visibleCount - 1) * itemWidth)), 0);
                        }
                    }
                }, 300);
                TravelTextColorIndicator.this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetAllTextViewColor();
                        setSelectedTab(position);
                    }
                }, 300);
                if(onPageChangedListener != null)
                    onPageChangedListener.onPageSelected(position);

                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(onPageChangedListener != null)
                    onPageChangedListener.onPageScrollStateChanged(state);
            }
        });
    }

    private void travelToRight(int nowPosition, int nextPosition, float offSet){
        View view = indicatorLayout.getChildAt(nowPosition);
        if(view instanceof TravelColorTextView){
            TravelColorTextView transferText = (TravelColorTextView)view;
            transferText.setProgress(offSet);
            transferText.setDirection(TravelColorTextView.DIRECTION_TO_RIGHT);
            transferText.setStatus(TravelColorTextView.STATUS_OUT);
            transferText.invalidate();
        }
        View view2 = indicatorLayout.getChildAt(nextPosition);
        if(view2 instanceof TravelColorTextView){
            TravelColorTextView transferText = (TravelColorTextView)view2;
            transferText.setProgress(offSet);
            transferText.setDirection(TravelColorTextView.DIRECTION_TO_RIGHT);
            transferText.setStatus(TravelColorTextView.STATUS_IN);
            transferText.invalidate();
        }
    }

    private void travelToLeft(int nowPosition, int lastPosition, float offSet){
        View view = indicatorLayout.getChildAt(nowPosition);
        if(view instanceof TravelColorTextView){
            TravelColorTextView transferText = (TravelColorTextView)view;
            transferText.setProgress(offSet);
            transferText.setDirection(TravelColorTextView.DIRECTION_TO_LEFT);
            transferText.setStatus(TravelColorTextView.STATUS_OUT);
            transferText.invalidate();
        }
        View view2 = indicatorLayout.getChildAt(lastPosition);
        if(view2 instanceof TravelColorTextView){
            TravelColorTextView transferText = (TravelColorTextView)view2;
            transferText.setProgress(offSet);
            transferText.setDirection(TravelColorTextView.DIRECTION_TO_LEFT);
            transferText.setStatus(TravelColorTextView.STATUS_IN);
            transferText.invalidate();
        }
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

    public int getActiveTextColor() {
        return activeTextColor;
    }

    public void setActiveTextColor(int activeTextColor) {
        this.activeTextColor = activeTextColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public List<String> getTabs() {
        return tabs;
    }
}
