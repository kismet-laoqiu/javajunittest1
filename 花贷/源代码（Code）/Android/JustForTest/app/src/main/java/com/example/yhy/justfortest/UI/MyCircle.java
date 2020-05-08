package com.example.yhy.justfortest.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Dimension;
import android.view.View;
import android.util.AttributeSet;
import android.widget.Toast;

import com.example.yhy.justfortest.R;

/**
 * Created by yhy on 19-6-28.
 */

public class MyCircle extends View {
    private float percent;
    private int Backgroud;
    private int shadowColor;
    public MyCircle(Context context, AttributeSet attrs) {
        super(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyCircle,0,0);
        percent=a.getFloat(R.styleable.MyCircle_percent,0);
        Backgroud= a.getColor(R.styleable.MyCircle_BackgroundColor,0x000000);
        shadowColor=a.getColor(R.styleable.MyCircle_ShadowColor,0x000000);

    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int h=this.getHeight();
        int w=this.getWidth();
        @SuppressLint("DrawAllocation") Paint paint=new Paint();
        paint.setColor(Backgroud);
        canvas.drawCircle(w/2, h/2,w/2,paint);
        @SuppressLint("DrawAllocation") RectF rectF=new RectF(0,(h-w)/2,w,(h+w)/2);
        paint.setColor(shadowColor);
        canvas.drawArc(rectF,270,percent*365,true,paint);
        Toast.makeText(getContext(),"测试用的扇形，可以从后台获得数据更改所占比例",Toast.LENGTH_SHORT).show();
    }

    public void setPercent(float percent) {
        this.percent = percent;
        invalidate();
    }
}
