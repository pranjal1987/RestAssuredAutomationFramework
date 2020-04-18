package com.api.automation.utils;

import org.apache.log4j.Logger;

public class CustomException {

	public static void logException(String methodName, Exception E, Logger log) throws Exception{
		log.info(methodName+" method failed with exception "+E);
	}
	
}
