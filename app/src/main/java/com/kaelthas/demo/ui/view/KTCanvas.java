package com.kaelthas.demo.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;

import com.kaelthas.demo.R;

import java.util.Date;

/**
 * Created by KaelThas.Wang on 2017/10/12.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class KTCanvas extends View {
    private Paint paint;

    private float r = 300;


    public KTCanvas(Context context) {
        this(context, null);

    }

    public KTCanvas(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public KTCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(); //设置一个笔刷大小是3的黄色的画笔
        paint.setColor(Color.YELLOW);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);

        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        r = canvas.getWidth() / 2 - 200;

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        //黑色背景
        canvas.drawColor(Color.rgb(0, 0, 0));


//        paint.setTextSize(20);
//        canvas.translate(20,20);
//        canvas.drawText("1",0,0,paint);
//        canvas.translate(20,20);
//        canvas.drawText("2",0,0,paint);
//        canvas.translate(20,20);
//        canvas.drawText("3",0,0,paint);
//        canvas.translate(20,20);
//        canvas.drawText("4",0,0,paint);


        canvas.translate(canvas.getWidth() / 2, r * 2); //将位置移动画纸的坐标点:横向中间,纵向r
        canvas.drawCircle(0, 0, r, paint); //画圆圈


        canvas.save();

        Paint tmpPaint = new Paint(paint); //小刻度画笔对象
        tmpPaint.setStrokeWidth(2);
        tmpPaint.setTextSize(30);
        float y = r;
        int count = 60; //总刻度数
        canvas.rotate(210);
        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(0f, y, 0, y -20f, paint);
                //canvas.drawText(String.valueOf(i / 5 + 1), -12f, y + 50f, tmpPaint);

            } else {
                canvas.drawLine(0f, y, 0f, y - 10f, tmpPaint);
            }
            canvas.rotate(360 / count, 0f, 0f); //旋转画纸
        }


        canvas.restore();
        canvas.save();


        //使用path绘制路径文字 保存画布,绘制完成后恢复画布,方便处理圆心点为0,0

        canvas.translate(-0.75f * r, -0.75f * r);


        Path path = new Path();
        path.addArc(new RectF(0, 0, 1.5f * r, 1.5f * r), -180, 180);
        Paint citePaint = new Paint(paint);
        citePaint.setTextSize(22);
        citePaint.setStrokeWidth(1);
        canvas.drawTextOnPath("http://www.android777.com11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", path, 100, -20, citePaint);

        //恢复圆心位置
        canvas.restore();
        canvas.save();

        //绘制指针
        tmpPaint.setColor(Color.GRAY);
        tmpPaint.setStrokeWidth(4);
        canvas.drawCircle(0, 0, 7, tmpPaint);
        tmpPaint.setStyle(Paint.Style.FILL);
        tmpPaint.setColor(Color.YELLOW);
        canvas.drawCircle(0, 0, 5, tmpPaint);

        canvas.rotate(6 * getTime().second);
        canvas.drawLine(0, 10, 0, -0.95f * r, paint);

        //分针
        canvas.restore();
        canvas.save();
        paint.setStrokeWidth(6);
        canvas.rotate(6 * getTime().minute);
        canvas.drawLine(0, 10, 0, -0.8f * r, paint);


        canvas.restore();
        canvas.save();
        paint.setStrokeWidth(8);
        canvas.rotate(6 * getTime().hour);
        canvas.drawLine(0, 10, 0, -0.6f * r, paint);

        canvas.restore();

        canvas.skew(0,1);

    }


    public  Time getTime() {
        Time t = new Time();
        t.setToNow();
        return t;
    }

    public void init() {
        new Thread() {
            @Override
            public void run() {
                while (true) {

                    try {
                        mHandler.sendEmptyMessage(0);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
        }
    };
}
