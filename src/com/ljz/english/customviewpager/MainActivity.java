package com.ljz.english.customviewpager;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ljz.englishstudy.R;
import com.ljz.english.customviewpager.custom.TabSwipPager;

/**
 * 
 * @author LJZ
 * 
 */
public class MainActivity extends FragmentActivity {

	private LinearLayout llTabSwipPager;
	private TabSwipPager tabSwipPager;

	private ArrayList<Fragment> fragmentsList;
	private String[] tags;
	private int backTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		initData();

		llTabSwipPager = (LinearLayout) findViewById(R.id.llTabSwipPager);

		tabSwipPager = new TabSwipPager(getApplicationContext(),
				getSupportFragmentManager(), llTabSwipPager);
		if (!tabSwipPager.setFragmentList(fragmentsList, tags)) {
			finish();
		}
	}

	private void initData() {
		fragmentsList = new ArrayList<Fragment>();
		fragmentsList.add(new NewWordFragment());
		fragmentsList.add(new ReciteWordFragment());
		fragmentsList.add(new NoteFragment());

		tags = new String[] { "璇嶅吀", "鑳屽崟璇�", "鍏充簬杞欢" };

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if (System.currentTimeMillis() - backTime > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", 0).show();
				backTime = (int) System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
