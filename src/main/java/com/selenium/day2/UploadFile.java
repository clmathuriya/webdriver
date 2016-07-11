package com.selenium.day2;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * 
 * @author cl
 *
 */
public class UploadFile {

	public static void main(String[] args) {

		WebDriver driver = new FirefoxDriver();
		driver.get("https://how-old.net/");
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; arguments[0].style.width = '1px'; arguments[0].style.opacity = 1",
				driver.findElement(By.id("uploadBtn")));
		driver.findElement(By.id("uploadBtn")).sendKeys(new File("data/image.jpg").getAbsolutePath());

	}

}
