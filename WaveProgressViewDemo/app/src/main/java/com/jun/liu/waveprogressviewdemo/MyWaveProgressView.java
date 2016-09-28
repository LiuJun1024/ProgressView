package com.jun.liu.waveprogressviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liujun on 2016/8/26.
 */
public class MyWaveProgressView extends View {

    private Bitmap background;
    private Path mPath_1;
    private Path mPath_2;
    private Paint mPathPaint_1;
    private Paint mPathPaint_2;
    private Paint mTextPaint;

    private String mWaveColor_1 = "#36F5C8";
    private String mWaveColor_2 = "#83FFDE";
    private String mTextColor = "#FFFFFF";
    private String currentText = "";

    private int width;
    private int height;
    private int mTextSize = 41;
    private int maxProgress = 100;
    private int curProgress = 0;
    private int waveSpeedContral = 26;

    private float CurY;
    private float distance = 0;
    private float distance2 = 100f;
    private float mWaveHeight = 100f;
    private float mWaveHalfWidth = 150f;

    public int getWaveSpeedContral() {
        return waveSpeedContral;
    }

    public void setWaveSpeedContral(int waveSpeedContral) {
        this.waveSpeedContral = waveSpeedContral;
    }


    /**
     * 设置波浪宽度
     *
     * @param waveHalfWidth
     */
    public void setWaveHalfWidth(float waveHalfWidth) {
        mWaveHalfWidth = waveHalfWidth;
    }

    /**
     * 设置波浪高度
     *
     * @param waveHeight
     */
    public void setWaveHeight(float waveHeight) {
        mWaveHeight = waveHeight;
    }


    /**
     * 设置当前进度与进度文字
     *
     * @param currentProgress
     * @param currentText
     */
    public void setCurrent(int currentProgress, String currentText) {
        this.curProgress = currentProgress;
        this.currentText = currentText;
    }

    /**
     * 设置波纹颜色
     *
     * @param mWaveColor
     */
    public void setWaveColor(String mWaveColor) {
        this.mWaveColor_1 = mWaveColor;
    }

    /**
     * 设置文字大小和颜色
     */

    public void setText(String mTextColor, int mTextSize) {
        this.mTextColor = mTextColor;
        this.mTextSize = mTextSize;
    }

    public MyWaveProgressView(Context context) {
        this(context, null);
    }

    public MyWaveProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWaveProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (null == getBackground()) {
            throw new IllegalArgumentException("background is null");
        } else {
            background = getBitmapFromDrawable(getBackground());
        }

        mPath_1 = new Path();
        mPath_2 = new Path();

        mPathPaint_1 = new Paint();
        mPathPaint_1.setAntiAlias(true);
        mPathPaint_1.setStyle(Paint.Style.FILL);

        mPathPaint_2 = new Paint();
        mPathPaint_2.setAntiAlias(true);
        mPathPaint_2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        mPathPaint_2.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
                postDelayed(this,10);
            }
        },10);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        CurY = height = MeasureSpec.getSize(heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(createBitmap(), 0, 0, null);
    }

    private Bitmap createBitmap() {

        mPathPaint_1.setColor(Color.parseColor(mWaveColor_1));
        mPathPaint_2.setColor(Color.parseColor(mWaveColor_2));
        mTextPaint.setColor(Color.parseColor(mTextColor));
        mTextPaint.setTextSize(mTextSize);

        //缩放背景的画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Bitmap finalBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(finalBitmap);//画path的画布

        float CurMidY = height * (maxProgress - curProgress) / maxProgress;

        CurY = CurY - (CurY - CurMidY) / 10;


        mPath_1.reset();
        mPath_1.moveTo(0 - distance, CurY);
        int waveNum = width / ((int) mWaveHalfWidth * 4) + 2;
        int multiplier = 0;
        for (int i = 0; i < waveNum; i++) {
            mPath_1.quadTo(mWaveHalfWidth * (multiplier + 1) - distance, CurY - mWaveHeight, mWaveHalfWidth * (multiplier + 2) - distance, CurY);
            mPath_1.quadTo(mWaveHalfWidth * (multiplier + 3) - distance, CurY + mWaveHeight, mWaveHalfWidth * (multiplier + 4) - distance, CurY);
            multiplier += 4;
        }
        distance += mWaveHalfWidth / waveSpeedContral;
        distance %= (mWaveHalfWidth * 4);
        mPath_1.lineTo(width, height);
        mPath_1.lineTo(0, height);
        mPath_1.close();


        mPath_2.reset();
        mPath_2.moveTo(0 - distance2, CurY);
        int waveNum2 = width / ((int) mWaveHalfWidth * 4) + 2;
        int multiplier2 = 0;
        for (int i = 0; i < waveNum2; i++) {
            mPath_2.quadTo(mWaveHalfWidth * (multiplier2 + 1) - distance2, CurY - mWaveHeight, mWaveHalfWidth * (multiplier2 + 2) - distance2, CurY);
            mPath_2.quadTo(mWaveHalfWidth * (multiplier2 + 3) - distance2, CurY + mWaveHeight, mWaveHalfWidth * (multiplier2 + 4) - distance2, CurY);
            multiplier2 += 4;
        }
        distance2 += mWaveHalfWidth / waveSpeedContral;
        distance2 %= (mWaveHalfWidth * 4);
        mPath_2.lineTo(width, height);
        mPath_2.lineTo(0, height);
        mPath_2.close();

        canvas.drawPath(mPath_2, mPathPaint_2);


        canvas.drawPath(mPath_1, mPathPaint_1);

        int min = Math.min(width, height);
        background = Bitmap.createScaledBitmap(background, min, min, false);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

        canvas.drawBitmap(background, 0, 0, paint);

        canvas.drawText(currentText, width / 2, height / 2, mTextPaint);
        return finalBitmap;
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (null == drawable) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }

    }

}
