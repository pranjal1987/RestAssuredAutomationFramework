package com.restapi.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;



@RunWith(Cucumber.class)
@CucumberOptions(
			//glue={"src/main/java/com/restapi/addplace/stepdefinitions"},
			glue={"com.restapi.roombooking.stepdefinitions"},
			features="src/test/resources/com/restapi/roombooking/features",
			tags={"@TC04"},
			dryRun=false,
			strict=true
		)
	



public class RoomBookingRunner {


}
