package com.example.android.flixt.view.callback;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class GridScrollListener extends RecyclerView.OnScrollListener {
	private final GridLayoutManager layoutManager;

	protected GridScrollListener(GridLayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}

	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);

		int visibleItemCount = layoutManager.getChildCount(); // No of child views bound to the recycler view
		int totalItemCount = layoutManager.getItemCount(); // No of items in the adapter
		int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition(); // Partially or fully covered child view

		// Pagination logic
		if (!isLoading() && !isLastPage()) {
			// View scrolled and data is not loading nor is the current data the last page
			if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
					&& firstVisibleItemPosition >= 0 /* ensures result set is not empty */) {
				// Ok, now check if all the items in the adapter has been bound to the recycler view
				// In other words, have all the data got from the last network call shown to the user
				// when all the conditions satisfy, new data is required to display to user
				loadMoreItems();
			}
		}
	}

	protected abstract void loadMoreItems();

	@SuppressWarnings("unused")
	public abstract int getTotalPageCount();

	public abstract boolean isLastPage();

	public abstract boolean isLoading();
}
