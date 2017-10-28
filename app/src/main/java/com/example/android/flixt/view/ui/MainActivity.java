package com.example.android.flixt.view.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.flixt.R;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Call GridFragment
		if (savedInstanceState == null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			GridFragment gridFragment = new GridFragment();
			fragmentManager.beginTransaction()
					.replace(R.id._grid_fragment_container, gridFragment)
					.commit();
		}
	}
}
