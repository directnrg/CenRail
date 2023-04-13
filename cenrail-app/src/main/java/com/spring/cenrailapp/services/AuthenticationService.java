package com.spring.cenrailapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cenrailapp.models.Passenger;
import com.spring.cenrailapp.repositories.PassengerRepository;


@Service
public class AuthenticationService {

	@Autowired
	private PassengerRepository passengerRepository;


	/**
	 * Receives two parameters: user name and password which are being passed from
	 * the passenger helper class UserCredentials to verify if the values exist in
	 * the database, accessing the passenger repository. if a match is found for
	 * both user name and password, then the property isUserAuthenticated gets
	 * updated accordingly. In case an empty value is passed, the passenger object
	 * will be empty. a try and catch block is executed to catch an exception. if
	 * exception happens,then the isUserAuthenticated property is set to false
	 * confirming that no user is logged in.
	 * 
	 * @throws NullPointerException
	 * @see com.spring.helpers.UserCredentials User Credential Class
	 * @see com.spring.repositories.PassengerRepository Passenger Repository Class
	 */

	public boolean authenticateUser(String userName, String password) {
		try {
	
			Passenger passenger = passengerRepository.findByUserNameAndPassword(userName, password);
			//debug
			// System.out.println("inside authenticateUser. Passenger username = " + passenger.getUserName()); 
			// System.out.println("inside authenticateUser. Passenger pass = " + passenger.getPassword()); 
			// System.out.println("inside authenticateUser. string username = " + userName); 
			// System.out.println("inside authenticateUser. string pass = " + password);

			if (passenger.getUserName().equals(userName) && passenger.getPassword().equals(password)) {

				System.out.println("return = true");
				return true;
				
			}
			// else
			//userCredentials.setUserAuthenticated(false);
			return false;

		} catch (NullPointerException e) {
			System.out.println("inside catch");
			System.out.println("User non existant in database. Error: " + e.getMessage());
			//userCredentials.setUserAuthenticated(false);
			System.out.println("return = false");
			return false;
		} 
	}
}
