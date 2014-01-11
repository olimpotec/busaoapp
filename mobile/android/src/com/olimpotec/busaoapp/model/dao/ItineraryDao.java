package com.olimpotec.busaoapp.model.dao;

import java.sql.SQLException;
import java.util.List;

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
}
