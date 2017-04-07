package com.yukun.textapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yukun on 17-4-6.
 */
public class RoateTextView extends TextView {

    Paint paint=new Paint();
    Rect rect=new Rect();
    int lastX;
    int lastY;
    private int screenWidth;
    private int screenHeight;

    public RoateTextView(Context context) {
        super(context);
        init(context,null,0);

    }

    public RoateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);

    }

    public RoateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    boolean isCheck=false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View v=this;
        int action=event.getAction();
        Log.i("@@@@@@", "Touch:"+action);
        switch(action){
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx =(int)event.getRawX() - lastX;
                int dy =(int)event.getRawY() - lastY;

                int left = v.getLeft() + dx;
                int top = v.getTop() + dy;
                int right = v.getRight() + dx;
                int bottom = v.getBottom() + dy;
                if(left < 0){
                    left = 0;
                    right = left + v.getWidth();
                }
                if(right > screenWidth){
                    right = screenWidth;
                    left = right - v.getWidth();
                }
                if(top < 0){
                    top = 0;
                    bottom = top + v.getHeight();
                }
                if(bottom > screenHeight){
                    bottom = screenHeight;
                    top = bottom - v.getHeight();
                }
                v.layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        rect=new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
        if(isCheck){
            canvas.drawRect( rect,paint);
        }
        paint.setTextAlign(Paint.Align.LEFT);
        super.onDraw(canvas);
    }

    float progress=0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    public void setRoate(float progress){
        this.progress=progress;
        invalidate();
    }

    public void setPos(int x,int y){
//        this.x=x;
//        this.y=y;
        invalidate();
    }
}
