package com.example.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class BarView extends View {

    public float mWidth;
    public float mHeight;
    private Paint mBarPaint;
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

    public BarView(Context context) {
        super(context);
        init();
    }

    public BarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
    }

    private float mayorValor(){
        float mayor = 0;
        for(int i=0; i<tasa_natalidad.length; i++){
            if(tasa_natalidad[i] > mayor){
                mayor = tasa_natalidad[i];
            }
        }
        return mayor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //PADDINGS
        int paddingWidth = 50;
        int paddingHeight = 80;
        float heightFinal = (float) (getMeasuredHeight()-paddingHeight);

        //VALORES EJE Y
        float mayorValor = mayorValor();
        int decimalSuperior = (((int)(mayorValor/10)+1)*10);
        Log.d("LINE", "Decimal: " + String.valueOf(decimalSuperior));
        Log.d("LINE", "Height: " + String.valueOf(getMeasuredHeight()));
        int escala = 5;
        int nLineas = decimalSuperior/escala + 1;
        float division = heightFinal/nLineas;
        Log.d("LINE", "Division: " + String.valueOf(division));

        //DIBUJAR LAS LINEAS
        for(int j=nLineas; j>0; j--){
            canvas.drawLine(paddingWidth, division*j, getMeasuredWidth() - (paddingWidth - 30), division*j, mTextPaint);
            Log.d("LINE","Coor: \n" +
                                    "x1: " + paddingWidth +"\n"+
                                    "y1: "+ division*j+"\n"+
                                    "x2: "+ getMeasuredWidth()+"\n"+
                                    "y2: "+ division*j);
        }

        //DIBUJAR LOS INDICES
        for(int i=0; i<nLineas; i++){
            String medida = String.valueOf(i*escala);
            canvas.drawText(medida,20, heightFinal - (i*division) + 5, mTextPaint);
        }

        //DIBUJAR LAS BARRAS
        int paddingBars = 40;
        float rectangleWidth = (getMeasuredWidth() - paddingBars * 2)/14;
        Log.d("BAR", "Width: "+String.valueOf(getMeasuredWidth()));
        Log.d("BAR", "Height: "+String.valueOf(getMeasuredHeight()));
        Log.d("BAR", "Rectangle width: "+String.valueOf(rectangleWidth));

        for(int i=0; i<tasa_natalidad.length; i++){
            canvas.drawRect(paddingBars*2 + (rectangleWidth*i),
                            division * ((decimalSuperior - tasa_natalidad[i])/escala+1),
                    paddingBars + rectangleWidth * (i+1),
                                heightFinal, mBarPaint);
            Log.d("REC","Medidas: "+
                                "L: " + String.valueOf(paddingBars + (rectangleWidth*i))+"\n"+
                                "T: " + String.valueOf(getMeasuredHeight()/2)+"\n"+
                                "R: "+ String.valueOf(paddingBars + rectangleWidth * (i+1))+"\n"+
                                "B: "+String.valueOf(getMeasuredHeight() - paddingBars));

            canvas.rotate(-45, paddingBars + (rectangleWidth/2) + (rectangleWidth*i), heightFinal + paddingHeight);
            canvas.drawText(paises[i], paddingBars + (rectangleWidth/2) + (rectangleWidth*i),heightFinal + paddingHeight, mTextPaint );
            canvas.rotate(45, paddingBars + (rectangleWidth/2) + (rectangleWidth*i), heightFinal + paddingHeight);
        }
    }

    private void init(){
        int color = ContextCompat.getColor(getContext(), R.color.orange);
        mBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBarPaint.setColor(color);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(20);
    }
}
