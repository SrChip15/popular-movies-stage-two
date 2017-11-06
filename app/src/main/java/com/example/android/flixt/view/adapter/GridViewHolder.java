package com.example.android.flixt.view.adapter;


import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.android.flixt.R;
import com.example.android.flixt.service.model.Movie;
import com.example.android.flixt.view.adapter.GridAdapter.ListItemClickListener;
import com.example.android.flixt.view.custom.PropPosterImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class GridViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

	@BindView(R.id.list_item_image_view)
	PropPosterImageView posterImageView;
	private List<Movie> movies;
	private ListItemClickListener listItemClickListener;

	GridViewHolder(View itemView, List<Movie> movies, @Nullable ListItemClickListener listItemClickListener) {
		super(itemView);
		ButterKnife.bind(this, itemView);
		this.movies = movies;
		this.listItemClickListener = listItemClickListener;
		itemView.setOnClickListener(this);
		itemView.setDrawingCacheEnabled(true);
	}

	public GridViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	@Override
	public void onClick(View view) {
		Movie movie = movies.get(getAdapterPosition());
		listItemClickListener.onPosterClick(movie);
	}
}
