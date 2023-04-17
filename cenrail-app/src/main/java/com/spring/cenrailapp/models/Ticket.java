package com.spring.cenrailapp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

@Document("ticket")
public class Ticket {

	@Id
	private String ticketNo;

	@NotNull
	private Journey journey;

	@NotNull
	private String passengerId;

	@NotNull
	private String paymentId;

	
	@NotNull
	private String status;

	@NotNull
	private double amountPaid;
	
	
	public String getTicketNo() {
		return ticketNo;
	}



	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}


	public Journey getJourney() {
		return journey;
	}



	public void setJourney(Journey journey) {
		this.journey = journey;
	}



	public String getPassengerId() {
		return passengerId;
	}



	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}
	
	public String getPaymentId() {
		return paymentId;
	}


	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}


	public double getAmountPaid() {
		return amountPaid;
	}



	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}



	public Ticket() {
	}



	
}
