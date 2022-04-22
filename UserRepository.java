package com.citi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.citi.dto.User;

public interface UserRepository  extends MongoRepository<User, Integer>{

	public Optional<User> findByuserid(String userid);

	

}
