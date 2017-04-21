package com.yukun.textapplication.mvptest;

import android.util.Log;


/**
 * Created by yukun on 17-4-18.
 */
public class MVPPresent implements Controler.BasePresent {
    Controler.View mView;

    public MVPPresent(Controler.View view) {
        mView = view;
    }

    @Override
    public void onsubscribe() {
        Log.i("------s","MVPPresent");
        mView.load();
    }

    @Override
    public void unsubscribe() {
        Log.i("------e","MVPPresent");

    }
}
