package com.plancess.selenium.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;

public class BaseTest {
	public WebDriver driver;
	public Util util;
	public Verifications verifications;

	@BeforeSuite
	public void beforeSuite() {
		// create driver here
		// driver = new FirefoxDriver();
		util = new Util();
		verifications = new Verifications();

	}

	@AfterSuite
	public void afterSuite() {
		// kill driver here
		if (driver != null)
			driver.quit();
	}

}
