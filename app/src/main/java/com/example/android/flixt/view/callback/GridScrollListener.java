package com.example.android.flixt.view.callback;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class GridScrollListener extends RecyclerView.OnScrollListener {
	final GridLayoutManager layoutManager;

	public GridScrollListener(GridLayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}

	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);

		int visibleItemCount = layoutManager.getChildCount();
		int totalItemCount = layoutManager.getItemCount();
		int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

		// Pagination logic
		if (!isLoading() && !isLastPage()) {
			if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
					&& firstVisibleItemPosition >= 0) {
				loadMoreItems();
			}
		}
	}

	// TODO - Enable upward pagination also

	protected abstract void loadMoreItems();

	public abstract int getTotalPageCount();

	public abstract boolean isLastPage();

	public abstract boolean isLoading();
}
