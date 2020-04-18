package com.restapi.addplace.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.api.automation.utils.FileOperations;
import com.api.automation.utils.Reporter;
import com.api.automation.utils.RestAssuredUtils;
import com.restapi.addplace.pojo.Location;
import com.restapi.addplace.pojo.Place;

public class StepDefinition {

	String url = "";
	public static Logger log = Logger.getLogger(StepDefinition.class.getName());
	public String scenarioName;
	public String projectName;
	public HashMap<String,String> getData = new HashMap<String,String>();
	Place addPlaceBody = new Place();
	RestAssuredUtils raUtils = new RestAssuredUtils();
	Response response;

	@Before
	public void initialize(Scenario scenarName) throws Exception {
		FileOperations.loadProperties();
		projectName = FileOperations.getProperty("MODULE_NAME");
		scenarioName=scenarName.getName();
		
		//Initializing the Extner Report object
		Reporter.intializeReport();
		
		//Initializing the Extent Test for each TC.
		Reporter.createExtentTest(scenarioName);
		
		//Get Data from Excel
		getData = FileOperations.getDataFromExcel(scenarioName,projectName);
	}
	
	
	@After
	public void finalize(){
		Reporter.flushReports();
	}
	
	
	@Given("User add place payload")
	public void user_add_place_payload() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		List<String> type = new ArrayList<String>();
		type.add(getData.get("types").split("#")[0]);
		type.add(getData.get("types").split("#")[1]);
		
		Location loc = new Location();
		loc.setLat(Double.parseDouble(getData.get("lat")));
		loc.setLng(Double.parseDouble(getData.get("lng")));
		
		addPlaceBody.setAccuracy(Integer.parseInt(getData.get("accuracy")));
		addPlaceBody.setAddress(getData.get("address"));
		addPlaceBody.setLanguage(getData.get("language"));
		addPlaceBody.setPhone_number(getData.get("phone_number"));
		addPlaceBody.setWebsite(getData.get("website"));
		addPlaceBody.setName(getData.get("name"));
		addPlaceBody.setLocation(loc);
		addPlaceBody.setTypes(type);;
	    Reporter.writeLog("Added payload for Add Place API", true, false);
	    Reporter.setExtentNodeNull();
	}

	@When("User calls add place api with post request")
	public void user_calls_add_place_api_with_post_request() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		raUtils.setBaseURI(FileOperations.getProperty(projectName+"_URL"));
		raUtils.setParameters("QueryParameter","key", getData.get("key"));
		RestAssuredUtils.requestSpecification.body(addPlaceBody);
		response = raUtils.postRequest(getData,getData.get("resource"));
		Reporter.writeLog("POST request sent for Add Place API", true, false);
		Reporter.setExtentNodeNull();
	}

	@Then("User gets success status code")
	public void user_gets_success_status_code() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		raUtils.validateStatusCode(response, 200);
		Reporter.setExtentNodeNull();
	}

	@Then("User gets the place_id response")
	public void user_gets_the_place_id_response() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		String place_id = raUtils.getValueFromJSONResponse(response,"place_id","STRING");
		Reporter.writeLog("Place_id : {"+place_id+"} is fetched in response while posting Add Place API", true, false);
		Reporter.setExtentNodeNull();
	}

}
