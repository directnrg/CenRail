package com.spring.cenrailapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.cenrailapp.models.Passenger;
import com.spring.cenrailapp.repositories.PassengerRepository;


@Service
public class AuthenticationService {

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Looks up the passenger by user name and checks the supplied password
	 * against the stored BCrypt hash. Returns false for both a missing user
	 * and a wrong password so the caller can't distinguish the two.
	 *
	 * @see com.spring.cenrailapp.repositories.PassengerRepository
	 */
	public boolean authenticateUser(String userName, String password) {
		Passenger passenger = passengerRepository.findByuserName(userName);

		if (passenger == null || password == null) {
			return false;
		}

		return passwordEncoder.matches(password, passenger.getPassword());
	}
}
