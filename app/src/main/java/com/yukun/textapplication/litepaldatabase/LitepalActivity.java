package com.yukun.textapplication.litepaldatabase;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yukun.textapplication.R;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.Date;
import java.util.List;
/**
 * Litepal,一个很6的第三方数据库.
 *
 */
public class LitepalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal);
        SQLiteDatabase database = Connector.getDatabase();

        News news = new News();
        news.setTitle("这是一条新闻标题");
        news.setContent("这是一条新闻内容");
        news.setPublishDate(new Date());
        boolean save = news.save();
        Log.i("--save",save+"");

        News news2 = new News();
        news2.setTitle("今日iPhone6发布标题");
        news2.setContent("今日iPhone6发布内容");
        news2.setPublishDate(new Date());
        boolean save2 = news.save();
        Log.i("--save2",save2+"");

        ContentValues values = new ContentValues();
        values.put("title", "今日iPhone8发布");
        DataSupport.update(News.class, values, 3);


        News news3 = new News();
        news3.setTitle("今日发布标题");
        news3.setContent("今日发布内容");
        news3.setPublishDate(new Date());
        boolean save3 = news.save();
        Log.i("--save3",save3+"");

        int deleteCount = DataSupport.delete(News.class, 3);
//        Log.d("TAG", "delete count is " + deleteCount);
//        News news1 = DataSupport.find(News.class, 1);
//        Log.i("--news",news1.toString());


        List<News> allNews = DataSupport.findAll(News.class);
        Log.i("--newsall",allNews.toString());



    }
}
