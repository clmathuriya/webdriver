package com.plancess.selenium.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.plancess.selenium.reporter.PlancessReporter;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;

public class BaseTest {
	public WebDriver driver;
	public static Util util;
	public Verifications verifications;
	String startReport = "<!DOCTYPE html> <html> <head> <style> table, th, td { border: 1px solid black; border-collapse: collapse; } th, td { padding: 5px; text-align: left; } </style> </head> <body> <table style='width:100%'> <caption>Execution summary</caption> <tr> <th>Start Time</th> <th>Duration</th> <th>Step Description</th> <th>Status</th> <th>Screenshot</th> </tr>";
	String endReport = "</table> </body> </html>";
	PlancessReporter reporter;

	@BeforeSuite
	public void beforeSuite() {
		// create driver here
		// driver = new FirefoxDriver();
		// util = Util.getInstance();
		// verifications = Verifications.getInstance();
		reporter = PlancessReporter.getInstance();
		startReport();

	}

	@AfterSuite
	public void afterSuite() {
		// kill driver here
		if (driver != null)
			driver.quit();
		endReport();
	}

	private void endReport() {

		reporter.log(endReport);
		reporter.killReporter();
	}

	private void startReport() {

		reporter.log(startReport);
	}

}
