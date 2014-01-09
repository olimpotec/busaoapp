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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		 // get the listview
		ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
 
        // preparing list data
        prepare();
 
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
        /*
		
		LinearLayout l = (LinearLayout)rootView.findViewById(R.id.itinerary);
		
		for (Routes r : routes)
		{
			CustomTextView item = new CustomTextView(getActivity());
			item.setText(r.getStreet().getStreetName());
			l.addView(item);
		}
		progress.dismiss();*/
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
 
        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");
 
        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");
 
        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");
 
        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");
 
        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }
    
	public void setBusId (int id)
	{
		busId = id;
		
	}
}
