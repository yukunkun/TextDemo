package com.yukun.textapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Path;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.yukun.textapplication.observerutil.NameObservable;
import com.yukun.textapplication.observerutil.NameObserver;
import com.yukun.textapplication.receiver.StateBroadcastReceiver;
import com.yukun.textapplication.views.GiftFrameLayout;
import com.yukun.textapplication.views.GiftSendModel;

import java.util.ArrayList;
import java.util.List;

public class ObserableActivity extends AppCompatActivity {

//    private PathAnimView mPathAnimView;
//    private StoreHouseAnimView storeHouseAnimView;
    private GiftFrameLayout giftFrameLayout1;
    private GiftFrameLayout giftFrameLayout2;
    List<GiftSendModel> giftSendModelList = new ArrayList<GiftSendModel>();
    private StateBroadcastReceiver stateBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obserable);
        stateBroadcastReceiver = new StateBroadcastReceiver();

        IntentFilter filter=new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(stateBroadcastReceiver,filter);

        init();
//        mPathAnimView = (PathAnimView) findViewById(R.id.pathAnimView);
//        storeHouseAnimView = (StoreHouseAnimView) findViewById(R.id.pathAnimView);
        setPathAnim();
        anims();
    }

    private void setPathAnim() {
//        mPathAnimView.setSourcePath(PathParserUtils.getPathFromArrayFloatList(StoreHousePath.getPath("ZhangXuTong", 0.4f, 5)));
//        mPathAnimView.setAnimTime(500);
//        mPathAnimView.setColorBg(Color.WHITE).setColorFg(Color.BLACK);
//        mPathAnimView.getPaint().setStrokeWidth(10);
//        mPathAnimView.getPaint().setTextSize(100);
//        SvgPathParser svgPathParser = new SvgPathParser();
//        String viewpath = getResources().getString(R.string.del);
//        try {
//            Path path = svgPathParser.parsePath(viewpath);
//            mPathAnimView.setSourcePath(path);
//        } catch (java.text.ParseException e) {
//            e.printStackTrace();
//        }
//
//        mPathAnimView.getPathAnimHelper().setAnimTime(5000);
//        mPathAnimView.startAnim();
//        storeHouseAnimView.setSourcePath(PathParserUtils.getPathFromArrayFloatList(StoreHousePath.getPath("2017-12-12",0.5f,5)));
//        storeHouseAnimView.setPathMaxLength(50).setAnimTime(1000).startAnim();
    }

    private void init() {
        NameObservable nameObservable=new NameObservable();
        nameObservable.addObserver(new NameObserver());
        nameObservable.setName("sam");
        nameObservable.setName("tom");
        stateBroadcastReceiver.setOnReceivedMessageListener(new StateBroadcastReceiver.MessageListener() {
            @Override
            public void OnReceived(String message) {
                Toast.makeText(ObserableActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anims() {
        giftFrameLayout1 = (GiftFrameLayout) findViewById(R.id.gift_layout1);
        giftFrameLayout2 = (GiftFrameLayout) findViewById(R.id.gift_layout2);
    }

    public void startAnim(View view) {
        starGiftAnimation(createGiftSendModel());
    }

    private void starGiftAnimation(GiftSendModel model) {
        if (!giftFrameLayout1.isShowing()) {
            sendGiftAnimation(giftFrameLayout1,model);
        }else if(!giftFrameLayout2.isShowing()){
            sendGiftAnimation(giftFrameLayout2,model);
        }else{
            giftSendModelList.add(model);
        }
    }

    private void sendGiftAnimation(final GiftFrameLayout view, GiftSendModel model){
        view.setModel(model);
        AnimatorSet animatorSet = view.startAnimation(model.getGiftCount());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                synchronized (giftSendModelList) {
                    if (giftSendModelList.size() > 0) {
                        view.startAnimation(giftSendModelList.get(giftSendModelList.size() - 1).getGiftCount());
                        giftSendModelList.remove(giftSendModelList.size() - 1);
                    }
                }
            }
        });
    }

    private GiftSendModel createGiftSendModel(){
        return new GiftSendModel((int)(Math.random()*10));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(stateBroadcastReceiver);
    }
}
