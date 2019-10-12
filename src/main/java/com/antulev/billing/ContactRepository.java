package com.antulev.billing;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact, String>{
	List<Contact> findByOwner(String owner);
}
