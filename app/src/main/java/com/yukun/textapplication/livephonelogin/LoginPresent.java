package com.yukun.textapplication.livephonelogin;


import android.os.SystemClock;
import android.util.Log;

import com.yukun.textapplication.MyApp;
import com.yukun.textapplication.livephonelogin.bean.Response;
import com.yukun.textapplication.livephonelogin.bean.User2;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yukun on 17-3-23.
 */
public class LoginPresent implements BasePresentImpl {

    View mView;

    public LoginPresent(View mView) {
        this.mView = mView;
    }


    @Override
    public void login(String number,String getnum) {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext(null);
            }
        }).filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                if(number.length()!=11||getnum.length()!=6){
                    mView.showMessage("号码错误");
                    return false;
                }else {
                    mView.showMessage("登录中");
                    return true;
                }
            }
        }).flatMap(new Func1<Object, Observable<Response<User2>>>() {
            @Override
            public Observable<Response<User2>> call(Object o) {

                return PresentInfo.login(number,getnum);
            }
        }).flatMap(new Func1<Response<User2>, Observable<User2>>() {
            @Override
            public Observable<User2> call(Response<User2> os) {
                return Observable.create(new Observable.OnSubscribe<User2>() {
                    @Override
                    public void call(Subscriber<? super User2> subscriber) {
                        subscriber.onNext(os.getO());
                    }
                });
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<User2>() {
            @Override
            public void call(User2 user2) {
                mView.showMessage("登录success");
                MyApp.getInstance().setUser(user2.getUser());

                MyApp.getInstance().setUid(user2.getUser().getId()+"");
                Log.i("----getid",MyApp.getInstance().getUid()+"");
                mView.doNext();

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                mView.showMessage("登录错误"+throwable.toString());

            }
        }, new Action0() {
            @Override
            public void call() {
                mView.showMessage("登录完成");
            }
        });
    }

    @Override
    public void sendPhoneNum(String num) {

        Subscription subscribe = Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                subscriber.onNext(null);
            }
        }).filter(new Func1<Void, Boolean>() {
            @Override
            public Boolean call(Void aVoid) {
                if (num.length() < 11) {
                    mView.showMessage("号码不正确!");
                    return false;
                } else {
                    mView.showMessage("发送验证码!");
                    return true;
                }
            }
        }).flatMap(new Func1<Void, Observable<Response<Void>>>() {
            @Override
            public Observable<Response<Void>> call(Void aVoid) {
                return PresentInfo.sendPhoneNum(num);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response<Void>>() {
            @Override
            public void call(Response<Void> voidResponse) {
                mView.showMessage(voidResponse.toString());
            }
        });
    }
    public  void getUserInfo() {

         Observable.create(new Observable.OnSubscribe<String>() {
            public static final long TIME_DELAY_MS = 2000;

            @Override
            public void call(Subscriber<? super String> subscriber) {
                SystemClock.sleep(TIME_DELAY_MS);//子线程
                subscriber.onNext(null);
            }
        }).subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<String, Observable<Response<User2>>>() {

                    private long uid;

                    @Override
                    public Observable<Response<User2>> call(String s) {
                        if(MyApp.getInstance().getUid()!=null){
                            uid = Long.valueOf(MyApp.getInstance().getUid());
                        }
                        Log.i("----id", uid +"");
                        return PresentInfo.userinfo(896);
                    }
                }).flatMap(new Func1<Response<User2>, Observable<User2>>() {
                    @Override
                    public Observable<User2> call(Response<User2> user2Response) {
                        return Observable.create(new Observable.OnSubscribe<User2>() {

                            @Override
                            public void call(Subscriber<? super User2> subscriber) {
                                Log.i("-----userinfo",user2Response.toString()+"");
                                subscriber.onNext(user2Response.getO());
                            }
                        });
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<User2>() {
             @Override
             public void call(User2 user2) {
                 mView.showMessage("获取信息成功");
                 Log.i("-----userinfo",user2.getUser().toString()+"");

             }
         }, new Action1<Throwable>() {
             @Override
             public void call(Throwable throwable) {
                 mView.showMessage(throwable.toString());
                 Log.i("-----throwable",throwable.toString());
             }
         });
    }

}
