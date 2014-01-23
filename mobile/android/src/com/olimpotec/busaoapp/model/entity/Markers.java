package com.olimpotec.busaoapp.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "markers") 
public class Markers 
{
	@DatabaseField(id = true, columnName = "marker_id") 
    private Integer markerId;
	
	@DatabaseField (columnName = "marker_name")
    private String markerName;
     
    public Integer getMarkerId() 
    {
		return markerId;
	}

	public void setMarkerId(Integer markerId) 
	{
		this.markerId = markerId;
	}

	public String getMarkerName() 
	{
		return markerName;
	}

	public void setMarkerName(String markerName) 
	{
		this.markerName = markerName;
	}

	@Override
	public String toString() {
		return "Markers [markerId=" + markerId + ", markerName=" + markerName
				+ "]";
	}
}
