package com.spring.cenrailapp.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.cenrailapp.models.Ticket;

public interface TicketRepository extends MongoRepository <Ticket, String>{
    List<Ticket> findAllByPassengerId(String passengerId);
    Ticket findByPaymentId(String paymentId);

}

