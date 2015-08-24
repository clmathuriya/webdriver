package com.plancess.selenium.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelReader {

	public String[][] readExcelFile(String filepath, String sheetName) {
		try {

			FileInputStream file = new FileInputStream(new File(filepath));

			// Get the workbook instance for XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(file);

			// Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheet(sheetName);

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			String[][] sheetData = new String[sheet.getLastRowNum()][];
			int i = 0, j = 0;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				j = 0;

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();

					sheetData[i][j++] = cell.getStringCellValue();

				}
				i++;
			}
			file.close();

			return sheetData;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
