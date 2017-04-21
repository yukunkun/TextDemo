package com.yukun.textapplication.mvptest;


import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yukun.textapplication.R;

import java.util.List;

/**
 * Created by yukun on 17-4-18.
 */
public class RVAdapter extends BaseQuickAdapter<Status>{
    private Context mContext;
    public RVAdapter(int layoutResId, List<Status> data, Context context) {
        super(layoutResId, data);
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Status status) {
        helper.setText(R.id.text, status.getText())
                .setText(R.id.text2, status.getText());

        helper.setOnClickListener(R.id.text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"text",Toast.LENGTH_LONG).show();
            }
        });
    }

}
