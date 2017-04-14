package com.yukun.textapplication.chat;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yukun.textapplication.R;
import com.yukun.textapplication.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListActivity extends AppCompatActivity {

    @BindView(R.id.rv_chat)
    RecyclerView mRvChat;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout mSrRefresh;
    private List<String> mList=new ArrayList<>();
    private RecyclerItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        ButterKnife.bind(this);
        init();
        getaddInfo();
        setListener();
    }
    private void init() {
        mSrRefresh.setRefreshing(true);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this);
        mRvChat.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerItemAdapter(mList,getApplicationContext());
        mRvChat.setAdapter(mAdapter);
    }

    private void setListener() {

        mSrRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getaddInfo();
            }
        });
    }

    private void getaddInfo() {
        //新的数据
        List<String> mListNew=new ArrayList<>();
        for (int j = 1; j <=20;j++) {
            mListNew.add("第"+j+"个!!");
        }
        mListNew.add("加载更多的底部");
        //从头插入数据,原来的保留
        mList.addAll(0,mListNew);

        mSrRefresh.setRefreshing(false);
        mAdapter.addMesasge(mList);
        mAdapter.notifyDataSetChanged();
        mRvChat.smoothScrollToPosition(mListNew.size()-1);
    }
}
