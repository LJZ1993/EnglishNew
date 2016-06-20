package com.ljz.english.activities;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ljz.englishstudy.R;
import com.ljz.english.db.AddWordDB;
import com.ljz.english.db.NewWord;
import com.ljz.english.utils.ParseJSON;
import com.ljz.english.utils.SendHttpRequest;

@SuppressLint({ "ShowToast", "ResourceAsColor" })
public class SearchWordActivity extends Activity implements OnClickListener {
	private Context context;
	private ImageView iv_search;
	private EditText et_word;
	private ImageView iv_back;
	private TextView tv_center;
	private ImageView iv_delect;
	private static final String TAG = "SearchActivity";
	private String query;
	private String explains;
	private String us_phonetic;
	private Button bt_add;
	private TextView tv_explains;
	private TextView tv_phonetic;
	private TextView tv_query;
	private TextView tv_web1;
	private String webwebObject;
	private View view1;
	private View view2;
	private String value;
	private String key0;
	private String value0;
	private String key1;
	private String value1;
	private String key2;
	private String value2;
	private TextView tv_web2;
	private TextView tv_web3;
	private TextView tv_back;
	private Boolean isInNewWord;
	private String getWord;
	private Boolean isNewWord = false;
	private String intent_word;
	private ProgressDialog pd;
	private AddWordDB db;
	private TextView tv_hint;

