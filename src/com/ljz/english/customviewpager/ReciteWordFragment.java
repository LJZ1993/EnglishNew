package com.ljz.english.customviewpager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ljz.englishstudy.R;
import com.ljz.english.activities.NewWordActivity;
import com.ljz.english.activities.WordListActivity;
import com.ljz.english.db.RWDB;
import com.ljz.english.domain.RWSP;

public class ReciteWordFragment extends Fragment implements OnClickListener {

	private LinearLayout ll_save;
	private LinearLayout ll_four;
	private LinearLayout ll_six;
	private LinearLayout ll_college;
	private ProgressDialog pd;
	private static String name = null;
	private boolean isImport;
	private InputStream in;
	private static String English = "English";
	private static String Meaning = "Meaning";

	public ReciteWordFragment() {
		super();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_recite_word, container,
				false);
		ll_save = (LinearLayout) view.findViewById(R.id.rw_ll_save);
		ll_four = (LinearLayout) view.findViewById(R.id.rw_ll_four);
		ll_six = (LinearLayout) view.findViewById(R.id.rw_ll_six);
		ll_college = (LinearLayout) view.findViewById(R.id.rw_ll_college);
		ll_save.setOnClickListener(this);
		ll_four.setOnClickListener(this);
		ll_six.setOnClickListener(this);
		ll_college.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.rw_ll_save:
			intent = new Intent(getActivity(), NewWordActivity.class);
			startActivity(intent);
			break;
		case R.id.rw_ll_four:
			// 首先去判断是否已经导入了单词
			name = "four";
			isImport = RWSP.setSP(getActivity(), name);
			if (!isImport) {
				// 没有导入
				// 则执行导入操作
				isImport = true;
				// 展示加载控件
				showProgressDialog();
				// 从数据库中导入四级数据 name=four
				try {
					importData(name);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 对是否保存了四级数据进行记录
				RWSP.getSP(getActivity(), name, isImport);
			}
			intent = new Intent(getActivity(), WordListActivity.class);
			intent.putExtra("word_level", name);
			startActivity(intent);
			// Log.w("导入", "是否");
			break;
		case R.id.rw_ll_six:
			// 首先去判断是否已经导入了单词
			name = "six";
			isImport = RWSP.setSP(getActivity(), name);
			if (!isImport) {
				// 没有导入
				// 则执行导入操作
				isImport = true;
				// 展示加载控件
				showProgressDialog();
				// 从数据库中导入四级数据 name=four
				try {
					importData(name);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 对是否保存了四级数据进行记录
				RWSP.getSP(getActivity(), name, isImport);
			}
			intent = new Intent(getActivity(), WordListActivity.class);
			intent.putExtra("word_level", name);
			startActivity(intent);
			break;
		case R.id.rw_ll_college:
			// 首先去判断是否已经导入了单词
			name = "college";
			isImport = RWSP.setSP(getActivity(), name);
			if (!isImport) {
				// 没有导入
				// 则执行导入操作
				isImport = true;
				// 展示加载控件
				showProgressDialog();
				// 从数据库中导入四级数据 name=four
				try {
					importData(name);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 对是否保存了四级数据进行记录
				RWSP.getSP(getActivity(), name, isImport);
			}
			intent = new Intent(getActivity(), WordListActivity.class);
			intent.putExtra("word_level", name);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	private void importData(String name) throws IOException {
		if (name.equals("four")) {
			in = getResources().openRawResource(R.raw.college);
		} else if (name.equals("six")) {
			in = getResources().openRawResource(R.raw.fourthlevel);
		} else {
			in = getResources().openRawResource(R.raw.sixthlevel);
		}
		InputStreamReader reader = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(reader);
		String line;
		while ((line = br.readLine()) != null) {
			String[] temp = line.split(" ");
			ContentValues values = new ContentValues();
			values.put(English, temp[0]);
			values.put(Meaning, temp[1]);
			RWDB db = new RWDB(getActivity(), name, values);
		}
		// 取消加载控件
		pd.cancel();
		reader.close();
		br.close();
	}

	private void showProgressDialog() {
		pd = ProgressDialog.show(getActivity(), "正在导入数据", "");
	}
}