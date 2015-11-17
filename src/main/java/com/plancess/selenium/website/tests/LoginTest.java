package com.plancess.selenium.website.tests;

import org.testng.annotations.Test;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.utils.ExcelReader;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;
import com.plancess.selenium.website.pages.LandingPage;
import com.plancess.selenium.website.pages.LoginDialogPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class LoginTest extends BaseTest{
	//private WebDriver driver;

	//private WebDriverWait wait;
	//private Executioner executor;

	//private Util util;
	//private Verifications verifications;
	
	private LoginDialogPage loginDialogPage;
	//private LandingPage landingPage;
/*
	@Parameters({ "host_ip", "port", "os", "browser", "browserVersion" })

	@BeforeMethod(alwaysRun = true)
	public void setUp(@Optional("localhost") String host, @Optional("4444") String port, @Optional("LINUX") String os,
			@Optional("firefox") String browser, @Optional("40.0") String browserVersion) {

		try {

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName(browser);
			// capabilities.setCapability("version", browserVersion);
			capabilities.setCapability("platform", Platform.valueOf(os));

			this.driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), capabilities);

			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 30);
			executor = new Executioner(driver, wait);
			landingPage  = new LandingPage(driver, wait);
			util = Util.getInstance();
			verifications = Verifications.getInstance();

		} catch (MalformedURLException e) {
			Assert.fail("Unable to start selenium session make sure Grid hub is running on url :" + "http://" + host
					+ ":" + port + "/wd/hub");

		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}*/

	@Test(alwaysRun = true, dataProvider = "PlancessDataProvider")
	public void LoginContentTest(Map<String, String> user) {

		executor.navigateToURL(user.get("baseUrl"));
		landingPage  = new LandingPage(driver, wait);
		//System.out.println(getTitle());
		if (!"Plancess JEE Main & Advanced Preparation | IIT JEE Online Coaching | AIPMT Preparation | Test Series".equals(driver.getTitle().trim())) {
			throw new IllegalStateException("This is not  the Plancess Landing page");
		}	
		landingPage.openLoginDialogPage();
		loginDialogPage = new LoginDialogPage(driver, wait);
		
		
		//if (!driver.getTitle().contains("Plancess"))
		loginDialogPage.doLogin(user);
			
		executor.softWaitForCondition(ExpectedConditions.titleContains("Plancess"));
		verifications.verifyTrue(driver.getTitle().contains("Plancess"),
				util.takeScreenshot(driver, "Verify Login"));
		;
		verifications.verifyTrue(executor.getElement(By.xpath("//*[contains(text(),'" + user.get("name") + "')]")).isDisplayed(),
				util.takeScreenshot(driver, "verify name field displayed"));

	}

	@DataProvider(name = "PlancessDataProvider")
	public Object[][] PlancessLoginDataProvider() {

		ExcelReader excelReader = new ExcelReader();
		return excelReader.getUserDataFromExcel("testData.xlsx", "PlancessUserData");

	}

}
