package com.olimpotec.busaoapp.fragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.olimpotec.busaoapp.R;
import com.olimpotec.busaoapp.adapter.ExpandableListAdapter;
import com.olimpotec.busaoapp.model.dao.RouteDao;
import com.olimpotec.busaoapp.model.entity.Routes;

public class ItineraryFragment extends Fragment 
{
	private static int busId;
	
	private RouteDao routeDao;
	
	private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    
    private RelativeLayout panelProgress;
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		
		LinearLayout rootView = (LinearLayout)inflater.inflate(R.layout.fragment_itinerary, container, false);
        
		try {
			routeDao = new RouteDao();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
        
		expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
		
		showLoader (rootView);
		
		new LoadItineraries().execute();
        
        return rootView;
    }
	
    private class LoadItineraries extends AsyncTask<Void, Void, Void> 
	{

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    }

	    @Override
	    protected Void doInBackground(Void... params) 
	    {
	    	List<Routes> routes  = routeDao.getRoutesByBus(busId);
	    	
	        
	        listDataHeader = new ArrayList<String>();
	        listDataChild = new HashMap<String, List<String>>();
	        
	        for (Routes route : routes)
	        {
	        	String routeWay = route.getLine() +" - " +route.getRouteWay().replace("Sentido:", "");
	        	if(!listDataChild.containsKey(routeWay))
	        	{
	        		listDataHeader.add(routeWay);
	        		listDataChild.put(routeWay, new ArrayList<String>());
	        	}
	        	
	        	listDataChild.get(routeWay).add(route.getStreet().getStreetName());
	        } 
	        return null;
	    }

	    @Override
	    protected void onPostExecute(Void result) 
	    {
	    	 listAdapter = new ExpandableListAdapter(getActivity(),listDataHeader, listDataChild);
	       	 
	         expListView.setAdapter(listAdapter);
	         
	         hideLoader ();
	    }
	}
    
	public void setBusId (int id)
	{
		busId = id;
	}
	
	public void showLoader (View view)
	{
		panelProgress = (RelativeLayout) view.findViewById(R.id.loadingPanel);
		
		panelProgress.setVisibility(View.VISIBLE);
	}
	
	public void hideLoader ()
	{
		panelProgress.setVisibility(View.GONE);
	}
}
