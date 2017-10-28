package com.example.android.flixt.view.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.flixt.R;
import com.example.android.flixt.presenter.GridLoader;
import com.example.android.flixt.service.model.Movie;
import com.example.android.flixt.view.adapter.GridAdapter;
import com.example.android.flixt.view.callback.GridScrollListener;
import com.example.android.flixt.view.custom.AppRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/** A simple {@link Fragment} subclass. */
public class GridFragment
		extends Fragment
		implements GridAdapter.ListItemClickListener, LoaderManager.LoaderCallbacks<List<Movie>> {

	private static final int MOVIE_GRID_LOADER_ID = 11;
	@BindView(R.id.recycler_view)
	AppRecyclerView mRecyclerView;
	@BindView(R.id.empty_text_view)
	TextView mEmptyStateTextView;
	@BindView(R.id.progress_bar)
	ProgressBar mLoadingProgressBar;
	/** Flag for paginated scroll listener */
	private boolean isLoading = false;
	private boolean isLastPage = false;
	/** Total number of movie pages from the API */
	private int totalPages;
	/** Store current movie page that is being parsed */
	private int currentPage;
	private GridAdapter mAdapter;

	public GridFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View movieGridView = inflater.inflate(R.layout.grid_view, container, false);
		ButterKnife.bind(this, movieGridView);
		setupPosterGridView();
		getActivity().getSupportLoaderManager().initLoader(MOVIE_GRID_LOADER_ID, null, this);

		return movieGridView;
	}

	private void setupPosterGridView() {

		mAdapter = new GridAdapter(getActivity(), this);
		mRecyclerView.setAdapter(mAdapter);

		mRecyclerView.setHasFixedSize(true);
		int columns = getResources().getInteger(R.integer.grid_columns);
		GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);
		mRecyclerView.setLayoutManager(layoutManager);

		mLoadingProgressBar.setIndeterminate(true);
		mRecyclerView.setEmptyView(mEmptyStateTextView);

		mRecyclerView.addOnScrollListener(new GridScrollListener(layoutManager) {
			@Override
			protected void loadMoreItems() {
				isLoading = true;
				currentPage++;

				loadNextPage();
			}

			@Override
			public int getTotalPageCount() {
				return totalPages;
			}

			@Override
			public boolean isLastPage() {
				return isLastPage;
			}

			@Override
			public boolean isLoading() {
				return isLoading;
			}
		});
	}

	private void loadNextPage() {

	}

	@Override
	public void onPosterClick(Movie movie) {
	    /*Intent detailActivityIntent = new Intent(getActivity(), MovieDetailActivity.class);
	    Log.d(TAG, "" + movie.getTitle() + " movie clicked!");
        detailActivityIntent.putExtra("Movie", movie);
        startActivity(detailActivityIntent);*/
	}

	@Override
	public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
		return new GridLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
		// Update adapter
		mAdapter.addMovies(data);
		mLoadingProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void onLoaderReset(Loader<List<Movie>> loader) {
		mAdapter.addMovies(null);
	}
}
