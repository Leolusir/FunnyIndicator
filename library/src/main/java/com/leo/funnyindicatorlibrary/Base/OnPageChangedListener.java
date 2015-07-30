package com.leo.funnyindicatorlibrary.Base;

/**
 * Created by littleming on 15/7/29.
 */
public interface OnPageChangedListener {
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
    void onPageSelected(int position);
    void onPageScrollStateChanged(int state);
}
