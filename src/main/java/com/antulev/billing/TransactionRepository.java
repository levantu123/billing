package com.antulev.billing;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

public interface TransactionRepository extends MongoRepository<Transaction, String>{
	List<Transaction> findByissuedTimeBetween(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@Param("start")Date start, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@Param("end") Date end);
}
