package com.ljz.english.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ljz.englishstudy.R;
import com.ljz.english.db.RWDB;

public class WordListActivity extends Activity {
	private ListView lv;
	private Context context;
	private String name;
	private static final String TAG = "WordListActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		context = this;
		setContentView(R.layout.activity_wordlist);
		lv = (ListView) findViewById(R.id.wordlist_lv);
		// 获取调用方传递的word_level用于判断是四级or六级orcollege
		name = getIntent().getStringExtra("word_level");
		initData();
	}

	private void initData() {
		Cursor cursor = RWDB.quueryDB(context, name);
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < cursor.getCount(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("ItemTitle",
					cursor.getString(cursor.getColumnIndex("English")));
			map.put("ItemText",
					cursor.getString(cursor.getColumnIndex("Meaning")));
			listItem.add(map);
			cursor.moveToNext();
		}
		SimpleAdapter listItemAdapter = new SimpleAdapter(context, listItem,
				R.layout.list_item, new String[] { "ItemTitle", "ItemText" },
				new int[] { R.id.list_item_word, R.id.list_item_explain });
		lv.setAdapter(listItemAdapter);
	}
}
