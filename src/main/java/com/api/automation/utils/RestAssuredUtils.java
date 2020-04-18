package com.api.automation.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class RestAssuredUtils {
	
	Logger log = Logger.getLogger(RestAssured.class.getName());
	public static RequestSpecification requestSpecification = RestAssured.given() ;
	
	public void setBaseURI(String uri) throws Exception{
		try{
			requestSpecification.baseUri(uri);
		}catch(Exception E){
			Reporter.writeLog("Got exception while setting baseUri as : "+uri+", please refer console logs", false, false);
			CustomException.logException("setBaseURI", E, log);
		}
	}
	
	public void setParameters(String parameterType, String key, String value) throws Exception{
		try{
			switch (parameterType.toUpperCase()){
				case "HEADER" :
					requestSpecification.header(key, value);
					break;
				case "QUERYPARAMETER" :
					requestSpecification.queryParam(key, value);
					break;
				case "PATHPARAMETER" :
					requestSpecification.pathParam(key, value
							
							);
					break;
				default :
					Reporter.writeLog("Parameter type "+parameterType+" is not correct parameter",false,true);
					break;
			}
		}catch(Exception E){
			Reporter.writeLog("Got exception while setting "+parameterType+" parameter, please refer console logs", false, false);
			CustomException.logException("setParameters", E, log);
		}
	}
	
	public Response postRequest(HashMap<String, String> getData, String resource) throws Exception{
		try{
			String contentType = getData.get("content_Type");
			if(contentType.equals("JSON")){
				requestSpecification.contentType(ContentType.JSON);
			}
			Response response =requestSpecification.post(resource);
			Reporter.writeLog("POST Request sent for resource : "+resource, true, false);
			return response;
		}catch(Exception E){
			CustomException.logException("postRequest", E, log);
			Reporter.writeLog("Got exception while sending POST Request for resource : "+resource+" please refer console logs", false, false);
			return null;
		}
	}
	
	public Response getRequest(HashMap<String, String> getData, String resource) throws Exception{
		try{
			String contentType = getData.get("content_Type");
			if(contentType.equals("JSON")){
				requestSpecification.contentType(ContentType.JSON);
			}
			Response response =requestSpecification.get(resource);
			Reporter.writeLog("GET Request sent for resource : "+resource, true, false);
			return response;
		}catch(Exception E){
			Reporter.writeLog("Got exception while sending GET Request for resource : "+resource+" please refer console logs", false, false);
			CustomException.logException("getRequest", E, log);
			return null;
		}
	}
	
	public void validateStatusCode(Response response,int expectedStatusCode) throws Exception{
		try{
			if(response.getStatusCode()==expectedStatusCode){
				Reporter.writeLog("Got expected status code as "+expectedStatusCode, true, false);
			}else{
				Reporter.writeLog("Didn't got success status code and actual value of status code is : "
						+ ""+response.getStatusCode()+" and response is : "+response.asString(), false, false);
			}
		}catch(Exception E){
			Reporter.writeLog("Got exception while validating the status code, please refer console logs", false, false);
			CustomException.logException("validateStatusCode", E, log);
		}
	}
	
	public String getValueFromJSONResponse(Response response, String key, String expectedDataType) throws Exception{
		String value = "";
		try{
			JsonPath jPath = new JsonPath(response.asString());
			switch (expectedDataType.toUpperCase()){
				case "STRING" :
					value = jPath.getString(key);
					break;
				case "INTEGER" :
					value = String.valueOf(jPath.getInt(key));
					break;
				default :
					Reporter.writeLog("ExpectedDataType value is not passed correctly as : "+expectedDataType, false, false);
					break;
			}
			return value;
		}catch(Exception E){
			Reporter.writeLog("Got exception while fetching value from JSON response, please refer console logs", false, false);
			CustomException.logException("getValueFromJSONResponse", E, log);
			return "";
		}
	}
}
