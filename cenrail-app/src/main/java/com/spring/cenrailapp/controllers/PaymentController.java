package com.spring.cenrailapp.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.cenrailapp.models.Journey;
import com.spring.cenrailapp.models.Passenger;
import com.spring.cenrailapp.models.Payment;
import com.spring.cenrailapp.models.Ticket;
import com.spring.cenrailapp.services.JourneyService;
import com.spring.cenrailapp.services.PassengerService;
import com.spring.cenrailapp.services.PaymentService;
import com.spring.cenrailapp.services.TicketService;
import com.spring.cenrailapp.utils.SessionUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PaymentController {

    @Autowired
	private PaymentService paymentService;

    @Autowired
	private JourneyService journeyService;

    @Autowired
	private TicketService ticketService;

    @Autowired
	private PassengerService passengerService;

    @GetMapping("/payment/{journeyId}")
	public String getPaymentPage(@PathVariable String journeyId, Payment payment, HttpSession session, Model model) {

		if (!SessionUtil.checkSession(session)) {
			return "redirect:/login-form";
		}

		Passenger loggedPassenger = (Passenger) session.getAttribute("loggedPassenger");
		Passenger dbPassenger = passengerService.getPassengerByuserName(loggedPassenger.getUserName());

		Journey journey = journeyService.getJourneyById(journeyId).orElse(null);

		// journey must exist and belong to the logged-in passenger
		if (journey == null || !Objects.equals(journey.getPassengerId(), dbPassenger.getPassengerId())) {
			return "redirect:/booking";
		}

		double ticketPrice = journey.getTicketPrice();

		// passing the journey calculated attribute to the payment object to display it
		// in the view
		payment.setTotalFare(ticketPrice);

		model.addAttribute("payment", payment);
		// adding journey Id to the model to access it in the post method of payment
		model.addAttribute("journeyId", journeyId);

		return "payment";
	}

	@PostMapping("/payment")
	public String processPayment(@RequestParam("journeyId") String journeyId, @Valid @ModelAttribute("payment") Payment payment,
			BindingResult result,
			Ticket paidTicket, HttpSession session, Model model) {

		// validate session
		if (!SessionUtil.checkSession(session)) {
			return "redirect:/login-form";
		}

		Passenger loggedPassenger = (Passenger) session.getAttribute("loggedPassenger");
		Passenger dbPassenger = passengerService.getPassengerByuserName(loggedPassenger.getUserName());

		Journey dbJourney = journeyService.getJourneyById(journeyId).orElse(null);

		// journey must exist and belong to the logged-in passenger
		if (dbJourney == null || !Objects.equals(dbJourney.getPassengerId(), dbPassenger.getPassengerId())) {
			return "redirect:/booking";
		}

		if (result.hasErrors()) {
			//returning back the journeyId value to the model
			model.addAttribute("journeyId", journeyId);

			return "payment";
		}

		// ticket saved to repo
		Ticket dbTicket = ticketService.createTicket(paidTicket, dbJourney, dbPassenger, payment);

		paymentService.addPayment(dbTicket, payment);

		return "redirect:/ticket-confirmation/" + dbTicket.getTicketNo();

	}
}
