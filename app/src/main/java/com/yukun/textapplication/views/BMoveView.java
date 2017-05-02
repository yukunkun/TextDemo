package com.yukun.textapplication.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

/**
 * Created by yukun on 17-4-27.
 */
public class BMoveView extends View {

    private int mWidth;
    private int mHeight;
    private Paint mPaint=new Paint();
    private RectF mRectF;
    private int boardWidth=50;
    private int maxCircle=360;
    private int firstPos;  //第一次点击位置
    private int lastPos;  //第二次点击位置
    private int lineControl; //下面控制线条的起始位置

    public void setTwoPos(int firstPos,int lastPos) {
        this.firstPos = firstPos;
        this.lastPos=lastPos;
        devidePos();
    }

    //判断两次的位置,选择不同动画
    private void devidePos() {
        setRoationx(0);
        if(firstPos==0){
            if(lastPos==1){
                leftToRigth(lastPos, 1);
            } else if(lastPos==2){
                leftToRigth(lastPos, 2);
            }
        }else if(firstPos==1){
            if(lastPos==0){
                leftToRigth(lastPos, -1);
            } else if(lastPos==2){
                leftToRigth(lastPos, 1);
            }
        }
        else if(firstPos==2){
            if(lastPos==0){
                leftToRigth(lastPos, -2);
            } else if(lastPos==1){
                leftToRigth(lastPos, -1);
            }
        }
    }

    /**
     *
     * @param lastPos 上一次的位置
     * @param startLineineLastPosition 正为向右,负为想左,如果是1.则跨度为一,如果是2,则跨度为2;
     */
    private void leftToRigth(int lastPos, int startLineineLastPosition) {
        setPosition(lastPos);
        startAnim();
        lineControl=lastPos-startLineineLastPosition;//下面控制线条的起始位置
//        if(startLineineLastPosition==1){
//            lineControl=lastPos-1;
//        }else if(startLineineLastPosition==-1){
//            lineControl=lastPos+1;
//        }else if(startLineineLastPosition==2){
//            lineControl=lastPos-2;
//        }else if(startLineineLastPosition==-2){
//            lineControl=lastPos+2;
//        }
        startLineAnim(startLineineLastPosition);
        startLineEndAnim(startLineineLastPosition);
    }

    public void setRoationx(int roationx) {
        mRoationx = roationx;
    }

    private int mRoationx=0;

    private int radio=5;
    private int position=0;//点击到了那个button
    private int mLineEndLength;
    private int mLineLength;

    public void setPosition(int position) {
        this.position = position;
    }

    public BMoveView(Context context) {
        super(context);
        init(context,null,0);
    }

    public BMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public BMoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        boardWidth=dip2px(context,25);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画弧度
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        //位置计算比较麻烦,用比例
        mRectF=new RectF(mWidth/6-boardWidth+position*mWidth/3,mHeight/2-boardWidth,mWidth/6+boardWidth+position*mWidth/3,mHeight/2+boardWidth);
        canvas.drawArc(mRectF,90,mRoationx,true,mPaint);
        //画圆覆盖
        mPaint.reset();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mWidth/6+(position)*mWidth/3,mHeight/2,boardWidth-radio,mPaint);
        //画线条
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(radio);
        //起始和结束不同,每次动画结束位置是相同的,控制起始点和结束点
        canvas.drawLine(mWidth/6+lineControl*mWidth/3+mLineEndLength,mHeight/2+boardWidth-radio/2,mWidth/6+lineControl*mWidth/3+mLineLength,mHeight/2+boardWidth-radio/2, mPaint);
    }

    //圆圈的动画
    public void startAnim(){
        ValueAnimator animator = ValueAnimator.ofInt(0,maxCircle);
        animator.setDuration(500);
        animator.setStartDelay(500);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRoationx = (int)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
    //线条开始的动画
    public void startLineAnim(int startLineineLastPosition){
        ValueAnimator animator = ValueAnimator.ofInt(0,(mWidth/3)*startLineineLastPosition);
        animator.setDuration(150);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLineLength = (int)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
    //线条结束的动画
    public void startLineEndAnim(int endLineineLastPosition){
        ValueAnimator animator = ValueAnimator.ofInt(0,(mWidth/3)*endLineineLastPosition);
        animator.setDuration(500);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLineEndLength = (int)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
