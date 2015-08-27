package com.plancess.selenium.executor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.plancess.selenium.utils.Util;

public class Executioner {
	private final WebDriver driver;
	private Util util;
	private WebDriverWait wait;

	public Executioner(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		util = Util.getInstance();
		this.wait = wait;
	}

	public void navigateToURL(String url) {
		Reporter.log("Navigating to URL:" + url, 0, true);
		driver.navigate().to(url);
		driver.manage().window().maximize();
		util.takeScreenshot(driver, "Navigated to URL :" + url);

	}

	public void softWaitForCondition(ExpectedCondition<Boolean> expectedCondition) {
		try {
			wait.until(expectedCondition);
		} catch (Exception e) {
			Reporter.log("wait timeout for condition", 0, true);

		}

	}

	public void softWaitForWebElement(ExpectedCondition<WebElement> visibilityOf) {
		try {
			wait.until(visibilityOf);
		} catch (Exception e) {
			Reporter.log("wait timeout for web element", 0, true);

		}

	}

	public void softWaitForWebElement(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			Reporter.log("wait timeout for web element", 0, true);
		}
	}

}
