package com.yukun.textapplication;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MyViewActivity extends AppCompatActivity {
    ImageView myImage;
    RelativeLayout mLayout;
    RelativeLayout.LayoutParams mParams;
    Button mButton;
    /**Calledwhentheactivityisfirstcreated.*/
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);
        mLayout=(RelativeLayout)findViewById(R.id.rea);

        mParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        myImage=new ImageView(this);
        myImage.setImageResource(R.mipmap.ic_launcher);
        mLayout.addView(myImage);
        mButton=(Button)findViewById(R.id.myButton1);
        mButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                ImageView icon=new ImageView(MyViewActivity.this);
                if(icon!=null){
                    mLayout.removeView(icon);
                }
                addIcon(1234,200,200);
            }
        });
    }
    private ImageView addIcon(int id,int x,int y){
        RelativeLayout.LayoutParams mParams=new RelativeLayout.LayoutParams(50,50);

        ImageView icon=new ImageView(this);
        icon.setImageResource(R.drawable.circle2);
        icon.setLayoutParams(mParams);
        mLayout.addView(icon);
        return icon;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action=event.getAction();
        int mX=(int)event.getX();
        int mY=(int)event.getY();
        switch(action){
            case MotionEvent.ACTION_DOWN:
//logd("ACTION_DOWN");
            break;
            case MotionEvent.ACTION_MOVE:
//logd("ACTION_MOVE");
            break;
            case MotionEvent.ACTION_UP:
                logd("ACTION_UPmX="+mX+",mY="+mY);
/**
 *不能使用myImage.layout()方法，来改变位置。
 *layout()虽然可以改变控件的位置，但不会将位置信息保存到layoutparam中。
 *而调用addView往布局添加新的控件时，是根据layoutparam来重新布局控件位置的。
 *这里需要用另一种方法：先获取控件的layoutparam，改变其中相关的值后，再设置回去。
 */
//myImage.layout(mX,mY,mX+myImage.getWidth(),mY+myImage.getHeight());
                ViewGroup.LayoutParams layoutParams=myImage.getLayoutParams();
                layoutParams.width=mX;
                layoutParams.height=mY;
            myImage.setLayoutParams(layoutParams);
            break;
            default:
                break;
        }
        return true;
    }
    private static final String TAG="MyViewActivity";
    private static final boolean debugOn=true;
    private int logd(String msg){
        int retVal=0;
        if(debugOn){
            retVal=Log.i(TAG,msg);
        }
        return retVal;
    }
}
