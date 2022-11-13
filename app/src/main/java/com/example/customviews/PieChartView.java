package com.example.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.Random;

public class PieChartView extends View {

    public float mWidth;
    public float mHeight;
    public float mRadius;
    private Paint mPiePaint;
    private Paint mTextPaint;

    String paises[] = {"Argentina",
            "Bolivia",
            "Brazil",
            "Canada",
            "Chile",
            "Colombia",
            "Ecuador",
            "Guyana",
            "Mexico",
            "Paraguay",
            "Peru",
            "USA",
            "Uruguay",
            "Venezuela"};

    float tasa_natalidad[] = {20.7f, 46.6f, 28.6f, 14.5f, 23.4f, 27.4f, 32.9f, 28.3f, 29f, 34.8f, 32.9f, 16.7f, 18f, 27.5f};

    public PieChartView(Context context) {
        super(context);
        init();
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        mRadius = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
    }

    private float totalValues(){
        float sum=0f;
        for(int i=0; i<tasa_natalidad.length; i++){
            sum+=tasa_natalidad[i];
        }
        return sum;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Random rnd = new Random();
        DecimalFormat df = new DecimalFormat("#");

        float mX = mWidth/2;
        float mY = mHeight/2;
        float mR = mRadius/1.3f;

        float total = totalValues();
        float startAngle = 0;
        float sweepAngle = 0;

        float val = 0;
        float val2 = 0;

        RectF rect = new RectF(mX - mR, mY - mR, mX + mR, mY + mR);

        mPiePaint.setStrokeWidth(mRadius * 0.6f);
        mPiePaint.setStyle(Paint.Style.STROKE);

        for(int i=0; i<tasa_natalidad.length; i++){
            //DRAW PIE CHART
            sweepAngle = 360*(tasa_natalidad[i]/total);
            mPiePaint.setARGB(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(100));;
            canvas.drawArc(rect, startAngle, sweepAngle,false, mPiePaint);
            startAngle += sweepAngle;
            // DRAW TEXT
            val2+=sweepAngle;
            canvas.drawText(String.valueOf(df.format (tasa_natalidad[i]*100/total))+"%", (float)(mR * Math.cos(2 * Math.PI * ((val+val2)/2)/360))+ mX, (float)(mR * Math.sin(2 * Math.PI * ((val+val2)/2)/360)) +mY, mTextPaint);
            val = val2;
        }
    }

    private void init(){
        mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(40f);
    }


}
