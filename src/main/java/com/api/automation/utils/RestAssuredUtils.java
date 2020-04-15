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
	
	public void setBaseURI(String uri){
		//RestAssured.baseURI = uri;
		requestSpecification.baseUri(uri);
	}
	
	public void setParameters(String parameterType, String key, String value) throws Exception{
		switch (parameterType){
			case "Header" :
				requestSpecification.header(key, value);
				break;
			case "QueryParameter" :
				requestSpecification.queryParam(key, value);
				break;
			case "PathParameter" :
				requestSpecification.pathParam(key, value);
				break;
			default :
				Reporter.writeLog("Parameter type "+parameterType+" is not correct parameter",false,true);
				break;
		}
	}
	
	public Response postRequest(HashMap<String, String> getData, String resource) throws Exception{
		try{
			String contentType = getData.get("content_Type");
			
			//requestSpecification = RestAssured.given();
			if(contentType.equals("JSON")){
				requestSpecification.contentType(ContentType.JSON);
			}
			Response response =requestSpecification.post(resource);
			return response;
		}catch(Exception E){
			log.info("postRequest method failed with exception "+E);
			Reporter.writeLog("postRequest method failed, refer console logs for exception", false, false);
			return null;
		}
	}
	
	public void validateStatusCode(Response response,int expectedStatusCode) throws Exception{
		try{
			if(response.getStatusCode()==expectedStatusCode){
				Reporter.writeLog("Got success status code", true, false);
			}else{
				Reporter.writeLog("Didn't got success status code and actual value of status code is : "+response.getStatusCode(), false, false);
			}
		}catch(Exception E){
			log.info("validateStatusCode method failed with exception "+E);
			Reporter.writeLog("validateStatusCode method failed, refer console logs for exception", false, false);
		}
	}
	
	public String getValueFromJSONResponse(Response resonse, String key) throws Exception{
		try{
			JsonPath jPath = new JsonPath(resonse.asString());
			return jPath.getString(key);
		}catch(Exception E){
			log.info("getValueFromJSONResponse method failed with exception "+E);
			Reporter.writeLog("getValueFromJSONResponse method failed, refer console logs for exception", false, false);
			return "";
		}
	}
}
