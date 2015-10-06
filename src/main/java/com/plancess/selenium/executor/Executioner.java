package com.plancess.selenium.executor;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Optional;

import com.plancess.selenium.reporter.PlancessReporter;
import com.plancess.selenium.utils.Util;

public class Executioner {
	private WebDriver driver;
	private Util util;
	private WebDriverWait wait;
	private long startTime;
	private long duration;
	public StopWatch stopWatch;
	public PlancessReporter reporter;

	public Executioner(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		util = Util.getInstance();
		this.wait = wait;
		reporter = PlancessReporter.getInstance();
		stopWatch = reporter.getStopWatch();

	}

	public Executioner() {
		util = Util.getInstance();
		reporter = PlancessReporter.getInstance();
		stopWatch = reporter.getStopWatch();
	}

	public PlancessReporter getReporter() {
		return reporter;
	}

	private void addStep(long start, long duration, String step, String status, String screenShot) {

		if (screenShot.length() > 4) {
			reporter.log("<tr> <td>" + start + "</td> <td>" + duration + "</td> <td>" + step + " </td> <td>" + status
					+ "</td> <td><a href='" + screenShot + "' > screenshot </a></td></tr>");
		} else {
			reporter.log("<tr> <td>" + start + "</td> <td>" + duration + "</td> <td>" + step + " </td> <td>" + status
					+ "</td> <td>" + screenShot + "</td></tr>");
		}
	}

	public WebDriver openBrowser(String host, String port, DesiredCapabilities capabilities) {
		try {

			startTime = stopWatch.getTime();
			WebDriver driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), capabilities);
			duration = stopWatch.getTime() - startTime;
			addStep(startTime, duration, "Open browser", "Done", "NA");

			return driver;
		} catch (MalformedURLException e) {
			addStep(startTime, stopWatch.getTime() - startTime, "Open browser", "Failed", util.takeScreenshot(driver));
			Assert.fail("Unable to start selenium session make sure Grid hub is running on url :" + "http://" + host
					+ ":" + port + "/wd/hub");
			return null;

		}

	}

	public void navigateToURL(String url) {
		startTime = stopWatch.getTime();
		driver.navigate().to(url);
		addStep(startTime, stopWatch.getTime() - startTime, "Open URL " + url, "Done", util.takeScreenshot(driver));
		driver.manage().window().maximize();
		util.takeScreenshot(driver, "Navigated to URL :" + url);

	}

	public void softWaitForCondition(ExpectedCondition<Boolean> expectedCondition, String step) {
		try {
			startTime = stopWatch.getTime();
			wait.until(expectedCondition);
			addStep(startTime, stopWatch.getTime() - startTime, step, "Pass", util.takeScreenshot(driver));
		} catch (Exception e) {
			Reporter.log("wait timeout for condition", 0, true);

		}

	}

	public void softWaitForCondition(ExpectedCondition<Boolean> expectedCondition) {
		try {
			startTime = stopWatch.getTime();
			wait.until(expectedCondition);
			addStep(startTime, stopWatch.getTime() - startTime, "Wait for condition", "Pass",
					util.takeScreenshot(driver));
		} catch (Exception e) {
			Reporter.log("wait timeout for condition", 0, true);

		}

	}

	public void softWaitForWebElement(ExpectedCondition<WebElement> visibilityOf) {
		try {
			startTime = stopWatch.getTime();

			wait.until(visibilityOf);
			addStep(startTime, stopWatch.getTime() - startTime, "Wait for condition", "Pass",
					util.takeScreenshot(driver));
		} catch (Exception e) {
			Reporter.log("wait timeout for web element", 0, true);

		}

	}

	public void softWaitForWebElement(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			Reporter.log("wait timeout for web element", 0, true);
			System.out.println("time out for web element");
		}
	}

	public boolean isElementExist(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public WebElement getElement(By by) {
		try {
			return driver.findElement(by);
		} catch (Exception e) {
			return null;
		}
	}

	public void mouseClick(WebElement e) {
		new Actions(driver).click(e).build().perform();
	}

	public boolean isElementExist(WebElement e) {
		try {

			return e.getTagName() != null;
		} catch (Exception exception) {
			return false;
		}

	}

}
