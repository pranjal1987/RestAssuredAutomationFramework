package com.api.automation.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class JavaUtils {
	
	Logger log = Logger.getLogger(JavaUtils.class.getName());
	
	public static void main(String[] args)   {
	}
	
	public String getTimeStamp(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		String timeStamp =  format.format(date);
		log.info("Generated time stamp is : "+timeStamp);		
		return timeStamp;
	}
	
	public String formatDate(String format, String dateValue, String intervalType, int value) throws Exception{
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateValue);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Date modifiedDate;
		switch (intervalType.toUpperCase()){
			case "DAY" :
				cal.add(Calendar.DAY_OF_MONTH,value);
				break;
			case "MONTH" :
				cal.add(Calendar.MONTH,value);
				break;
			case "YEAR" :
				cal.add(Calendar.YEAR,value);
				break;
		}
		modifiedDate = cal.getTime();
		String finalDate = new SimpleDateFormat("yyyy-MM-dd").format(modifiedDate);
		return finalDate;
	}
	
	public String generateRandomNumber(int numberOfDigits){
		String value = "";
		switch (numberOfDigits){
			case 4 :
				value = String.valueOf(new SimpleDateFormat("mmss").format(new Date()));
				break;
			case 5 :
				value = String.valueOf(new SimpleDateFormat("hhmmss").format(new Date())).substring(1);
				break;
			case 6 :
				value = String.valueOf(new SimpleDateFormat("hhmmss").format(new Date()));
				break;
			case 7 :
				value = String.valueOf(new SimpleDateFormat("ddhhmmss").format(new Date())).substring(1);
				break;
			case 8 :
				value = String.valueOf(new SimpleDateFormat("ddhhmmss").format(new Date()));
				break;
			case 14 :
				value = String.valueOf(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
				break;
				
		}
		return value;
	}
	
}
