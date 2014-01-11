package com.olimpotec.busaoapp.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.olimpotec.busaoapp.BusActivity;
import com.olimpotec.busaoapp.fragment.ItineraryFragment;
import com.olimpotec.busaoapp.fragment.ScheduleFragment;
import com.olimpotec.busaoapp.helper.LogHelper;

public class TabsPagerAdapter extends FragmentPagerAdapter 
{
	private Activity act;
	 
    public TabsPagerAdapter(FragmentManager fm, Activity act) 
    {
        super(fm);
        this.act = act;
    }
 
    @Override
    public Fragment getItem(int index) 
    {
    	LogHelper.debug(this, "Tab: "+index);
        switch (index) 
        {
	        case 0:
	        	ItineraryFragment it = new ItineraryFragment();
	        	
	        	it.setBusId(act.getIntent().getExtras().getInt(BusActivity.BUS_ID));
	            return it ;
	        case 1:
	        case 2:
	        	ScheduleFragment schedule = new ScheduleFragment();
	        	LogHelper.debug(this, ""+act.getIntent().getExtras().getInt(BusActivity.BUS_ID));
	        	schedule.setBusId(act.getIntent().getExtras().getInt(BusActivity.BUS_ID));
	            return schedule ;
	            
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
 
}
