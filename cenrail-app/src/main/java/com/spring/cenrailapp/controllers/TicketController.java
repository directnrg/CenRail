package com.spring.cenrailapp.controllers;

import java.util.Objects;

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
			return "redirect:/login-form";
		}

		Passenger loggedPassenger = (Passenger) session.getAttribute("loggedPassenger");
		Passenger dbPassenger = passengerService.getPassengerByuserName(loggedPassenger.getUserName());

		Ticket dbTicket = ticketService.getTicketById(ticketId).orElse(null);

		// ticket must exist and belong to the logged-in passenger
		if (dbTicket == null || !Objects.equals(dbTicket.getPassengerId(), dbPassenger.getPassengerId())) {
			return "redirect:/booking";
		}

		model.addAttribute("passenger", dbPassenger);
		model.addAttribute("ticketJourney", dbTicket.getJourney());
		session.setAttribute("PaidTicket", dbTicket);

		return "ticket-confirmation";
	}
}
