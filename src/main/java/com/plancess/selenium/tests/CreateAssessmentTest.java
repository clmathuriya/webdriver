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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.pages.CreateAccessment;
import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.LandingPage;
import com.plancess.selenium.pages.LoginDialogPage;
import com.plancess.selenium.pages.ReportPage;
import com.plancess.selenium.utils.ExcelReader;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;

public class CreateAssessmentTest extends BaseTest {

	private WebDriver driver;
	private LoginDialogPage loginDialogPage;
	private LandingPage landingPage;
	private String pageTitle = "Plancess Dashboard";
	private WebDriverWait wait;
	private Executioner executor;
	private CreateAccessment createAssessment;
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
			landingPage = new LandingPage(driver, wait);

			util = Util.getInstance();
			verifications = Verifications.getInstance();

		} catch (MalformedURLException e) {
			Assert.fail("Unable to start selenium session make sure Grid hub is running on url :" + "http://" + host
					+ ":" + port + "/wd/hub");

		}
	}

	@AfterMethod
	public void tearDown(ITestResult testResult) {

		if (testResult.getStatus() == ITestResult.FAILURE) {
			verifications.verifyEquals(testResult.getStatus(), ITestResult.SUCCESS,
					util.takeScreenshot(driver, "verify test status for :" + testResult.getTestName()));
		}
		driver.quit();
	}

	@Test(dataProvider = "createAssessmentDataProvider", groups = { "smoke", "regression" })
	public void CreateAssessmentWithValidDataTest(Map<String, String> user) {
		loginDialogPage = landingPage.openLoginDialogPage();
		Dashboard dashboard = loginDialogPage.doLogin(user);
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(dashboard.getCreateAssessmentButton()));

		Assert.assertTrue(dashboard.getCreateAssessmentButton().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and start assessment section displayed"));
		// executor.softWaitForWebElement(ExpectedConditions.visibilityOf(dashboard.getToggleDropDown()));
		createAssessment = dashboard.navigateToCreateAccessment();
		executor.softWaitForWebElement(createAssessment.getCustomTestLink());
		selectCustomTest(user);

		createAssessment.getCreateTest().click();
		//-------------
		
		executor.softWaitForWebElement(dashboard.getStartTest());
		verifications.verifyEquals(dashboard.getTimeRequired().getText().trim(), user.get("timeRequired").trim(),
				util.takeScreenshot(driver, "verify time required"));
		verifications.verifyEquals(dashboard.getTotalQuestions().getText().trim(), user.get("totalQuestions"),
				util.takeScreenshot(driver, "verify number of total questions"));

		dashboard.getStartTest().click();
		executor.softWaitForWebElement(dashboard.getNextButton());

		// to test pause/resume button

		dashboard.getPauseTestButton().click();
		executor.softWaitForWebElement(dashboard.getRemainingTime());
		String remainingTime = dashboard.getRemainingTime().getText().trim();
		executor.softWaitForWebElement(dashboard.getResumeTest());
		verifications.verifyTrue(dashboard.getResumeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify resume test button displayed"));
		verifications.verifyEquals(dashboard.getRemainingTime().getText().trim(), remainingTime, util.takeScreenshot(
				driver, "verify remaining time not changing for paused test expected=" + remainingTime));
		dashboard.getResumeTest().click();

		// to test hint button and hint text
		
		//dashboard.getHintButton().click();
		//executor.softWaitForWebElement(dashboard.getHintText());
		//verifications.verifyTrue(dashboard.getHintText().isDisplayed(),util.takeScreenshot(driver, "verify hint text displayed"));

		// to test mark for review option

		dashboard.getMarkForReview().click();

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
		
/*
		reportPage = new ReportPage(driver, wait);
		executor.softWaitForWebElement(reportPage.getTopicTitle());
		verifications.verifyTrue(reportPage.getTopicTitle().isDisplayed(),
				util.takeScreenshot(driver, "verify topic title displayed"));
		verifications.verifyTrue(reportPage.getRecomendationsSection().isDisplayed(),
				util.takeScreenshot(driver, "verify recommendations section displayed"));
		verifications.verifyTrue(reportPage.getQuestionsWisePerformance().isDisplayed(),
				util.takeScreenshot(driver, "verify questions wise performance displayed"));
		createAssessment.getActions().click(dashboard.getDashBoardButton()).build().perform();
		executor.softWaitForWebElement(dashboard.getPerformanceSection());
		verifications.verifyTrue(dashboard.getPerformanceSection().isDisplayed(),
				util.takeScreenshot(driver, "verify performance section displayed on report page"));
		// to verify notification displayed for test completion
		dashboard.getNotificationsButton().click();
		String notificationItemText = dashboard.getNotificationItem().getText().toLowerCase();
		verifications.verifyTrue(
				notificationItemText.contains(user.get("subject")) && notificationItemText.contains("completed"),
				util.takeScreenshot(driver,
						"verify notification item contains test completed for subject" + user.get("subject")));

		dashboard.logoutUser();
*/
		
	}

	public CreateAccessment selectCustomTest(Map<String, String> user) {

		String subjectTopic = user.get("subject");
		String[] topics = subjectTopic.split(",");

		int len = topics.length;

		for (int i = 0; i < len; i++) {
			String[] topicList = topics[i].split(">");
			String subject = topicList[0];
			String module = topicList[1];
			String sub_Module = topicList[2];

			switch (subject.toLowerCase()) {

			case "physics":
				wait.until(ExpectedConditions.visibilityOf(createAssessment.getsubjectPhysicsLink()));
				createAssessment.getsubjectPhysicsLink().click();
				break;
			case "chemistry":
				wait.until(ExpectedConditions.visibilityOf(createAssessment.getsubjectChemistryLink()));
				createAssessment.getsubjectChemistryLink().click();
				break;
			case "math":
			case "maths":
			case "mathematics":
				wait.until(ExpectedConditions.visibilityOf(createAssessment.getsubjectMathematicsLink()));
				createAssessment.getsubjectMathematicsLink().click();
				break;
			default:
				Assert.fail("Subject :" + user.get("subject") + "not found");

			}

			WebElement toggleModule = executor.getElement(By.xpath("//*[normalize-space(.)='" + module + "']/../a"));

			if (toggleModule.findElement(By.tagName("span")).getAttribute("class").contains("plus")
					&& sub_Module.length() > 0) {
				toggleModule.click();
			}

			if (sub_Module != "") {
				// executor.getElement(By.xpath("//span[.='" + module +
				// "']")).click();
				// executor.getElement(By.xpath("//span[.='" + module +
				// "']")).click();
				executor.getElement(By.xpath("//span[.='" + sub_Module + "']")).click();
			} else {
				executor.getElement(By.xpath("//span[.='" + module + "']")).click();
			}

			String selectedTopics = "";
			for (WebElement e : createAssessment.getTopicsSelected()) {
				selectedTopics += e.getText();
			}

			Assert.assertTrue(selectedTopics.contains(sub_Module) && selectedTopics.contains(module),
					util.takeScreenshot(driver, "verify topic selected"));

		}
		executor.getElement(By.xpath("//label[normalize-space(.)='" + user.get("ExamType") + "']/input")).click();

		// executor.getElement(By.xpath("//label[normalize-space(.)='"
		// +user.get("ExamType")+ "']/input")).click();

		new Select(createAssessment.getExamDurationDropDown()).selectByVisibleText(user.get("TestDuration"));

		executor.getElement(By.xpath("//span[.='" + user.get("DifficultyLevel") + "']")).click();

		return new CreateAccessment(driver, wait);
	}

	@DataProvider(name = "createAssessmentDataProvider")
	public static Object[][] dashboardDataProvider() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "createAssessment");

	}

}
