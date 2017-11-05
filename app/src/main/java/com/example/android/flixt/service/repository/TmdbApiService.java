package com.example.android.flixt.service.repository;

import com.example.android.flixt.service.model.DiscoverResponse;
import com.example.android.flixt.service.model.ReviewResponse;
import com.example.android.flixt.service.model.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApiService {

	String BASE_URL = "https://api.themoviedb.org/3/";

	@GET("discover/movie")
	Call<DiscoverResponse> getMovies(
			@Query("api_key") String apiKey,
			@Query("page") int page
	);

	@GET("movie/{id}/reviews")
	Call<ReviewResponse> getReviews(
			@Path("id") int movieId,
			@Query("api_key") String apiKey
	);

	@GET("movie/{id}/videos")
	Call<VideoResponse> getVideos(
			@Path("id") int movieId,
			@Query("api_key") String apiKey
	);

	@GET("movie/top_rated")
	Call<DiscoverResponse> getTopRatedMovies(
			@Query("api_key") String apiKey,
			@Query("page") int pageNumber
	);

	@GET("movie/popular")
	Call<DiscoverResponse> getPopularMovies(
			@Query("api_key") String apiKey,
			@Query("page") int pageNumber
	);
}