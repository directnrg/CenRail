package com.spring.cenrailapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.cenrailapp.dtos.ProfileUpdateDTO;
import com.spring.cenrailapp.models.Passenger;
import com.spring.cenrailapp.repositories.PassengerRepository;


@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        passenger.setPassword(passwordEncoder.encode(passenger.getPassword()));
        passengerRepository.save(passenger);
    }

    /**
     * Applies editable profile fields from the submitted form onto the
     * persisted passenger. The password hash is intentionally left alone -
     * it is never sent to or read back from the update-profile form.
     */
    public void updatePassenger(Passenger dbPassenger, ProfileUpdateDTO submittedChanges) {

    	dbPassenger.setFirstName(submittedChanges.getFirstName());
		dbPassenger.setLastName(submittedChanges.getLastName());
		dbPassenger.setAddress(submittedChanges.getAddress());
		dbPassenger.setCity(submittedChanges.getCity());
		dbPassenger.setPostalCode(submittedChanges.getPostalCode());
		dbPassenger.setPhone(submittedChanges.getPhone());
		dbPassenger.setEmail(submittedChanges.getEmail());
		dbPassenger.setAge(submittedChanges.getAge());

        passengerRepository.save(dbPassenger);
    }

    public void deletePassengerById(String id) {
        passengerRepository.deleteById(id);
    }

    public void addPassengerTerms(Passenger passenger, boolean termsAcceptance){
        passenger.setTermsAcceptance(termsAcceptance);
        passengerRepository.save(passenger);
        }
}
