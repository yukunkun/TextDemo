package com.yukun.textapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yukun.textapplication.R;

import java.util.ArrayList;

/**
 * Created by yukun on 17-2-24.
 */
public class ListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> arrayList;
    public ListViewAdapter(Context applicationContext, ArrayList<Integer> arrayList) {
        this.context=applicationContext;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.listitem,null);
        ImageView imageView= (ImageView) convertView.findViewById(R.id.image);
        imageView.setImageResource(arrayList.get(position));
        return convertView;
    }
}
