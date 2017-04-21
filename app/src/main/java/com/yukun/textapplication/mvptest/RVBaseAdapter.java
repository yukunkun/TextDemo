package com.yukun.textapplication.mvptest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.yukun.textapplication.R;

import java.util.List;
import java.util.Map;


/**
 * Created by yukun on 17-4-19.
 */
public  class RVBaseAdapter<T,V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private int typeCount=1;
    public View mInflate;
    private static final int STATEONE=1;
    private static final int STATETWO=2;
    private static final int STATETHREE=3;
    private List<T> mList;
    private boolean isViewList;
    private int layout;
    private static Map<Integer,View> mMap;

    public RVBaseAdapter(Context context, List<T> list,int layout) {
        mContext = context;
        mList = list;
        this.layout=layout;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(typeCount==1){
            mInflate = LayoutInflater.from(mContext).inflate(layout, null);
            return new RVBActivity.MHolder(mInflate);
        }else if(typeCount==2){
//            mInflate = LayoutInflater.from(mContext).inflate(layout2, null);
//            return mViewHolder2;
        }else {
//            mInflate = LayoutInflater.from(mContext).inflate(layout3, null);
//            return mViewHolder3;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if(holder instanceof RVBActivity.MHolder){
//            ((RVBActivity.MHolder) holder).mTextView.setText(((Status)(mList.get(position))).getText());
//            ((RVBActivity.MHolder) holder).mTextView2.setText(((Status)(mList.get(position))).getUserName());
//        }
    }

    @Override
    public int getItemCount() {
        if(typeCount==1){
            return mList.size();
        }else if(typeCount==2){
            if(isViewList){
                return mList.size()+1;
            }else {
                return typeCount;
            }
        }else if(typeCount==3){
            if(isViewList){
                return mList.size()+2;
            }else {
                return typeCount;
            }
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(typeCount ==1){
            return position;
        }else if(typeCount ==2){
            if(position==1){
                return STATEONE;
            }else {
                if(isViewList){
                    return position;
                }else {
                    return STATETWO;
                }
            }
        }else {
            if(position==2){
                return STATEONE;
            }else if(position==3){
                return STATETWO;
            }else {
                if(isViewList){
                    return position;
                }else {
                    return STATETHREE;
                }
            }
        }
    }
}
