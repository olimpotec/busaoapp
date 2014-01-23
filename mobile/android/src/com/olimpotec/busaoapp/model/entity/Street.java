package com.olimpotec.busaoapp.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "streets") 
public class Street 
{
	@DatabaseField(id = true, columnName = "street_id") 
    private Integer streetId;
     
    @DatabaseField (columnName = "street_name")
    private String streetName;

	public Integer getStreetId() {
		return streetId;
	}

	public void setStreetId(Integer streetId) {
		this.streetId = streetId;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	@Override
	public String toString() {
		return "Street [streetId=" + streetId + ", streetName=" + streetName
				+ "]";
	}
	
}
