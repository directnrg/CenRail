package com.spring.cenrailapp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Document("passenger")
public class Passenger {

	// REGULAR PROPERTIES
	@Id
	private String passengerId;

	@NotBlank(message = "Username is mandatory")
	private String userName;

	@NotBlank(message = "Password is mandatory")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "Password must have 1 lower and 1 upper case letter, 1 digit, and 1 special character.")
	@Size(min = 8, message = "length must be 8 characters")
	private String password;

	@NotBlank(message = "First Name field is required")
	private String firstName;

	@NotBlank(message = "Last name field is required")
	private String lastName;

	@NotBlank(message = "Address field is required")
	private String address;

	@NotBlank(message = "City field is required")
	private String city;

	@NotBlank(message = "Postal Code field is required")
	@Pattern(regexp = "^[ABCEGHJ-NPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z][ -]?\\d[ABCEGHJ-NPRSTV-Z]\\d$", message = "Please insert a valid Canadian Postal Code")
	private String postalCode;

	@NotNull(message = "Age field is required")
	@Min(value=18, message="You have to be more than 18 to book a ticket")
	@Max(value=90, message="You have to be less than 90 to book a ticket")
	private int age;

	// property than can be null. not mandatory
	private String gender;
	
	@NotBlank(message = "Phone field is required")
	@Pattern(regexp="^(\\+\\d{1,2}\\s?)\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", message = "Please insert a valid phone number. The phone number must include the country code. Ex: +1999-999-9999")
	private String phone;
	
	@NotBlank(message = "Email field is required")
	@Pattern(regexp="^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Email is not of requested format (abc@gmail.com)")
	private String email;

	private boolean termsAcceptance;

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isTermsAcceptance() {
		return termsAcceptance;
	}

	public void setTermsAcceptance(boolean termsAcceptance) {
		this.termsAcceptance = termsAcceptance;
	}

	// Default constructor
	public Passenger() {
	}
}
