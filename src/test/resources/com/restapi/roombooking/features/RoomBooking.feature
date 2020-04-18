Feature: Add Room Booking API automation 

@TC01 @All
Scenario: Get availble room id using Room Booking API 
	Given User post authentication api for token 
	When User get authentication token from api 
	Then User send get request for getting room id 
	And User gets the room id in the response 
	
@TC02 @All
Scenario: Verify if user is able to book a room 
	Given User post authentication api for token 
	When User get authentication token from api 
	Then User send get request for getting room id 
	And User gets the room id in the response 
	And User generates pay load for room booking 
	And User send post request for room booking 
	And User verifies room is booked successfully 
	
@TC03 @All
Scenario: Verify if user is not able to book a room with firstname field size greater than 18 characters
	Given User post authentication api for token 
	When User get authentication token from api 
	Then User send get request for getting room id 
	And User gets the room id in the response 
	And User generates pay load for room booking with firstname field size greater than 18 characters
	And User gets bad request with 400 as response code for post request for room booking
	And User validate error message in response as expected for "firstname" field
	
@TC04 
Scenario: Verify if user is not able to book a room with checkin date in wrong format
	Given User post authentication api for token 
	When User get authentication token from api 
	Then User send get request for getting room id 
	And User gets the room id in the response 
	And User generates pay load for room booking with checkin date in wrong format
	And User gets bad request with 400 as response code for post request for room booking
	And User validate error message in response as expected for "checkin date" field