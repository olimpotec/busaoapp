package com.olimpotec.busaoapp.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.olimpotec.busaoapp.model.entity.Bus;

public class BusDao extends GenericDao<Bus, Integer> 
{ 
	public BusDao() throws java.sql.SQLException 
	{
	    super();
	}
	
	public List<Bus> getAllByQuery (String query, int offset, int limit)
	{
		try 
		{
			return dao.queryBuilder()
					.limit(Long.valueOf(limit))
					.offset(Long.valueOf(offset))
					.where().eq("bus_number", query)
					.or()
					.like("bus_name", "%"+query+"%")
					.query();
		} 
		catch (NumberFormatException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
