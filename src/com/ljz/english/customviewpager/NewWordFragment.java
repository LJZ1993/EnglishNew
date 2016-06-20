package com.ljz.english.customviewpager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ljz.englishstudy.R;
import com.ljz.english.activities.SearchWordActivity;

@SuppressLint("ServiceCast")
public class NewWordFragment extends Fragment implements OnClickListener {

	private LinearLayout ll_search;
	private String jsonstr;
	private JSONObject json;
	private String engstr;
	private String chistr;
	private String imagurl;
	private String timestr;
	private String tagstr;
	private String fromstr;
	private TextView tv_eng;
	private TextView tv_chi;
	private TextView tv_tag;
	private TextView tv_time;
	private TextView tv_from;

	private static final String TAG = "NewWordFragment";
	private ImageView iv_image;
	private View newword_view1;
	private View newword_view2;
	private boolean available;
	private TextView tv_everyday;

	public NewWordFragment() {
		super();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_newword, container,
				false);
		ll_search = (LinearLayout) view.findViewById(R.id.newword_ll_search);
		ll_search.setOnClickListener(this);
		tv_eng = (TextView) view.findViewById(R.id.newword_tv_eng);
		tv_everyday = (TextView) view.findViewById(R.id.newword_tv_everyday);
		tv_chi = (TextView) view.findViewById(R.id.newword_tv_chi);
		tv_tag = (TextView) view.findViewById(R.id.newword_tv_tag);
		tv_time = (TextView) view.findViewById(R.id.newword_tv_time);
		tv_from = (TextView) view.findViewById(R.id.newword_tv_from);
		iv_image = (ImageView) view.findViewById(R.id.newword_iv_image);
		newword_view1 = view.findViewById(R.id.newword_view1);
		newword_view2 = view.findViewById(R.id.newword_view2);
		// bt_newword = (Button) view.findViewById(R.id.newword_bt_newword);
		// bt_newword.setOnClickListener(this);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		// 判断是否连接了网络
		ConnectivityManager manager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager.getActiveNetworkInfo() != null) {
			available = manager.getActiveNetworkInfo().isAvailable();
		}
		if (!available) {
			// 不可用 这显示网络连接失败
			tv_tag.setVisibility(View.VISIBLE);
			tv_everyday.setVisibility(View.INVISIBLE);
			tv_eng.setVisibility(View.INVISIBLE);
			tv_chi.setVisibility(View.INVISIBLE);
			tv_from.setVisibility(View.INVISIBLE);
			tv_time.setVisibility(View.INVISIBLE);
			newword_view1.setVisibility(View.INVISIBLE);
			newword_view2.setVisibility(View.INVISIBLE);
		} else {
			// 如果可用 则
			new EveryDayAsyncTask().execute();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.newword_ll_search:

			Intent searchWord_intent = new Intent(getActivity(),
					SearchWordActivity.class);
			startActivity(searchWord_intent);
			break;
		// case R.id.newword_bt_newword:
		// Intent newWord_intent = new Intent(getActivity(),
		// NewWordActivity.class);
		// startActivity(newWord_intent);
		// break;
		default:
			break;
		}

	}

	/**
	 * @author LJZ 每日一句要采用异步操作
	 */
	class EveryDayAsyncTask extends AsyncTask<String, String, String> {
		String url = "http://open.iciba.com/dsapi/";
		private Bitmap image;

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			// imageLoader.DisplayImage(imagurl, imageview);
			tv_eng.setText("    " + engstr);
			tv_chi.setText("    " + chistr);
			// tv_tag.setText(tagstr);
			tv_time.setText(timestr);
			tv_from.setText("    " + fromstr);
			iv_image.setImageBitmap(image);
			newword_view1.setVisibility(View.VISIBLE);
			newword_view2.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... params) {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			try {
				HttpResponse response = client.execute(httpPost);
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				jsonstr = sb.toString();
				json = new JSONObject(jsonstr.toString());
				engstr = json.getString("content");
				Log.w(TAG, engstr);
				chistr = json.getString("note");
				imagurl = json.getString("picture");
				image = getUrlImage(imagurl);
				Log.w(TAG, imagurl);
				timestr = json.getString("dateline");
				fromstr = json.getString("translation");
				JSONArray array = json.getJSONArray("tags");
				tagstr = null;
				for (int i = 0; i < array.length(); i++) {
					JSONObject tag = (JSONObject) array.get(i);
					tagstr += tag.getString("name") + ",";
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * @author LJZ 图片处理类
	 * 
	 */
	public Bitmap getUrlImage(String url) {

		Bitmap img = null;

		try {

			URL picurl = new URL(url);

			// 获得连接

			HttpURLConnection conn = (HttpURLConnection) picurl
					.openConnection();

			conn.setConnectTimeout(6000);// 设置超时

			conn.setDoInput(true);

			conn.setUseCaches(false);// 不缓存

			conn.connect();

			InputStream is = conn.getInputStream();// 获得图片的数据流

			img = BitmapFactory.decodeStream(is);

			is.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return img;

	}

}