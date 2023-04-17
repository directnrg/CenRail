package com.spring.cenrailapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cenrailapp.models.Journey;
import com.spring.cenrailapp.models.Passenger;
import com.spring.cenrailapp.models.Payment;
import com.spring.cenrailapp.models.Ticket;
import com.spring.cenrailapp.repositories.TicketRepository;

import jakarta.validation.Valid;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(String id) {
        return ticketRepository.findById(id);
    }
   
    public Ticket getTicketbyPaymentId(String paymentId) {
        return ticketRepository.findByPaymentId(paymentId);
    }

    public Ticket createTicket(Ticket ticket, Journey journey, Passenger dbPassenger, @Valid Payment payment) {
    
		// method to format the date in the desired format
		journey.formatDepartureDate(journey.getDepartureDate());

        System.out.println("journey inside createTicket: " + journey);

        ticket.setAmountPaid(payment.getTotalFare());;
        ticket.setPaymentId(payment.getPaymentId());
        ticket.setJourney(journey);

        //mock statement- always approved after post of payment. Is always Approved for now - demo
        ticket.setStatus("Paid");

        ticket.setPassengerId(dbPassenger.getPassengerId());
        return ticketRepository.save(ticket);
    }

    public void deleteTicketById(String id) {
        ticketRepository.deleteById(id);
    }
}
