package com.ljz.english.activities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ljz.english.db.RWDB;
import com.ljz.englishstudy.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ljz.english.activities.RVActivty.HomeAdapter.*;

public class RVActivty extends Activity {
    private RecyclerView mRecyclerView;
    private String name;
    ArrayList<HashMap<String, Object>> listItem;
    private List<String> mDatas;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtity_rv);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_rv);
        //给recyclerView设置可以滑动的方向 默认是上下 可以设置成左右
        //设置成垂直方向 默认
         lm.setOrientation(OrientationHelper.VERTICAL);
        // 获取调用方传递的word_level用于判断是四级or六级orcollege
        name = getIntent().getStringExtra("word_level");
        initData();
        //initData1();
        //rv和lm进行连接起来
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private void initData() {
        Cursor cursor = RWDB.quueryDB(RVActivty.this, name);

        listItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < cursor.getCount(); i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("ItemTitle",
                    cursor.getString(cursor.getColumnIndex("English")));
            map.put("ItemText", cursor.getString(cursor.getColumnIndex("Meaning")));
            listItem.add(map);
            cursor.moveToNext();
        }

    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
        //共3个方法
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    RVActivty.this).inflate(R.layout.list_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
           // Log.w("ItemTitle",listItem.get(position).get("ItemTitle").toString());
           // holder.item_word.setText(mDatas.get(position));
            holder.item_word.setText(listItem.get(position).get("ItemTitle").toString());
            holder.item_explain.setText(listItem.get(position).get("ItemText").toString());
        }

        @Override
        public int getItemCount() {
            return listItem.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView item_word;
            TextView item_explain;


            public MyViewHolder(View view) {
                super(view);
                item_word = (TextView) view.findViewById(R.id.list_item_word);
                item_explain = (TextView) view.findViewById(R.id.list_item_explain);
            }
        }
    }
}