package com.iuce.constants;

public class DBConstants {

	public static String PACKAGE_NAME = "com.iuce.diaryandroidproject2";
	public static String PROJECT_TAG = "DiaryProject";

	// genel bilgiler
	public static String DATABASE_NAME = "Diary.db";
	public static int DATABASE_VERSION = 1;
	public static String DIARY_TABLE = "diary";
	public static String PHOTO_TABLE = "photoTable";

	public static String DB_PATH = "content://" + PACKAGE_NAME + "/diary";

	// diary tablosu kolon isimleri
	public static String DIARY_ID = "id";
	public static String DIARY_DATE = "diaryDate";
	public static String DIARY_TITLE = "title";
	public static String DIARY_CONTENT = "content";
	public static String DIARY_LONGITUDE = "longitude";
	public static String DIARY_LATITUDE = "latitude";
	public static String DIARY_PHOTO_PATH = "photoPath";
	public static String DIARY_AUDIO_PATH = "audioPath";

	// fotolar tablosu kolon isimleri
	public static String PHOTO_ID = "id";
	public static String PHOTO_NAME = "fotoName";
	public static String PHOTO_DIARY_ID = "diaryID";

	// diary tablo create
	public static String CREATE_DIARY_TABLE = "create table " + DIARY_TABLE
			+ "( " + DIARY_ID + " integer primary key autoincrement, "
			+ DIARY_TITLE + " text, " + DIARY_CONTENT + " text, " + DIARY_DATE
			+ " text, " + DIARY_LATITUDE + " float," + DIARY_LONGITUDE
			+ " float," + DIARY_PHOTO_PATH + " text," + DIARY_AUDIO_PATH
			+ " text);";

	// foto tablo create
	public static String CREATE_PHOTO_TABLE = "create table " + PHOTO_TABLE
			+ "( " + PHOTO_ID + " integer primary key autoincrement, "
			+ PHOTO_NAME + " text, " + PHOTO_DIARY_ID + " integer);";

}
