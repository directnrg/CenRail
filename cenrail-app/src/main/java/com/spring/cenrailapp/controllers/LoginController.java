package com.spring.cenrailapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

		if (result.hasErrors()) {
			return "login";
		}

		/*
		 * section to get the user credentials information and validate if the user name
		 * and password matches any records in the database.
		 */
		String passengerUserName = userCredentials.getUserNameInput();
		String passengerPassword = userCredentials.getPasswordInput();

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
			authMessage = "Wrong user name or password. Please try again.";
			redirectAttributes.addFlashAttribute("authMessage", authMessage);
			return "redirect:/login-form";
		}

		// getting the recently registered db Passenger
		loggedPassenger = passengerService.getPassengerByuserName(userCredentials.getUserNameInput());
		// session to store the data of the user and request it whenever necessary.
		session.setAttribute("loggedPassenger", loggedPassenger);

		// flash attribute consumed after 1 redirect to display proper message to user.
		redirectAttributes.addFlashAttribute("userId", loggedPassenger.getPassengerId());

		authMessage = "Welcome! you are Authenticated";
		redirectAttributes.addFlashAttribute("authMessage", authMessage);

		return "redirect:/booking"; //
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {

		// close session
		session.invalidate();

		return "redirect:/login-form";
	}
}
