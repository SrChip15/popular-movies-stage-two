package com.example.android.flixt.view.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.flixt.R;
import com.example.android.flixt.Utils;
import com.example.android.flixt.presenter.ReviewsLoader;
import com.example.android.flixt.service.model.Movie;
import com.example.android.flixt.service.model.Review;
import com.example.android.flixt.view.adapter.ReviewAdapter;
import com.example.android.flixt.view.adapter.ReviewAdapter.ListItemClickListener;
import com.example.android.flixt.view.custom.AppRecyclerView;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment
		extends Fragment
		implements ListItemClickListener, LoaderManager.LoaderCallbacks<List<Review>> {

	@BindView(R.id.movie_title)
	TextView mMovieTitle;
	@BindView(R.id.movie_release_date)
	TextView mMovieReleaseDate;
	@BindView(R.id.rating_bar)
	RatingBar mMovieRating;
	@BindView(R.id.rating_count)
	TextView mRatingCount;
	@BindView(R.id.movie_overview)
	TextView mMovieOverview;
	@BindView(R.id.reviews_recycler_view)
	AppRecyclerView mReviewRecyclerView;
	@BindView(R.id.review_empty_text_view)
	TextView mEmptyStateTextView;
	private Movie mMovie;
	private ReviewAdapter mReviewAdapter;
	private static final String TAG = DetailFragment.class.getSimpleName();
	private static final int REVIEWS_LOADER_ID = 22;

	public DetailFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View detailMovieView = inflater.inflate(R.layout.detail_view, container, false);
		ButterKnife.bind(this, detailMovieView);

		// Get passed-in movie object
		Intent mIntent = getActivity().getIntent();
		mMovie = mIntent.getParcelableExtra("Movie");

		// Setup views
		setupReleaseDate(mMovie);
		setupRatings(mMovie);
		setupReviews();

		// Return view
		return detailMovieView;
	}

	private void setupReviews() {
		// Setup movie mReviews recycler view
		mReviewAdapter = new ReviewAdapter(getActivity(), this);
		mReviewRecyclerView.setAdapter(mReviewAdapter);
		mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		mReviewRecyclerView.setHasFixedSize(true);
		mReviewRecyclerView.setEmptyView(mEmptyStateTextView);
		getActivity().getSupportLoaderManager().initLoader(REVIEWS_LOADER_ID, null, this);
	}

	private void setupReleaseDate(Movie movie) {
		String formattedDate = Utils.getFormattedDate(movie.getReleaseDate());
		mMovieTitle.setText(movie.getTitle());
		mMovieReleaseDate.setText(formattedDate);
	}

	private void setupRatings(Movie movie) {
		BigDecimal getVoteAverage = new BigDecimal(movie.getVoteAverage());
		float tenPointRating = getVoteAverage.floatValue();
		float convertedRating = 5 * (tenPointRating / 10);

		mMovieRating.setRating(convertedRating);
		mRatingCount.setText(getResources().getString(R.string.vote_count_label, movie.getVoteCount()));
		mMovieOverview.setText(movie.getOverview());
	}

	@Override
	public void onItemClick(Uri reviewUri) {
		Intent openReviewInBrowser = new Intent(Intent.ACTION_VIEW, reviewUri);
		if (openReviewInBrowser.resolveActivity(getActivity().getPackageManager()) != null) {
			startActivity(openReviewInBrowser);
		}
	}

	@Override
	public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
		return new ReviewsLoader(getActivity(), mMovie);
	}

	@Override
	public void onLoadFinished(Loader<List<Review>> loader, List<Review> data) {
		mReviewAdapter.setReviews(data);
		Log.d(TAG, "No of Reviews = " + data.size());
	}

	@Override
	public void onLoaderReset(Loader<List<Review>> loader) {
		// Do nothing
	}
}
