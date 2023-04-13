package com.spring.cenrailapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.cenrailapp.dtos.UserCredentials;
import com.spring.cenrailapp.models.Passenger;
import com.spring.cenrailapp.services.AuthenticationService;
import com.spring.cenrailapp.services.PassengerService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {

    @Autowired
	private AuthenticationService authService;

    @Autowired
	private PassengerService passengerService;

	public boolean checkSession(HttpSession session) {
		Passenger loggedPassenger = (Passenger) session.getAttribute("loggedPassenger");
		return loggedPassenger != null;
	}

    // mapping to booking
	@GetMapping("/")
	public String getMainPage() {

		return "redirect:/booking";
	}

	@GetMapping("/login-form")
	public String getUserLoginInfo(UserCredentials userCredentials) {

		return "login";
	}

	@PostMapping("/login-form")
	public String getBookingPage(@Valid @ModelAttribute UserCredentials userCredentials, BindingResult result,
			Passenger loggedPassenger, RedirectAttributes redirectAttributes, HttpSession session, Model model) {
		// for debugging purposes if errors exist
		if (result.hasErrors()) {
			// get all field errors
			System.out.println("Errors in fields");
			for (FieldError error : result.getFieldErrors()) {
				System.out.println(String.format("field Rejected: %s", error.getField()));
				System.out.println(String.format("Value Rejected: %s", error.getRejectedValue()));
				System.out.println(String.format("Custom error field message: %s", error.getDefaultMessage()));
			}

			return "login";
		}

		/*
		 * section to get the user credentials information and validate if the user name
		 * and password matches any records in the database.
		 */
		String passengerUserName = userCredentials.getUserNameInput();
		String passengerPassword = userCredentials.getPasswordInput();
		System.out.println("value retrieved from view for username and pass"); // debug
		System.out.println(passengerUserName + "/" + passengerPassword);

		// method to authenticate if the user exist in the database or not and store
		// return - true or false
		// if user exist in database, return true, if not false.
		boolean isAuthenticated = authService.authenticateUser(passengerUserName, passengerPassword);

		// update user credentials property.
		userCredentials.setUserAuthenticated(isAuthenticated);
		// init variable - friendly message provided depending of authenticated state.
		String authMessage;

		// get property value to check if user is authenticated.
		if (!userCredentials.isUserAuthenticated()) {
			// debug
			System.out.println("inside if isAuthenticated");
			System.out.println("User Not Authenticated Correctly / passenger inserted userName: " + passengerUserName);
			System.out.println("User Not Authenticated Correctly / passenger inserted password: " + passengerPassword);

			authMessage = "Wrong user name or password. Please try again.";
			redirectAttributes.addFlashAttribute("authMessage", authMessage);
			return "redirect:/login-form";
		}

		//debug
		// else - user logged in section
		System.out.println("User Authenticated / passenger inserted userName: " + passengerUserName);
		System.out.println("User Authenticated / passenger inserted password: " + passengerPassword);

		// getting the recently registered db Passenger
		loggedPassenger = passengerService.getPassengerByuserName(userCredentials.getUserNameInput());
		System.out.println("logged passenger object: " + loggedPassenger);
		// session to store the data of the user and request it whenever necessary.
		session.setAttribute("loggedPassenger", loggedPassenger);

		// flash attribute consumed after 1 redirect to display proper message to user.
		redirectAttributes.addFlashAttribute("userId", loggedPassenger.getPassengerId());

		System.out.println("passenger id: " + loggedPassenger.getPassengerId());
		// System.out.println("logged passenger object ID: " +
		authMessage = "Welcome! you are Authenticated";
		redirectAttributes.addFlashAttribute("authMessage", authMessage);

		return "redirect:/booking"; // 
	}
    
}
