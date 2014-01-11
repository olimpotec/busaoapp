package com.olimpotec.busaoapp.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "functional_plan") 
public class FunctionalPlan 
{
	@DatabaseField(id = true, columnName = "functional_plan_id") 
    private Integer functionalPlanId;
	
	@DatabaseField (columnName = "functional_plan_name")
    private String functionalPlanName;

	public Integer getFunctionalPlanId() {
		return functionalPlanId;
	}

	public void setFunctionalPlanId(Integer functionalPlanId) {
		this.functionalPlanId = functionalPlanId;
	}

	public String getFunctionalPlanName() {
		return functionalPlanName;
	}

	public void setFunctionalPlanName(String functionalPlanName) {
		this.functionalPlanName = functionalPlanName;
	}
	
}
