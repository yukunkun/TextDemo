package com.yukun.textapplication.livephonelogin;

import okhttp3.RequestBody;
import com.yukun.textapplication.livephonelogin.bean.Response;
import com.yukun.textapplication.livephonelogin.bean.User2;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yukun on 17-3-23.
 */
public interface UrlSeverice {

    @POST("account/vef")
    Observable<Response<Void>> sendVerifyCode(@Body RequestBody requestBody);

    @POST("account/login")
    Observable<Response<User2>> loginWithPhone(@Body RequestBody requestBody);

    @POST("account/getUserInfo")
    Observable<Response<User2>> getUserInfo(@Body RequestBody requestBody);
}
