package com.tutorialsninja.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Utilities { 
	
	//Constant kind of data
	public static final int IMPLICIT_WAIT_TIME = 10;
	public static final int PAGE_LOAD_TIME = 5;
	
	public static String generateEmailWithTimeStamp() {//If you want to call this method easily then make this method as static
		Date date = new Date();
		String timestamp = date.toString().replace(" ", "_").replace(":", "_"); //method chaining
		return "Test"+timestamp+"@gmail.com";
	}
	
	public static Object[][] getTestDataFromExcel(String sheetName) {
		File excelFile = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\tutorialsninja\\qa\\testdata\\TutorialsNinjaTestData.xlsx");
		XSSFWorkbook workbook = null;
		
		try {
		FileInputStream fisExcel = new FileInputStream(excelFile);		
		workbook = new XSSFWorkbook(fisExcel); //object of XSSFWorkbook
		}catch(Throwable e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = workbook.getSheet(sheetName);
		
		int rows = sheet.getLastRowNum(); //Give count of total rows
		int cols = sheet.getRow(0).getLastCellNum(); //It will count of colmns of 0th row
		Object[][] data = new Object[rows][cols];
		for(int i=0; i<rows; i++) {
			XSSFRow row = sheet.getRow(i+1);
			for(int j=0; j<cols; j++) {
				XSSFCell cell = row.getCell(j);
				CellType cellType = cell.getCellType();
				
				switch(cellType) {
				case STRING:
					data[i][j] = cell.getStringCellValue();
					break;
				case NUMERIC:
					data[i][j] = Integer.toString((int)cell.getNumericCellValue());
					break;
				case BOOLEAN:
					data[i][j] = cell.getBooleanCellValue();
					break;
				}
				
			}
		}
		return data;
	}


}
