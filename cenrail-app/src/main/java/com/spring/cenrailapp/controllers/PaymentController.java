package com.spring.cenrailapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
	public String getPaymentPage(@PathVariable String journeyId, Payment payment, Model model) {

		// block to check if nullability of journey.
		try {
			Journey journey = journeyService.getJourneyById(journeyId).orElse(null);

			// debug
			System.out.println("inside GET /payment");
			System.out.println("Price from journey session GET /payment: " + journey.getTicketPrice());

			double ticketPrice = journey.getTicketPrice();

			// passing the journey calculated attribute to the payment object to display it
			// in the view
			payment.setTotalFare(ticketPrice);

			model.addAttribute("payment", payment);
			// adding journey Id to the model to access it in the post method of payment
			model.addAttribute("journeyId", journeyId);

		} catch (Exception e) {
			System.out.println("Journey Object was found null, returning to /booking. Error: " + e.getMessage());
			return "redirect:/booking";
		}

		return "payment";
	}

	@PostMapping("/payment")
	public String processPayment(@RequestParam("journeyId") String journeyId, @Valid @ModelAttribute("payment") Payment payment,
			BindingResult result,
			Ticket paidTicket, HttpSession session, Model model) {

		// validate session
		if (!SessionUtil.checkSession(session)) {
			System.out.println("Passenger Object was found null, returning to /login-form");
			return "redirect:/login-form";
		}

		// debug errors
		//System.out.println("inside POST /payment");
		if (result.hasErrors()) {
			// get all field errors
			System.out.println("Errors in fields");
			for (FieldError error : result.getFieldErrors()) {
				System.out.println(String.format("field Rejected: %s", error.getField()));
				System.out.println(String.format("Value Rejected: %s", error.getRejectedValue()));
				System.out.println(String.format("Custom error field message: %s", error.getDefaultMessage()));
			}

			//returning back the journeyId value to the model
			model.addAttribute("journeyId", journeyId);

			return "payment";
		}

		// Saved journey in session
		Passenger loggedPassenger = (Passenger) session.getAttribute("loggedPassenger");

		Journey dbJourney = journeyService.getJourneyById(journeyId).orElse(null);
		Passenger dbPassenger = passengerService.getPassengerByuserName(loggedPassenger.getUserName());

		// ticket saved to repo
		Ticket dbTicket = ticketService.createTicket(paidTicket, dbJourney, dbPassenger, payment);

		paymentService.addPayment(dbTicket, payment);

		return "redirect:/ticket-confirmation/" + dbTicket.getTicketNo();

	} 
}
