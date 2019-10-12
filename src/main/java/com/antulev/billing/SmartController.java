package com.antulev.billing;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmartController {
	
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	GoalRepository goalRepository;
	@Autowired
	InvestRepository investRepository;
	
	@PostMapping("smart/addTransaction")
	public Transaction addTransaction(@RequestBody Transaction body){
		if(body.getRecursive().equals("none")) {
			transactionRepository.save(body);
		}else {
			Date startDate = body.getIssuedTime();
			while(startDate.before(body.getUntil())) {
				transactionRepository.save(body);
				startDate.setDate(startDate.getDate()+Integer.valueOf(body.getRecursive()));
				body.setId(null);
			}
		}
		return body;	
	}
	
	@PostMapping("smart/optimize")
	public Map<String, List<Double>> optimize(@RequestBody Optimize body) {
		List<Transaction> calTransactions = transactionRepository.findByOwnerAndIssuedTimeBetween(body.getCustomerId(), body.getStart(), body.getEnd());
		Date strminus = new Date(body.getStart().getTime());
		strminus.setDate(strminus.getDate()+1);
		Date cDateminus = new Date();
		cDateminus.setDate(cDateminus.getDate()-1);
		List<Transaction> preTransactions = transactionRepository.findByOwnerAndIssuedTimeBetween(body.getCustomerId(), cDateminus, strminus);
		List<Invest> invests = investRepository.findByOwner(body.getCustomerId());
		invests.sort(new Comparator<Invest>() {

			@Override
			public int compare(Invest o1, Invest o2) {
				if(o1.getProfitRate()/o1.getIssuedPerious()> o2.getProfitRate()/o2.getIssuedPerious()) {
					return -1;
				}
				if(o1.getProfitRate()/o1.getIssuedPerious()< o2.getProfitRate()/o2.getIssuedPerious()) {
					return 1;
				}
				return 0;
			}
		});
		double initialValue = this.getfinalMoney(preTransactions);
		List<Double> banlances = new ArrayList<>();
		List<Double>bakupBanlances = new ArrayList<>();

		long totalLeng = ( body.getEnd().getTime() - body.getStart().getTime() )/(24 * 60 * 60 * 1000);
		for(int i = 0; i< totalLeng; i++) {
			Date sta = new Date(body.getStart().getTime());
			sta.setDate(sta.getDate()+i);
			Date en = new Date(body.getStart().getTime());
			en.setDate(en.getDate()+i+2);
			double dateBalance = getfinalMoney(transactionRepository.findByOwnerAndIssuedTimeBetween(body.getCustomerId(), sta, en));
			initialValue += dateBalance;
			bakupBanlances.add(initialValue);
			banlances.add(initialValue);
		}
		
		for(Transaction transaction : optimize(body.getStart(), invests, banlances, body.getCustomerId())){
			transactionRepository.save(transaction);
		}
		
		Map<String, List<Double>> map= new HashMap<>();
		map.put("old", bakupBanlances);
		map.put("new", banlances);
		
		return map;
	}
	
	private List<Transaction> optimize(Date start, List<Invest> invests, List<Double> balances, String customerId) {
		List<Transaction> transactions = new ArrayList<>();
		for(Invest invest: invests) {
			transactions.addAll(optimize(start, invest, balances, customerId));
		}
		return transactions;
	}
	
	private List<Transaction> optimize(Date start, Invest invest, List<Double> balances, String customerId) {
		List<Transaction> transactions = new ArrayList<>();
		int ind = findFirstPoint(invest, balances);
		while(ind!= -1) {
			Transaction newtrs = new Transaction();
			newtrs.setAmountOfMoney(getlowest(ind, invest.getIssuedPerious(), balances));
			newtrs.setCategory("Invest");
			newtrs.setDescription(invest.getName());
			newtrs.setOwner(customerId);
			Date stdar = new Date(start.getTime());
			stdar.setDate(stdar.getDate()+ind);
			newtrs.setIssuedTime(stdar);
			Transaction newtrsincome = new Transaction();
			newtrsincome.setAmountOfMoney(newtrs.getAmountOfMoney() * (1+ invest.getProfitRate()/100));
			newtrsincome.setCategory("Business");
			newtrsincome.setDescription(invest.getName());
			newtrsincome.setOwner(customerId);
			Date sndar = new Date(start.getTime());
			sndar.setDate(sndar.getDate()+ind+invest.getIssuedPerious());
			newtrsincome.setIssuedTime(sndar);
			transactions.add(newtrs);
			transactions.add(newtrsincome);
			affectBalance(ind, invest.getIssuedPerious(), newtrs.getAmountOfMoney(), invest.getProfitRate(), balances);
			ind = findFirstPoint(invest, balances);
		}
		return transactions;
	}
	void affectBalance(int ind, int leng, double sub, double profix, List<Double> balances){
		for(int i= ind-1; i < ind+leng-1; i++) {
			balances.set(i, balances.get(i)-sub);
		}
		for(int i = ind+leng-1; i< balances.size(); i++) {
			balances.set(i, balances.get(i)+ sub*(profix/100));
		}
	}
	double getlowest(int ind, int leng, List<Double> balances){
		double low = Double.MAX_VALUE;
		for(int i= ind-1; i < ind+leng-1; i++) {
			if(balances.get(i) < low) {
				low = balances.get(i);
			}
		}
		return low;
	}
	private int findFirstPoint(Invest invest, List<Double> balances) {
		int i = 0;
		for(Double balance: balances) {
			i++;
			if(balance > invest.getLimitAmount()) {
				if(checkValid( invest.getLimitAmount(), invest.getIssuedPerious(), i,balances)) {
					return i;
				}
			}
		}
		return -1;
	}
	boolean checkValid(double sub, int leng, int ind, List<Double> balances){
		if(ind+ leng < balances.size()) {
			for(int i = ind; i< ind+leng; i++) {
				if(balances.get(i)-sub <0) {
					return false;
				}
			}
			return true;
		}
		
		return false;
	}
	private double getfinalMoney(List<Transaction> transactions){
		double finaValue = 0;
		for( Transaction transaction : transactions) {
			if(transaction.getCategory().equals("Salary")|| transaction.getCategory().equals("Business") || transaction.getCategory().equals("Reward")) {
				finaValue += transaction.getAmountOfMoney();
			}else {
				finaValue -= transaction.getAmountOfMoney();
			}
		}
		return finaValue;
	}
	
	

}

