package com.spring.cenrailapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.cenrailapp.utils.SessionUtil;

import jakarta.servlet.http.HttpSession;

@Controller
public class BookingController {
    
    // load booking page
	@GetMapping("/booking")
	public String getBookingPage(HttpSession session) {
		// validate session
		if (!SessionUtil.checkSession(session)) {
			System.out.println("Passenger Object was found null, returning to /login-form");
			return "redirect:/login-form";
		}

		return "booking";

	}
}
