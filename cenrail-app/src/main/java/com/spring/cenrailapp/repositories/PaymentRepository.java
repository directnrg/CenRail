package com.spring.cenrailapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.cenrailapp.models.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String>{
    
}
