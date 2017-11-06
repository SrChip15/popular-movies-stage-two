package com.example.android.flixt.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.flixt.R;
import com.example.android.flixt.data.MovieContract.MovieEntry;

@SuppressWarnings("ConstantConditions")
public class MovieProvider extends ContentProvider {

	private MovieDbHelper mDbHelper;
	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private static final int MOVIES = 100;
	private static final int MOVIE_ID = 101;
	private static final String TAG = MovieProvider.class.getSimpleName();

	static {
		sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);
		sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES + "/#", MOVIE_ID);
	}

	@Override
	public boolean onCreate() {
		mDbHelper = new MovieDbHelper(getContext());
		return false;
	}

	@Nullable
	@Override
	public Cursor query(
			@NonNull Uri uri,
			@Nullable String[] projection,
			@Nullable String selection,
			@Nullable String[] selectionArgs,
			@Nullable String sortOrder
	) {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
		int match = sUriMatcher.match(uri);

		switch (match) {
			case MOVIES:
				cursor = db.query(
						MovieEntry.TABLE_NAME,
						projection,
						selection,
						selectionArgs,
						null,
						null,
						sortOrder
				);
				break;
			case MOVIE_ID:
				selection = MovieEntry.ID + " = ? ";
				selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri)) };
				cursor = db.query(
						MovieEntry.TABLE_NAME,
						projection,
						selection,
						selectionArgs,
						null,
						null,
						sortOrder
				);
				break;
			default:
				throw new IllegalArgumentException(getContext().getString(R.string.unsupported_query) + uri);
		}

		return cursor;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		return null;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		final int match = sUriMatcher.match(uri);
		switch (match) {
			case MOVIES:
				return insertMovie(uri, values);
			default:
				throw new IllegalArgumentException(getContext().getString(R.string.unsupported_insertion) + uri);
		}
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		int numRecordsDeleted;

		switch (match) {
			case MOVIES:
				numRecordsDeleted = db.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
				if (numRecordsDeleted != 0) {
					getContext().getContentResolver().notifyChange(uri, null);
				}

				return numRecordsDeleted;
			case MOVIE_ID:
				selection = MovieEntry.ID + " = ? ";
				selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
				numRecordsDeleted = db.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
				if (numRecordsDeleted != 0) {
					getContext().getContentResolver().notifyChange(uri, null);
				}

				return numRecordsDeleted;
			default:
				throw new IllegalArgumentException(getContext().getString(R.string.unsupported_deletion) + uri);
		}
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		final int match = sUriMatcher.match(uri);

		switch (match) {
			case MOVIES:
				return updateMovie(uri, values, selection, selectionArgs);
			case MOVIE_ID:
				selection = MovieEntry.ID + " = ? ";
				selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
				return updateMovie(uri, values, selection, selectionArgs);
			default:
				throw new IllegalArgumentException(getContext().getString(R.string.unsupported_update) + uri);
		}
	}

	private Uri insertMovie(Uri uri, ContentValues values) {

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		long rowID = db.insert(MovieEntry.TABLE_NAME, null, values);
		if (rowID == -1) {
			Log.e(TAG, getContext().getString(R.string.insert_failed) + uri);
			return null;
		}
		return ContentUris.withAppendedId(uri, rowID);
	}

	private int updateMovie(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		if (values.size() == 0) {
			return 0;
		}
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		int numRowsAffected = db.update(MovieEntry.TABLE_NAME, values, selection, selectionArgs);
		if (numRowsAffected != 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return numRowsAffected;
	}
}
