package com.leo.funnyindicatorlibrary.Base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by littleming on 15/7/29.
 */
public class IndicatorLayout extends LinearLayout{

    private int tabsCount;
    /*
    * draw indicator line
    * */
    private Paint paint;
    private int lineColor = Color.RED;
    private int lineWidth;
    private float translationX;

    public IndicatorLayout(Context context) {
        super(context);
        initPaint();
    }

    public IndicatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(lineColor);
        paint.setStrokeWidth(4);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(translationX, getHeight() - paint.getStrokeWidth() + 0.5f);
        canvas.drawLine(lineWidth / 5, 0, lineWidth * 4 / 5, 0, paint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(tabsCount > 0){
            lineWidth = w / tabsCount;
        }
    }

    public void scroll(int position, float offset){
        if(tabsCount > 0) {
            if(position == (tabsCount - 1)){

            }else {
                translationX = getWidth() / tabsCount * (position + offset);
            }
        }
    }

    public void setTabsCount(int tabsCount){
        this.tabsCount = tabsCount;
    }

    public void setLineWidth(int lineWidth){
        this.lineWidth = lineWidth;
    }

    public void setLineColor(int lineColor){
        this.lineColor = lineColor;
        paint.setColor(lineColor);
    }
}
