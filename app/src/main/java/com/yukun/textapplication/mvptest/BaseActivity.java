package com.yukun.textapplication.mvptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yukun.textapplication.R;

import butterknife.ButterKnife;

public class BaseActivity<T extends Controler.BasePresent> extends AppCompatActivity {
    public T basePresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        Log.i("-----","onsubscribe");
        basePresent.onsubscribe();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("-----","unsubscribe");
        basePresent.unsubscribe();
    }

   public void setLayout(int layout){
       setContentView(layout);
   }
}
