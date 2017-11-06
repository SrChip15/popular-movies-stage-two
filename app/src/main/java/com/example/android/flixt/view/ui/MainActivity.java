package com.example.android.flixt.view.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.flixt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	@BindView(R.id.main_toolbar)
	Toolbar mToolbar;
	@BindView(R.id.drawer_layout)
	DrawerLayout mDrawerLayout;
	@BindView(R.id.nav_view)
	NavigationView mNavView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		setupToolbar();
		setupNavDrawer();

		if (savedInstanceState == null) {
			FragmentManager fragMgr = getSupportFragmentManager();
			GridFragment frag = new GridFragment();
			fragMgr.beginTransaction()
					.add(R.id.grid_fragment_container, frag)
					.commit();
		}
	}

	private void setupToolbar() {
		setSupportActionBar(mToolbar);
	}

	private void setupNavDrawer() {
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		mToolbar.setNavigationIcon(R.drawable.ic_menu);
		mToolbar.setNavigationOnClickListener(view -> mDrawerLayout.openDrawer(GravityCompat.START));
		mNavView.setNavigationItemSelectedListener(this);
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

	@Override
	public void onBackPressed() {
		if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
			mDrawerLayout.closeDrawers();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.nav_favorite:
				FavoritesFragment favFrag = new FavoritesFragment();
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.grid_fragment_container, favFrag)
						.commit();
				mDrawerLayout.closeDrawers();
				setTitle(getResources().getString(R.string.fav_frag_title));
				return true;
			case R.id.nav_explore:
				GridFragment gridFrag = new GridFragment();
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.grid_fragment_container, gridFrag)
						.commit();
				mDrawerLayout.closeDrawers();
				setTitle(getResources().getString(R.string.app_name));
				return true;
			default:
				return false;
		}
	}
}
