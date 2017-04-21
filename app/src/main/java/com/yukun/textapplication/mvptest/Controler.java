package com.yukun.textapplication.mvptest;

/**
 * Created by yukun on 17-4-18.
 */
public interface Controler {
    interface BasePresent  {
        void onsubscribe();
        void unsubscribe();
    }

    interface View  {
        void load();
        void init();
        void setAdapter();
    }

}
