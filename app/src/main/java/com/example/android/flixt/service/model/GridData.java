package com.example.android.flixt.service.model;


import java.util.List;

public class GridData {

	private List<Movie> movies;
	private int totalPages;

	public GridData(List<Movie> movies, int totalPages) {
		this.movies = movies;
		this.totalPages = totalPages;
	}

	public GridData() {
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}
