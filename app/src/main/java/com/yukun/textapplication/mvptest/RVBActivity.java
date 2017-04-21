package com.yukun.textapplication.mvptest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yukun.textapplication.R;

import java.util.List;

/**
 * Created by yukun on 17-4-19.
 */
public class RVBActivity extends RVBaseAdapter<Status,RVBActivity.MHolder> {
    private Context mContext;
    private List<Status> mList;

    public RVBActivity(Context context, List<Status> list) {
        super(context, list,R.layout.rv_item);
        mContext = context;
        mList = list;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MHolder){
            ((MHolder) holder).mTextView.setText(((Status)(mList.get(position))).getText());
            ((MHolder) holder).mTextView2.setText(((Status)(mList.get(position))).getUserName());
        }
    }

    public static  class MHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        TextView mTextView2;
        public MHolder(View itemView) {
            super(itemView);
            mTextView= (TextView) itemView.findViewById(R.id.text);
            mTextView2= (TextView) itemView.findViewById(R.id.text2);
        }
    }
}
