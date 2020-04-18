package com.api.automation.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class FileOperations {
	
	static Properties properties = new Properties();
	public static Logger log = Logger.getLogger(FileOperations.class.getName());

	
	//Properties file reader
	public static void loadProperties() throws FileNotFoundException, IOException{
		PropertyConfigurator.configure("src//main//resources//com//generic//log4j.properties");
		properties.load(new FileReader(System.getProperty("user.dir")+"//src//main//resources//com//generic//Configuration.properties")); 
	}
	
	//Get property from properties file
	public static String getProperty(String key){
		try{
			return properties.getProperty(key);
		}catch(Exception E){
			log.warn("Exception in getProperty method in FileOperations.java file"+ E);
			return "";
		}
	}
	
	//Excel file reader
	public static HashMap<String,String> getDataFromExcel(String testCaseName, String projectName) throws Exception, IOException{
		HashMap<String,String> hmData = new HashMap<String, String>();
		File file;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet;
		int testCaseRow = 0;
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		try{
			file = new File(System.getProperty("user.dir")+"//src//test//resources//com//restapi//"
					+ ""+projectName.toLowerCase()+"//testdata//TestData.xlsx");
			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getLastRowNum();
			int columnCount = sheet.getRow(0).getLastCellNum();
			for(int iCount=0;iCount<=rowCount;iCount++){
				if(testCaseName.equals(sheet.getRow(iCount).getCell(1).getStringCellValue())){
					testCaseRow=iCount;
					break;
				}
			}
			if(testCaseRow>0){
				String key = "";
				for(int jCount=1;jCount<columnCount;jCount++){
					Row row = sheet.getRow(0);
					Cell cell = row.getCell(jCount);
						key = cell.getStringCellValue();
						if(!keys.contains(key)){
							keys.add(key);
						}
				}
				for(int jCount=1;jCount<columnCount;jCount++){
					Row row = sheet.getRow(testCaseRow);
					Cell cell = row.getCell(jCount);
					switch (cell.getCellType()){
						case STRING :
							values.add(cell.getStringCellValue());
							break;
						case NUMERIC :
							values.add(String.valueOf(cell.getNumericCellValue()));
							break;
						case BOOLEAN :
							values.add(String.valueOf(cell.getBooleanCellValue()));
							break;
						case FORMULA :
							values.add(String.valueOf(cell.getCellFormula()));
							break;
						default :
							log.info(cell.getCellType()+" is not having cell type from String,Numeric,Boolean,Formula");
							break;
					}
					hmData.put(keys.get(jCount-1), values.get(jCount-1));
				}
			}else{
				hmData=null;
				log.info("Pleasen check whether data with scenario name : "+testCaseName+" is present in test data");
			}

		}catch(Exception E){
			log.info("getDataFromExcel method failed : "+E);
			Reporter.writeLog("getDataFromExcel method failed, please refer console logs for exception", false, false);
		}finally{
			file=null;
			workbook.close();
			workbook=null;
			sheet=null;
		}
		return hmData;
	}
	
	//JSON file operations
	
	
	
}
