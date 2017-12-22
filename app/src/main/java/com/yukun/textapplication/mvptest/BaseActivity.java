package com.yukun.textapplication.mvptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yukun.textapplication.R;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends Controler.BasePresent> extends AppCompatActivity {
    public T basePresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        ButterKnife.bind(this);
        Log.i("-----","onsubscribe");
        basePresent.onsubscribe();
        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("-----","unsubscribe");
        basePresent.unsubscribe();
    }
    abstract int setLayout();

    abstract void initView();
}
