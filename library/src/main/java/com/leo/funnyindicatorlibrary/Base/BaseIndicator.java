package com.leo.funnyindicatorlibrary.Base;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by littleming on 15/7/29.
 */
public class BaseIndicator extends HorizontalScrollView{
    protected IndicatorLayout indicatorLayout;

    protected int default_text_normal_color = Color.GRAY;
    protected int default_text_normal_size = 10;
    protected int default_text_selected_color = Color.RED;
    protected int default_Text_selected_size = 16;
    protected int default_line_color = Color.RED;

    public BaseIndicator(Context context) {
        super(context);
        initBaseIndicator(context);
    }

    public BaseIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBaseIndicator(context);
    }

    public BaseIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBaseIndicator(context);
    }

    private void initBaseIndicator(Context context){
        indicatorLayout = new IndicatorLayout(context);
        indicatorLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(indicatorLayout);
        setHorizontalScrollBarEnabled(false);
    }


}
