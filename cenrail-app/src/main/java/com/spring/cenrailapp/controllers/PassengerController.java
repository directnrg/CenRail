package com.spring.cenrailapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.cenrailapp.dtos.ProfileUpdateDTO;
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
			return "redirect:/login-form";
		}

		// getting the logged passenger object from the session
		Passenger loggedPassenger = (Passenger) session.getAttribute("loggedPassenger");

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
			return "redirect:/login-form";
		}

		// Session passenger / registered passenger object
		Passenger loggedPassenger = (Passenger) session.getAttribute("loggedPassenger");

		// db passenger
		Passenger dbPassenger = passengerService.getPassengerByuserName(loggedPassenger.getUserName());

		model.addAttribute("userName", dbPassenger.getUserName());
		model.addAttribute("passenger", ProfileUpdateDTO.fromPassenger(dbPassenger));

		return "update-profile";
	}

    @PostMapping("/update-profile")
	public String updateProfile(@Valid @ModelAttribute("passenger") ProfileUpdateDTO submittedChanges, BindingResult result,
			HttpSession session, Model model) {
		// validate session
		if (!SessionUtil.checkSession(session)) {
			return "redirect:/login-form";
		}

		Passenger loggedPassenger = (Passenger) session.getAttribute("loggedPassenger");
		Passenger dbPassenger = passengerService.getPassengerByuserName(loggedPassenger.getUserName());

		if (result.hasErrors()) {
			model.addAttribute("userName", dbPassenger.getUserName());
			return "update-profile";
		}

		// update passenger, preserving the stored password hash
		passengerService.updatePassenger(dbPassenger, submittedChanges);

		return "redirect:/profile";
	}
}
