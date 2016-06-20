package com.ljz.english.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.content.Context;

public class SendHttpRequest {
	private static StringBuffer response;

	public static StringBuffer sendRequestWithURLHttpConnection(
			Context context, String word) {
		HttpURLConnection conn = null;
		// ÓÐµÀapi
		URL url;
		try {
			url = new URL(
					"http://fanyi.youdao.com/openapi.do?keyfrom=wei54544545"
							+ "&key=86156187&type=data&doctype=json&version=1.1&q="
							+ URLEncoder.encode(word));
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(8000);
			conn.setConnectTimeout(8000);
			if (200 != conn.getResponseCode()) {
			} else {
				// Log.w(TAG, conn.getResponseCode() + "");
				InputStream in = conn.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));
				response = new StringBuffer();
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();

			}
		}
		return response;
	}

}
