package com.restapi.roombooking.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.api.automation.utils.FileOperations;
import com.api.automation.utils.JavaUtils;
import com.api.automation.utils.Reporter;
import com.api.automation.utils.RestAssuredUtils;
import com.restapi.addplace.pojo.Place;
import com.restapi.roombooking.pojo.BookingDates;
import com.restapi.roombooking.pojo.Room;

public class StepDefinition {

	String url = "";
	public static Logger log = Logger.getLogger(StepDefinition.class.getName());
	public String scenarioName;
	public String projectName;
	public HashMap<String,String> getData = new HashMap<String,String>();
	Place addPlaceBody = new Place();
	RestAssuredUtils raUtils = new RestAssuredUtils();
	Response response;
	String token = "";
	Room room = new Room();
	String room_id;
	JavaUtils jUtils = new JavaUtils(); 
	
	
	@Before
	public void initialize(Scenario scenarName) throws Exception {
		FileOperations.loadProperties();
		projectName = FileOperations.getProperty("MODULE_NAME");
		scenarioName=scenarName.getName();
		url = FileOperations.getProperty(projectName+"_URL");
		
		//Initializing the Extner Report object
		Reporter.intializeReport();
		
		//Initializing the Extent Test for each TC.
		Reporter.createExtentTest(scenarioName);
		
		//Get Data from Excel
		getData = FileOperations.getDataFromExcel(scenarioName,projectName);
		
		log.info("Starting execution for scenario : "+scenarioName);
	}
	
	
	@After
	public void finalize(){
		Reporter.flushReports();
	}
	
