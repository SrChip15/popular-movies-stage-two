package com.example.android.flixt.service.repository;

import com.example.android.flixt.service.model.DiscoverResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbApiService {

	String BASE_URL = "https://api.themoviedb.org/3/";

	@GET("discover/movie")
	Call<DiscoverResponse> getMovies(
			@Query("api_key") String apiKey/*,
			@Query("page") int page*/
	);
}