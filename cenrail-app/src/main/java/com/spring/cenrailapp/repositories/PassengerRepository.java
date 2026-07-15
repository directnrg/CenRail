package com.spring.cenrailapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.cenrailapp.models.Passenger;

public interface PassengerRepository extends MongoRepository <Passenger, String>{

	Passenger findByuserName(String userName);
}
