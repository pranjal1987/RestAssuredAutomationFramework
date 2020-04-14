package com.restapi.addplace.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.api.automation.utils.FileOperations;
import com.api.automation.utils.Reporter;

public class StepDefinition {

	String url = "";
	public static Logger log = Logger.getLogger(StepDefinition.class.getName());
	public String scenarioName;
	public String projectName;
	public HashMap<String,String> getData = new HashMap<String,String>();

	@Before
	public void initialize(Scenario scenarName) throws Exception {
		FileOperations.loadProperties();
		url = FileOperations.getProperty("ADD_PLACE_URL");
		projectName = FileOperations.getProperty("MODULE_NAME");
		scenarioName=scenarName.getName();
		
		//Initializing the Extner Report object
		Reporter.intializeReport();
		
		//Initializing the Extent Test for each TC.
		Reporter.createExtentTest(scenarioName);
		
		//Get Data from Excel
		getData = FileOperations.getDataFromExcel(scenarioName,projectName);
		System.out.println(getData.get("contentType"));
	}
	
	
	@After
	public void finalize(){
		Reporter.flushReports();
	}
	
	
	@Given("User add place payload")
	public void user_add_place_payload() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
	    Reporter.writeLog("Step1", true, false);
	    Reporter.setExtentNodeNull();
	}

	@When("User calls add place api with post request")
	public void user_calls_add_place_api_with_post_request() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		Reporter.writeLog("Step2", true, false);
		Reporter.setExtentNodeNull();
	}

	@Then("User gets success status code")
	public void user_gets_success_status_code() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		Reporter.writeLog("Step3", true, false);
		Reporter.setExtentNodeNull();
	}

	@Then("User gets the place_id response")
	public void user_gets_the_place_id_response() throws Exception {
		Reporter.createExtentNode(new Throwable().getStackTrace()[0].getMethodName());
		Reporter.writeLog("Step4", false, false);
		Reporter.setExtentNodeNull();
	}

}
