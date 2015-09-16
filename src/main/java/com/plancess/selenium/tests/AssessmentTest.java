package com.plancess.selenium.tests;

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
	public void setUp(@Optional("localhost") String host, @Optional("4444") String port, @Optional("WINDOWS") String os,
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

	@Test(dataProvider = "noMoreTestDataProvider", groups = { "regression" })
	public void noMoreTestErorTest(Map<String, String> user) {

		// takeTestWithValidDataTest(user);

		Dashboard dashboard = loginPage.doLogin(user);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@type='submit']")));

		Assert.assertTrue(dashboard.getStartAssessmentSection().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and start assessment section displayed"));

		switch (user.get("subject").toLowerCase()) {

		case "physics":
			executor.softWaitForWebElement(dashboard.getPhysicsTakeTest());
			dashboard.getPhysicsTakeTest().click();
			break;
		case "chemistry":
			executor.softWaitForWebElement(dashboard.getChemistryTakeTest());
			dashboard.getChemistryTakeTest().click();
			break;
		case "math":
		case "maths":
		case "mathematics":
			executor.softWaitForWebElement(dashboard.getMathsTakeTest());
			dashboard.getMathsTakeTest().click();
			break;
		default:
			Assert.fail("Subject :" + user.get("subject") + "not found");

		}
		executor.softWaitForWebElement(dashboard.getToastTitle());
		Assert.assertTrue(executor.isElementExist(By.cssSelector("div.toast-title")),
				util.takeScreenshot(driver, "Verify if no more tests exist for this subject"));

		dashboard.logoutUser();

	}

	@Test(dataProvider = "takeTestValidData", groups = { "smoke", "regression" })
	public void takeTestWithValidDataTest(Map<String, String> user) {

		Dashboard dashboard = loginPage.doLogin(user);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@type='submit']")));

		Assert.assertTrue(dashboard.getStartAssessmentSection().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and start assessment section displayed"));

		switch (user.get("subject").toLowerCase()) {

		case "physics":
			executor.softWaitForWebElement(dashboard.getPhysicsTakeTest());
			dashboard.getPhysicsTakeTest().click();
			break;
		case "chemistry":
			executor.softWaitForWebElement(dashboard.getChemistryTakeTest());
			dashboard.getChemistryTakeTest().click();
			break;
		case "math":
		case "maths":
		case "mathematics":
			executor.softWaitForWebElement(dashboard.getMathsTakeTest());
			dashboard.getMathsTakeTest().click();
			break;
		default:
			Assert.fail("Subject :" + user.get("subject") + "not found");

		}
		Assert.assertTrue(!executor.isElementExist(By.cssSelector("div.toast-title")),
				util.takeScreenshot(driver, "Verify if no more tests exist for this subject"));
		executor.softWaitForWebElement(dashboard.getStartTest());
		dashboard.getStartTest().click();
		executor.softWaitForWebElement(dashboard.getNextButton());

		// to test pause/resume button

		dashboard.getPauseTestButton().click();
		executor.softWaitForWebElement(dashboard.getResumeTest());
		Assert.assertTrue(dashboard.getResumeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify resume test button displayed"));

		// to test hint button and hint text

		dashboard.getHintButton().click();
		executor.softWaitForWebElement(dashboard.getHintText());
		Assert.assertTrue(dashboard.getHintText().isDisplayed(),
				util.takeScreenshot(driver, "verify hint text displayed"));

		int count = 0;
		switch (user.get("answerChoices").toLowerCase()) {

		case "a":
			while (dashboard.getNextButton().isEnabled() && count++ <= 90) {
				for (WebElement choice : dashboard.getAnswerChoicesA()) {
					if (choice.isDisplayed()) {
						choice.click();
					}
				}
				if (dashboard.getNextButton().isEnabled()
						&& dashboard.getNextButton().getAttribute("aria-disabled").equals("false")) {
					dashboard.getNextButton().click();
				} else {
					dashboard.getSubmitTestButton().click();
					dashboard.getConfirmSubmitTestButton().click();
					break;
				}
			}
			dashboard.getSubmitTestButton().click();
			dashboard.getConfirmSubmitTestButton().click();
			break;

		case "b":
			while (dashboard.getNextButton().isEnabled() && count++ <= 90) {
				for (WebElement choice : dashboard.getAnswerChoicesB()) {
					if (choice.isDisplayed()) {
						choice.click();
					}
				}
				if (dashboard.getNextButton().isEnabled()
						&& dashboard.getNextButton().getAttribute("aria-disabled").equals("false")) {
					dashboard.getNextButton().click();
				} else {
					dashboard.getSubmitTestButton().click();
					dashboard.getConfirmSubmitTestButton().click();
				}
			}
			dashboard.getSubmitTestButton().click();
			dashboard.getConfirmSubmitTestButton().click();
			break;
		case "c":
			while (dashboard.getNextButton().isEnabled() && count++ <= 90) {
				for (WebElement choice : dashboard.getAnswerChoicesC()) {
					if (choice.isDisplayed()) {
						choice.click();
					}
				}
				if (dashboard.getNextButton().isEnabled()
						&& dashboard.getNextButton().getAttribute("aria-disabled").equals("false")) {
					dashboard.getNextButton().click();
				} else {
					dashboard.getSubmitTestButton().click();
					dashboard.getConfirmSubmitTestButton().click();
				}
			}
			dashboard.getSubmitTestButton().click();
			dashboard.getConfirmSubmitTestButton().click();
			break;
		case "d":
			while (dashboard.getNextButton().isEnabled() && count++ <= 90) {
				for (WebElement choice : dashboard.getAnswerChoicesD()) {
					if (choice.isDisplayed()) {
						choice.click();
					}
				}
				if (dashboard.getNextButton().isEnabled()
						&& dashboard.getNextButton().getAttribute("aria-disabled").equals("false")) {
					dashboard.getNextButton().click();
				} else {
					dashboard.getSubmitTestButton().click();
					dashboard.getConfirmSubmitTestButton().click();
				}
			}
			dashboard.getSubmitTestButton().click();
			dashboard.getConfirmSubmitTestButton().click();
			break;
		default:
			Assert.fail("invalid answer choic option.");

		}

		reportPage = new ReportPage(driver, wait);
		executor.softWaitForWebElement(reportPage.getTopicTitle());
		Assert.assertTrue(reportPage.getTopicTitle().isDisplayed(),
				util.takeScreenshot(driver, "verify topic title displayed"));
		Assert.assertTrue(reportPage.getRecomendationsSection().isDisplayed(),
				util.takeScreenshot(driver, "verify recommendations section displayed"));
		Assert.assertTrue(reportPage.getQuestionsWisePerformance().isDisplayed(),
				util.takeScreenshot(driver, "verify questions wise performance displayed"));
		dashboard.logoutUser();

	}

	@DataProvider(name = "noMoreTestDataProvider")
	public Object[][] noMoreTestDataProvider() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "assessment_no_test");
	}

	@DataProvider(name = "takeTestValidData")
	public Object[][] takeTestValidData() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "assessment_valid");
	}

}
