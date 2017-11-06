package com.example.android.flixt.view.adapter;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.flixt.R;
import com.example.android.flixt.data.MovieContract;
import com.squareup.picasso.Picasso;

public class FavoritesAdapter extends RecyclerView.Adapter<GridViewHolder> {

	private Context mContext;
	private Cursor mCursor;

	private static final String POSTER_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
	private static final String POSTER_IMAGE_SIZE = "w780";

	public FavoritesAdapter(Context context, Cursor cursor) {
		mContext = context;
		mCursor = cursor;
	}

	@Override
	public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View vh = LayoutInflater.from(mContext).inflate(R.layout.grid_list_item, parent, false);
		return new GridViewHolder(vh);
	}

	@Override
	public void onBindViewHolder(GridViewHolder holder, int position) {
		mCursor.moveToPosition(position);
		String posterPath = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.POSTER_PATH));
		String imageUrl = POSTER_IMAGE_BASE_URL + POSTER_IMAGE_SIZE + posterPath;
		Picasso.with(mContext)
				.load(imageUrl)
				.into(holder.posterImageView);
	}

	@Override
	public int getItemCount() {
		return mCursor == null ? 0 : mCursor.getCount();
	}

	public void swapCursor(Cursor cursor) {
		if (mCursor != null) mCursor.close();
		mCursor = cursor;
		notifyDataSetChanged();
	}
}
