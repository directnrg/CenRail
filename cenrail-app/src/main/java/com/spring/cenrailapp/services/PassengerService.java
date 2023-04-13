package com.spring.cenrailapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cenrailapp.models.Passenger;
import com.spring.cenrailapp.repositories.PassengerRepository;


@Service
public class PassengerService {
    
    @Autowired
    private PassengerRepository passengerRepository;
    
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }
    
    public Optional<Passenger> getPassengerById(String id) {
        return passengerRepository.findById(id);
    }

    public Passenger getPassengerByuserName(String userName) {
        return passengerRepository.findByuserName(userName);
    }
    
    public void createPassenger(Passenger passenger) {
        passengerRepository.save(passenger);
    }
    
    public void updatePassenger(Passenger passenger) {

        // validations to only update Passenger object if the value is different than
		// null

		passenger.setPassword(passenger.getPassword());
		passenger.setFirstName(passenger.getFirstName());
		passenger.setLastName(passenger.getLastName());
		passenger.setAddress(passenger.getAddress());
		passenger.setCity(passenger.getCity());
		passenger.setPostalCode(passenger.getPostalCode());
		passenger.setPhone(passenger.getPhone());
		passenger.setEmail(passenger.getEmail());
		passenger.setAge(passenger.getAge());

        passengerRepository.save(passenger);
    }

    public void deletePassengerById(String id) {
        passengerRepository.deleteById(id);
    }
}
