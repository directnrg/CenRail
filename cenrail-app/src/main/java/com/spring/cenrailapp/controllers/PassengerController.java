package com.spring.cenrailapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.cenrailapp.models.Passenger;
import com.spring.cenrailapp.services.PassengerService;
import com.spring.cenrailapp.utils.SessionUtil;

import jakarta.servlet.http.HttpSession;

@Controller
public class PassengerController {
    @Autowired
	private PassengerService passengerService;

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

		return "profile";
	}
}
