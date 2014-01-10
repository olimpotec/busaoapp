package com.olimpotec.busaoapp.fragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.olimpotec.busaoapp.R;
import com.olimpotec.busaoapp.adapter.ExpandableListAdapter;
import com.olimpotec.busaoapp.model.dao.RouteDao;
import com.olimpotec.busaoapp.model.entity.Routes;

public class ItineraryFragment extends Fragment 
{
	private static int busId;
	
	private RouteDao routeDao;
	
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		ProgressDialog progress = ProgressDialog.show (getActivity (), "Aguarde...", "Um momento por favor...", true, false);
		LinearLayout rootView = (LinearLayout)inflater.inflate(R.layout.fragment_itinerary, container, false);
        
		try {
			routeDao = new RouteDao();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
        
		 // get the listview
		ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
 
        // preparing list data
        prepare();
 
        listAdapter = new ExpandableListAdapter(getActivity(),listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
      
        progress.dismiss();
        return rootView;
    }
	
	/*
     * Preparing the list data
     */
    private void prepare() 
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
    }
    
	public void setBusId (int id)
	{
		busId = id;
	}
}
