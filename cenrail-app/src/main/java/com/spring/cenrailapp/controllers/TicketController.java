package com.spring.cenrailapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.spring.cenrailapp.models.Passenger;
import com.spring.cenrailapp.models.Ticket;
import com.spring.cenrailapp.services.PassengerService;
import com.spring.cenrailapp.services.TicketService;
import com.spring.cenrailapp.utils.SessionUtil;

import jakarta.servlet.http.HttpSession;

@Controller
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private PassengerService passengerService;

	@GetMapping("/ticket-confirmation/{ticketId}")
	public String getConfirmationPage(@PathVariable String ticketId,
			HttpSession session, Model model) {
		if (!SessionUtil.checkSession(session)) {
			System.out.println("Passenger Object was found null, returning to /login-form");
			return "redirect:/login-form";
		}

		Passenger loggedPassenger = (Passenger) session.getAttribute("loggedPassenger");
		Passenger dbPassenger = passengerService.getPassengerByuserName(loggedPassenger.getUserName());

		model.addAttribute("passenger", dbPassenger);

		// handling nullability of journey
		try {
			Ticket dbTicket = ticketService.getTicketById(ticketId).orElse(null);

			System.out.println("Journey object inside Ticket at - /ticket-confirmation: " + dbTicket.getJourney());
			model.addAttribute("ticketJourney", dbTicket.getJourney());

			session.setAttribute("PaidTicket", dbTicket);
		} catch (Exception e) {
			System.out.println("Journey object inside Ticket was found null. returning to /booking");
			return "redirect:/booking";
		}

		return "ticket-confirmation";
	}
}
