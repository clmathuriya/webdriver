package com.plancess.selenium.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.HomePage;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;

public class HomePageTest extends BaseTest {

	private WebDriver driver;

	private HomePage homePage;
	private String pageTitle = "Plancess Dashboard";

	private WebDriverWait wait;

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
			wait = new WebDriverWait(driver, 30);
			util = Util.getInstance();
			verifications = Verifications.getInstance();

			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			homePage = new HomePage(driver, wait);

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
	public void homePageContentTest() {

		Assert.assertEquals(homePage.getTitle(), pageTitle, util.takeScreenshot(driver, "Assert Title"));
		verifications.verifyTrue(homePage.isHeaderLogoVisible(), util.takeScreenshot(driver, "Verify Header Logo"));
		verifications.verifyTrue(homePage.isFooterLogoVisible(), util.takeScreenshot(driver, "Verify Footer Logo"));
		verifications.verifyTrue(homePage.isToggleDropDownVisible(),
				util.takeScreenshot(driver, "Verify Toggle Drop Down"));
		homePage.clickToggelDropDown();
		Assert.assertTrue(homePage.isLoginLinkDisplayed(), util.takeScreenshot(driver, "Assert Login Link"));
		Assert.assertTrue(homePage.isSignUpLinkDisplayed(), util.takeScreenshot(driver, "Assert Signup Link"));

	}
}
