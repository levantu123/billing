package com.antulev.billing;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Transaction {
	@Id
    private String id;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	private String category;
	private String description;
	private double amountOfMoney;
	private String moneySource;
	private String note;
	private Date issuedTime;
	private String owner; 
	private String recursive;
	private Date until;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getAmountOfMoney() {
		return amountOfMoney;
	}
	public void setAmountOfMoney(double amountOfMoney) {
		this.amountOfMoney = amountOfMoney;
	}
	
	public Date getIssuedTime() {
		return issuedTime;
	}
	public void setIssuedTime(Date issuedTime) {
		this.issuedTime = issuedTime;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMoneySource() {
		return moneySource;
	}
	public void setMoneySource(String moneySource) {
		this.moneySource = moneySource;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRecursive() {
		return recursive;
	}

	public void setRecursive(String recursive) {
		this.recursive = recursive;
	}

	public Date getUntil() {
		return until;
	}

	public void setUntil(Date until) {
		this.until = until;
	}	
}
