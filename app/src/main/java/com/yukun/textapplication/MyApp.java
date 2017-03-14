package com.yukun.textapplication;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by yukun on 17-2-28.
 */
public class MyApp extends Application {
    String APPID="dkYtqfYnQsJR6NDzQd1lMNgT-gzGzoHsz";
    String APPKEY="RoFaOKPxeSI7g9IFVUxr8u0P";

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"dkYtqfYnQsJR6NDzQd1lMNgT-gzGzoHsz","RoFaOKPxeSI7g9IFVUxr8u0P");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);
    }
}
