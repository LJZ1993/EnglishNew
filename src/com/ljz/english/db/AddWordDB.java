package com.ljz.english.db;

import java.net.ContentHandler;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AddWordDB {
	private SQLiteDatabase db;
	public static final String ADDWORDB = "NewWord";

	public AddWordDB(Context context) {
		AddWordSQLiteHelper helper = new AddWordSQLiteHelper(context, ADDWORDB,
				null, 1);
		db = helper.getWritableDatabase();

	}

	/**
	 * ���뵥��
	 * 
	 * @param newWord
	 */
	public void saveAddWordDB(NewWord newWord) {
		// ����ӵ�������ӵ����ݿ���
		ContentValues values = new ContentValues();
		values.put(NewWord.WORD, newWord.getWord().toString());
		values.put(NewWord.MONTH, newWord.getMonth());
		values.put(NewWord.HOUR, newWord.getHour());
		db.insert(ADDWORDB, null, values);
	}

	/**
	 * @param context
	 * @param delect_word
	 *            ���ݿ������ɾ��
	 */
	public void delectAddWordDB(Context context, String[] delect_word) {
		db.delete(ADDWORDB, "word=?", delect_word);
	}

	/**
	 * ��ȡ���ݿ������еĴ洢�ĵ���
	 * 
	 * @param selection
	 * @return
	 */

	public ArrayList<NewWord> getAllWord(String selection) {
		ArrayList<NewWord> list = new ArrayList<NewWord>();
		Cursor cursor = db.query(ADDWORDB, null, selection, null, null, null,
				null);
		if (cursor.moveToFirst()) {
			while (cursor.moveToNext()) {
				NewWord newWord = new NewWord();
				String word = cursor.getString(cursor.getColumnIndex("word"));
				int month = cursor.getInt(cursor.getColumnIndex("month"));
				int hour = cursor.getInt(cursor.getColumnIndex("hour"));
				newWord.setWord(word);
				newWord.setMonth(month);
				newWord.setHour(hour);
				list.add(newWord);
			}
			cursor.close();
		}
		return list;
	}
}
