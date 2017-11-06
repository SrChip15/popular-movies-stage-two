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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.flixt.R;
import com.example.android.flixt.presenter.GridLoader;
import com.example.android.flixt.service.model.GridData;
import com.example.android.flixt.service.model.Movie;
import com.example.android.flixt.view.adapter.GridAdapter;
import com.example.android.flixt.view.custom.AppRecyclerView;
import com.paginate.Paginate;

import butterknife.BindView;
import butterknife.ButterKnife;


/** A simple {@link Fragment} subclass. */
public class GridFragment
		extends Fragment
		implements GridAdapter.ListItemClickListener,
		LoaderManager.LoaderCallbacks<GridData>,
		Paginate.Callbacks {

	@BindView(R.id.recycler_view)
	AppRecyclerView mRecyclerView;
	@BindView(R.id.empty_text_view)
	TextView mEmptyStateTextView;
	private boolean mLoading = false; // Flag for paginated scroll listener
	private boolean mHasNextPage = false;
	private int mCurrentPage = 1; // Store current movie page that is being parsed
	private GridAdapter mAdapter;
	private Integer mActiveLoader;

	private static final String CURRENT_PAGE = "currentPage";
	private static final String TAG = GridFragment.class.getSimpleName();
	private static final int MOVIE_GRID_LOADER_ID = 11;
	private static final int TOP_RATED_LOADER_ID = 22;
	private static final int POPULAR_LOADER_ID = 33;
	private static final String LAST_ACTIVE_LOADER = "LastActiveLoader";

	public GridFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		if (savedInstanceState != null) {
			mCurrentPage = savedInstanceState.getInt(CURRENT_PAGE);
			mActiveLoader = savedInstanceState.getInt(LAST_ACTIVE_LOADER);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View movieGridView = inflater.inflate(R.layout.grid_view, container, false);
		ButterKnife.bind(this, movieGridView);
		setupPosterGridView();

		Log.d(TAG + " onCreateView()", "Loader triggered to fetch CURRENT PAGE = " + mCurrentPage);

		if (savedInstanceState != null) {
			getActivity().getSupportLoaderManager().initLoader(mActiveLoader, null, this);
		} else {
			// Default behavior
			mActiveLoader = MOVIE_GRID_LOADER_ID;
			getActivity().getSupportLoaderManager().initLoader(MOVIE_GRID_LOADER_ID, null, this);
		}

		return movieGridView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(CURRENT_PAGE, mCurrentPage);
		outState.putInt(LAST_ACTIVE_LOADER, mActiveLoader);
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.action_top_rated:
				mActiveLoader = TOP_RATED_LOADER_ID;
				mCurrentPage = 1; // Reset current page to start from beginning
				getActivity().getSupportLoaderManager().restartLoader(TOP_RATED_LOADER_ID, null, this);
				return true;
			case R.id.action_popular:
				mActiveLoader = POPULAR_LOADER_ID;
				mCurrentPage = 1;
				getActivity().getSupportLoaderManager().restartLoader(POPULAR_LOADER_ID, null, this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setupPosterGridView() {

		mAdapter = new GridAdapter(getActivity(), this);
		mRecyclerView.setAdapter(mAdapter);

		mRecyclerView.setHasFixedSize(true);
		int columns = getResources().getInteger(R.integer.grid_columns);
		GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);
		mRecyclerView.setLayoutManager(layoutManager);

		/*mLoadingProgressBar.setIndeterminate(true);*/
		mRecyclerView.setEmptyView(mEmptyStateTextView);
		@SuppressWarnings("unused")
		Paginate mPaginate = Paginate.with(mRecyclerView, this)
				.setLoadingTriggerThreshold(1)
				.addLoadingListItem(true)
				.setLoadingListItemCreator(null)
				.setLoadingListItemSpanSizeLookup(() -> getResources().getInteger(R.integer.grid_columns))
				.build();
	}

	private void loadNextPage() {
		Log.d(TAG, "loadMore() callback just called loadNextPage()");
		getActivity().getSupportLoaderManager().restartLoader(mActiveLoader, null, this);
	}

	@Override
	public void onPosterClick(Movie movie) {
		Log.d(TAG, "" + movie.getTitle() + " movie clicked!");

		Intent detailActivityIntent = new Intent(getActivity(), DetailActivity.class);
		detailActivityIntent.putExtra("Movie", movie);
		startActivity(detailActivityIntent);
	}

	@Override
	public Loader<GridData> onCreateLoader(int id, Bundle args) {
		Log.d(TAG + " onCreateLoader()", "Loader triggered to fetch CURRENT PAGE = " + mCurrentPage);
		switch (id) {
			case TOP_RATED_LOADER_ID:
				return new GridLoader(getActivity(), mCurrentPage, TOP_RATED_LOADER_ID /* Sort by identifier */);
			case POPULAR_LOADER_ID:
				return new GridLoader(getActivity(), mCurrentPage, POPULAR_LOADER_ID);
			default:
				return new GridLoader(getActivity(), mCurrentPage, MOVIE_GRID_LOADER_ID);
		}
	}

	@Override
	public void onLoadFinished(Loader<GridData> loader, GridData data) {
		Log.d(TAG + " onLoadFinished()", "CURRENT PAGE = " + mCurrentPage + "| TOTAL PAGES = " + data.getTotalPages());
		int loaderId = loader.getId();

		if (loaderId == TOP_RATED_LOADER_ID && mCurrentPage == 1) {
			mAdapter.clear(); // Clears out adapter's discover movie data set
			mRecyclerView.smoothScrollToPosition(0);
		}
		if (loaderId == POPULAR_LOADER_ID && mCurrentPage == 1) {
			mAdapter.clear();
			mRecyclerView.smoothScrollToPosition(0);
		}
		mAdapter.addMovies(data.getMovies());
		mLoading = false;
		mHasNextPage = data.getTotalPages() >= mCurrentPage;
	}

	@Override
	public void onLoaderReset(Loader<GridData> loader) {
		mAdapter.addMovies(null);
	}

	@Override
	public void onLoadMore() {
		mLoading = true;

		if (mHasNextPage) {
			mCurrentPage++;
			loadNextPage();
		}
	}

	@Override
	public boolean isLoading() {
		return mLoading;
	}

	@Override
	public boolean hasLoadedAllItems() {
		return !mHasNextPage;
	}
}
