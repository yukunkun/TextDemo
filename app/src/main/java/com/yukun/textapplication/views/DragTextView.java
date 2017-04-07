package com.yukun.textapplication.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by yukun on 17-4-7.
 */
public class DragTextView extends RelativeLayout {
    Context context;

    public DragTextView(Context context) {
        super(context);
        init(context,null,0);
    }

    public DragTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);

    }

    public DragTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context=context;
    }

    public void addTextView(){
        RoateTextView roateTextView=new RoateTextView(context);
        roateTextView.setText("My Task");
        roateTextView.setTextSize(20);
        roateTextView.setTextColor(Color.GREEN);
        roateTextView.setPadding(5,5,5,5);
        roateTextView.setClickable(true);
        this.addView(roateTextView);
    }
}
