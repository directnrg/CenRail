package com.spring.cenrailapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.cenrailapp.dtos.TermsAcceptanceDTO;
import com.spring.cenrailapp.models.Journey;
import com.spring.cenrailapp.models.Passenger;
import com.spring.cenrailapp.services.JourneyService;
import com.spring.cenrailapp.services.PassengerService;
import com.spring.cenrailapp.utils.SessionUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class BookingController {

	@Autowired
	private JourneyService journeyService;

	@Autowired
	private PassengerService passengerService;

   	// load booking page
	@GetMapping("/booking")
	public String getBookingPage(Journey journey, TermsAcceptanceDTO termsAcceptance, HttpSession session,
			Model model) {
		// validate session
		if (!SessionUtil.checkSession(session)) {
			return "redirect:/login-form";
		}

		model.addAttribute("TermsAcceptanceDTO", termsAcceptance);
		model.addAttribute("reservationTypes", journey.getReservationTypes().keySet());
		model.addAttribute("trainsList", journey.getTrainsList());
		model.addAttribute("citiesList", journey.getCitiesList());
		model.addAttribute("berthsList", journey.getBerthsList());

		return "booking";

	}

	@PostMapping("/booking")
	public String showPayment(@Valid @ModelAttribute Journey journey, BindingResult result,
			@Valid @ModelAttribute("TermsAcceptanceDTO") TermsAcceptanceDTO termsAcceptanceDTO,
			BindingResult termsAcceptanceResult, HttpSession session,
			Model model) {
		// validate session
		if (!SessionUtil.checkSession(session)) {
			return "redirect:/login-form";
		}

		// VALIDATION if errors exist for Journey
		if (result.hasErrors() || termsAcceptanceResult.hasErrors()) {
			// repopulate lists on reload
			model.addAttribute("reservationTypes", journey.getReservationTypes().keySet());
			model.addAttribute("trainsList", journey.getTrainsList());
			model.addAttribute("citiesList", journey.getCitiesList());
			model.addAttribute("berthsList", journey.getBerthsList());
			return "booking";
		}

		// getting the logged passenger object from the session
		Passenger loggedPassenger = (Passenger) session.getAttribute("loggedPassenger");
		Passenger dbPassenger = passengerService.getPassengerByuserName(loggedPassenger.getUserName());

		boolean termsAcceptance = termsAcceptanceDTO.isTermsAcceptance();
		// add terms and conditions to db passenger
		passengerService.addPassengerTerms(dbPassenger, termsAcceptance);

		// tie the journey/booking to the passenger who created it so the
		// payment and ticket-confirmation pages can verify ownership.
		journey.setPassengerId(dbPassenger.getPassengerId());

		journeyService.createJourney(journey);
		String id = journey.getTrainCode();

		return "redirect:/payment/" + id;
	}
}
