### 下拉加载更多的聊天数据更新 在ChatListActivity里,其他的是测试的demo
#### 聊天的时候会遇到下拉加载更多消息,并且定位到加载的数据最后一条,这里用recyclerview来写一个简单的demo,完成下拉加载更多数据,并且定位到加载的数据的最后一条
#### 布局文件很简单
        <android.support.v4.widget.SwipeRefreshLayout
            android:id="@+id/sr_refresh"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            <android.support.v7.widget.RecyclerView
                android:id="@+id/rv_chat"
                android:layout_width="match_parent"
                android:layout_height="match_parent"/>
        </android.support.v4.widget.SwipeRefreshLayout>
#### 下面是activity里的代码
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
#### 东西其实很少,就是几个常用的方法
#### 主要是下面的这几句完成加载更多的数据,并且定位
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
                //定位到加载的最后一条数据
                mRvChat.smoothScrollToPosition(mListNew.size()-1);
#### adapter和常用的没啥区别,代码贴出来,为了完整性
    public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        List<String> mList;
        Context context;
    
        public RecyclerItemAdapter(List<String> list, Context context) {
            mList = list;
            this.context = context;
        }
        public void addMesasge(List<String> mList){
            this.mList=mList;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item,null));
        }
    
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((MyHolder)holder).mTextView.setText(mList.get(position));
        }
    
        @Override
        public int getItemCount() {
            return mList.size();
        }
        class MyHolder extends RecyclerView.ViewHolder {
            TextView mTextView;
            public MyHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.tv_content);
            }
        }
    }
#### adapter的布局文件
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                  android:orientation="vertical"
                  android:layout_width="match_parent"
                  android:layout_height="match_parent">
        <TextView
            android:text=""
            android:textSize="20dp"
            android:textColor="#70f57b"
            android:gravity="center"
            android:id="@+id/tv_content"
            android:layout_width="match_parent"
            android:layout_height="30dp"/>
        <View
            android:background="#ff8888"
            android:layout_width="match_parent"
            android:layout_height="0.5dp"/>
    
    </LinearLayout>
#### 这里就OK了,虽然简单,下次遇到了才能更好的完成.留个笔记.