package com.plancess.selenium.lms.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.Test;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.lms.pages.AddCampaignPage;
import com.plancess.selenium.lms.pages.DashboardPage;
import com.plancess.selenium.lms.pages.LoginPage;
import com.plancess.selenium.lms.pages.SignUpPage;
import com.plancess.selenium.utils.ExcelReader;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;

public class CampaignTest {

	private WebDriver driver;
	private SignUpPage signUpPage;

	private WebDriverWait wait;
	private Executioner executor;

	private Util util;
	private Verifications verifications;

	private DashboardPage dashBoardPage;
	private LoginPage loginPage;
	private AddCampaignPage addCampaignPage;

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

			util = Util.getInstance();
			verifications = Verifications.getInstance();

		} catch (MalformedURLException e) {
			AssertJUnit.fail("Unable to start selenium session make sure Grid hub is running on url :" + "http://"
					+ host + ":" + port + "/wd/hub");

		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

	@Test(alwaysRun = true, dataProvider = "LmsDataProvider")
	public void createCampaignTest(Map<String, String> user) {

		// login to crm

		executor.navigateToURL(user.get("baseUrl") + "auth");
		loginPage = new LoginPage(driver, wait);
		if (!driver.getTitle().contains("Plancess - CRM"))
			loginPage.login(user);
		executor.softWaitForCondition(ExpectedConditions.titleContains("Plancess - CRM"));
		verifications.verifyTrue(driver.getTitle().contains("Plancess - CRM"),
				util.takeScreenshot(driver, "Verify Dashboard"));

		dashBoardPage = new DashboardPage(driver, wait);
		addCampaignPage = dashBoardPage.openAddCampaignPage();
		String campaignId = addCampaignPage.addCampaign(user);
		WebElement element = executor
				.getElement(By.xpath(".//*[@id='mytable']/tbody/tr/td[contains(.,'" + campaignId + "')]"));
		verifications.verifyTrue(element != null && element.isDisplayed(),
				util.takeScreenshot(driver, "verify campaign created."));

	}

	@DataProvider(name = "LmsDataProvider")
	public Object[][] LmsSignUpDataProvider() {

		ExcelReader excelReader = new ExcelReader();
		return excelReader.getUserDataFromExcel("testData.xlsx", "Campaign");

	}

}
