package com.olimpotec.busaoapp.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.GenericRawResults;
import com.olimpotec.busaoapp.model.entity.Itineraries;

public class ItineraryDao extends GenericDao<Itineraries, Integer> 
{ 
	public ItineraryDao() throws java.sql.SQLException 
	{
	    super();
	}
	
	public List<Itineraries> getAllItinerariesByBus (int busId)
	{
		try 
		{	
			return dao.queryBuilder()
					.orderBy("itinerary_id", true)
					.where()
					.eq("bus_id", busId)
					.query();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Long getQtdMarkers (int bus_id)
	{
		try 
		{
			
			GenericRawResults<String[]> rawResults = dao.queryRaw("SELECT max(_order) FROM itineraries WHERE bus_id = "+bus_id);
			List<String[]> results = rawResults.getResults();
			
			String[] resultArray = results.get(0);
			
			return Long.valueOf(resultArray[0]);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Long.valueOf(0);
	}
}
