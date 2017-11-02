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

import retrofit2.Response;

public class GridLoader extends AsyncTaskLoader<GridData> {

	private GridData mData;
	private int currentPage;

	public GridLoader(Context context, int currentPage) {
		super(context);
		this.currentPage = currentPage;
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
				Response<DiscoverResponse> response = apiService.getMovies(PrivateApiKey.YOUR_API_KEY, currentPage).execute();
				if (response.isSuccessful()) {
					DiscoverResponse rootResponse = response.body();
					if (rootResponse != null) {
						data.setMovies(rootResponse.getMovies());
						data.setTotalPages(rootResponse.getTotalPages());
					}
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
