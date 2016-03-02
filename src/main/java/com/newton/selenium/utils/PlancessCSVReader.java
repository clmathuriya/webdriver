package com.newton.selenium.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class PlancessCSVReader {

	public static List<String[]> parseCSVFileLineByLine(File file) throws IOException {

		CSVReader reader = new CSVReader(new FileReader(file), ',');
		List<String[]> allRows = reader.readAll();
		reader.close();
		return allRows;

	}

	public static void main(String[] args) throws IOException {

		File file = new File("testData.csv");

		for (String[] s : parseCSVFileLineByLine(file)) {
			for (String s1 : s) {
				System.out.print(s1);
			}

		}

	}

}
