package com.example.android.flixt.view.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.flixt.R;
import com.example.android.flixt.presenter.GridLoader;
import com.example.android.flixt.service.model.GridData;
import com.example.android.flixt.service.model.Movie;
import com.example.android.flixt.view.adapter.GridAdapter;
import com.example.android.flixt.view.callback.GridScrollListener;
import com.example.android.flixt.view.custom.AppRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;


/** A simple {@link Fragment} subclass. */
public class GridFragment
		extends Fragment
		implements GridAdapter.ListItemClickListener, LoaderManager.LoaderCallbacks<GridData> {

	private static final int MOVIE_GRID_LOADER_ID = 11;
	@BindView(R.id.recycler_view)
	AppRecyclerView mRecyclerView;
	@BindView(R.id.empty_text_view)
	TextView mEmptyStateTextView;
	@BindView(R.id.progress_bar)
	ProgressBar mLoadingProgressBar;

	private boolean mIsLoading = false; // Flag for paginated scroll listener
	private boolean mIsLastPage = false;
	private int mTotalPages; // Flag for paginated scroll listener
	private int mCurrentPage; // Store current movie page that is being parsed
	private GridAdapter mAdapter;

	private static final String CURRENT_PAGE = "currentPage";
	private static final String LAST_PAGE = "lastPage";
	private static final String TAG = GridFragment.class.getSimpleName();

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

		// Get info from bundle
		if (savedInstanceState != null) {
			mIsLastPage = savedInstanceState.getBoolean(LAST_PAGE);
			mCurrentPage = savedInstanceState.getInt(CURRENT_PAGE);
			// TODO Show scrolled movies too!
		} else {
			mCurrentPage = 1; // First run
		}

		Log.d(TAG + " onCreateView()", "CURRENT PAGE = " + mCurrentPage + "| TOTAL PAGES = " + mTotalPages);
		Log.d(TAG + " onCreateView()", "Loader triggered to fetch CURRENT PAGE = " + mCurrentPage);
		getActivity().getSupportLoaderManager().initLoader(MOVIE_GRID_LOADER_ID, null, this);

		return movieGridView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(CURRENT_PAGE, mCurrentPage);
		outState.putBoolean(LAST_PAGE, mIsLastPage);
		// TODO Save to database to show old
		super.onSaveInstanceState(outState);
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
				mIsLoading = true;
				mCurrentPage++;
				loadNextPage();
			}

			@Override
			public int getTotalPageCount() {
				return mTotalPages;
			}

			@Override
			public boolean isLastPage() {
				return mIsLastPage;
			}

			@Override
			public boolean isLoading() {
				return mIsLoading;
			}
		});
	}

	private void loadNextPage() {
		getActivity().getSupportLoaderManager().restartLoader(MOVIE_GRID_LOADER_ID, null, this);
	}

	@Override
	public void onPosterClick(Movie movie) {
	    Intent detailActivityIntent = new Intent(getActivity(), DetailActivity.class);
	    Log.d(TAG, "" + movie.getTitle() + " movie clicked!");
        detailActivityIntent.putExtra("Movie", movie);
        startActivity(detailActivityIntent);
	}

	@Override
	public Loader<GridData> onCreateLoader(int id, Bundle args) {
		Log.d(TAG + " onCreateLoader()", "Loader triggered to fetch CURRENT PAGE = " + mCurrentPage);
		return new GridLoader(getActivity(), mCurrentPage);
	}

	@Override
	public void onLoadFinished(Loader<GridData> loader, GridData data) {
		// Update adapter
		mAdapter.addMovies(data.getMovies());
		Log.d(TAG, "Size of adapter list = " + mAdapter.getItemCount());
		mTotalPages = data.getTotalPages();

		Log.d(TAG + " onLoadFinished()", "Total Pages = " + data.getTotalPages());
		Log.d(TAG + " onLoadFinished()", "CURRENT PAGE = " + mCurrentPage + "| TOTAL PAGES = " + mTotalPages);

		mLoadingProgressBar.setVisibility(View.GONE);

		if (mCurrentPage != 1) mIsLoading = false;
		if (mCurrentPage == mTotalPages) mIsLastPage = true;
	}

	@Override
	public void onLoaderReset(Loader<GridData> loader) {
		mAdapter.addMovies(null);
		mTotalPages = 0;
	}
}
