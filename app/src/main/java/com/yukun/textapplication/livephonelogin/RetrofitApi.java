package com.yukun.textapplication.livephonelogin;

import android.util.Log;

import com.yukun.textapplication.MyApp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yukun on 17-2-20.
 */
public class RetrofitApi {
    static String baseUrl = "https://api.douban.com/v2/movie/";
    public static final String BASEURL = "http://www.yushizhibo.com/web/";


    public static RetrofitApi getInstance(){
        return new RetrofitApi();
    }

    public Retrofit retrofitJson(){

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(10000, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();
                Response response=null;
                if(MyApp.getInstance().getToken()!=null){
                    Log.i("--getToken",MyApp.getInstance().getToken()+"");
//                    Request builder = oldRequest.newBuilder().addHeader("Set-Cookie",MyApp.getInstance().getToken())
                    Request builder = oldRequest.newBuilder().addHeader("Set-Cookie","5C0BF19729B343318D1AA5737C0FBFB4")
                            .header("Content-Type", "application/json; charset=utf-8")
                            .build();
                    response=chain.proceed(builder);
                }else {
                    response=chain.proceed(oldRequest);
                }

                String tempStr = response.headers().get("Set-Cookie");
                if (tempStr != null && MyApp.getInstance().getToken()==null) {
                    String s = tempStr.substring(0, tempStr.indexOf(";"));
                    Log.i("--tokenss",tempStr+"");
                    Log.i("--token",s+"");
                    MyApp.getInstance().setToken(s);
                }
                return response;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(BASEURL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //使用RxJava不能忘记
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
