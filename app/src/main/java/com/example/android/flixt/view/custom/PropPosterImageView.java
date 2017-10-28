package com.example.android.flixt.view.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class PropPosterImageView extends AppCompatImageView {
	private static final float ASPECT_RATIO = 1.5f;

	public PropPosterImageView(Context context) {
		super(context);
	}

	public PropPosterImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PropPosterImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = Math.round(width * ASPECT_RATIO);
		setMeasuredDimension(width, height);
	}
}
