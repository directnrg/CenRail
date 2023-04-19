package com.spring.cenrailapp.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document("journey")
public class Journey {

	// Constants
	// Business class related constants

	@Transient
	final private String BUSINESS_CLASS = "Business";
	@Transient
	final private int BUSINESS_PRICE = 25;

	// Economy class related constants
	@Transient
	final private String ECONOMY_CLASS = "Economy";
	@Transient
	final private int ECONOMY_PRICE = 18;

	// Sleeper class related constants
	@Transient
	final private String SLEEPERPLUS_CLASS = "Sleeper Plus";
	@Transient
	final private int SLEEPER_PRICE = 30;

	// Prestige class related constants
	@Transient
	final private String PRESTIGE_CLASS = "Prestige";
	@Transient
	final private int PRESTIGE_PRICE = 40;

	// List properties for predefined values

	@Transient
	private Map<String, Integer> reservationTypes;

	@Transient
	private List<String> berthsList;

	@Transient
	private List<String> citiesList;

	@Transient
	private List<String> trainsList;

	@Transient
	private List<String> gendersList;

	
	// Properties
	@Id
	private String trainCode;

	@NotBlank(message = "Reservation Class field is required")
	private String reservationClass;

	@NotBlank(message = "Train Name field is required")
	private String trainName;

	@NotNull(message = "This field is required") 
	@FutureOrPresent(message = "We have not invented a time machine yet!. Only present or future date is allowed") 
	private LocalDateTime departureDate;

	@NotBlank(message = "Berth choice field is required")
	private String berthChoice;

	@NotBlank(message = "The place you are traveling from is required")
	private String journeyFrom;

	@NotBlank(message = "Destination field is required")
	private String journeyTo;

	private double ticketPrice;

	@NotNull(message = "Number of Passangers field is required")
	@Min(value = 1, message= "value cannot be negative or 0")
	@Max(value = 10, message= "Maximum amount ticket purchase online is 10")
	private int numPassengers;

	private String formattedDate;

	// Getters and Setters
	public String getTrainCode() {
		return trainCode;
	}

	public void setTrainCode(String trainCode) {
		this.trainCode = trainCode;
	}

	public String getReservationClass() {
		return reservationClass;
	}

	public void setReservationClass(String reservationClass) {
		this.reservationClass = reservationClass;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public LocalDateTime getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDateTime departureDate) {
		this.departureDate = departureDate;
	}

	public String getBerthChoice() {
		return berthChoice;
	}

	public void setBerthChoice(String berthChoice) {
		this.berthChoice = berthChoice;
	}

	public String getJourneyFrom() {
		return journeyFrom;
	}

	public void setJourneyFrom(String journeyFrom) {
		this.journeyFrom = journeyFrom;
	}

	public String getJourneyTo() {
		return journeyTo;
	}

	public void setJourneyTo(String journeyTo) {
		this.journeyTo = journeyTo;
	}

	public int getNumPassengers() {
		return numPassengers;
	}

	public void setNumPassengers(int numPassengers) {
		this.numPassengers = numPassengers;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double totalFare) {
		this.ticketPrice = totalFare;
	}

	// Getters and setters TRANSIENT PROPERTIES
	@Transient
	public Map<String, Integer> getReservationTypes() {
		return reservationTypes;
	}
	@Transient
	public void setReservationTypes(Map<String, Integer> reservationTypes) {
		this.reservationTypes = reservationTypes;
	}
	@Transient
	public List<String> getBerthsList() {
		return berthsList;
	}
	@Transient
	public void setBerthsList(List<String> berthsList) {
		this.berthsList = berthsList;
	}
	@Transient
	public List<String> getCitiesList() {
		return citiesList;
	}
	@Transient
	public void setCitiesList(List<String> citiesList) {
		this.citiesList = citiesList;
	}
	@Transient
	public List<String> getTrainsList() {
		return trainsList;
	}
	@Transient
	public void setTrainsList(List<String> trainsList) {
		this.trainsList = trainsList;
	}
	
	public String getFormattedDate() {
		return formattedDate;
	}
	
	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}

	// default constructor
	public Journey() {
		populateFields();
	}

	// set predefined values to each list.
	public void populateFields() {
		// populating available reservation types
		reservationTypes = new HashMap<>();
		reservationTypes.put(BUSINESS_CLASS, BUSINESS_PRICE);
		reservationTypes.put(ECONOMY_CLASS, ECONOMY_PRICE);
		reservationTypes.put(SLEEPERPLUS_CLASS, SLEEPER_PRICE);
		reservationTypes.put(PRESTIGE_CLASS, PRESTIGE_PRICE);

		// populating available trains
		trainsList = new ArrayList<>();
		trainsList.add("Ocean");
		trainsList.add("Alouette");
		trainsList.add("600");
		trainsList.add("604");
		trainsList.add("606");

		// populating available berths
		berthsList = new ArrayList<>();
		berthsList.add("Lower");
		berthsList.add("Middle");
		berthsList.add("Upper");

		citiesList = new ArrayList<>();
		citiesList.add("Ottowa");
		citiesList.add("Montreal");
		citiesList.add("London");
		citiesList.add("Kitchener");
		citiesList.add("Vancouver");
		citiesList.add("Halifax");
	}

	@Override
	public String toString() {
		return "Journey [reservationTypes=" + reservationTypes + ", berthsList=" + berthsList + ", citiesList="
				+ citiesList + ", trainsList=" + trainsList + ", gendersList=" + gendersList + ", trainCode=" + trainCode + ", reservationClass=" + reservationClass
				+ ", trainName=" + trainName + ", departureDate=" + departureDate + ", formattedDate=" + formattedDate +", berthChoice=" + berthChoice
				+ ", journeyFrom=" + journeyFrom + ", journeyTo=" + journeyTo + ", numPassengers=" + numPassengers
				+ ", ticketPrice=" + ticketPrice + "]";
	}

	/**
	 * it doesnt have a return and takes the value of the getReservationClass
	 * property
	 * to get the price value depending on the class selected.
	 */

	public void calculateFare() {
		double selectedClassPrice = 0;

		switch (this.getReservationClass()) {
			case BUSINESS_CLASS:
				selectedClassPrice = reservationTypes.get(BUSINESS_CLASS);
				break;
			case ECONOMY_CLASS:
				selectedClassPrice = reservationTypes.get(ECONOMY_CLASS);
				break;
			case SLEEPERPLUS_CLASS:
				selectedClassPrice = reservationTypes.get(SLEEPERPLUS_CLASS);
				break;
			case PRESTIGE_CLASS:
				selectedClassPrice = reservationTypes.get(PRESTIGE_CLASS);
				break;
			default:

				break;
		}
		double totalFare = this.numPassengers * selectedClassPrice;

		this.setTicketPrice(totalFare);
	}

	//method to format date in the desired pattern
	public void formatDepartureDate(LocalDateTime date) {
		String pattern = "MM/dd/yyyy";		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		String formattedDate = formatter.format(date);

		System.out.println("new format date of Departure set to: " + formattedDate);
		this.formattedDate = formattedDate;
	}
}