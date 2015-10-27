package com.plancess.selenium.tests;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.AssessmentPage;
import com.plancess.selenium.pages.CreateAccessment;
import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.LoginDialogPage;
import com.plancess.selenium.pages.ReportPage;
import com.plancess.selenium.utils.ExcelReader;

public class CreateAssessmentTest extends BaseTest {

	private LoginDialogPage loginDialogPage;
	private CreateAccessment createAssessment;
	private ReportPage reportPage;
	private AssessmentPage assessmentPage;

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

		executor.click(dashboard.getStartTest(), "Start The Test");
		// dashboard.getStartTest().click();
		executor.softWaitForWebElement(dashboard.getNextButton());

		assessmentPage = new AssessmentPage(driver, wait);
		assessmentPage.takeAssessment(user);

		reportPage = new ReportPage(driver, wait);
		reportPage.verifyReport(user);
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
				executor.softWaitForWebElement(createAssessment.getsubjectChemistryLink(), "wait for chemistry link");
				// wait.until(ExpectedConditions.visibilityOf(createAssessment.getsubjectChemistryLink()));
				executor.click(createAssessment.getsubjectChemistryLink(), "Chemistry Link");
				// createAssessment.getsubjectChemistryLink().click();
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
			WebElement toggleModule = executor.softWaitForWebElement(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//*[normalize-space(.)='" + module + "']/../a")));

			// WebElement toggleModule =
			// executor.getElement(By.xpath("//*[normalize-space(.)='" + module
			// + "']/../a"));

			if (toggleModule.findElement(By.tagName("span")).getAttribute("class").contains("plus")
					&& sub_Module.length() > 0) {
				executor.click(toggleModule, module);
				// toggleModule.click();
			}

			if (sub_Module != "") {

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

			executor.assertTrue(selectedTopics.contains(sub_Module) && selectedTopics.contains(module),
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
