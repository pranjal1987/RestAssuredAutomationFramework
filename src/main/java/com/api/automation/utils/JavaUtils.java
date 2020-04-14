package com.api.automation.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class JavaUtils {
	
	Logger log = Logger.getLogger(JavaUtils.class.getName());
	
	public String getTimeStamp(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		String timeStamp =  format.format(date);
		log.info("Generated time stamp is : "+timeStamp);		
		return timeStamp;
	}
	
}
