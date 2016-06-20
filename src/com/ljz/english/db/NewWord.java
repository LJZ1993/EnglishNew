package com.ljz.english.db;

public class NewWord {
	public static final String WORD = "word";
	public static final String MONTH = "month";
	public static final String HOUR = "hour";

	private String word;
	private int month;
	private int hour;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

}
