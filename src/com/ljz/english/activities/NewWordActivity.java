package com.ljz.english.activities;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ljz.englishstudy.R;
import com.ljz.english.db.AddWordDB;
import com.ljz.english.db.NewWord;

public class NewWordActivity extends Activity implements
		android.view.View.OnClickListener {
	private Context context;
	private ImageView iv_search;
	private EditText et_word;
	private ImageView iv_back;
	private TextView tv_center;
	private ImageView iv_delect;
	private TextView tv_back;
	private ListView lv;
	private Map<String, String> map;
	private ArrayList<NewWord> allWord;
	private static final String TAG = "NewWordActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_newword);
		resetTitle();
		init();
	}

	private void init() {
		AddWordDB db = new AddWordDB(context);
		allWord = db.getAllWord("");
		lv = (ListView) findViewById(R.id.newword_lv);
		MyAdapter adapter = new MyAdapter();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(NewWordActivity.this,
						SearchWordActivity.class);
				String word = allWord.get(position).getWord();
				intent.putExtra("word", word);
				startActivity(intent);
				// Log.w(TAG, word);
			}
		});
	}

	private void resetTitle() {
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.common_titlebar);
		iv_search = (ImageView) findViewById(R.id.titlebar_iv_search);
		iv_delect = (ImageView) findViewById(R.id.titlebar_iv_delect);
		tv_center = (TextView) findViewById(R.id.titlebar_tv_center);
		et_word = (EditText) findViewById(R.id.titlebar_et_word);
		iv_back = (ImageView) findViewById(R.id.titlebar_iv_back);
		tv_back = (TextView) findViewById(R.id.titlebar_tv_back);
		iv_back.setOnClickListener(this);
		iv_search.setVisibility(View.GONE);
		et_word.setVisibility(View.GONE);
		iv_back.setVisibility(View.VISIBLE);
		tv_back.setVisibility(View.VISIBLE);
		tv_center.setVisibility(View.GONE);
		iv_delect.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlebar_iv_back:
			finish();
			break;

		default:
			break;
		}

	}

	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return allWord.size();
		}

		@Override
		public Object getItem(int position) {
			return allWord.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			View view;
			if (convertView == null) {
				holder = new ViewHolder();
				view = LayoutInflater.from(context).inflate(
						R.layout.newword_adapter_item, null);
				holder.tv_word = (TextView) view
						.findViewById(R.id.newword_tv_word);
				holder.tv_time = (TextView) view
						.findViewById(R.id.newword_tv_time);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			holder.tv_word.setText(allWord.get(position).getWord());
			holder.tv_time.setText(allWord.get(position).getMonth() + "月"
					+ allWord.get(position).getHour() + "日");
			// Log.w(TAG, holder.tv_word.getText().toString());
			return view;
		}

		class ViewHolder {
			TextView tv_word;
			TextView tv_time;
		}

	}
}
