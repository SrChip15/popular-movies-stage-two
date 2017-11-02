package com.example.android.flixt.view.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.flixt.R;
import com.example.android.flixt.service.model.Review;
import com.example.android.flixt.view.adapter.ReviewAdapter.ListItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public final class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

	@BindView(R.id.movie_review_content_tv)
	TextView reviewContentTextView;
	@BindView(R.id.movie_review_author_tv)
	TextView reviewAuthorTextView;
	private List<Review> reviews;
	private ListItemClickListener listItemClickListener;

	ReviewViewHolder(View itemView, List<Review> reviews, ListItemClickListener listener) {
		super(itemView);
		ButterKnife.bind(this, itemView);
		itemView.setOnClickListener(this);

		this.reviews = reviews;
		this.listItemClickListener = listener;
	}

	void setContent(String review) {
		reviewContentTextView.setText(review);
	}

	void setAuthor(String author) {
		String formatted = " - " + author;
		reviewAuthorTextView.setText(formatted);
	}

	@Override
	public void onClick(View view) {
		int itemIndex = getAdapterPosition();
		Uri uri = Uri.parse(reviews.get(itemIndex).getUrl());
		Log.d("ReviewAdapter", "Review URL - " + uri.toString());
		listItemClickListener.onItemClick(uri);
	}
}
