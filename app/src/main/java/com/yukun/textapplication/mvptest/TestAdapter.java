package com.yukun.textapplication.mvptest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yukun.textapplication.R;

import java.util.List;

/**
 * Created by yukun on 17-4-19.
 */
public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<Status> mStatusList;

    public TestAdapter(Context context, List<Status> statusList) {
        mContext = context;
        mStatusList = statusList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mInflate = LayoutInflater.from(mContext).inflate(R.layout.rv_item, null);
        return new MHolder(mInflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MHolder){
            ((MHolder) holder).mTextView.setText(mStatusList.get(position).getText());
        }
    }

    @Override
    public int getItemCount() {
        return mStatusList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    class MHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        public MHolder(View itemView) {
            super(itemView);
            mTextView= (TextView) itemView.findViewById(R.id.text);
        }
    }
}
