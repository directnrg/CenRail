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

import com.spring.cenrailapp.models.Passenger;
import com.spring.cenrailapp.services.PassengerService;

import jakarta.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
	private PassengerService passengerService;

	@GetMapping("/register")
	public String getRegisterPage(Passenger passenger, Model model) {

		model.addAttribute("registeredPassenger", passenger);
		return "register";
	}

	@PostMapping("/register")
	public String addNewUser(@Valid @ModelAttribute("registeredPassenger") Passenger passenger, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {
		// for debugging purposes if errors exist
		if (result.hasErrors()) {
			// get all field errors
			System.out.println("Errors in fields");
			for (FieldError error : result.getFieldErrors()) {
				System.out.println(String.format("field Rejected: %s", error.getField()));
				System.out.println(String.format("Value Rejected: %s", error.getRejectedValue()));
				System.out.println(String.format("Custom error field message: %s", error.getDefaultMessage()));
			}

			// to display lists again an passenger inserted information after validation
			model.addAttribute("registeredPassenger", passenger);

			return "register";
		}

		// User Registered Successfully. User needs to login with registered data to
		// access platform
		// friendly message is provided after registering when redirected to login
		String regMessage = "Thanks For Registering to CenRail. Please Login";

		// added to database
		passengerService.createPassenger(passenger);

		System.out.println("getRegisterPage passenger: " + passenger);
		redirectAttributes.addFlashAttribute("regMessage", regMessage);
		return "redirect:/login-form";
	}
}