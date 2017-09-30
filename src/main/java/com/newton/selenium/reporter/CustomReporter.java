package com.newton.selenium.reporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.Reporter;

public class CustomReporter {
	private String step = "";

	private String buildStatus = "Failed";

	private int buildNumber;

	private int passCount;

	private int failCount;

	private int skipCount;

	private Date date;
	private PrintWriter mOut;
	private static CustomReporter instance;
	private StopWatch stopWatch;
	private String reportFileName = "";

	public CustomReporter() {
		new File("./test-output/custom-reports").mkdirs();
		String reportPath = "./test-output/custom-reports/custom-report" + System.currentTimeMillis() + ".html";
		stopWatch = new StopWatch();
		stopWatch.start();
		try {
			File reportFile = new File(reportPath);
			mOut = new PrintWriter(new BufferedWriter(new FileWriter(reportFile)), true);
			Reporter.log("<h1><a href='" + reportFile.getAbsolutePath() + "'>Custom Report</a></h1>");
			instance = this;
			if (reportFile.getAbsolutePath().contains("jenkins"))
				reportFileName = reportFile.getName();
		} catch (IOException e) {
			System.out.println("Error in creating writer: " + e);
		}

	}

	public static CustomReporter getInstance() {
		if (instance == null) {
			instance = new CustomReporter();
		}
		return instance;
	}

	public StopWatch getStopWatch() {
		return stopWatch;
	}

	public void log(String s) {
		mOut.print(s);
	}

	// private void print(String text) {
	// System.out.println(text);
	// mOut.println(text + "");
	// }

	public void killReporter() {

		mOut.flush();
		mOut.close();
	}

}