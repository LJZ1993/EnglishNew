package com.ljz.english.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RWSQLiteHelper extends SQLiteOpenHelper {
	private static String English = "English";
	private static String Meaning = "Meaning";
	private static String Table_ID = "_id";
	private static final String CREATE_TABLE_FOUR = "create table four" + "("
			+ Table_ID + " integer primary key autoincrement, " + English
			+ " char[20] ," + Meaning + " TEXT );";
	private static final String CREATE_TABLE_SIX = "create table six" + "("
			+ Table_ID + " integer primary key autoincrement, " + English
			+ " char[20] ," + Meaning + " TEXT );";
	private static final String CREATE_TABLE_COLLEGE = "create table college"
			+ "(" + Table_ID + " integer primary key autoincrement, " + English
			+ " char[20] ," + Meaning + " TEXT );";

	public RWSQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_FOUR);
		db.execSQL(CREATE_TABLE_SIX);
		db.execSQL(CREATE_TABLE_COLLEGE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
