package com.yukun.textapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yukun.textapplication.views.DragTextView;
import com.yukun.textapplication.views.RoateTextView;

public class TextViewActivity extends AppCompatActivity {

//    private RoateTextView roateTextView;
    private SeekBar seekBar;
    private SeekBar seekBar2;
    private int size=15;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        init();
    }

    private void init() {
//        roateTextView = (RoateTextView) findViewById(R.id.roatetextview);
        seekBar = ((SeekBar) findViewById(R.id.seekbar));
        seekBar2 = ((SeekBar) findViewById(R.id.seekbar2));
        layout = (RelativeLayout) findViewById(R.id.reaContain);
//        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setMax(360);
        seekBar2.setMax(100);
    }


//        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if(fromUser){
//                    roateTextView.setTextSize(size+progress);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        roateTextView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                return false;
//            }
//        });
//
//        RoateTextView roateTextView=new RoateTextView(this);
//        RoateTextView roateTextView1=new RoateTextView(this);
//        layout.addView(roateTextView);
//        layout.addView(roateTextView1);
//    }

    public void But(View view) {
        RelativeLayout.LayoutParams mParams=
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        RoateTextView roateTextView=new RoateTextView(this);
        roateTextView.setText("我们的东西真的");
        roateTextView.setTextColor(Color.BLUE);
        roateTextView.setTextSize(20);
        roateTextView.setClickable(true);
        roateTextView.setLayoutParams(mParams);
        layout.addView(roateTextView);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
//        layout.addTextView();
        Intent intent=new Intent(this,MyViewActivity.class);
//        startActivity(intent);

    }

//    @Override
//    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//        if(fromUser){
//            roateTextView.setRoate(progress);
//        }
//    }
//
//    @Override
//    public void onStartTrackingTouch(SeekBar seekBar) {
//
//    }
//
//    @Override
//    public void onStopTrackingTouch(SeekBar seekBar) {
//
//    }
}
