package com.antulev.billing;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;

public class Goal {
	@Id
	private String id;
	private String name;
	private double goalValue;
	private double initialValue;
	private Date endingDate;
	
	@CreatedBy
	private String owner;
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
	public double getInitialValue() {
		return initialValue;
	}
	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}
	public Date getEndingDate() {
		return endingDate;
	}
	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
}
