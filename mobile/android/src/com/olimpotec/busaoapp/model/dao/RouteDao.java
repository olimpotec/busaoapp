package com.olimpotec.busaoapp.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.olimpotec.busaoapp.model.entity.Routes;

public class RouteDao extends GenericDao<Routes, Integer> 
{ 
	public RouteDao() throws java.sql.SQLException 
	{
	    super();
	}
	
	public List<Routes> getRoutesByBus (int busId)
	{
		try {
			return this.dao.query(this.dao.queryBuilder().where().eq("bus_id", busId).prepare());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<String> getDistinctRouteWay ( int busId)
	{
		List<Routes> routes = null;
		
		try {
			
			routes = dao.query(dao.queryBuilder()
					.distinct().selectColumns("route_way")
					.where().eq("bus_id", busId).prepare());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> list = new ArrayList<String>();
		
		for(Routes route: routes )
		{
			list.add(route.getRouteWay());
		}
		
		return list;
	}
}