package com.example.android.flixt.view.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.action_top_rated:
				// Fragment handles this
				return false;
			case R.id.action_popular:
				// Fragment handles this
				return false;
		}
		return super.onOptionsItemSelected(item);
	}
}
