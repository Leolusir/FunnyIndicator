package com.leo.funnyindicatorlibrary.Base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by littleming on 15/7/29.
 */
public class TravelColorTextView extends View {

    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_IN = STATUS_NORMAL + 1;
    public static final int STATUS_OUT = STATUS_IN + 1;
    public static final int DIRECTION_TO_RIGHT = STATUS_OUT + 1;
    public static final int DIRECTION_TO_LEFT = DIRECTION_TO_RIGHT + 1;

    private int viewHeight;

    private int normalTextColor  = Color.BLUE;
    private int activeTextColor  = Color.RED;
    private int textSize = 24;
    private int textColor = normalTextColor;

    private float progress = 1;
    private int status;
    private int direction;

    private Paint paint;

    private int startX = 0;
    private int textWidth;
    private String text;

    public TravelColorTextView(Context context) {
        super(context);
        initPaint();
    }

    public TravelColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public TravelColorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureTextWidth();
        viewHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(status == STATUS_NORMAL){
            paint.setColor(textColor);
            canvas.save();
            canvas.clipRect(startX, 0, startX + textWidth, viewHeight);
            paint.setTextSize(textSize);
            canvas.drawText(text, startX, viewHeight / 3 * 2, paint);
        }else{
            if(direction == DIRECTION_TO_RIGHT){
                if(status == STATUS_IN){
                    canvas.save();
                    paint.setColor(activeTextColor);
                    canvas.clipRect(startX, 0, startX + textWidth * progress, viewHeight);
                    paint.setTextSize(textSize);
                    canvas.drawText(text, startX, viewHeight / 3 * 2, paint);
                    canvas.restore();
                    paint.setColor(normalTextColor);
                    canvas.clipRect(startX + textWidth * progress, 0, startX + textWidth, viewHeight);
                    paint.setTextSize(textSize);
                    canvas.drawText(text, startX, viewHeight / 3 * 2, paint);
                }else if(status == STATUS_OUT){
                    canvas.save();
                    paint.setColor(normalTextColor);
                    canvas.clipRect(startX, 0, startX + textWidth * progress, viewHeight);
                    paint.setTextSize(textSize);
                    canvas.drawText(text, startX, viewHeight / 3 * 2, paint);
                    canvas.restore();
                    paint.setColor(activeTextColor);
                    canvas.clipRect(startX + textWidth * progress, 0, startX + textWidth, viewHeight);
                    paint.setTextSize(textSize);
                    canvas.drawText(text, startX, viewHeight / 3 * 2, paint);
                }
            }else if(direction == DIRECTION_TO_LEFT){
                if(status == STATUS_IN){
                    canvas.save();
                    paint.setColor(activeTextColor);
                    canvas.clipRect(startX, 0, startX + textWidth * progress, viewHeight);
                    paint.setTextSize(textSize);
                    canvas.drawText(text, startX, viewHeight / 3 * 2, paint);
                    canvas.restore();
                    paint.setColor(normalTextColor);
                    canvas.clipRect(startX + textWidth * progress, 0, startX + textWidth, viewHeight);
                    paint.setTextSize(textSize);
                    canvas.drawText(text, startX, viewHeight / 3 * 2, paint);
                }else if(status == STATUS_OUT){
                    canvas.save();
                    paint.setColor(normalTextColor);
                    canvas.clipRect(startX, 0, startX + textWidth * progress, viewHeight);
                    paint.setTextSize(textSize);
                    canvas.drawText(text, startX, viewHeight / 3 * 2, paint);
                    canvas.restore();
                    paint.setColor(activeTextColor);
                    canvas.clipRect(startX + textWidth * progress, 0, startX + textWidth, viewHeight);
                    paint.setTextSize(textSize);
                    canvas.drawText(text, startX, viewHeight / 3 * 2, paint);
                }
            }
        }
    }

    public void setText(String text){
        this.text = text;
        measureTextWidth();
    }

    private void measureTextWidth(){
        if(!TextUtils.isEmpty(text)){
            textWidth = (int)paint.measureText(text);
            startX = getMeasuredWidth() / 2 - textWidth / 2 + getPaddingLeft();
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        paint.setTextSize(textSize);
        measureTextWidth();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public String getText() {
        return text;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public int getNormalTextColor() {
        return normalTextColor;
    }

    public void setNormalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getActiveTextColor() {
        return activeTextColor;
    }

    public void setActiveTextColor(int activeTextColor) {
        this.activeTextColor = activeTextColor;
    }


}
