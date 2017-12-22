package com.yukun.textapplication.mvptest;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.yukun.textapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MVPActivity extends BaseActivity<MVPPresent> implements Controler.View {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.swip)
    SwipeRefreshLayout mSwip;
    private RVAdapter mRvAdapter;
    int PAGE_SIZE;
    private List<Status> mStatuses;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
////        setContentView(R.layout.activity_mvp);
////        setLayout(R.layout.activity_mvp); //加载在baseactivity的布局,使用ButterKnife
//
//        super.onCreate(savedInstanceState);//这句必须在最后,表示先执行上面的代码,后执行baseactivity的代码
////        init();
//    }

    @Override
    int setLayout() {
        return R.layout.activity_mvp;
    }

    @Override
    void initView() {
        MVPPresent mvpPresent = new MVPPresent(this);
        this.basePresent = mvpPresent;
    }

    @Override
    public void load() {
        Toast.makeText(MVPActivity.this, "load", Toast.LENGTH_SHORT).show();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvList.setLayoutManager(manager);
        mStatuses = new ArrayList<>();
        getInfo();
//        RVBaseAdapter rvbAdapter=new RVBaseAdapter(this,mStatuses);
//        mRvList.setAdapter(rvbAdapter);
        RVBActivity rvbAdapter=new RVBActivity(this,mStatuses);
        mRvList.setAdapter(rvbAdapter);
//        TestAdapter rvbAdapter=new TestAdapter(this,mStatuses);
//        mRvList.setAdapter(rvbAdapter);
    }

    @Override
    public void init() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvList.setLayoutManager(manager);
        mStatuses = new ArrayList<>();
        getInfo();
        mRvAdapter = new RVAdapter(R.layout.rv_item, mStatuses, this);
        mRvList.setAdapter(mRvAdapter);

        mRvAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Toast.makeText(MVPActivity.this, pos + "", Toast.LENGTH_SHORT).show();
            }
        });
        mRvAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Status status = (Status) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.text2:
                        Toast.makeText(MVPActivity.this, "text2", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

//        mRvAdapter.openLoadAnimation();

//        // 自定义动画如此轻松
        mRvAdapter.openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{
                        ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                        ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
                };
            }
        });
        PAGE_SIZE = mStatuses.size();

        mRvAdapter.openLoadMore(PAGE_SIZE, true);
        mRvAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRvList.post(new Runnable() {
                    @Override
                    public void run() {
//                        if (mCurrentCounter >= TOTAL_COUNTER) {
//                            mRvAdapter.notifyDataChangedAfterLoadMore(false);
//                        } else {
//                            mRvAdapter.notifyDataChangedAfterLoadMore(statuses.getSampleData(PAGE_SIZE), true);
//                            mCurrentCounter = mQuickAdapter.getItemCount();
//                        }
                        getInfo();
                        mRvAdapter.notifyDataChangedAfterLoadMore(true);
                        Toast.makeText(MVPActivity.this, "more", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        mSwip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStatuses.clear();
                getInfo();
            }
        });

    }

    private void getInfo() {
        for (int i = 1; i <= 10; i++) {
            Status status = new Status();
            status.setText("我的你的.." + i);
            status.setUserName("小m"+i);
            mStatuses.add(status);
//            mSwip.setRefreshing(false);
        }

    }

    @Override
    public void setAdapter() {

    }
}
