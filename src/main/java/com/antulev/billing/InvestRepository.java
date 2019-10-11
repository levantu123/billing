package com.antulev.billing;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvestRepository extends MongoRepository<Invest, String>{
	List<Invest> findByOwner(String owner);
}
