package com.antulev.billing;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Invest {
	
	@Id
	private String id;
	private String name;
	private double limitAmount;
	private int issuedPerious;
	private double profitRate;
	@CreatedBy
	private String owner;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLimitAmount() {
		return limitAmount;
	}
	public void setLimitAmount(double limitAmount) {
		this.limitAmount = limitAmount;
	}
	public int getIssuedPerious() {
		return issuedPerious;
	}
	public void setIssuedPerious(int issuedPerious) {
		this.issuedPerious = issuedPerious;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getProfitRate() {
		return profitRate;
	}
	public void setProfitRate(double profitRate) {
		this.profitRate = profitRate;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
		
}
