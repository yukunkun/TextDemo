package com.yukun.textapplication.livephonelogin;

/**
 * Created by yukun on 17-3-23.
 */
public interface BasePresentImpl {
    void login(String number,String getnum);
    void sendPhoneNum(String num);

    interface  View{
        void showMessage(String msg);
        void doNext();
    }
}
