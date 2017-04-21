package com.yukun.textapplication.litepaldatabase;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yukun.textapplication.R;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Litepal,一个很6的第三方数据库.
 *
 */
public class LitepalActivity extends AppCompatActivity {

    private String mString;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal);
        mList = new ArrayList<>();

//        SQLiteDatabase database = Connector.getDatabase();
//
//        News news = new News();
//        news.setTitle("这是一条新闻标题");
//        news.setContent("这是一条新闻内容");
//        news.setPublishDate(new Date());
//        boolean save = news.save();
//        Log.i("--save",save+"");
//
//        News news2 = new News();
//        news2.setTitle("今日iPhone6发布标题");
//        news2.setContent("今日iPhone6发布内容");
//        news2.setPublishDate(new Date());
//        boolean save2 = news.save();
//        Log.i("--save2",save2+"");
//
//        ContentValues values = new ContentValues();
//        values.put("title", "今日iPhone8发布");
//        DataSupport.update(News.class, values, 3);
//
//
//        News news3 = new News();
//        news3.setTitle("今日发布标题");
//        news3.setContent("今日发布内容");
//        news3.setPublishDate(new Date());
//        boolean save3 = news.save();
//        Log.i("--save3",save3+"");
//
//        int deleteCount = DataSupport.delete(News.class, 3);
////        Log.d("TAG", "delete count is " + deleteCount);
////        News news1 = DataSupport.find(News.class, 1);
////        Log.i("--news",news1.toString());
//
//
//        List<News> allNews = DataSupport.findAll(News.class);
//        Log.i("--newsall",allNews.toString());

    }

    public void videoPress(View view) {
//        Intent intent = new Intent();
//		/* 开启Pictures画面Type设定为image */
//        //intent.setType("image/*");
//        // intent.setType("audio/*"); //选择音频
//        intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
//        // intent.setType("video/*;image/*");//同时选择视频和图片
//		/* 使用Intent.ACTION_GET_CONTENT这个Action */
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//		/* 取得相片后返回本画面 */
//        startActivityForResult(intent, 1);
        String VIDEO_UNSPECIFIED = "video/.mp4";
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(VIDEO_UNSPECIFIED); //String VIDEO_UNSPECIFIED = "video/*";
        Intent wrapperIntent = Intent.createChooser(intent, null);
//       startActivityForResult(wrapperIntent, 1);

        ContentResolver contentResolver = this.getContentResolver();
        String[] projection = new String[]{MediaStore.Video.Media.DATA};
        Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int fileNum = cursor.getCount();

        for(int counter = 0; counter < fileNum; counter++){
            String string = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            if(string.endsWith(".mp4")){
                mList.add(string);
            }
            cursor.moveToNext();
        }
        cursor.close();

        Log.i("--", "file is: " +mList.toString());
        Log.i("--", "choosefile: " + mList.get(3));
        CompressUtil.getInstance().shrinkVideo(getApplicationContext(),mList.get(3));
    }
}
