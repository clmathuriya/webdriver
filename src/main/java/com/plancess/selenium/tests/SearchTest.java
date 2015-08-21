package com.plancess.selenium.tests;

import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.pages.SearchPage;

public class SearchTest extends BaseTest {

	private WebDriver driver;
	private Executioner executor;
	private String url = "https://www.google.co.in/";

	@Parameters({ "host_ip", "port", "os", "browser", "browserVersion" })
	@BeforeMethod
	public void setUp(@Optional("localhost") String host, @Optional("4444") String port, @Optional("LINUX") String os,
			@Optional("firefox") String browser, @Optional("40.0") String browserVersion) throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(browser);
		// capabilities.setCapability("version", browserVersion);
		capabilities.setCapability("platform", Platform.valueOf(os));

		this.driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), capabilities);
		// driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		executor = new Executioner(driver);
		executor.navigateToURL(url);

	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test(dataProvider = "dataProvider")
	public void helloTest(Integer n, String s) {

		new SearchPage(driver).searchText(s);

		Assert.assertEquals(driver.getTitle(), s + " - Google Search");

	}

	@DataProvider
	public Object[][] dataProvider() {
		return new Object[][] { new Object[] { 1, "Text 1" }, new Object[] { 2, "Text 2" }, };
	}
}