	@Given("User post authentication api for token")
	public void user_post_authentication_api_for_token() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		raUtils.setBaseURI(FileOperations.getProperty(projectName+"_URL"));
		String body = createJsonForToken();
		RestAssuredUtils.requestSpecification.body(body);
		response = raUtils.postRequest(getData, "/auth/login");
		raUtils.validateStatusCode(response, 200);
		Reporter.writeLog("POST request sent successfully for generating token", true, false);
		Reporter.setExtentNodeNull();
	}
	
	@When("User get authentication token from api")
	public void user_get_authentication_token_from_api() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		token = raUtils.getValueFromJSONResponse(response, "token","STRING");
		if(!token.equals("")){
			Reporter.writeLog("Token is generated succesully as '"+token+"'", true, false);
		}else{
			Reporter.writeLog("Token is not generated", false, false);
		}
		Reporter.setExtentNodeNull();
	}

	@Then("User send get request for getting room id")
	public void user_send_get_request_for_getting_room_id() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		response = raUtils.getRequest(getData, "/room/");
		raUtils.validateStatusCode(response, 200);
		Reporter.writeLog("GET request sent successfully for getting room id", true, false);
		Reporter.setExtentNodeNull();
	}

	@Then("User gets the room id in the response")
	public void user_gets_the_room_id_in_the_response() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		room_id = raUtils.getValueFromJSONResponse(response, "rooms[0].roomid", "INTEGER");
		if(!room_id.equals("")){
			Reporter.writeLog("Room id is generated succesully as '"+room_id+"'", true, false);
		}else{
			Reporter.writeLog("Room id is not got from room availability resource", false, false);
		}
		Reporter.setExtentNodeNull();
	}
	
	@Then("User generates pay load for room booking")
	public void user_generates_pay_load_for_room_booking() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		generatePayLoadforRoomBooking();
		Reporter.setExtentNodeNull();
	}
	
	@Then("User send post request for room booking")
	public void user_send_post_request_for_room_booking() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		raUtils.setBaseURI(url);
		raUtils.setParameters("QUERYPARAMETER", "token", token);
		RestAssuredUtils.requestSpecification.body(room);
		response = raUtils.postRequest(getData, getData.get("resource"));
		raUtils.validateStatusCode(response, 201);
		Reporter.writeLog("POST request sent successfully for Room booking", true, false);
		Reporter.setExtentNodeNull();
	}

	@Then("User verifies room is booked successfully")
	public void user_verifies_room_is_booked_successfully() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		String bookingid = raUtils.getValueFromJSONResponse(response, "booking.bookingid", "Integer");
		if(!bookingid.equals("")){
			Reporter.writeLog("Room is booked succesfully with booking id as : '"+bookingid+"'", true, false);
		}else{
			Reporter.writeLog("Room is not booked", false, false);
		}
		Reporter.setExtentNodeNull();
	}
	
	@Then("User generates pay load for room booking with firstname field size greater than 18 characters")
	public void user_generates_pay_load_for_room_booking_with_firstname_field_size_greater_than_characters() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		generatePayLoadforRoomBooking();
		Reporter.setExtentNodeNull();
	}

	@Then("User gets bad request with 400 as response code for post request for room booking")
	public void user_gets_bad_request_as_response_code() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		raUtils.setBaseURI(url);
		raUtils.setParameters("QUERYPARAMETER", "token", token);
		RestAssuredUtils.requestSpecification.body(room);
		response = raUtils.postRequest(getData, getData.get("resource"));
		raUtils.validateStatusCode(response, 400);
		Reporter.writeLog("Validated user gets bad request for incorrect field values", true, false);
		Reporter.setExtentNodeNull();
	}

	@Then("User validate error message in response as expected for {string} field")
	public void user_validate_error_message_in_response_as_expected(String field) throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		String message = "";
		if(field.equals("firstname")){
			message = raUtils.getValueFromJSONResponse(response, "errors[0].defaultMessage", "STRING");
		}else if(field.equals("checkin date")){
			message = raUtils.getValueFromJSONResponse(response, "message", "STRING");
		}
		
		if(message.contains(getData.get("errorMessage"))){
			Reporter.writeLog("Successfully got error message for "+field+" field with incorrect value as : "+message, true, false);
		}else{
			Reporter.writeLog("Didn't got error message for "+field+" field with incorrect value with Expected : "+getData.get("errorMessage")+" and Actual : "+message, false, false);
		}
		Reporter.setExtentNodeNull();
	}
	
	@Then("User generates pay load for room booking with checkin date in wrong format")
	public void user_generates_pay_load_for_room_booking_with_date_as_wrong_format() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		generatePayLoadforRoomBooking();
		Reporter.setExtentNodeNull();
	}
	
	void generatePayLoadforRoomBooking() throws Exception{
		BookingDates bookingdates = new BookingDates();
		String checkInDate = "";
		String checkOutDate = "";
		if(getData.get("checkInDate").length()==10){
			checkInDate = jUtils.formatDate("yyyy-MM-dd", getData.get("checkInDate"), "DAY", 1);
			checkOutDate = jUtils.formatDate("yyyy-MM-dd", getData.get("checkInDate"), "DAY", 5);
		}else{
			checkInDate = getData.get("checkInDate");
			checkOutDate = getData.get("checkInDate");
		}
		bookingdates.setCheckin(checkInDate);
		bookingdates.setCheckout(checkOutDate);
		room.setFirstname(getData.get("firstname")+"_"+jUtils.generateRandomNumber(6));
		room.setLastname(getData.get("lastname")+"_"+jUtils.generateRandomNumber(6));
		room.setRoomid(Integer.parseInt(room_id));
		room.setTotalprice(Integer.parseInt(getData.get("totalprice")));
		room.setDepositpaid(Boolean.parseBoolean(getData.get("depositpaid")));
		room.setBookingdates(bookingdates);
		Reporter.writeLog("Payload for Room bookig is created", true, false);
	}
	
	public String createJsonForToken(){
		String username = getData.get("username");
		String password = getData.get("password");
		JSONObject authenticationBody = new JSONObject();
		authenticationBody.put("username", username);
		authenticationBody.put("password", password);
		return authenticationBody.toString();
	}

}
