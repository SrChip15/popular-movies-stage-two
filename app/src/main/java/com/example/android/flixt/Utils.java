package com.example.android.flixt;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Utils {

	private static final String TAG = Utils.class.getSimpleName();

	public static String getFormattedDate(String releaseDate) {
		@SuppressLint("SimpleDateFormat")
		SimpleDateFormat srcFormat = new SimpleDateFormat("yyyy-MM-dd");
		@SuppressWarnings("UnusedAssignment")
		Date srcDate = null;

		try {
			srcDate = srcFormat.parse(releaseDate);
		} catch (ParseException pe) {
			Log.e(TAG, "Text date parse exception occurred for " + releaseDate);
			pe.printStackTrace();
			return null;
		}

		@SuppressLint("SimpleDateFormat")
		SimpleDateFormat formatted = new SimpleDateFormat("MMM d, yyyy");
		String formattedDate = "";
		if (srcDate != null) {
			formattedDate = formatted.format(srcDate);
		}
		return formattedDate;
	}
}
