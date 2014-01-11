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
import com.olimpotec.busaoapp.model.dao.ItineraryDao;
import com.olimpotec.busaoapp.model.entity.Itineraries;

public class ScheduleFragment extends Fragment 
{
	private static int busId;
	
	private ItineraryDao itDao;
	
	private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    
    private RelativeLayout panelProgress;
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		
		LinearLayout rootView = (LinearLayout)inflater.inflate(R.layout.fragment_schedule, container, false);
        
		try 
		{
			itDao = new ItineraryDao();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
        
		expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp2);
       
		showLoader (rootView);
		
		new LoadSchedules().execute();
		
		
        return rootView;
    }
	
	private class LoadSchedules extends AsyncTask<Void, Void, Void> 
	{

	    @Override
	    protected void onPreExecute() {
	    	
	        super.onPreExecute();
	    }

	    @Override
	    protected Void doInBackground(Void... params) 
	    {
	    	List<Itineraries> itineraries  = itDao.getAllItinerariesByBus(busId);
	    	
	        listDataHeader = new ArrayList<String>();
	        listDataChild = new HashMap<String, List<String>>();
	        
	        for (Itineraries it : itineraries)
	        {
	        	String functionalPlan;
	        	if(it.getFunctionalPlan().getFunctionalPlanName().indexOf("Segunda") > -1)
	        		functionalPlan = "Segunda a Sexta";
	        	else if (it.getFunctionalPlan().getFunctionalPlanName().indexOf("Domingo") > -1)
	        		functionalPlan = "Domingos e Feriados";
	        	else
	        		functionalPlan = "S‡bado";
	        	
	        	functionalPlan += " - Linha "+ it.getLine();
	        	
	        	if(!listDataChild.containsKey(functionalPlan))
	        	{
	        		listDataHeader.add(functionalPlan);
	        		listDataChild.put(functionalPlan, new ArrayList<String>());
	        	}
	        	
	        	listDataChild.get(functionalPlan).add(it.getSchedule() + " - ");
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
