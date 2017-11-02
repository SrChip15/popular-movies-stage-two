
package com.example.android.flixt.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class ReviewResponse {

	@SerializedName("id")
	private Integer id;

	@SerializedName("page")
	private int page;

	@SerializedName("results")
	private List<Review> reviews = null;

	@SerializedName("total_pages")
	private int totalPages;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getPage() {
		return page;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public int getTotalPages() {
		return totalPages;
	}
}
