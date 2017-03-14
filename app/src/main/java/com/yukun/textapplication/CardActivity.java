package com.yukun.textapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.mcxtzhang.layoutmanager.swipecard.CardConfig;
import com.mcxtzhang.layoutmanager.swipecard.OverLayCardLayoutManager;
import com.mcxtzhang.layoutmanager.swipecard.RenRenCallback;
import com.yukun.textapplication.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<Integer> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        recyclerView = (RecyclerView) findViewById(R.id.rec);
        for (int i = 0; i < 5; i++) {
            list.add(R.mipmap.apic18729);
//            list.add(R.mipmap.feng1);
        }
        recyclerView.setLayoutManager(new OverLayCardLayoutManager());
        CardConfig.initConfig(this);
        CardConfig.MAX_SHOW_COUNT = 6;
        RecyclerViewAdapter mAdapter=new RecyclerViewAdapter(getApplicationContext(),list);
        ItemTouchHelper.Callback callback = new RenRenCallback(recyclerView, mAdapter, list);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter);
    }
}
