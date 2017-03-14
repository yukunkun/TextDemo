package com.yukun.textapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yukun.textapplication.R;

import java.util.List;

/**
 * Created by yukun on 17-3-13.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Integer> integers;

    public RecyclerViewAdapter(Context context, List<Integer> integers) {
        this.context = context;
        this.integers = integers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.card_item,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Glide.with(context).load(integers.get(position)).into(((MyHolder)holder).imageView);
        ((MyHolder)holder).imageView.setImageResource(integers.get(position));
    }

    @Override
    public int getItemCount() {
        return integers.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public MyHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.image);

        }
    }
}
