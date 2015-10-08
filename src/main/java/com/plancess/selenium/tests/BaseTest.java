package com.plancess.selenium.tests;

import java.lang.reflect.Method;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.pages.LandingPage;
import com.plancess.selenium.reporter.PlancessReporter;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;

public class BaseTest {
	public WebDriver driver;
	public static Util util;
	public Verifications verifications;
	String startReport = "<!DOCTYPE html> <html> <head> <style> table, th, td { border: 1px solid black; border-collapse: collapse; } th, td { padding: 5px; text-align: left; } </style> </head> <body> ";
	String startTable = "<table style='width:100%'> <caption><h3>TESTCASE_NAME</h3></caption> <tr> <th>Start Time</th> <th>Duration</th> <th>Step Description</th> <th>Status</th> <th>Screenshot</th> </tr>";
	String endReport = "</body> </html>";
	String endTable = "</table>";
	public static PlancessReporter reporter;
	String PROXY = "192.168.1.124:8090";

	protected LandingPage landingPage;
	protected String pageTitle = "Preplane";
	protected WebDriverWait wait;
	protected Executioner executor;

	@BeforeTest
	public void beforeSuite() {

		reporter = PlancessReporter.getInstance();
		startReport();

	}

	@Parameters({ "host_ip", "port", "os", "browser", "browserVersion", "useProxy" })
	@BeforeMethod
	public void setUp(@Optional("localhost") String host, @Optional("4444") String port, @Optional("WINDOWS") String os,
			@Optional("firefox") String browser, @Optional("40.0") String browserVersion,
			@Optional("false") String useProxy, @Optional Method method) {

		// to log starting of test case in report table
		startTable(method.getName());

		DesiredCapabilities capabilities = new DesiredCapabilities();

		if (useProxy.equalsIgnoreCase("true")) {
			setProxy(capabilities);
		}

		capabilities.setBrowserName(browser);
		// capabilities.setCapability("version", browserVersion);
		capabilities.setCapability("platform", Platform.valueOf(os));
		executor = new Executioner();
		this.driver = executor.openBrowser(host, port, capabilities);
		wait = new WebDriverWait(driver, 30);
		executor = new Executioner(driver, wait);
		landingPage = new LandingPage(driver, wait);
		util = Util.getInstance();
	}

	@AfterMethod
	public void tearDown(ITestResult testResult) {

		if (testResult.getStatus() == ITestResult.FAILURE) {
			executor.verifyTrue(testResult.getStatus() != ITestResult.FAILURE,
					"verify test status for :" + testResult.getTestName());
		}
		executor.closeBrowser();
		endTable();
	}

	@AfterTest(alwaysRun = true)
	public void afterSuite() {
		// kill driver here
		if (driver != null)
			driver.quit();
		endReport();
	}

	private void endReport() {

		reporter.log(endReport);
		reporter.killReporter();
	}

	private void startReport() {

		reporter.log(startReport);
	}

	protected void startTable(String testName) {

		reporter.log(startTable.replace("TESTCASE_NAME", testName));
	}

	protected void endTable() {
		reporter.log(endTable);
	}

	protected void setProxy(DesiredCapabilities capabilities) {

		org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);

		capabilities.setCapability(CapabilityType.PROXY, proxy);

	}
}
