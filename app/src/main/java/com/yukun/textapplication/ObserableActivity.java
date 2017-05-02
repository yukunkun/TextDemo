package com.yukun.textapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Path;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.yukun.textapplication.observerutil.NameObservable;
import com.yukun.textapplication.observerutil.NameObserver;
import com.yukun.textapplication.receiver.StateBroadcastReceiver;
import com.yukun.textapplication.views.BMoveView;
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
    private int lastPos;
    private int firstPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obserable);
        stateBroadcastReceiver = new StateBroadcastReceiver();

        IntentFilter filter=new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(stateBroadcastReceiver,filter);

        init();
        anims();
        bMoveInit();
    }

    private void bMoveInit() {
        BMoveView bMoveView= (BMoveView) findViewById(R.id.bmoveview);
        RadioGroup radioGroup= (RadioGroup) findViewById(R.id.rg_group);
        ((RadioButton) (radioGroup.getChildAt(0))).setChecked(true);
        firstPos=0;
        bMoveView.startAnim();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    boolean checked = ((RadioButton) (group.getChildAt(i))).isChecked();
                    if(checked){
                        lastPos=i;
                        bMoveView.setTwoPos(firstPos,lastPos);
//                        bMoveView.setPosition(i);
//                        bMoveView.startAnim();
//                        bMoveView.startLineAnim();
//                        bMoveView.setRoationx(0);
//                        bMoveView.startLineEndAnim();
                        firstPos=lastPos;
                    }
                }
            }
        });
    }

    public void testPathAnimator(){
        final FrameLayout l = (FrameLayout) findViewById(R.id.root_view);

        final ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher);
        FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l.addView(imageView, param);

        Path path = new Path();
        path.moveTo(200, 200);

        path.quadTo(800, 200, 800, 800);

        PathInterpolator pathInterpolator = new PathInterpolator(0.33f,0f,0.12f,1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                l.removeView(imageView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                l.removeView(imageView);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        ObjectAnimator scalex = ObjectAnimator.ofFloat(imageView, View.SCALE_X, 1.0f, 0.3f);
        ObjectAnimator scaley = ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 1.0f, 0.3f);
        ObjectAnimator traslateAnimator = ObjectAnimator.ofFloat(imageView, "x", "y", path);

        animSet.playTogether(scalex, scaley, traslateAnimator);

        animSet.setInterpolator(pathInterpolator);
        animSet.setDuration(1500);
        animSet.start();
    }


    private void init() {
        testPathAnimator();
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
