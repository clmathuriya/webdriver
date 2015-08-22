package com.plancess.selenium.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

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

import com.plancess.selenium.pages.HomePage;
import com.plancess.selenium.pages.SignUpPage;

public class SignUpTest extends BaseTest {

	private WebDriver driver;
	private HomePage homePage;
	private SignUpPage signUpPage;
	private String pageTitle = "Plancess Dashboard";

	@Parameters({ "host_ip", "port", "os", "browser", "browserVersion" })
	@BeforeMethod
	public void setUp(@Optional("localhost") String host, @Optional("4444") String port, @Optional("LINUX") String os,
			@Optional("chrome") String browser, @Optional("40.0") String browserVersion) {

		try {

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName(browser);
			// capabilities.setCapability("version", browserVersion);
			capabilities.setCapability("platform", Platform.valueOf(os));

			this.driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), capabilities);

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			homePage = new HomePage(driver);

		} catch (MalformedURLException e) {
			Assert.fail("Unable to start selenium session make sure Grid hub is running on url :" + "http://" + host
					+ ":" + port + "/wd/hub");

		}
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void signUpContentTest() {

		signUpPage = homePage.openSignUpPage();

		Assert.assertEquals(signUpPage.getTitle(), pageTitle, util.takeScreenshot(driver, "assert title"));

	}

	@DataProvider
	public Object[][] signUpDataProvider() {
		return new Object[][] { new Object[] { 1, "Text 1" }, new Object[] { 2, "Text 2" }, };
	}
}
