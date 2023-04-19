package com.spring.cenrailapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.cenrailapp.models.Passenger;
import com.spring.cenrailapp.services.PassengerService;
import com.spring.cenrailapp.services.TicketService;
import com.spring.cenrailapp.utils.SessionUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PassengerController {
    @Autowired
	private PassengerService passengerService;

	@Autowired
	private TicketService ticketService;

	@GetMapping("/profile")
	public String getProfilePage(HttpSession session,
			Model model) {
		if (!SessionUtil.checkSession(session)) {
			System.out.println("Passenger Object was found null, returning to /login-form");
			return "redirect:/login-form";
		}

		// getting the logged passenger object from the session
		Passenger loggedPassenger = (Passenger) session.getAttribute("loggedPassenger");
		// debug
		System.out.println("passenger Id inside  GET /profile: " + loggedPassenger.getPassengerId());

		// db passenger
		Passenger dbPassenger = passengerService.getPassengerByuserName(loggedPassenger.getUserName());
		// add passenger to model
		model.addAttribute("passenger", dbPassenger);

		model.addAttribute("tickets", ticketService.getTicketsByPassengerId(dbPassenger.getPassengerId()));

		return "profile";
	}

    @GetMapping("/update-profile")
	public String showUpdatePage(HttpSession session,
			Model model) {
		if (!SessionUtil.checkSession(session)) {
			System.out.println("Passenger Object was found null, returning to /login-form");
			return "redirect:/login-form";
		}

		// Session passenger / registered passenger object
		Passenger loggedPassenger = (Passenger) session.getAttribute("loggedPassenger");

		// db passenger
		Passenger dbPassenger = passengerService.getPassengerByuserName(loggedPassenger.getUserName());

		// debug
		System.out.println("loggedPassenger Object at GET /update-profile: " + loggedPassenger);
		model.addAttribute("passenger", dbPassenger);

		return "update-profile";
	}

    @PostMapping("/update-profile")
	public String updateProfile(@Valid @ModelAttribute("passenger") Passenger passenger, BindingResult result,
			HttpSession session) {
		// validate session
		if (!SessionUtil.checkSession(session)) {
			System.out.println("Passenger Object was found null, returning to /login-form");
			return "redirect:/login-form";
		}

		// debug errors
		System.out.println("inside POST /update-profile");
		if (result.hasErrors()) {
			// get all field errors
			System.out.println("Errors in fields");
			for (FieldError error : result.getFieldErrors()) {
				System.out.println(String.format("field Rejected: %s", error.getField()));
				System.out.println(String.format("Value Rejected: %s", error.getRejectedValue()));
				System.out.println(String.format("Custom error field message: %s", error.getDefaultMessage()));
			}
			return "update-profile";
		}

		// persistant session passenger / logged passenger
		Passenger loggedPassenger = (Passenger) session.getAttribute("loggedPassenger");

		// db Passenger
		Passenger dbPassenger = passengerService.getPassengerByuserName(loggedPassenger.getUserName());
		System.out.println("dbPassenger Object at POST /update-profile: " + dbPassenger);
		System.out.println("sessionPassenger Object at POST /update-profile: " + loggedPassenger);

		// update passenger
        passenger.setPassengerId(dbPassenger.getPassengerId());
		passengerService.updatePassenger(passenger);

		return "redirect:/profile";
	}
}
