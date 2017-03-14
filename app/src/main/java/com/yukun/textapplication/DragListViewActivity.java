package com.yukun.textapplication;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yukun.textapplication.adapter.ListViewAdapter;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class DragListViewActivity extends AppCompatActivity {


    private ArrayList<Integer> arrayList;
    private SlideAndDragListView listView;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_list_view);
        ButterKnife.bind(this);

        initMenu();
        getInfo();
        setAdapter();
        setListener();


    }

    private void setAdapter() {
        adapter = new ListViewAdapter(getApplicationContext(),arrayList);
        listView.setAdapter(adapter);
    }

    private void getInfo() {
        arrayList = new ArrayList();
        arrayList.add(R.mipmap.circle1);
        arrayList.add(R.mipmap.circle2);
        arrayList.add(R.mipmap.circle3);
        arrayList.add(R.mipmap.circle4);
        arrayList.add(R.mipmap.circle5);
        arrayList.add(R.mipmap.menu);
        arrayList.add(R.mipmap.circle1);
        arrayList.add(R.mipmap.circle2);
        arrayList.add(R.mipmap.circle3);
        arrayList.add(R.mipmap.circle4);
        arrayList.add(R.mipmap.circle5);
    }


    private void initMenu() {
        listView = (SlideAndDragListView) findViewById(R.id.draglistView);
        Menu menu = new Menu(true, true, 0);
        menu.addItem(new MenuItem.Builder().setWidth(90)//单个菜单button的宽度
                .setBackground(new ColorDrawable(Color.RED))//设置菜单的背景
                .setText("One")//set text string
                .setTextColor(Color.GRAY)//set text color
                .setTextSize(20)//set text size
                .build());
        menu.addItem(new MenuItem.Builder().setWidth(120)
                .setBackground(new ColorDrawable(Color.GREEN))
                .setDirection(MenuItem.DIRECTION_RIGHT)//设置方向 (默认方向为DIRECTION_LEFT)
                .setIcon(getResources().getDrawable(R.mipmap.ic_launcher))// set icon
                .build());
        menu.addItem(new MenuItem.Builder().setWidth(120)
                .setBackground(new ColorDrawable(Color.BLUE))
                .setDirection(MenuItem.DIRECTION_RIGHT)//设置方向 (默认方向为DIRECTION_LEFT)
                .setIcon(getResources().getDrawable(R.mipmap.menu))// set icon
                .build());
        //set in sdlv
        listView.setMenu(menu);
    }

    private void setListener() {

        listView.setOnSlideListener(new SlideAndDragListView.OnSlideListener() {
            @Override
            public void onSlideOpen(View view, View parentView, int position, int direction) {

            }

            @Override
            public void onSlideClose(View view, View parentView, int position, int direction) {

            }
        });


        listView.setOnMenuItemClickListener(new SlideAndDragListView.OnMenuItemClickListener() {
            @Override
            public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
                int menu=Menu.ITEM_NOTHING;
                switch (direction) {
                    case MenuItem.DIRECTION_LEFT:
                        switch (buttonPosition) {
                            case 0://One
                                Toast.makeText(DragListViewActivity.this, "one", Toast.LENGTH_SHORT).show();
                                menu=Menu.ITEM_SCROLL_BACK;
                        }
                        break;
                    case MenuItem.DIRECTION_RIGHT:
                        switch (buttonPosition) {
                            case 0://icon
                                menu=Menu.ITEM_SCROLL_BACK;
                                arrayList.remove(itemPosition);
                                adapter.notifyDataSetChanged();
//                                menu=Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
                        }
                        break;
                    default:
                        menu=Menu.ITEM_NOTHING;
                }
                return menu;
            }
        });
        //
        listView.setOnDragListener(new SlideAndDragListView.OnDragListener() {
            @Override
            public void onDragViewStart(int position) {

            }

            @Override
            public void onDragViewMoving(int position) {

            }

            @Override
            public void onDragViewDown(int position) {

            }
        }, arrayList);

        //Item 长单击
        listView.setOnListItemLongClickListener(new SlideAndDragListView.OnListItemLongClickListener() {
            @Override
            public void onListItemLongClick(View view, int position) {
                Toast.makeText(DragListViewActivity.this, position+"长点击", Toast.LENGTH_SHORT).show();

            }
        });

        //item点击
        listView.setOnListItemClickListener(new SlideAndDragListView.OnListItemClickListener() {
            @Override
            public void onListItemClick(View v, int position) {

                Toast.makeText(DragListViewActivity.this, position+"", Toast.LENGTH_SHORT).show();

            }
        });
    }

}