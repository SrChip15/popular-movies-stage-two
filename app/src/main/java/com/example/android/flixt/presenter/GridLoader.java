package com.example.android.flixt.presenter;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.flixt.service.model.DiscoverResponse;
import com.example.android.flixt.service.model.Movie;
import com.example.android.flixt.service.repository.AppDataRepository;
import com.example.android.flixt.service.repository.PrivateApiKey;
import com.example.android.flixt.service.repository.TmdbApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class GridLoader extends AsyncTaskLoader<List<Movie>> {

	private List<Movie> movies;

	public GridLoader(Context context) {
		super(context);
	}

	@Override
	protected void onStartLoading() {
		if (movies == null) {
			// no data so kick off loading
			onForceLoad();
		} else {
			// Use cached data
			deliverResult(movies);
		}
	}

	/** Retrofit call on Background thread */
	@Override
	public List<Movie> loadInBackground() {
		List<Movie> movieList = new ArrayList<>();
		TmdbApiService apiService = AppDataRepository.getInstance().getTmdbApiService();

		try {
			Response<DiscoverResponse> response = apiService.getMovies(PrivateApiKey.YOUR_API_KEY).execute();
			if (response.isSuccessful()) {
				DiscoverResponse rootResponse = response.body();
				if (rootResponse != null) {
					movieList.addAll(rootResponse.getMovies());
				}
			}
		} catch (IOException ioe) {
			Log.d("GridLoader", "IOException has occurred while fetching movies from API");
			ioe.getCause();
			ioe.getStackTrace();
		}
		return movieList;
	}

	@Override
	public void deliverResult(List<Movie> data) {
		// Cache data
		movies = data;
		super.deliverResult(data);
	}
}
