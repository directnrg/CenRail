package com.spring.cenrailapp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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

    // Raw PAN is only used to validate the form and derive cardLast4 below -
    // it is never persisted.
    @Transient
    @NotNull(message = "Card number is mandatory")
    @Pattern(regexp="^\\d{16,}$", message="only digits accepted, Credit/debit cards must must have min 16 numbers.")
    private String cardNumber;

    // Last 4 digits only, safe to store, used to render the receipt.
    private String cardLast4;

    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/?([0-9]{2})$", message= "Please insert the Expiry date in the right format. MM/YY")
    @NotBlank(message = "Expiry is mandatory")
    private String expiry;

    // CVV must never be persisted, per PCI-DSS guidance - even for a mock flow.
    @Transient
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

    public String getCardLast4() {
        return cardLast4;
    }

    public void setCardLast4(String cardLast4) {
        this.cardLast4 = cardLast4;
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