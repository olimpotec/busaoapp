package com.olimpotec.busaoapp.fragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.olimpotec.busaoapp.R;
import com.olimpotec.busaoapp.action.ActionItem;
import com.olimpotec.busaoapp.action.QuickAction;
import com.olimpotec.busaoapp.adapter.ExpandableListAdapter;
import com.olimpotec.busaoapp.adapter.ExpandableScheduleListAdapter;
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
    private HashMap<Integer, String> markers;
    
    private RelativeLayout panelProgress;
    
    private RelativeLayout footerPanel;
    private QuickAction markersAction;
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		
		RelativeLayout rootView = (RelativeLayout)inflater.inflate(R.layout.fragment_schedule, container, false);
        
		try 
		{
			itDao = new ItineraryDao();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
        
		expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp2);
		footerPanel = (RelativeLayout) rootView.findViewById(R.id.footerPanel);
		
		
		
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
	        markers = new HashMap <Integer, String>(); 
	        
	        int oldOrder = -1, newOrder = 0;
	        String functionalPlan, busSchedule = null;
	        
	        for (Itineraries it : itineraries)
	        {
	        	
	        	newOrder = it.getOrder();
	        	
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
	        	
	        	//verificando se deve quebrar uma linha na tabela
	        	if(newOrder < oldOrder)
	        	{
	        		listDataChild.get(functionalPlan).add(busSchedule);
	        		
	        		int i = 0;
	        		busSchedule = "";
	        		while (i++< newOrder)
	        			busSchedule += " - ";
	        		busSchedule += it.getSchedule().substring(0, 5)+ "#" +it.getOrder();
	        	}
	        	else
	        	{
	        		if(busSchedule == null)
	        		{
	        			int i = 0;
		        		busSchedule = "";
		        		while (i++< newOrder)
		        			busSchedule += " - ";
		        		
	        			busSchedule += it.getSchedule().substring(0, 5) + "#" +it.getOrder();
	        		}
	        		else
	        			busSchedule += " - "+ it.getSchedule().substring(0, 5) + "#" +it.getOrder();
	        	}
	        	
	        	oldOrder = newOrder;
	        	
	        	if(!markers.containsKey(it.getOrder()))
	        		markers.put(it.getOrder(), it.getMarker().getMarkerName());
	        } 
	        
	       
	        return null;
	    }

	    @Override
	    protected void onPostExecute(Void result) 
	    {
	    	 listAdapter = new ExpandableScheduleListAdapter(getActivity(),listDataHeader, listDataChild, itDao.getQtdMarkers(busId));
	       	 
	         expListView.setAdapter(listAdapter);
	         
	         markersAction = new QuickAction(getActivity(), QuickAction.VERTICAL);
	         
	         markersAction.setAnimStyle(QuickAction.ANIM_REFLECT);
	        	 
	         Iterator it = markers.entrySet().iterator();
	         while(it.hasNext())
	         {
	        	 Map.Entry pairs = (Map.Entry)it.next();
	        	 ActionItem nextItem         = new ActionItem(((Integer)pairs.getKey()).intValue(), 
	        			 					(Integer)pairs.getKey()+" - " + ((String) pairs.getValue()), getResources().getDrawable(R.drawable.ic_action_place),
	        			 					Color.parseColor(ExpandableScheduleListAdapter.colorMarkers[((Integer)pairs.getKey()).intValue()]));
	 	         markersAction.addActionItem(nextItem);
	         }
	       
	         
	         Button b = (Button)footerPanel.findViewById(R.id.buttonFooter);
	         
	         b.setOnClickListener(new View.OnClickListener() 
	         {
				
				@Override
				public void onClick(View v) 
				{
					markersAction.show(v);
				}
			});
	         
	 		
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
		
		footerPanel.setVisibility(View.GONE);
	}
	
	public void hideLoader ()
	{
		panelProgress.setVisibility(View.GONE);
		
		footerPanel.setVisibility(View.VISIBLE);
	}
}
