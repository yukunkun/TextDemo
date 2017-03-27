package com.yukun.textapplication.livephonelogin.bean;


import java.util.List;

/**
 * Created by haiyang-lu on 16-9-19.
 * Response2 带有列表的对象
 */
public class Response2<T> {
    public int i;
    public int b;
    public String msg;
//    public OBean o;
    public List<T> a;

    public boolean isSuccess() {
//        return b == Constant.OK;
    return true;

    }
}