	// http://api.fanyi.baidu.com/api/trans/vip/translate?q=apple&from=en&to=zh&appid=20160527000022230&salt=1435660288&sign=7836884437239fa36d7b5cc62a4015ef
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		isInNewWord = false;
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_search);
		intent_word = getIntent().getStringExtra("word");
		if (intent_word != null && intent_word != "") {
			isNewWord = true;
		}
		init();
		resertTitle();

	}

	private void init() {
		tv_query = (TextView) findViewById(R.id.search_tv_query);
		tv_phonetic = (TextView) findViewById(R.id.search_tv_phonetic);
		tv_explains = (TextView) findViewById(R.id.search_tv_explains);
		tv_hint = (TextView) findViewById(R.id.search_tv_hint);
		bt_add = (Button) findViewById(R.id.search_bt_add);
		bt_add.setOnClickListener(this);
		tv_web1 = (TextView) findViewById(R.id.search_tv_web1);
		tv_web2 = (TextView) findViewById(R.id.search_tv_web2);
		tv_web3 = (TextView) findViewById(R.id.search_tv_web3);
		view1 = findViewById(R.id.search_view1);
		view2 = findViewById(R.id.search_view2);
	}

	private void resertTitle() {
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.common_titlebar);
		iv_search = (ImageView) findViewById(R.id.titlebar_iv_search);
		iv_delect = (ImageView) findViewById(R.id.titlebar_iv_delect);
		iv_delect.setOnClickListener(this);
		tv_center = (TextView) findViewById(R.id.titlebar_tv_center);
		iv_search.setOnClickListener(this);
		et_word = (EditText) findViewById(R.id.titlebar_et_word);
		iv_back = (ImageView) findViewById(R.id.titlebar_iv_back);
		iv_back.setOnClickListener(this);
		tv_back = (TextView) findViewById(R.id.titlebar_tv_back);
		if (isNewWord) {
			getWord = intent_word.toString();
			showWordMean();
		} else {
			iv_search.setVisibility(View.GONE);
			et_word.setVisibility(View.VISIBLE);
			iv_back.setVisibility(View.VISIBLE);
			tv_center.setVisibility(View.GONE);
			iv_delect.setVisibility(View.VISIBLE);
			et_word.performClick();
			et_word.setFocusable(true);
			et_word.setFocusableInTouchMode(true);
			et_word.requestFocus();
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				public void run() {
					InputMethodManager inputManager = (InputMethodManager) et_word
							.getContext().getSystemService(
									Context.INPUT_METHOD_SERVICE);
					inputManager.showSoftInput(et_word, 0);
				}

			}, 500);
			et_word.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {
						showWordMean();
					}
					return false;
				}
			});
		}
	}

	protected void showWordMean() {
		if (isNetWorkAvailable(context)) {
			if (et_word.equals("")) {
				Toast.makeText(context, R.string.toast_null, 0).show();
			} else {
				// showProgress();
				new WordAsyncTask().execute();
			}
		} else {
			Toast.makeText(context, R.string.toast_net, 0).show();

		}
	}

	@SuppressLint("ShowToast")
	private Boolean isNetWorkAvailable(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	class WordAsyncTask extends AsyncTask<String, Void, Void> {
		private Map map;

		@SuppressLint("ResourceAsColor")
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			iv_search.setVisibility(View.GONE);
			et_word.setVisibility(View.GONE);
			iv_back.setVisibility(View.VISIBLE);
			tv_back.setVisibility(View.VISIBLE);
			tv_center.setVisibility(View.GONE);
			iv_delect.setVisibility(View.GONE);
			view1.setVisibility(View.VISIBLE);
			view2.setVisibility(View.VISIBLE);
			bt_add.setVisibility(View.VISIBLE);
			if (isNewWord) {
				bt_add.setText("添加");
				bt_add.setBackgroundColor(R.color.SearchActivity_add_word);
			}
			us_phonetic = (String) map.get("us_phonetic");
			if (!TextUtils.isEmpty(us_phonetic)) {
				if (isInNewWOrd()) {
				}
				tv_hint.setText("网络释义：");
				tv_query.setText((CharSequence) map.get("query"));
				tv_phonetic.setText(":  " + "/" + map.get("us_phonetic") + "/");
				tv_explains.setText("单词解释：" + map.get("explains"));
				tv_web1.setText("\n\t<" + "1" + ">" + map.get("key0")
						+ "\n\t   " + map.get("value0"));
				tv_web2.setText("\n\t<" + "2" + ">" + map.get("key1")
						+ "\n\t   " + map.get("value1"));
				tv_web3.setText("\n\t<" + "3" + ">" + map.get("key2")
						+ "\n\t   " + map.get("value2"));
			} else {
				Toast.makeText(getApplicationContext(), "没有查询到相应的单词", 0).show();
				view1.setVisibility(View.GONE);
				view2.setVisibility(View.GONE);
				bt_add.setVisibility(View.GONE);
				iv_search.setVisibility(View.GONE);
				et_word.setVisibility(View.VISIBLE);
				tv_back.setVisibility(View.VISIBLE);
				tv_center.setVisibility(View.GONE);
				iv_delect.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			if (isNewWord) {
				getWord = intent_word.toString();
			} else {
				getWord = et_word.getText().toString().trim();
			}

			StringBuffer response = SendHttpRequest
					.sendRequestWithURLHttpConnection(context, getWord);
			map = ParseJSON.parseJSONWithJSONOBject(context, response);
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlebar_iv_delect:
			et_word.setText("");
			et_word.setHint("������Ҫ��ѯ�ĵ���");
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				public void run() {
					InputMethodManager inputManager = (InputMethodManager) et_word
							.getContext().getSystemService(
									Context.INPUT_METHOD_SERVICE);
					inputManager.showSoftInput(et_word, 0);
				}

			}, 500);
			break;
		case R.id.titlebar_iv_back:
			finish();
			break;
		case R.id.search_bt_add:
			String add_word = tv_query.getText().toString();
			if (isInNewWord) {
				delectNewWOrd(add_word);
				// 不是新单词，已经存在了
				Toast.makeText(getApplicationContext(), "已经添加了，不用重复添加", 0)
						.show();
			} else {
				bt_add.setBackgroundColor(R.color.SearchActivity_add_word);

				String add_explains = tv_explains.getText().toString();
				NewWord newWord = new NewWord();
				newWord.setWord(add_word);

				Time time = new Time();
				time.setToNow();
				int month = time.month;
				int hour = time.hour;
				newWord.setMonth(month);
				newWord.setHour(hour);
				db = new AddWordDB(context);
				db.saveAddWordDB(newWord);
			}
			break;
		default:
			break;
		}
	}

	private void delectNewWOrd(String add_word) {
		// db.delectAddWordDB(context, add_word);

	}

	private boolean isInNewWOrd() {
		String tv_query_word = tv_query.getText().toString();
		AddWordDB db = new AddWordDB(context);
		ArrayList<NewWord> list = db.getAllWord("");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getWord().equals(tv_query_word)) {
				bt_add.setBackgroundColor(R.color.SearchActivity_add_word);
				isInNewWord = true;
			}
		}
		return false;
	}

	public void showProgress() {
		pd = ProgressDialog.show(context, "", "正在加载", true, true);

	}
}
