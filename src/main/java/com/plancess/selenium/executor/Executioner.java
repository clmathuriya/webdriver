package com.plancess.selenium.executor;

import org.openqa.selenium.WebDriver;

import com.plancess.selenium.utils.Util;

public class Executioner {
	private final WebDriver driver;
	private Util util;

	public Executioner(WebDriver driver) {
		this.driver = driver;
		util = new Util();
	}

	public void navigateToURL(String url) {
		//Reporter.log("Navigating to URL: " + url + "<br>", 0, true);
		driver.navigate().to(url);
		util.takeScreenshot(driver, "Navigated to URL :" + url + "<br>");

	}

}
