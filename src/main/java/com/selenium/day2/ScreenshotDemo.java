package com.selenium.day2;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Hello world!
 *
 */
public class ScreenshotDemo {
	public static void main(String[] args) {
		// to open chrome browser
		// 1. set system property or add chromedriver in classpath
		System.setProperty("webdriver.chrome.driver", "/home/rss-23/chromedriver");
		// to open chrome browser
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// to navigate to google
		driver.navigate().to("http://newtours.demoaut.com/");
		WebElement e = driver.findElement(By.linkText("REGISTER"));
		e.click();
		// WebElement e = (WebElement) ((JavascriptExecutor) driver)
		// .executeScript("return document.getElementById('lst-ib')" + "");

		// e.sendKeys("cheese");

		// List<WebElement> btns =
		// driver.findElements(By.xpath("//*[@name='btnG' or @name='btnK']"));
		// for (WebElement ele : btns) {
		// if (ele.isDisplayed()) {
		// ele.click();
		// }
		// }

		WebElement ele = driver.findElement(By.tagName("select"));
		Select dd = new Select(ele);
		dd.selectByVisibleText("INDIA");

		driver.findElement(By.id("email")).sendKeys("email@email.com");
		driver.findElement(By.name("password")).sendKeys("password");
		driver.findElement(By.name("confirmPassword")).sendKeys("password");
		driver.findElement(By.name("register")).click();

		// driver.close();

	}
}
