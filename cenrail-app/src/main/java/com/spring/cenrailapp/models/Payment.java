package com.spring.cenrailapp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Document("payment")
public class Payment {

    @Id
    private String paymentId;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Card number is mandatory")
    @Pattern(regexp="^\\d{16,}$", message="only digits accepted, Credit/debit cards must must have min 16 numbers.")
    private String cardNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/?([0-9]{2})$", message= "Please insert the Expiry date in the right format. MM/YY")
    @NotBlank(message = "Expiry is mandatory")
    private String expiry;

    @NotNull(message = "CVV is mandatory")
    private int cvv;

    //to store relationship with ticket
    private Ticket ticket;

    private String status;


    @NotNull
    private double totalFare;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
    
   
    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(double totalFare) {
        this.totalFare = totalFare;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public int getCvv() {
        return cvv;
    }
    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public Payment() {
    }
}