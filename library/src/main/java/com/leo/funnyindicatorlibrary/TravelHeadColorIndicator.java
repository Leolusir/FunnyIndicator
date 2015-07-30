package com.leo.funnyindicatorlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.leo.funnyindicatorlibrary.Base.LogHelper;
import com.leo.funnyindicatorlibrary.Base.OnPageChangedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by littleming on 15/7/29.
 */
public class TravelHeadColorIndicator extends RelativeLayout {
    public static final int DIRECTION_CONSTANT = 0;
    public static final int DIRECTION_TO_RIGHT = DIRECTION_CONSTANT + 1;
    public static final int DIRECTION_TO_LEFT = DIRECTION_TO_RIGHT + 1;

    private int leftColor = Color.RED;
    private int rightColor = Color.BLUE;
    private int nowColor = Color.RED;

    private float progress = 1;
    private int direction;

    private int viewWidth;
    private int viewHeight;

    private int startX;
    private int textWidth;

    private String leftText = "小世界";
    private String rightText = "大梦想";
    private String nowText = "";

    private Paint paint;

    private ViewPager viewPager;
    private OnPageChangedListener onPageChangedListener;
    
    private List<String> titles = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();

    private int lastPosition;

    public TravelHeadColorIndicator(Context context) {
        super(context);
        this.setWillNotDraw(false);
        initPaint();
    }

    public TravelHeadColorIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setWillNotDraw(false);
        initPaint();
    }

    public TravelHeadColorIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setWillNotDraw(false);
        initPaint();
    }

    private void initPaint(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(60);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        startX = getPaddingLeft();
        viewWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        viewHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        LogHelper.i("progress", progress + "");

        if(direction == DIRECTION_CONSTANT){
            paint.setColor(Color.WHITE);
            canvas.drawColor(nowColor);
            textWidth = (int)paint.measureText(nowText);
            canvas.drawText(nowText, viewWidth / 2 - textWidth / 2, viewHeight / 2 - 10, paint);
        } else if (direction == DIRECTION_TO_RIGHT) {
            canvas.clipRect(startX, 0, startX + viewWidth * (1 - progress), viewHeight);
            canvas.drawColor(leftColor);
            paint.setColor(Color.WHITE);
            textWidth = (int)paint.measureText(leftText);
            canvas.drawText(leftText, viewWidth / 2 - textWidth / 2, viewHeight / 2 - 10, paint);
            canvas.restore();

            canvas.clipRect(startX + viewWidth * (1 - progress), 0, viewWidth, viewHeight);
            canvas.drawColor(rightColor);
            paint.setColor(Color.WHITE);
            textWidth = (int)paint.measureText(rightText);
            canvas.drawText(rightText, viewWidth / 2 - textWidth / 2, viewHeight / 2 - 10, paint);
            canvas.restore();
        } else if (direction == DIRECTION_TO_LEFT) {
            canvas.clipRect(startX + viewWidth * (1 - progress), 0, viewWidth, viewHeight);
            canvas.drawColor(rightColor);
            paint.setColor(Color.WHITE);
            textWidth = (int)paint.measureText(rightText);
            canvas.drawText(rightText, viewWidth / 2 - textWidth / 2, viewHeight / 2 - 10, paint);
            canvas.restore();

            canvas.clipRect(startX, 0, startX + viewWidth * (1 - progress), viewHeight);
            canvas.drawColor(leftColor);
            paint.setColor(Color.WHITE);
            textWidth = (int)paint.measureText(leftText);
            canvas.drawText(leftText, viewWidth / 2 - textWidth / 2, viewHeight / 2 - 10, paint);
            canvas.restore();
        }
    }

    public void setViewPager(ViewPager viewPager){
        this.viewPager = viewPager;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == lastPosition && positionOffset > 0){
                    toRight(position, position + 1, positionOffset);
                }else if(position < lastPosition && positionOffset > 0){
                    toLeft(position + 1, position, positionOffset);
                }
                if(onPageChangedListener != null)
                    onPageChangedListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                setDirection(DIRECTION_CONSTANT);
                invalidate();

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

    private void toRight(int nowPosition, int nextPosition, float offset){
        setProgress(offset);
        setDirection(DIRECTION_TO_RIGHT);
        setLeftText(titles.get(nowPosition));
        setRightText(titles.get(nextPosition));
        setLeftColor(colors.get(nowPosition));
        setRightColor(colors.get(nextPosition));
        setNowColor(rightColor);
        setNowText(rightText);
        invalidate();
    }

    private void toLeft(int nowPosition, int lastPosition, float offset){
        setProgress(offset);
        setDirection(DIRECTION_TO_LEFT);
        setLeftText(titles.get(lastPosition));
        setRightText(titles.get(nowPosition));
        setLeftColor(colors.get(lastPosition));
        setRightColor(colors.get(nowPosition));
        setNowColor(leftColor);
        setNowText(leftText);
        invalidate();
    }

    public void setOnPageChangedListener(OnPageChangedListener onPageChangedListener){
        this.onPageChangedListener = onPageChangedListener;
    }

    public void setTitles(List<String> titles){
        this.titles.clear();
        this.titles.addAll(titles);;
        setNowText(titles.get(0));
    }

    public void setColors(List<Integer> colors){
        this.colors.clear();
        this.colors.addAll(colors);
        setLeftColor(colors.get(0));
        setRightColor(colors.get(1));
        setNowColor(colors.get(0));
    }

    public String getNowText() {
        return nowText;
    }

    public void setNowText(String nowText) {
        this.nowText = nowText;
    }

    public int getNowColor() {
        return nowColor;
    }

    public void setNowColor(int nowColor) {
        this.nowColor = nowColor;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public String getLeftText() {
        return leftText;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getLeftColor() {
        return leftColor;
    }

    public void setLeftColor(int leftColor) {
        this.leftColor = leftColor;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public int getRightColor() {
        return rightColor;
    }

    public void setRightColor(int rightColor) {
        this.rightColor = rightColor;
    }
}
