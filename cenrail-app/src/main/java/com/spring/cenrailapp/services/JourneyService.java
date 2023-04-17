package com.spring.cenrailapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cenrailapp.models.Journey;
import com.spring.cenrailapp.repositories.JourneyRepository;


@Service
public class JourneyService {
    
    @Autowired
    private JourneyRepository journeyRepository;
    
    public List<Journey> getAllJourneys() {
        return journeyRepository.findAll();
    }
    
    public Optional<Journey> getJourneyById(String id) {
        return journeyRepository.findById(id);
    }
    
    public void createJourney(Journey journey) {

        // method to calculate fare based on registered class user selection.
		journey.calculateFare();
		// method to format the date in the desired format
		journey.formatDepartureDate(journey.getDepartureDate());
        

        journeyRepository.save(journey);
        System.out.println("saved journey: " + journey);
    }
    
    public void updateJourney(Journey journey) {
        journeyRepository.save(journey);
    }
    
    public void deleteJourneyById(String id) {
        journeyRepository.deleteById(id);
    }
}
