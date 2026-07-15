package com.spring.cenrailapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cenrailapp.models.Payment;
import com.spring.cenrailapp.models.Ticket;
import com.spring.cenrailapp.repositories.PaymentRepository;
import com.spring.cenrailapp.repositories.TicketRepository;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired TicketRepository ticketRepository;

    public Ticket findTicketByPaymentId(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        Ticket dbTicket = ticketRepository.findById(payment.getTicket().getTicketNo()).orElse(null);

        return dbTicket;
    }

    public Payment addPayment(Ticket ticket, Payment payment) {

        payment.setStatus("Approved");
        payment.setTicket(ticket);

        // Only the last 4 digits of the card are ever persisted; the full
        // PAN and CVV are validated in-memory and discarded.
        String cardNumber = payment.getCardNumber();
        if (cardNumber != null && cardNumber.length() >= 4) {
            payment.setCardLast4(cardNumber.substring(cardNumber.length() - 4));
        }

        return paymentRepository.save(payment);
    }
    
}
