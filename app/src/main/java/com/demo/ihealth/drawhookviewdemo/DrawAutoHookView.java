package com.demo.ihealth.drawhookviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * DrawAutoHookView 加载进度条，结束对号
 * Created by hz on 2017/7/19.
 */
public class DrawAutoHookView extends View {

    private static String TAG = "DrawAutoHookView";

    //当前是进度状态还是完成状态
    private boolean isLoading = true;

    //绘制圆弧的进度值
    private int progress = 0;
    //线1的x轴
    private int line1_x = 0;
    //线1的y轴
    private int line1_y = 0;
    //线2的x轴
    private int line2_x = 0;
    //线2的y轴
    private int line2_y = 0;
    private int currentPosition;

    public DrawAutoHookView(Context context) {
        super(context);
    }

    public DrawAutoHookView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawAutoHookView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //绘制

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 绘制圆弧
         */
        Paint paint = new Paint();
        //设置画笔颜色
        paint.setColor(getResources().getColor(R.color.white));
        //设置圆弧的宽度
        paint.setStrokeWidth(6);
        //设置圆弧为空心
        paint.setStyle(Paint.Style.STROKE);
        //消除锯齿
        paint.setAntiAlias(true);

        //获取圆心的x坐标
        int center = getWidth() / 2;
        int center1 = center - getWidth() / 5;
        //圆弧半径
        int radius = getWidth() / 2 - 5;

        //定义的圆弧的形状和大小的界限
        RectF rectF = new RectF(center - radius - 1, center - radius - 1, center + radius + 1, center + radius + 1);


        /**
         * 当前等待状态95归0，成功则画对号
         */
        if (isLoading) {
            //画圆弧 加速2倍
            drawCircle(canvas, paint, rectF);
        } else {
            //画整圆 加速2倍
            setFullCircle(canvas, paint, center, center1, radius, rectF);
        }


        //每隔10毫秒界面刷新
        postInvalidateDelayed(100);
    }

    /**
     * 画圆弧
     *
     * @param canvas
     * @param paint
     * @param rectF
     */
    private void drawCircle(Canvas canvas, Paint paint, RectF rectF) {
        //根据进度画圆弧
        progress++;
        currentPosition = 360 * progress / 100;
        canvas.drawArc(rectF, currentPosition, 360 * progress / 100, false, paint);
        if (progress > 100) {
            progress = 0;
        }
    }
    /**
     * 画整圆
     *
     * @param canvas
     * @param paint
     * @param center
     * @param center1
     * @param radius
     * @param rectF
     */
    private void setFullCircle(Canvas canvas, Paint paint, int center, int center1, int radius, RectF rectF) {
        progress++;
        //根据进度画圆弧
        canvas.drawArc(rectF, currentPosition, 360 * progress / 100, false, paint);
        /**
         * 绘制对勾
         */
        //先等圆弧画完，才话对勾
        if (progress >= 100) {
            drawLine(canvas, paint, center, center1, radius);
            drawLine(canvas, paint, center, center1, radius);
            drawLine(canvas, paint, center, center1, radius);
            drawLine(canvas, paint, center, center1, radius);
        }
    }

    /**
     * 画对勾
     *
     * @param canvas
     * @param paint
     * @param center
     * @param center1
     * @param radius
     */
    private void drawLine(Canvas canvas, Paint paint, int center, int center1, int radius) {
        if (line1_x < radius / 3) {
            line1_x++;
            line1_y++;
        }
        //画第一根线
        canvas.drawLine(center1, center, center1 + line1_x, center + line1_y, paint);

        if (line1_x == radius / 3) {
            line2_x = line1_x;
            line2_y = line1_y;
            line1_x++;
            line1_y++;
        }
        if (line1_x >= radius / 3 && line2_x <= radius) {
            line2_x++;
            line2_y--;
        }
        //画第二根线
        canvas.drawLine(center1 + line1_x - 1, center + line1_y, center1 + line2_x, center + line2_y, paint);
    }



    /**
     * 设置是否加载的状态 完成就结束画对勾
     *
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
    }

}
