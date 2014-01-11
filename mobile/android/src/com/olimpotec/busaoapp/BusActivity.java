package com.olimpotec.busaoapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.olimpotec.busaoapp.adapter.TabsPagerAdapter;
import com.olimpotec.busaoapp.helper.LogHelper;

public class BusActivity extends FragmentActivity implements ActionBar.TabListener
{
	private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
	
    private String[] tabs = { "Itiner‡rios", "Hor‡rios", "Pontos de Interesse" };
    
	public static String BUS_ID = "bus_id";
	public static String BUS_NAME = "bus_name";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus);
		
		this.setTitle((CharSequence) this.getIntent().getExtras().getString(BUS_NAME));
		
		// Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), this);
 
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
     
        for (String tab_name : tabs) 
        {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
 
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
 
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) 
	{
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) 
	{
		LogHelper.debug(this,""+ tab.getPosition());
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) 
	{
		
	}
}
