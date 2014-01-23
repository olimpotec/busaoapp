package com.olimpotec.busaoapp.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "itineraries")
public class Itineraries {
	@DatabaseField(id = true, columnName = "itinerary_id")
	private Integer itineraryId;

	@DatabaseField
	private String line;

	@DatabaseField
	private String schedule;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "marker_id")
	private Markers marker;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "bus_id")
	private Bus bus;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "functional_plan_id")
	private FunctionalPlan functionalPlan;
	
	@DatabaseField(columnName = "_order")
	private int order;
	
	public FunctionalPlan getFunctionalPlan() {
		return functionalPlan;
	}

	public void setFunctionalPlan(FunctionalPlan functionalPlan) {
		this.functionalPlan = functionalPlan;
	}

	public Integer getItineraryId() {
		return itineraryId;
	}

	public void setItineraryId(Integer itineraryId) {
		this.itineraryId = itineraryId;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public Markers getMarker() {
		return marker;
	}

	public void setMarker(Markers marker) {
		this.marker = marker;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Itineraries [itineraryId=" + itineraryId + ", line=" + line
				+ ", schedule=" + schedule + ", marker=" + marker + ", bus="
				+ bus + ", functionalPlan=" + functionalPlan + "]";
	}

}
