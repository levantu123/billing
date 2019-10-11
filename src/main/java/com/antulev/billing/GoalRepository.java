package com.antulev.billing;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GoalRepository extends MongoRepository<Goal, String>{
	List<Goal> findByOwner(String owner);
}
