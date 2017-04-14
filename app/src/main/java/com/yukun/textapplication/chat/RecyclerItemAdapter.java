package com.yukun.textapplication.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yukun.textapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yukun on 17-4-14.
 */
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
