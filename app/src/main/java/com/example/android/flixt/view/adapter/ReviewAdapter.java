package com.example.android.flixt.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.flixt.R;
import com.example.android.flixt.service.model.Review;
import com.example.android.flixt.view.custom.AppRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends AppRecyclerView.Adapter<ReviewViewHolder> {

	private List<Review> mReviews = new ArrayList<>();
	private Context mContext;
	private ListItemClickListener mListItemClickListener;

	public interface ListItemClickListener {
		void onItemClick(Uri reviewUri);
	}

	public ReviewAdapter(Context context, ListItemClickListener itemClickListener) {
		mContext = context;
		mListItemClickListener = itemClickListener;
	}

	@Override
	public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(mContext).inflate(R.layout.review_list_item, parent, false);
		return new ReviewViewHolder(itemView, mReviews, mListItemClickListener);
	}

	@Override
	public void onBindViewHolder(ReviewViewHolder holder, int position) {
		Review review = mReviews.get(position);

		String formattedContent = formatted(review.getContent());
		holder.setContent(formattedContent);

		String authorAndSpacing = review.getAuthor() + "\n\n";
		holder.setAuthor(authorAndSpacing);
	}

	@Override
	public int getItemCount() {
		return mReviews != null ? mReviews.size() : 0;
	}

	public void setReviews(@Nullable List<Review> reviewList) {
		mReviews = reviewList;
		notifyDataSetChanged();
	}

	private String formatted(String text) {
		while (text.contains("\n")) {
			// New line found
			int index = text.indexOf("\n");
			String one = text.substring(0, index);
			String two = text.substring(index + 1, text.length());
			text = one.concat(two);
		}
		return text;
	}
}
