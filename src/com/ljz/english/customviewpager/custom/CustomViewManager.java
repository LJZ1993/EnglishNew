package com.ljz.english.customviewpager.custom;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ljz.englishstudy.R;

public class CustomViewManager {
	private Context context;
	private LinearLayout llCustomTabView, llTab;
	private ViewPager viewPager;
	private String[] tags;

	public CustomViewManager(Context context, String[] tags) {
		this.context = context;
		this.tags = tags;
		// ��ȡȫ������
		llCustomTabView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.custom_tab_view, null);
		// �õ����TAB�Ĳ���
		llTab = (LinearLayout) llCustomTabView.findViewById(R.id.llTab);
		viewPager = (ViewPager) llCustomTabView.findViewById(R.id.pager);

		createCustomView();
	}

	public LinearLayout getCustomTabView() {
		return llCustomTabView;
	}

	public LinearLayout getTabView() {
		return llTab;
	}

	public ViewPager getViewPager() {
		return viewPager;
	}

	private void createCustomView() {
		// ���TABS
		for (int i = 0; i < tags.length; i++) {
			RelativeLayout tab = new RelativeLayout(context);
			tab.setId(i);
			if (i == 0) {
				tab.setBackgroundResource(R.drawable.bg_view_pager_scroll_selected);
			} else {
				tab.setBackgroundResource(R.drawable.bg_view_pager_scroll_unselect);
			}

			TextView tv = new TextView(context);
			tv.setId(i);
			tv.setTextColor(Color.BLACK);
			tv.setText(tags[i]);

			RelativeLayout.LayoutParams tvlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			tvlp.addRule(RelativeLayout.CENTER_IN_PARENT);
			tab.addView(tv, tvlp);

			LinearLayout.LayoutParams tablp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
			tablp.weight = 1;
			llTab.addView(tab, tablp);

		}
	}
}