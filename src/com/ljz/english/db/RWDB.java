package com.ljz.english.db;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleAdapter;

import com.ljz.englishstudy.R;

public class RWDB {

	public RWDB(Context context, String name, ContentValues values) {
		SQLiteDatabase db = new RWSQLiteHelper(context, name, null, 1)
				.getWritableDatabase();
		db.insert(name, null, values);
	}

	public static Cursor quueryDB(Context context, String name) {
		SQLiteDatabase db = new RWSQLiteHelper(context, name, null, 1)
				.getWritableDatabase();
		Cursor cursor = db.query(true, name, new String[] { "_id", "English",
				"Meaning" }, "_id" + ">=" + 1, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}
}
