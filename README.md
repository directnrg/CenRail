# CenRail Train Booking Application

CenRail is a train ticket booking application built using Spring Boot, Java, Maven, and Thymeleaf. It allows users to search for train trips, book tickets, make payments, and manage their profile information. This document provides instructions on how to interact with the app.

## Table of Contents

- [Login](#login)
- [Booking a Train](#booking-a-train)
- [Payment](#payment)
- [Viewing Ticket Information](#viewing-ticket-information)
- [Managing Your Profile](#managing-your-profile)

## Login

1. Launch the application and navigate to the login screen.
2. Enter your credentials (username and password) in the provided input fields.
3. If you enter incorrect credentials, an error message will be displayed. Re-enter the correct credentials and try again.
4. Upon successful login, you will be redirected to the booking page.

## Booking a Train

1. On the booking page, fill out the journey details:
   - Reservation class
   - Train name or number
   - Departure station
   - Arrival station
   - Travel date
   - Berth choice
   - Number of passengers
2. If any field is missing or contains invalid data, error messages will be displayed for the corresponding fields. Correct the errors and submit the form again.
3. After filling out all the required fields and selecting the appropriate options, agree to the terms and conditions by checking the checkbox.
4. Click the "Submit" button to proceed with the booking. You will be redirected to the payment page.

## Payment
> [!NOTE]
> this feature is not fully implemented to showcase how the app processes or not a ticket creation only if the card format is correct. A proper payment system can be integrated, but the current app assumes uses a simple validation just for demostration purposes.
1. On the payment page, enter your card details:
   - Cardholder name
   - Card number
   - Expiration date
   - CVV/CVC code
2. Verify the total amount based on your journey details and click the "Pay" button.
3. Upon successful payment, you will be redirected to the ticket information page.

## Viewing Ticket Information

1. On the ticket information page, you can view all the details of your generated ticket, including:
   - Train name or number
   - Departure and arrival stations
   - Travel date
   - Berth choice
   - Number of passengers
   - Total payment amount
2. You can save, print, or share the ticket information as needed.

## Managing Your Profile

1. From any page in the application, click on the "Profile" link in the navigation bar to access your profile page.
2. In the profile page, you can view your personal information and a list of all purchased tickets.
3. To update your profile information, click the "Update Profile" button.
4. Modify your personal information in the provided fields and click "Save Changes" to update your profile.

Happy travels with CenRail!
