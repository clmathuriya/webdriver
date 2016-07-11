package com.selenium.day2;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * 
 * @author cl
 *
 *
 */
public class UploadFile {

	public static void main(String[] args) throws IOException {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "firefox");
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.2.125:5555/wd/hub"), capabilities);
		driver.get("https://how-old.net/");
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; arguments[0].style.width = '1px'; arguments[0].style.opacity = 1",
				driver.findElement(By.id("uploadBtn")));
		driver.findElement(By.id("uploadBtn")).sendKeys("/absolute/path/to/file.png");
		// take screenshot

		File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(f, new File("data/screenshot" + System.currentTimeMillis() + ".png"));

	}

}
