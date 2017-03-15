package com.yukun.textapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


/**
 * Created by yukun on 16-7-7.
 */
public class StateBroadcastReceiver extends BroadcastReceiver {
    MessageListener messageListener;
    @Override
    public void onReceive(Context context, Intent intent) {
        NetState(context);
    }
    private void NetState(Context context) {

        messageListener.OnReceived(isNetworkAvailable(context)+"");
        if (isNetworkAvailable(context)) {

        }else {

        }
    }
    public boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        }else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    // 回调接口
    public interface MessageListener {
        public void OnReceived(String message);
    }

    public void setOnReceivedMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }
}
