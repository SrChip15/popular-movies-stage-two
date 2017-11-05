package com.example.android.flixt.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.flixt.R;
import com.example.android.flixt.service.model.Movie;
import com.example.android.flixt.view.custom.AppRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class GridAdapter extends AppRecyclerView.Adapter<GridViewHolder> {

	private static final String POSTER_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
	private static final String POSTER_IMAGE_SIZE = "w780";
	private final ListItemClickListener mItemClickListener;
	private List<Movie> mListOfMovies;
	private Context mContext;

	/**
	 * Create adapter object
	 */
	public GridAdapter(Context context, ListItemClickListener itemClickListener) {
		// Initialize data set
		mContext = context;
		mItemClickListener = itemClickListener;
		mListOfMovies = new ArrayList<>();
	}

	@Override
	public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// Get a new layout inflater to create item view in parent
		LayoutInflater inflater = LayoutInflater.from(mContext);

		// Create a new item view with the above inflater
		View itemView = inflater.inflate(R.layout.grid_list_item, parent, false);

		// Return the newly created view
		return new GridViewHolder(itemView, mListOfMovies, mItemClickListener);
	}

	@Override
	public void onBindViewHolder(GridViewHolder holder, int position) {
		if (mListOfMovies != null && !(mListOfMovies.isEmpty())) {
			Movie currentMovie = mListOfMovies.get(position);
			String posterPath = currentMovie.getPosterPath();
			String imageUrl = POSTER_IMAGE_BASE_URL + POSTER_IMAGE_SIZE + posterPath;
			Picasso.with(mContext)
					.load(imageUrl)
					.into(holder.posterImageView);
		}
	}

	@Override
	public int getItemCount() {
		return mListOfMovies != null ? mListOfMovies.size() : 0;
	}

	public void addMovies(@Nullable List<Movie> listOfMovies) {
		if (listOfMovies != null) {
			int startPosition = mListOfMovies.size() - 1;
			int itemCount = 0;
			for (Movie movie : listOfMovies) {
				if (movie.getPosterPath() != null && !(TextUtils.isEmpty(movie.getPosterPath()))) {
					// Add movies to data set ONLY if it has a valid poster
					mListOfMovies.add(movie);
					itemCount++;
				}
			}
			notifyItemRangeInserted(startPosition, itemCount);
		} else {
			mListOfMovies = null;
		}
	}

	public void clear() {
		mListOfMovies.clear();
		notifyDataSetChanged();
	}

	public interface ListItemClickListener {
		void onPosterClick(Movie movie);
	}
}
