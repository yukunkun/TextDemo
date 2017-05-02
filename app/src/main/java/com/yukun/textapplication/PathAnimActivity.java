package com.yukun.textapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yukun.textapplication.views.CircleProgressbar;
import com.yukun.textapplication.views.CircleView;
import com.yukun.textapplication.views.DynamicHeartView;

import java.util.ArrayList;
import java.util.List;

public class PathAnimActivity extends AppCompatActivity {

    private DynamicHeartView mDynamicHeartView;
    private CircleProgressbar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_anim);
        init();
    }

    private void init() {
        mDynamicHeartView = (DynamicHeartView) findViewById(R.id.dynamicHeartView);
        mDynamicHeartView.startPathAnim(2000);
        mProgressbar = (CircleProgressbar) findViewById(R.id.circleprogress);
        List<Integer> mColorList=new ArrayList<>();
        mColorList.add(Color.GRAY);
        mColorList.add(Color.GREEN);
        mColorList.add(Color.BLUE);
        mProgressbar.setColorList(mColorList);
        mProgressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressbar.destoryProgressbar();
            }
        });
        mDynamicHeartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressbar.startProgressbar();
            }
        });
    }
}
