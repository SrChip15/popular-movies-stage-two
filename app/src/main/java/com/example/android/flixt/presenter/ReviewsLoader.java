package com.example.android.flixt.presenter;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.flixt.service.model.Movie;
import com.example.android.flixt.service.model.Review;
import com.example.android.flixt.service.model.ReviewResponse;
import com.example.android.flixt.service.repository.AppDataRepository;
import com.example.android.flixt.service.repository.PrivateApiKey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ReviewsLoader extends AsyncTaskLoader<List<Review>> {

	private List<Review> mData;
	private Movie movie;
	private static final String TAG = ReviewsLoader.class.getSimpleName();

	public ReviewsLoader(Context context, Movie movie) {
		super(context);
		this.movie = movie;
	}

	@Override
	protected void onStartLoading() {
		if (mData != null) {
			deliverResult(mData); // From cache
		} else {
			forceLoad();
		}
	}

	@Override
	public List<Review> loadInBackground() {
		if (isLoadInBackgroundCanceled()) {
			return null;
		} else {
			List<Review> data = new ArrayList<>();
			try {
				Response<ReviewResponse> response = AppDataRepository.getInstance()
						.getTmdbApiService()
						.getReviews(movie.getId(), PrivateApiKey.YOUR_API_KEY)
						.execute();
				if (response.isSuccessful()) {
					ReviewResponse body = response.body();
					if (body != null) {
						data = body.getReviews();
					}
				}
			} catch (IOException ioe) {
				Log.e(TAG, "IOException has occurred!");
			}
			return data;
		}
	}

	@Override
	public void deliverResult(List<Review> data) {
		mData = data; // Cache data
		super.deliverResult(data);
	}
}
