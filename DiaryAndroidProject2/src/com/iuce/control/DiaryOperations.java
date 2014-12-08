package com.iuce.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract.Constants;

import com.iuce.constants.DBConstants;
import com.iuce.contentproviderhelper.DiaryDBProvider;
import com.iuce.entity.Diary;

public class DiaryOperations implements IDiaryOperations {

	// bu sýnýftaki tum veritabaný islemleri mydb ile yapýlacak
	SQLiteDatabase mydb;
	SimpleDateFormat dateFormat;

	public DiaryOperations(Context context) {
		super();
		// db oluþturup mydb ye atýyoruz
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DiaryDBProvider dbProvider = new DiaryDBProvider(context);
		mydb = dbProvider.openDB();
	}

	@Override
	public boolean addDiary(Diary diary) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();
		values.put(DBConstants.DIARY_TITLE, diary.getTitle());
		values.put(DBConstants.DIARY_CONTENT, diary.getContent());

		// contentValues içine date tipinde datayý dogrudan atamadigimiz icin
		// simpleddateformattan yararlanýyoruz

		values.put(DBConstants.DIARY_DATE, diary.getDate());
		values.put(DBConstants.DIARY_LATITUDE, diary.getLatitude());
		values.put(DBConstants.DIARY_LONGITUDE, diary.getLongitude());
		values.put(DBConstants.DIARY_PHOTO_PATH, diary.getPhotoPath());
		values.put(DBConstants.DIARY_AUDIO_PATH, diary.getAudioPath());

		long id = mydb.insert(DBConstants.DIARY_TABLE, null, values);
		if (id > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean removeDiary(int id) {
		// TODO Auto-generated method stub
		long remove = mydb.delete(DBConstants.DIARY_TABLE, DBConstants.DIARY_ID
				+ "=" + id, null);
		if (remove > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateDiary(int id, Diary diary) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put(DBConstants.DIARY_TITLE, diary.getTitle());
		values.put(DBConstants.DIARY_CONTENT, diary.getContent());
		values.put(DBConstants.DIARY_DATE, dateFormat.format(diary.getDate()));
		values.put(DBConstants.DIARY_LATITUDE, diary.getLatitude());
		values.put(DBConstants.DIARY_LONGITUDE, diary.getLongitude());
		values.put(DBConstants.DIARY_PHOTO_PATH, diary.getPhotoPath());
		values.put(DBConstants.DIARY_AUDIO_PATH, diary.getAudioPath());
		long update = mydb.update(DBConstants.DIARY_TABLE, values,
				DBConstants.DIARY_ID + " = " + id, null);
		if (update > 0) {
			return true;
		}

		return false;
	}

	@Override
	public List<Diary> listDiary() {
		// TODO Auto-generated method stub
		List<Diary> diaries = new ArrayList<Diary>();
		Cursor cursor = mydb.query(DBConstants.DIARY_TABLE, new String[] {
				DBConstants.DIARY_ID, DBConstants.DIARY_TITLE,
				DBConstants.DIARY_CONTENT, DBConstants.DIARY_DATE,
				DBConstants.DIARY_LATITUDE, DBConstants.DIARY_LONGITUDE,
				DBConstants.DIARY_PHOTO_PATH, DBConstants.DIARY_AUDIO_PATH },
				null, null, null, null, null);
		while (cursor.moveToNext()) {
			Diary diary = new Diary();
			diary.setId(cursor.getInt(0));
			diary.setTitle(cursor.getString(1));
			diary.setContent(cursor.getString(2));
			diary.setDate(cursor.getString(3));
			diary.setLatitude(cursor.getFloat(4));
			diary.setLongitude(cursor.getFloat(5));
			diary.setPhotoPath(cursor.getString(6));
			diary.setAudioPath(cursor.getString(7));
			diaries.add(diary);
		}
		cursor.close();
		return diaries;
	}

	@Override
	public Diary getDiaryWithDate(String date) {
		// TODO Auto-generated method stub
		
		return null;
	}

}
