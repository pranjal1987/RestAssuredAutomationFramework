package com.api.automation.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

@SuppressWarnings("deprecation")
public class Reporter {
	
	
	@SuppressWarnings("deprecation")
	static ExtentHtmlReporter htmlReporter;
	static ExtentReports extent = new ExtentReports();
	static ExtentTest extentTest;		//For gherkin scenario
	static ExtentTest extentNode;		//For gherkin statement
	public static String executionReportName;
	public static String executionReportNamePath;
	static Logger log = Logger.getLogger(Reporter.class.getName());
	
	
	@SuppressWarnings("deprecation")
	public static void intializeReport()  throws FileNotFoundException {
		JavaUtils javaUtil = new JavaUtils();
		
		if(executionReportNamePath==null){
			reportPath();
			htmlReporter = new ExtentHtmlReporter(executionReportNamePath);
			extent.attachReporter(htmlReporter);
		}
		
		//avent.loadConfig(System.getProperty("user.dir")+"//src//main//resources//com//generic//extent-config.xml");
		//htmlReporter.se
		htmlReporter.loadConfig(new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//com//generic//extent-config.xml"));
		/*ExtentTest test = extent.createTest("Test"); // level = 0
		ExtentTest node = test.createNode("Node");  // level = 1
		node.pass("details");*/
		extent.flush();
	}
	
	
	public static void createExtentTest(String testCaseName){
		extentTest = extent.createTest(testCaseName);
	}
	
	public static void createExtentNode(String gherkinStatement){
		extentNode = extentTest.createNode(gherkinStatement);
	}
	
	public static void setExtentNodeNull(){
		extentNode = null;
	}
	
	public static void writeLog(String message, boolean isPassOrFail, boolean isScreenshotRequired) throws Exception{
		log.info(message);
		if(isPassOrFail){
			if(isScreenshotRequired)
				extentNode.pass(message,MediaEntityBuilder.createScreenCaptureFromPath("").build());
			else
				extentNode.pass(message);
		}else{
			if(isScreenshotRequired)
				extentNode.fail(message,MediaEntityBuilder.createScreenCaptureFromPath("").build());
			else	
				extentNode.fail(message);
			throw new Exception("Scenario is failed, please refer logs for details");
		}
	}
	
	public static void flushReports(){
		extent.flush();
	}
	
	public static void reportPath(){
		executionReportName = "Report_"+new JavaUtils().getTimeStamp();
		executionReportNamePath = System.getProperty("user.dir") + "//ExecutionReports//ExtentReports//"+executionReportName+".html";
	}
}


