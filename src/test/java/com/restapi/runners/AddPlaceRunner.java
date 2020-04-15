package com.restapi.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;



@RunWith(Cucumber.class)
@CucumberOptions(
			//glue={"src/main/java/com/restapi/addplace/stepdefinitions"},
			glue={"com.restapi.addplace.stepdefinitions"},
			features="src/test/resources/com/restapi/addplace/features",
			tags=" @TC01",
			dryRun=false,
			strict=true
		)
	



public class AddPlaceRunner {


}
