package com.olimpotec.busaoapp.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "bus") 
public class Bus 
{
   
	@DatabaseField(id = true, columnName = "bus_id") 
    private Integer busId;
     
    @DatabaseField (columnName = "bus_name")
    private String busName;
     
    @DatabaseField (columnName = "bus_number")
    private String busNumber;
     
    @DatabaseField
    private String color;
    
    @DatabaseField 
    private String company;
    
    public Integer getBusId() {
		return busId;
	}

	public void setBusId(Integer bus_id) {
		this.busId = bus_id;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String bus_name) {
		this.busName = bus_name;
	}

	public String getBusNumber() {
		return busNumber;
	}

	public void setBusNumber(String bus_number) {
		this.busNumber = bus_number;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Bus [busId=" + busId + ", busName=" + busName + ", busNumber="
				+ busNumber + ", color=" + color + ", company=" + company + "]";
	}
	

}