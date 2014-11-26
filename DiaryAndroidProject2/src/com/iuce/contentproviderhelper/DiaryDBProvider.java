package com.iuce.contentproviderhelper;

import com.iuce.constants.DBConstants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DiaryDBProvider {

	DiaryDBHelper dbHelper;

	public DiaryDBProvider(Context context) {
		super();
		dbHelper = new DiaryDBHelper(context, DBConstants.DATABASE_NAME, null,
				DBConstants.DATABASE_VERSION);
	}

	public SQLiteDatabase openDB() {
		return dbHelper.getWritableDatabase();
	}

}
