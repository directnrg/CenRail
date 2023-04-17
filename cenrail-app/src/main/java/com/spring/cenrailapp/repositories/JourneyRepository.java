package com.spring.cenrailapp.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.cenrailapp.models.Journey;

public interface JourneyRepository extends MongoRepository <Journey, String>{
    
}




