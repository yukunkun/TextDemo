package com.yukun.textapplication.livephonelogin.bean;


/**
 * Created by haiyang-lu on 16-9-19.
 * Response
 */
public class Response<T> {
    public int i;
    public int b;
    public String msg;
    public T o;

    public boolean isSuccess() {
//        return b == Constant.OK;
        return true;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getO() {
        return o;
    }

    public void setO(T o) {
        this.o = o;
    }

    @Override
    public String toString() {
        return "Response{" +
                "i=" + i +
                ", b=" + b +
                ", msg='" + msg + '\'' +
                ", o=" + o +
                '}';
    }
}
