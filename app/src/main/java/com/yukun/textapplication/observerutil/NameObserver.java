package com.yukun.textapplication.observerutil;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by yukun on 17-2-24.
 */
public class NameObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        NameObservable nameObservable= (NameObservable) o;
        //观察者
        Log.i("----NameObservable" ,nameObservable.name);
    }
}
