package com.antulev.billing;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Wallet {
	
	@Id
	private String id;
	private String name;
	private String type;
	private double goalValue;
	private double startAmount;
	private double endDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getGoalValue() {
		return goalValue;
	}
	public void setGoalValue(double goalValue) {
		this.goalValue = goalValue;
	}
	public double getStartAmount() {
		return startAmount;
	}
	public void setStartAmount(double startAmount) {
		this.startAmount = startAmount;
	}
	public double getEndDate() {
		return endDate;
	}
	public void setEndDate(double endDate) {
		this.endDate = endDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
