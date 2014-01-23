package com.olimpotec.busaoapp.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "routes") 
public class Routes 
{
	@DatabaseField (foreign = true, foreignAutoRefresh = true, columnName = "street_id")
	private Street street;
	
	@DatabaseField (foreign = true, foreignAutoRefresh = true, columnName = "bus_id")
	private Bus bus;
	
	@DatabaseField (columnName = "route_way")
	private String routeWay;
	
	@DatabaseField 
	private String line;

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public String getRouteWay() {
		return routeWay;
	}

	public void setRouteWay(String routeWay) {
		this.routeWay = routeWay;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	@Override
	public String toString() {
		return "Routes [street=" + street + ", bus=" + bus + ", routeWay="
				+ routeWay + ", line=" + line + "]";
	}
	
}
