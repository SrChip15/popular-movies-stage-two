package com.example.android.flixt.data;


import android.net.Uri;
import android.provider.BaseColumns;

public final class MovieContract {

	public static final String CONTENT_AUTHORITY = "com.example.android.flixt";
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	public static final String PATH_MOVIES = "movies";

	private MovieContract() {
	}

	/* Inner class that defines the table contents */
	public static class MovieEntry implements BaseColumns {
		public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);
		public static final String TABLE_NAME = "movies";
		public static final String ID = "movie_id";
		public static final String TITLE = "title";
		public static final String POSTER_PATH = "poster_path";
		public static final String BACKDROP_PATH = "backdrop_path";
		public static final String OVERVIEW = "overview";
		public static final String RELEASE_DATE = "release_date";
		public static final String VOTE_AVERAGE = "vote_average";
		public static final String VOTE_COUNT = "vote_count";
		public static final String POPULARITY = "popularity";
		public static final String ORIGINAL_TITLE = "original_title";
	}
}
