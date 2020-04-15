Feature: Add Place API automation
 
 @TC01
Scenario: Verify if place is being added using Add Place API
 Given User add place payload
 When User calls add place api with post request
 Then User gets success status code
 And User gets the place_id response

  @TC02
 Scenario: Verify if place is not being added using Add Place API
 Given User add place payload
 When User calls add place api with post request
 Then User gets success status code
# And User gets the place_id response