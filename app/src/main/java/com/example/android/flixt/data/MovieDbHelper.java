package com.example.android.flixt.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.flixt.data.MovieContract.MovieEntry;

public class MovieDbHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "app_database.db";

	private static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
					MovieEntry.ID + " INTEGER PRIMARY KEY, " +
					MovieEntry.TITLE + " TEXT NOT NULL, " +
					MovieEntry.POSTER_PATH + " TEXT NOT NULL, " +
					MovieEntry.BACKDROP_PATH + " TEXT NOT NULL, " +
					MovieEntry.OVERVIEW + " TEXT NOT NULL, " +
					MovieEntry.RELEASE_DATE + " TEXT, " +
					MovieEntry.VOTE_AVERAGE + " REAL, " +
					MovieEntry.VOTE_COUNT + " INTEGER, " +
					MovieEntry.POPULARITY + " REAL, " +
					MovieEntry.ORIGINAL_TITLE + " TEXT);";

	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;

	public MovieDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Currently, there is only one version of the db
		// So leave empty
	}
}
