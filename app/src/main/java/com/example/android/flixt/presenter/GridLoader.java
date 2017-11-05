package com.example.android.flixt.presenter;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.flixt.service.model.DiscoverResponse;
import com.example.android.flixt.service.model.GridData;
import com.example.android.flixt.service.repository.AppDataRepository;
import com.example.android.flixt.service.repository.PrivateApiKey;
import com.example.android.flixt.service.repository.TmdbApiService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class GridLoader extends AsyncTaskLoader<GridData> {

	private GridData mData;
	private int mCurrentPage;
	private int mSortBy;

	private static final int TOP_RATED_MODE = 22;
	private static final int POPULAR_MODE = 33;

	public GridLoader(Context context, int CurrentPage, int sortBy) {
		super(context);
		mCurrentPage = CurrentPage;
		mSortBy = sortBy;
		mData = new GridData();
	}

	@Override
	protected void onStartLoading() {
		if (mData.getMovies() == null || mData.getTotalPages() == 0) {
			// No data so kick off loading
			onForceLoad();
		} else {
			// Use cached data
			deliverResult(mData);
		}
	}

	/** Retrofit call on Background thread */
	@Override
	public GridData loadInBackground() {
		if (isLoadInBackgroundCanceled()) {
			return null;
		} else {
			GridData data = new GridData();
			TmdbApiService apiService = AppDataRepository.getInstance().getTmdbApiService();

			try {
				Call<DiscoverResponse> call = null;
				if (mSortBy == TOP_RATED_MODE) {
					call = apiService.getTopRatedMovies(PrivateApiKey.YOUR_API_KEY, mCurrentPage);
				} else if (mSortBy == POPULAR_MODE) {
					call = apiService.getPopularMovies(PrivateApiKey.YOUR_API_KEY, mCurrentPage);
				} else {
					call = apiService.getMovies(PrivateApiKey.YOUR_API_KEY, mCurrentPage);
				}
				String requestURL = call.request().url().toString();
				Log.d("GridLoader", "Requested URL - " + requestURL);
				Response<DiscoverResponse> response = call.execute();
				if (response.isSuccessful()) {
					DiscoverResponse rootResponse = response.body();
					if (rootResponse != null) {
						data.setMovies(rootResponse.getMovies());
						data.setTotalPages(rootResponse.getTotalPages());
					}
				} else {
					Log.d("GridLoader", "Request failed " + response.message());
				}
			} catch (IOException ioe) {
				Log.d("GridLoader", "IOException has occurred while fetching movies from API");
				ioe.getCause();
				ioe.getStackTrace();
			}
			return data;
		}
	}


	@Override
	public void deliverResult(GridData data) {
		// Cache data
		mData = data;
		super.deliverResult(data);
	}
}
