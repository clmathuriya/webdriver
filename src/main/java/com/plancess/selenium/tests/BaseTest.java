package com.plancess.selenium.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.plancess.selenium.utils.Util;

public class BaseTest {
	public WebDriver driver;
	public Util util;

	@BeforeSuite
	public void beforeSuite() {
		// create driver here
		// driver = new FirefoxDriver();
		util = new Util();

	}

	@AfterSuite
	public void afterSuite() {
		// kill driver here
		if (driver != null)
			driver.quit();
	}

}
