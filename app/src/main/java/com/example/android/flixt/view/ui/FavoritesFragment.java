package com.example.android.flixt.view.ui;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.flixt.R;
import com.example.android.flixt.data.MovieContract.MovieEntry;
import com.example.android.flixt.view.adapter.FavoritesAdapter;
import com.example.android.flixt.view.custom.AppRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment
		extends Fragment
		implements LoaderManager.LoaderCallbacks<Cursor> {

	@BindView(R.id.recycler_view)
	AppRecyclerView mRecyclerView;
	@BindView(R.id.empty_text_view)
	TextView mEmptyStateTextView;
	private FavoritesAdapter mAdapter;

	private static final int FAVORITES_LOADER_ID = 44;

	public FavoritesFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View favGrid = inflater.inflate(R.layout.grid_view, container, false);
		ButterKnife.bind(this, favGrid);
		setupGridView();
		getActivity().getSupportLoaderManager().initLoader(
				FAVORITES_LOADER_ID,
				null,
				this
		);
		return favGrid;
	}

	private void setupGridView() {
		mAdapter = new FavoritesAdapter(getActivity(), null);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setHasFixedSize(true);
		int columns = getResources().getInteger(R.integer.grid_columns);
		GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setEmptyView(mEmptyStateTextView);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = {
				MovieEntry.ID,
				MovieEntry.TITLE,
				MovieEntry.POSTER_PATH,
				MovieEntry.BACKDROP_PATH,
				MovieEntry.OVERVIEW
		};

		return new CursorLoader(getContext(), MovieEntry.CONTENT_URI, projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
}
