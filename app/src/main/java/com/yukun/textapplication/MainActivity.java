package com.yukun.textapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yukun.textapplication.chat.ChatActivity;
import com.yukun.textapplication.chat.ChatListActivity;
import com.yukun.textapplication.livephonelogin.LivePhoneActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textureView)
    Button mTextureView;
    @BindView(R.id.observer)
    Button mObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setListener();
    }

    private void setListener() {
//        mTextureView.setOnClickListener( v -> Toast.makeText(MainActivity.this, "mTextureView", Toast.LENGTH_SHORT).show());
//        mObserver.setOnClickListener( v -> Toast.makeText(MainActivity.this, "mObserver", Toast.LENGTH_SHORT).show());
    }

    public void TextureView(View view) {
        Intent intent = new Intent(this, TextureViewActivity.class);
        startActivity(intent);
    }

    public void Observer(View view) {
        Intent intent = new Intent(this, ObserableActivity.class);
        startActivity(intent);
    }

    public void DragListview(View view) {
        Intent intent = new Intent(this, DragListViewActivity.class);
        startActivity(intent);
    }

    public void Chat(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    public void CardView(View view) {
        Intent intent = new Intent(this, CardActivity.class);
        startActivity(intent);
    }

    public void LoginView(View view) {
        Intent intent = new Intent(this, LivePhoneActivity.class);
        startActivity(intent);
    }

    public void RoateView(View view) {
        Intent intent = new Intent(this, TextViewActivity.class);
        startActivity(intent);
    }

    public void AddList(View view) {
        Intent intent = new Intent(this, ChatListActivity.class);
        startActivity(intent);
    }
}
