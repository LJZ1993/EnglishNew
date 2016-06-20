package com.ljz.english.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * @author LJZ 解析json格式数据
 */
public class ParseJSON {
	private static String query;
	private static String explains;
	private static String us_phonetic;
	private static String webwebObject;
	private static String value;
	private static String key0;
	private static String value0;
	private static String key1;
	private static String value1;
	private static String key2;
	private static String value2;
	private static Map<String, String> map;

	public static Map parseJSONWithJSONOBject(Context context,
			StringBuffer response) {
		// 用hashmap存储数据进行返回调用
		map = new HashMap<String, String>();
		try {
			JSONArray jsonArray = new JSONArray("[" + response.toString() + "]");
			// Log.w(TAG, jsonArray.toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);

				if (object != null) {
					// 获取错误代号
					// Log.w(TAG, object.toString());
					String errorCode = object.getString("errorCode");
					if (errorCode.equals("20")) {
						Toast.makeText(context, "翻译的文本过长", Toast.LENGTH_SHORT)
								.show();
					} else if (errorCode.equals("30 ")) {
						Toast.makeText(context, "无法进行有效的翻译", Toast.LENGTH_SHORT)
								.show();
					} else if (errorCode.equals("40")) {
						Toast.makeText(context, "不支持的语言类型", Toast.LENGTH_SHORT)
								.show();
					} else if (errorCode.equals("50")) {
						Toast.makeText(context, "无效的key", Toast.LENGTH_SHORT)
								.show();
					} else {
						query = object.getString("query");
						map.put("query", query);
						JSONObject basic = object.getJSONObject("basic");
						explains = basic.getString("explains");
						map.put("explains", explains);
						us_phonetic = basic.optString("us-phonetic");
						map.put("us_phonetic", us_phonetic);
						if (TextUtils.isEmpty(us_phonetic)) {
							// 单返回为空字符串时
						} else {
							// 当查询的是正确的单词时
							webwebObject = object.getString("web");
							// web 也是一个json数据，对其进行解析
							parseWeb(webwebObject);
							// Log.w(TAG, webwebObject);
						}

					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	private static void parseWeb(String webwebObject) {
		JSONArray jsonArray;
		try {

			jsonArray = new JSONArray(webwebObject);
			key0 = jsonArray.getJSONObject(0).getString("key");
			value0 = jsonArray.getJSONObject(0).getString("value");
			key1 = jsonArray.getJSONObject(0).getString("key");
			value1 = jsonArray.getJSONObject(0).getString("value");
			key2 = jsonArray.getJSONObject(2).getString("key");
			value2 = jsonArray.getJSONObject(2).getString("value");
			map.put("key0", key0);
			map.put("value0", value0);
			map.put("key1", key1);
			map.put("value1", value1);
			map.put("key2", key2);
			map.put("value2", value2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
