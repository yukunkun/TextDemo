package com.yukun.textapplication.observerutil;

import java.util.Observable;

/**
 * Created by yukun on 17-2-24.
 */
public class NameObservable extends Observable {
    public String name;
    public  void setName(String name){
        this.name=name;
        setChanged();    //标记此 Observable对象为已改变的对象
        notifyObservers();    //通知所有观察者
    }
}
