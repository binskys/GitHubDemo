package com.github.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

import com.github.app.R;
import com.github.app.utils.DpOrPxUtils;

import java.text.BreakIterator;

/**
 * Created by benny
 * on 2017/10/16.
 */

public class CircleBarView extends View {

    private Paint paintRect;
    private Paint paintCircleBg;
    private Paint paintCircle;
    private CircleAnim anim;
    private static float sweepAngle = 360;//结束角度
    private float startAngle = 0;//开始角度
    private float barWidth = 10;//画笔宽度
    private int maxNum = 100;//最大进度值
    private int progressTime = 0;//进度时间
    private float progressSweepAngle = CircleBarView.sweepAngle;//进度比例值
    private float progressNum = 0;
    private int paintColor = Color.GRAY;//
    private int paintColorBg = Color.GRAY;
    private int defaultSize;//自定义View默认的宽高
    private RectF mRect;//绘制圆弧的矩形区域
    private TextView textView;
    private OnAnimationListener onAnimationListener;
    private int min = 0;

    public void setOnAnimationListener(OnAnimationListener onAnimationListener) {
        this.onAnimationListener = onAnimationListener;
    }

    public CircleBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        anim = new CircleAnim();
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        defaultSize = DpOrPxUtils.dip2px(context, 100);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleBarView);
        paintColor = typedArray.getColor(R.styleable.CircleBarView_progress_color, Color.GREEN);//默认为绿色
        paintColorBg = typedArray.getColor(R.styleable.CircleBarView_bg_color, Color.GRAY);//默认为灰色
        startAngle = typedArray.getFloat(R.styleable.CircleBarView_start_angle, 0);//默认为0
        sweepAngle = typedArray.getFloat(R.styleable.CircleBarView_sweep_angle, 360);//默认为360
        barWidth = typedArray.getDimension(R.styleable.CircleBarView_bar_width, DpOrPxUtils.dip2px(context, 10));//默认为10dp
        typedArray.recycle();//typedArray用完之后需要回收，防止内存泄漏
        paintRect(context, attrs);
        paintCircle(context, attrs);
        paintCircleBg(context, attrs);
    }

    /**
     * 矩形
     *
     * @param context
     * @param attrs
     */
    private void paintRect(Context context, AttributeSet attrs) {
        paintRect = new Paint();
        paintRect.setStyle(Paint.Style.STROKE);//只描边不填充；
        paintRect.setColor(Color.RED);//矩形画笔
    }

    /**
     * 圆环
     *
     * @param context
     * @param attrs
     */
    private void paintCircle(Context context, AttributeSet attrs) {
        paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);//只描边不填充；
        paintCircle.setColor(paintColor);//圆环画笔
        paintCircle.setAntiAlias(true);//设置抗锯齿
        paintCircle.setStrokeWidth(barWidth);
    }


    /**
     * 圆环背景
     *
     * @param context
     * @param attrs
     */
    private void paintCircleBg(Context context, AttributeSet attrs) {
        paintCircleBg = new Paint();
        paintCircleBg.setStyle(Paint.Style.STROKE);//只描边不填充；
        paintCircleBg.setColor(paintColorBg);//圆环画笔
        paintCircleBg.setAntiAlias(true);//设置抗锯齿
        paintCircleBg.setStrokeWidth(barWidth);
    }

    //写个方法给外部调用;
    // 用来设置动画时间
    public void setProgressNum(float progressNum, int time) {
        this.progressNum = progressNum;
        anim.setDuration(time);
        this.startAnimation(anim);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = 50;
        float y = 50;
        mRect = new RectF(x, y, x + 300, y + 300);//定义一个矩形
        if (min >= barWidth * 2) {//这里简单限制了圆弧的最大宽度
            mRect.set(barWidth / 2, barWidth / 2, min - barWidth / 2, min - barWidth / 2);
        }
        //矩形
     //   canvas.drawRect(mRect, paintRect);//绘制矩形
        //圆环背景
        canvas.drawArc(mRect, startAngle, sweepAngle, false, paintCircleBg);//这里角度0对应的是三点钟方向，顺时针方向递增

        //进度圆环
        canvas.drawArc(mRect, startAngle, progressSweepAngle, false, paintCircle);//这里角度0对应的是三点钟方向，顺时针方向递增
    }


    private class CircleAnim extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            progressSweepAngle = interpolatedTime * sweepAngle * progressNum / maxNum;//这里计算进度条的比例
            if (textView != null) {
                textView.setText(onAnimationListener.howToChangeText(interpolatedTime, progressNum, maxNum));
            }
            onAnimationListener.howTiChangeProgressColor(paintCircle,interpolatedTime,progressNum,maxNum);
            postInvalidate();
        }
    }

    /**
     * 设置显示文字的TextView
     *
     * @param textView
     */
    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public interface OnAnimationListener {
        /**
         * 如何处理要显示的文字内容
         *
         * @param interpolatedTime 从0渐变成1,到1时结束动画
         * @param progressNum      进度条数值
         * @param maxNum           进度条最大值
         * @return
         */
        String howToChangeText(float interpolatedTime, float progressNum, float maxNum);
        /**
         * 如何处理进度条的颜色
         * @param paint 进度条画笔
         * @param interpolatedTime 从0渐变成1,到1时结束动画
         * @param progressNum 进度条数值
         * @param maxNum 进度条最大值
         */
        void howTiChangeProgressColor(Paint paint, float interpolatedTime, float progressNum, float maxNum);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = measureSize(defaultSize, heightMeasureSpec);
        int width = measureSize(defaultSize, widthMeasureSpec);
        min = Math.min(width, height);// 获取View最短边的长度
        setMeasuredDimension(min, min);// 强制改View为以最短边为长度的正方形


    }

    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }
}
