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
 * @author LJZ ����json��ʽ����
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
		// ��hashmap�洢���ݽ��з��ص���
		map = new HashMap<String, String>();
		try {
			JSONArray jsonArray = new JSONArray("[" + response.toString() + "]");
			// Log.w(TAG, jsonArray.toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);

				if (object != null) {
					// ��ȡ�������
					// Log.w(TAG, object.toString());
					String errorCode = object.getString("errorCode");
					if (errorCode.equals("20")) {
						Toast.makeText(context, "������ı�����", Toast.LENGTH_SHORT)
								.show();
					} else if (errorCode.equals("30 ")) {
						Toast.makeText(context, "�޷�������Ч�ķ���", Toast.LENGTH_SHORT)
								.show();
					} else if (errorCode.equals("40")) {
						Toast.makeText(context, "��֧�ֵ���������", Toast.LENGTH_SHORT)
								.show();
					} else if (errorCode.equals("50")) {
						Toast.makeText(context, "��Ч��key", Toast.LENGTH_SHORT)
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
							// ������Ϊ���ַ���ʱ
						} else {
							// ����ѯ������ȷ�ĵ���ʱ
							webwebObject = object.getString("web");
							// web Ҳ��һ��json���ݣ�������н���
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
