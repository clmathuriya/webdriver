package com.newton.selenium.tests;

import java.io.File;
import java.lang.reflect.Method;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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
import org.testng.annotations.Test;

import com.newton.selenium.executor.Executioner;
import com.newton.selenium.reporter.CustomReporter;
import com.newton.selenium.reporter.MyReporter;
import com.newton.selenium.utils.Config;
import com.newton.selenium.utils.Util;
import com.newton.selenium.utils.Verifications;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest {
	public WebDriver driver;
	public static Util util;
	public Verifications verifications;
	String startReport = "<!DOCTYPE html> <html> <head> <style> table, th, td { border: 1px solid black; border-collapse: collapse; } th, td { padding: 5px; text-align: left; } </style> </head> <body> ";
	String startTable = "<table style='width:100%'> <caption><h3>TESTCASE_NAME</h3></caption> <tr> <th>Start Time</th> <th>Duration</th> <th>Step Description</th> <th>Status</th> <th>Screenshot</th> </tr>";
	String endReport = "</body> </html>";
	String endTable = "</table>";
	public static CustomReporter reporter;
	String PROXY = "localhost:8080";

	protected WebDriverWait wait;
	protected Executioner executor;
	MyReporter extentReporter;
	ExtentTest test;

	@BeforeTest
	public void beforeSuite() {

		reporter = CustomReporter.getInstance();
		startReport();
		util = Util.getInstance();
		extentReporter = MyReporter.getInstance(util.getReportPath());

	}

	@Parameters({ "host_ip", "port", "os", "browser", "browserVersion", "useProxy", "deviceName" })
	@BeforeMethod
	public void setUp(@Optional("localhost") String host, @Optional("4444") String port, @Optional("LINUX") String os,
			@Optional("firefox") String browser, @Optional("40.0") String browserVersion,
			@Optional("false") String useProxy, @Optional("") String deviceName, @Optional Method method) {

		// to log starting of test case in report table
		startTable(method.getName());
		test = extentReporter.startTest(method.getName());

		DesiredCapabilities capabilities = new DesiredCapabilities();

		if (useProxy.equalsIgnoreCase("true")) {
			setProxy(capabilities);
		}

		// capabilities.setBrowserName(browser);
		// capabilities.setCapability("version", browserVersion);
		capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
		// capabilities.setCapability("androidUseRunningApp", true);
		if (deviceName.length() >= 5) {
			capabilities.setCapability("deviceName", deviceName);
		} else

		{
			capabilities.setCapability("platform", Platform.valueOf(os));
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		}

		executor = new Executioner();

		this.driver = executor.openBrowser(host, port, capabilities);

		wait = new WebDriverWait(driver, 60);
		executor = new Executioner(driver, wait, test);
		executor.navigateToURL(Config.URL);

		util = Util.getInstance();

		// driver.manage().window().setSize(new Dimension(1920, 1080));
		// driver.manage().window().maximize();

	}

	private WebDriver getHeadLessFireFox() {
		String Xport = System.getProperty("lmportal.xvfb.id", ":1");
		final File firefoxPath = new File(System.getProperty("lmportal.deploy.firefox.path", "/usr/bin/firefox"));
		FirefoxBinary firefoxBinary = new FirefoxBinary(firefoxPath);
		firefoxBinary.setEnvironmentProperty("DISPLAY", Xport);
		return new FirefoxDriver(firefoxBinary, null);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) {

		if (driver != null)
			executor.closeBrowser();
		endTable();
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
		} else {
			test.log(LogStatus.PASS, "Test passed");
		}
		extentReporter.endTest(test);
	}

	@AfterTest(alwaysRun = true)
	public void afterSuite() {
		// kill driver here
		if (driver != null)
			driver.quit();
		endReport();
		extentReporter.flush();
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

	public ExtentTest getTest() {
		return test;
	}

	@Test
	public void test() {
		System.out.println("in test");
		executor.assertEquals("actual", "expected", "message");
	}
}
