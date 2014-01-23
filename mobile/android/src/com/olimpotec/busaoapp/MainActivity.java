package com.olimpotec.busaoapp;



import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.olimpotec.busaoapp.action.ActionItem;
import com.olimpotec.busaoapp.action.QuickAction;
import com.olimpotec.busaoapp.adapter.NavDrawerItem;
import com.olimpotec.busaoapp.adapter.NavDrawerListAdapter;
import com.olimpotec.busaoapp.fragment.HomeFragment;
import com.olimpotec.busaoapp.helper.LogHelper;

public class MainActivity extends Activity 
{
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;
	private CharSequence drawerTitle;
	private CharSequence title;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	private static final int NOTIFY     = 1;
	 QuickAction quickAction;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		title = drawerTitle = getTitle();
		
		handleSearch (getIntent());

		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		
		navMenuIcons.recycle();

		drawerList.setOnItemClickListener(new SlideMenuClickListener());

		
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		drawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) 
		{
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(title);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(drawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0, null);
		}
		
		ActionItem nextItem         = new ActionItem(NOTIFY, "Terminal General Oso—rio", getResources().getDrawable(R.drawable.action_search));
		
		quickAction = new QuickAction(this, QuickAction.VERTICAL);
		quickAction.addActionItem(nextItem);
		quickAction.setAnimStyle(QuickAction.ANIM_REFLECT);
		quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {                        
            @Override
            public void onItemClick(QuickAction source, int pos, int actionId) {                                
                    ActionItem actionItem = quickAction.getActionItem(pos);
     
                    //here we can filter which action item was clicked with pos or actionId parameter
                    if (actionId == NOTIFY) 
                    {
                            Toast.makeText(getApplicationContext(), "Let's do some search action", Toast.LENGTH_SHORT).show();
                    } else {
                            Toast.makeText(getApplicationContext(), actionItem.getTitle() + " selected", Toast.LENGTH_SHORT).show();
                    }
            }
    });
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements ListView.OnItemClickListener 
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			displayView(position, null);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);
		
	
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    searchView.setIconifiedByDefault(true); 
	    
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// toggle nav drawer on selecting action bar app icon/title
		if (drawerToggle.onOptionsItemSelected(item)) 
			return true;
		//LogHelper.debug(this, item.getActionView().toString());
		// Handle action bar actions click
		switch (item.getItemId()) 
		{
			
			case R.id.action_settings:
				quickAction.show(findViewById(R.id.action_settings));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) 
	{
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position, String query) 
	{
		Fragment fragment = null;
		
		switch (position) 
		{
			case 0:
				fragment = new HomeFragment();
				((HomeFragment) fragment).setQuery (query);
				break;
		

			default:
				break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			drawerList.setItemChecked(position, true);
			drawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			drawerLayout.closeDrawer(drawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		title = title;
		getActionBar().setTitle(title);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		drawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onSearchRequested() 
	{
	     Bundle appData = new Bundle();
	     LogHelper.debug(this, "Buscou");
	     startSearch(null, false, appData, false);
	     return true;
	}
	
	public void handleSearch (Intent intent)
	{
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) 
	    {
	        String query = intent.getStringExtra(SearchManager.QUERY);  
	       
		    LogHelper.debug(this, query);
		    displayView(0, query);
	    }
	}
	
	@Override
	protected void onNewIntent(Intent intent) 
	{
	    super.onNewIntent(intent);
	    handleSearch(intent);
	}
}
