package com.selenium.day2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Hello world!
 *
 */
public class RemoteWebDriverDemo {
	public static void main(String[] args) throws MalformedURLException {
		// to open chrome browser
		// 1. set system property or add chromedriver in classpath
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "firefox");
		WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// to navigate to google
		driver.navigate().to("https://www.google.co.in");

		WebElement e = (WebElement) ((JavascriptExecutor) driver)
				.executeScript("return document.getElementById('lst-ib')" + "");

		e.sendKeys("cheese");

		List<WebElement> btns = driver.findElements(By.xpath("//*[@name='btnG' or @name='btnK']"));
		for (WebElement ele : btns) {
			if (ele.isDisplayed()) {
				ele.click();
			}
		}

		driver.close();

	}
}
