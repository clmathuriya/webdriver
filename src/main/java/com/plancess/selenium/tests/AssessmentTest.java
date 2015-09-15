package com.plancess.selenium.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.HomePage;
import com.plancess.selenium.pages.LoginPage;
import com.plancess.selenium.pages.ProfilePage;
import com.plancess.selenium.pages.ReportPage;
import com.plancess.selenium.utils.ExcelReader;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;

public class AssessmentTest extends BaseTest {

	private WebDriver driver;
	private HomePage homePage;
	private LoginPage loginPage;
	private String pageTitle = "Plancess Dashboard";
	private WebDriverWait wait;
	private Executioner executor;
	private ProfilePage userProfile;
	private ReportPage reportPage;

	@Parameters({ "host_ip", "port", "os", "browser", "browserVersion" })
	@BeforeMethod
	public void setUp(@Optional("localhost") String host, @Optional("4444") String port, @Optional("LINUX") String os,
			@Optional("firefox") String browser, @Optional("40.0") String browserVersion) {

		try {

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName(browser);
			// capabilities.setCapability("version", browserVersion);
			capabilities.setCapability("platform", Platform.valueOf(os));

			this.driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), capabilities);

			// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 30);
			executor = new Executioner(driver, wait);
			homePage = new HomePage(driver, wait);

			loginPage = homePage.openLoginPage();
			util = Util.getInstance();
			verifications = Verifications.getInstance();

		} catch (MalformedURLException e) {
			Assert.fail("Unable to start selenium session make sure Grid hub is running on url :" + "http://" + host
					+ ":" + port + "/wd/hub");

		}
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(dataProvider = "takeTestValidData", groups = { "smoke", "regression" })
	public void takeTestWithValidDataTest(Map<String, String> user) {

		Dashboard dashboard = loginPage.doLogin(user);
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(dashboard.getStartAssessmentSection()));

		Assert.assertTrue(dashboard.getStartAssessmentSection().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and start assessment section displayed"));

		switch (user.get("subject").toLowerCase()) {

		case "physics":
			dashboard.getPhysicsTakeTest().click();
		case "chemistry":
			dashboard.getChemistryTakeTest().click();
		case "math":
		case "maths":
		case "mathematics":
			dashboard.getMathsTakeTest().click();
		default:
			Assert.fail("Subject :" + user.get("subject") + "not found");

		}
		dashboard.getStartTest().click();

		for (WebElement choice : dashboard.getAnswerChoicesA()) {
			if (choice.isDisplayed()) {
				choice.click();
			}
		}
		int count = 0;
		switch (user.get("answerChoices").toLowerCase()) {

		case "a":
			while (dashboard.getNextButton().isEnabled() && count <= 90) {
				for (WebElement choice : dashboard.getAnswerChoicesA()) {
					if (choice.isDisplayed()) {
						choice.click();
					}
				}
				if (dashboard.getNextButton().isEnabled()) {
					dashboard.getNextButton().click();
				} else {
					dashboard.getSubmitTestButton().click();
					dashboard.getConfirmSubmitTestButton().click();
				}
			}

		case "b":
			while (dashboard.getNextButton().isEnabled() && count <= 90) {
				for (WebElement choice : dashboard.getAnswerChoicesB()) {
					if (choice.isDisplayed()) {
						choice.click();
					}
				}
				if (dashboard.getNextButton().isEnabled()) {
					dashboard.getNextButton().click();
				} else {
					dashboard.getSubmitTestButton().click();
					dashboard.getConfirmSubmitTestButton().click();
				}
			}
		case "c":
			while (dashboard.getNextButton().isEnabled() && count <= 90) {
				for (WebElement choice : dashboard.getAnswerChoicesC()) {
					if (choice.isDisplayed()) {
						choice.click();
					}
				}
				if (dashboard.getNextButton().isEnabled()) {
					dashboard.getNextButton().click();
				} else {
					dashboard.getSubmitTestButton().click();
					dashboard.getConfirmSubmitTestButton().click();
				}
			}
		case "d":
			while (dashboard.getNextButton().isEnabled() && count <= 90) {
				for (WebElement choice : dashboard.getAnswerChoicesD()) {
					if (choice.isDisplayed()) {
						choice.click();
					}
				}
				if (dashboard.getNextButton().isEnabled()) {
					dashboard.getNextButton().click();
				} else {
					dashboard.getSubmitTestButton().click();
					dashboard.getConfirmSubmitTestButton().click();
				}
			}

		}

		reportPage = new ReportPage(driver, wait);
		Assert.assertTrue(reportPage.getTopicTitle().isDisplayed(),
				util.takeScreenshot(driver, "verify toast title displayed"));
		Assert.assertTrue(reportPage.getRecomendationsSection().isDisplayed(),
				util.takeScreenshot(driver, "verify toast title displayed"));
		Assert.assertTrue(reportPage.getQuestionsWisePerformance().isDisplayed(),
				util.takeScreenshot(driver, "verify toast title displayed"));

	}

	@DataProvider(name = "takeTestValidData")
	public Object[][] takeTestValidData() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "assessment");
	}

}
