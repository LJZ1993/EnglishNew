package com.ljz.english.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author LJZ
 * 
 */
public class RWSP {
	private static final String IS_IMPORT = "import";

	public static void getSP(Context context, String word_level,
			boolean isImport) {
		Editor edit = context.getSharedPreferences(IS_IMPORT,
				Context.MODE_PRIVATE).edit();
		edit.putBoolean(word_level, isImport);
		edit.commit();
	}

	public static boolean setSP(Context context, String word_level) {
		SharedPreferences sp = context.getSharedPreferences(IS_IMPORT,
				Context.MODE_PRIVATE);
		boolean is_import = sp.getBoolean(word_level, false);
		return is_import;

	}
}
