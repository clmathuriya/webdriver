package com.plancess.selenium.tests;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.CreateAccessment;
import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.LoginDialogPage;
import com.plancess.selenium.pages.ReportPage;
import com.plancess.selenium.utils.ExcelReader;

public class CreateAssessmentTest extends BaseTest {

	private LoginDialogPage loginDialogPage;
	private CreateAccessment createAssessment;
	private ReportPage reportPage;

	@Test(dataProvider = "createAssessmentDataProvider", groups = { "smoke", "regression" })
	public void CreateAssessmentWithValidDataTest(Map<String, String> user) {
		loginDialogPage = landingPage.openLoginDialogPage();
		Dashboard dashboard = loginDialogPage.doLogin(user);
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(dashboard.getCreateAssessmentButton()));

		executor.assertTrue(dashboard.getCreateAssessmentButton().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and start assessment section displayed"));
		// executor.softWaitForWebElement(ExpectedConditions.visibilityOf(dashboard.getToggleDropDown()));
		createAssessment = dashboard.navigateToCreateAccessment();
		executor.softWaitForWebElement(createAssessment.getCustomTestLink());
		selectCustomTest(user);

		executor.click(createAssessment.getCreateTest(), "Begin Test");
		// createAssessment.getCreateTest().click();
		// -------------

		executor.softWaitForWebElement(dashboard.getStartTest());
		executor.verifyEquals(dashboard.getTimeRequired().getText().trim(), user.get("timeRequired").trim(),
				util.takeScreenshot(driver, "verify time required"));
		executor.verifyEquals(dashboard.getTotalQuestions().getText().trim(), user.get("totalQuestions"),
				util.takeScreenshot(driver, "verify number of total questions"));

		dashboard.getStartTest().click();
		executor.softWaitForWebElement(dashboard.getNextButton());

		// to test pause/resume button

		dashboard.getPauseTestButton().click();
		executor.softWaitForWebElement(dashboard.getRemainingTime());
		String remainingTime = dashboard.getRemainingTime().getText().trim();
		executor.softWaitForWebElement(dashboard.getResumeTest());
		executor.verifyTrue(dashboard.getResumeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify resume test button displayed"));
		executor.verifyEquals(dashboard.getRemainingTime().getText().trim(), remainingTime, util.takeScreenshot(driver,
				"verify remaining time not changing for paused test expected=" + remainingTime));
		dashboard.getResumeTest().click();

		// to test hint button and hint text

		// dashboard.getHintButton().click();
		// executor.softWaitForWebElement(dashboard.getHintText());
		// executor.verifyTrue(dashboard.getHintText().isDisplayed(),util.takeScreenshot(driver,
		// "verify hint text displayed"));

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

		reportPage = new ReportPage(driver, wait);
		executor.softWaitForWebElement(reportPage.getTopicTitle());
		executor.verifyTrue(reportPage.getTopicTitle().isDisplayed(),
				util.takeScreenshot(driver, "verify topic title displayed"));
		executor.verifyTrue(reportPage.getRecomendationsSection().isDisplayed(),
				util.takeScreenshot(driver, "verify recommendations section displayed"));
		executor.verifyTrue(reportPage.getQuestionsWisePerformance().isDisplayed(),
				util.takeScreenshot(driver, "verify questions wise performance displayed"));
		createAssessment.getActions().click(dashboard.getDashBoardButton()).build().perform();
		executor.softWaitForWebElement(dashboard.getPerformanceSection());
		executor.verifyTrue(dashboard.getPerformanceSection().isDisplayed(),
				util.takeScreenshot(driver, "verify performance section displayed on report page"));
		// to verify notification displayed for test completion
		dashboard.getNotificationsButton().click();
		String notificationItemText = dashboard.getNotificationItem().getText().toLowerCase();
		executor.verifyTrue(
				notificationItemText.contains(user.get("subject")) && notificationItemText.contains("completed"),
				util.takeScreenshot(driver,
						"verify notification item contains test completed for subject" + user.get("subject")));

		dashboard.logoutUser();

	}

	public CreateAccessment selectCustomTest(Map<String, String> user) {

		String subjectTopic = user.get("subject");
		String[] topics = subjectTopic.split(",");

		int len = topics.length;

		for (int i = 0; i < len; i++) {
			String[] topicList = topics[i].split(">");
			String subject = topicList[0];
			String module = topicList[1];
			String sub_Module = "";
			if (topicList.length > 2)
				sub_Module = topicList[2];

			switch (subject.toLowerCase()) {

			case "physics":
				executor.softWaitForWebElement(createAssessment.getsubjectPhysicsLink(), "wait for physics link");
				// wait.until(ExpectedConditions.visibilityOf(createAssessment.getsubjectPhysicsLink()));
				executor.click(createAssessment.getsubjectPhysicsLink(), "Physics Link");
				// createAssessment.getsubjectPhysicsLink().click();
				break;
			case "chemistry":
				wait.until(ExpectedConditions.visibilityOf(createAssessment.getsubjectChemistryLink()));
				createAssessment.getsubjectChemistryLink().click();
				break;
			case "math":
			case "maths":
			case "mathematics":
				executor.softWaitForWebElement(createAssessment.getsubjectMathematicsLink());
				// wait.until(ExpectedConditions.visibilityOf(createAssessment.getsubjectMathematicsLink()));
				executor.click(createAssessment.getsubjectMathematicsLink(), "Subject Mathematics Link");
				// createAssessment.getsubjectMathematicsLink().click();
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
				// executor.getElement(By.xpath("//span[normalize-space(.)='" +
				// sub_Module + "']")).click();
				executor.getElement(By.xpath("//input[@name='" + sub_Module + "']")).click();
			} else {
				// executor.getElement(By.xpath("//span[normalize-space(.)='" +
				// module + "']")).click();
				executor.getElement(By.xpath("//input[@name='" + module + "']")).click();
			}

			String selectedTopics = "";
			for (WebElement e : createAssessment.getTopicsSelected()) {
				// selectedTopics += e.getText();
				selectedTopics += e.getAttribute("innerHTML");
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
