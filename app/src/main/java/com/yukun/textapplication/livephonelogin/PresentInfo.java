package com.yukun.textapplication.livephonelogin;

import com.yukun.textapplication.livephonelogin.bean.Response;
import com.yukun.textapplication.livephonelogin.bean.User2;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yukun on 17-3-23.
 */
public class PresentInfo {
    //发送验证码
    public static Observable<Response<Void>> sendPhoneNum(String phoneNumber ){
        Retrofit retrofit = RetrofitApi.getInstance().retrofitJson();
        //构造参数
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", phoneNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return retrofit.create(UrlSeverice.class)
                .sendVerifyCode(RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonObject.toString()))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    //发送验证码
    public static Observable<Response<User2>> login(String phoneNumber, String captcha ){
        Retrofit retrofit = RetrofitApi.getInstance().retrofitJson();
        //构造参数
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("mobile", phoneNumber);
            jsonObject.put("code", captcha);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return retrofit.create(UrlSeverice.class)
                .loginWithPhone(RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonObject.toString()))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    //发送验证码
    public static Observable<Response<User2>> userinfo(long uid){
        Retrofit retrofit = RetrofitApi.getInstance().retrofitJson();
        //构造参数
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retrofit.create(UrlSeverice.class)
                .getUserInfo(RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonObject.toString()))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
