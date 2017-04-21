package com.yukun.textapplication.photo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.yukun.textapplication.R;
import java.util.ArrayList;

public class ListDetailActivity extends Activity {

    private ArrayList<ImageDetail> lists=new ArrayList<>();
    private GridView gridView;
    private GlideViewAdapter glideViewAdapter;
    ArrayList<Integer> mCheckNumber=new ArrayList<>();

    private void init() {
        gridView = (GridView) findViewById(R.id.grideview);
        if(lists.size()==0){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        ArrayList<String> photo = getIntent().getStringArrayListExtra("photo");
        for (int i = 0; i < photo.size(); i++) {
            ImageDetail imageDetail=new ImageDetail();
            imageDetail.setImagePath(photo.get(i));
            imageDetail.setChecked(false);
            lists.add(imageDetail);
        }
        init();
        setAdapter();
        setListener();
    }

    private void setAdapter() {
        glideViewAdapter = new GlideViewAdapter(getApplicationContext(),lists);
        gridView.setAdapter(glideViewAdapter);
        //點擊的回調
        glideViewAdapter.setCheckCheckbox(new GlideViewAdapter.CheckCheckbox() {
            @Override
            public void checkCheckboxCallBacks(int position, boolean isChecked) {
                if(mCheckNumber.size()>=5){
                    glideViewAdapter.notifyDataSetChanged();
                    if(!isChecked){
                        for (int i = 0; i < mCheckNumber.size(); i++) {
                            if(mCheckNumber.get(i)==position){
                                mCheckNumber.remove(i);
                                lists.get(position).setChecked(false);
                            }
                        }
                    }
                    return;
                }
                if(isChecked){
                    mCheckNumber.add(position);
                    lists.get(position).setChecked(true);
                }else {
                    for (int i = 0; i < mCheckNumber.size(); i++) {
                        if(mCheckNumber.get(i)==position){
                            mCheckNumber.remove(i);
                            lists.get(position).setChecked(false);
                        }
                    }
                }
                glideViewAdapter.notifyDataSetChanged();
                Toast.makeText(ListDetailActivity.this, mCheckNumber.size()+"", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(lists.size()!=0){
                    final String detail = lists.get(position).getImagePath();
                    int nameCount = detail.lastIndexOf("/");
                    final String name=detail.substring(nameCount+1,detail.length());
                }
            }
        });

//        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                setAlert(position);
//                return true;
//            }
//        });
    }

//    private void setAlert(final int position) {
//        new AlertDialog.Builder(this).setMessage("确认删除吗?")
//                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        boolean b = FileUtil.deleteFile(lists.get(position));
//                        lists.remove(position);
//                        glideViewAdapter.notifyDataSetChanged();
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//                .create()
//                .show();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void ListBack(View view) {
        finish();
//        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }
}
