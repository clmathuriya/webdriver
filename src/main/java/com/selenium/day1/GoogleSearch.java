package com.selenium.day1;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Hello world!
 *
 */
public class GoogleSearch {
	public static void main(String[] args) {
		// to open chrome browser
		// 1. set system property or add chromedriver in classpath
		System.setProperty("webdriver.chrome.driver", new File("data/chromedriver").getAbsolutePath());
		// to open chrome browser
		WebDriver driver = new ChromeDriver();
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
