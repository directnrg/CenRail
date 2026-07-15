package com.spring.cenrailapp.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Editable subset of a Passenger's profile. Kept separate from the
 * Passenger document so the update-profile form can never read or
 * write the password hash.
 */
public class ProfileUpdateDTO {

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
	@Min(value = 18, message = "You have to be more than 18 to book a ticket")
	@Max(value = 90, message = "You have to be less than 90 to book a ticket")
	private int age;

	@NotBlank(message = "Phone field is required")
	@Pattern(regexp = "^(\\+\\d{1,2}\\s?)\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", message = "Please insert a valid phone number. The phone number must include the country code. Ex: +1999-999-9999")
	private String phone;

	@NotBlank(message = "Email field is required")
	@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Email is not of requested format (abc@gmail.com)")
	private String email;

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

	public ProfileUpdateDTO() {
	}

	public static ProfileUpdateDTO fromPassenger(com.spring.cenrailapp.models.Passenger passenger) {
		ProfileUpdateDTO dto = new ProfileUpdateDTO();
		dto.setFirstName(passenger.getFirstName());
		dto.setLastName(passenger.getLastName());
		dto.setAddress(passenger.getAddress());
		dto.setCity(passenger.getCity());
		dto.setPostalCode(passenger.getPostalCode());
		dto.setAge(passenger.getAge());
		dto.setPhone(passenger.getPhone());
		dto.setEmail(passenger.getEmail());
		return dto;
	}
}
