package com.yukun.textapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.yukun.textapplication.bean.OnEventMessage;
import com.yukun.textapplication.livephonelogin.bean.User;
import com.yukun.textapplication.views.StrokeTextView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by yukun on 17-2-28.
 */
public class MyApp extends Application {
    String APPID="dkYtqfYnQsJR6NDzQd1lMNgT-gzGzoHsz";
    String APPKEY="RoFaOKPxeSI7g9IFVUxr8u0P";
    User users=null;
    static MyApp myApp;
    @Override
    public void onCreate() {
        super.onCreate();
        myApp=this;
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"dkYtqfYnQsJR6NDzQd1lMNgT-gzGzoHsz","RoFaOKPxeSI7g9IFVUxr8u0P");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);

        AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());
    }

    public static MyApp getInstance(){
        return myApp;
    }

    public User getUser() {
        return users;
    }

    public void setUser(User user) {
        if(users!=null){
            user=null;
        }
        this.users = user;
    }

    public String getToken() {
        String token = this.getSharedPreferences("token", Context.MODE_PRIVATE).getString("token", null);
        if (token != null) {
            return token;
        }
        return null;
    }

    public void setToken(String token) {
        SharedPreferences sp = this.getSharedPreferences("token", Context.MODE_PRIVATE);
        sp.edit().putString("token", token).apply();
    }

    public String getUid() {
        String token = this.getSharedPreferences("uid", Context.MODE_PRIVATE).getString("uid", null);
        if (token != null) {
            return token;
        }
        return null;
    }

    public void setUid(String token) {
        SharedPreferences sp = this.getSharedPreferences("uid", Context.MODE_PRIVATE);
        sp.edit().putString("uid", token).apply();
    }

    public static class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
            if(message instanceof AVIMTextMessage){
                String from = ((AVIMTextMessage) message).getFrom();
                String name = (String)conversation.getAttribute("name");
//                Log.d("aa & bb",((AVIMTextMessage)message).getText());
                EventBus.getDefault().post(new OnEventMessage(((AVIMTextMessage)message).getText(),message));
            }
        }

        public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){

        }
    }

}
