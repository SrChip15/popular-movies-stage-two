package com.example.android.flixt.service.repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppDataRepository {
	private static AppDataRepository sDataRepository;
	private TmdbApiService mTmdbApiService;

	private AppDataRepository() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(TmdbApiService.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		mTmdbApiService = retrofit.create(TmdbApiService.class);
	}

	public synchronized static AppDataRepository getInstance() {
		if (sDataRepository == null) {
			sDataRepository = new AppDataRepository();
		}
		return sDataRepository;
	}

	public TmdbApiService getTmdbApiService() {
		return mTmdbApiService;
	}
}
