package com.plancess.selenium.tests;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.AssessmentPage;
import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.ReportPage;
import com.plancess.selenium.utils.ExcelReader;

public class AssessmentTest extends BaseTest {

	private Dashboard dashboard;
	private ReportPage reportPage;
	private AssessmentPage assessmentPage;

	@Test(dataProvider = "noMoreTestDataProvider", groups = { "regression" })
	public void noMoreTestErorTest(Map<String, String> user) {

		// takeTestWithValidDataTest(user);

		dashboard = landingPage.openLoginDialogPage().doLogin(user);

		executor.assertTrue(dashboard.getStartAssessmentSection().isDisplayed(),
				"assert user login succefull and start assessment section displayed");

		switch (user.get("subject").toLowerCase()) {

		case "physics":
			executor.softWaitForWebElement(dashboard.getPhysicsSubjectSection());
			executor.click(dashboard.getPhysicsSubjectSection(), "Physics subject section");
			executor.click(dashboard.getTakeSubjectTest(), "take subject test");
			break;
		case "chemistry":
			executor.softWaitForWebElement(dashboard.getChemistrySubjectSection());
			executor.click(dashboard.getChemistrySubjectSection(), "Physics subject section");
			executor.click(dashboard.getTakeSubjectTest(), "take subject test");
			break;
		case "math":
		case "maths":
		case "mathematics":
			executor.softWaitForWebElement(dashboard.getMathsSubjectSection());
			executor.click(dashboard.getMathsSubjectSection(), "Physics subject section");
			executor.click(dashboard.getTakeSubjectTest(), "take subject test");
			break;
		default:
			Assert.fail("Subject :" + user.get("subject") + "not found");

		}
		executor.softWaitForWebElement(dashboard.getNoTopicMsg());
		executor.assertTrue(executor.isElementExist(By.xpath("//*[@ng-if='noTopic']")),
				"Verify if no more tests exist for this subject");

		dashboard.logoutUser();

	}

	@Test(dataProvider = "takeTestValidData", groups = { "smoke", "regression" })
	public void takeTestWithValidDataTest(Map<String, String> user) {

		dashboard = landingPage.openLoginDialogPage().doLogin(user);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@type='submit']")));

		Assert.assertTrue(dashboard.getStartAssessmentSection().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and start assessment section displayed"));

		switch (user.get("subject").toLowerCase()) {

		case "physics":
			executor.softWaitForWebElement(dashboard.getPhysicsSubjectSection());
			dashboard.getPhysicsSubjectSection().click();
			// to test cancel button

			executor.softWaitForWebElement(dashboard.getStartTest());
			// dashboard.getStartTest().click();
			dashboard.getCancelButton().click();
			dashboard.getPhysicsSubjectSection().click();
			executor.softWaitForWebElement(dashboard.getStartTest());

			break;
		case "chemistry":
			executor.softWaitForWebElement(dashboard.getChemistrySubjectSection());
			dashboard.getChemistrySubjectSection().click();
			// to test cancel button

			executor.softWaitForWebElement(dashboard.getStartTest());
			// dashboard.getStartTest().click();
			dashboard.getCancelButton().click();
			dashboard.getChemistrySubjectSection().click();
			executor.softWaitForWebElement(dashboard.getStartTest());

			break;
		case "math":
		case "maths":
		case "mathematics":
			executor.softWaitForWebElement(dashboard.getMathsSubjectSection());
			dashboard.getMathsSubjectSection().click();
			// to test cancel button

			executor.softWaitForWebElement(dashboard.getStartTest());
			// dashboard.getStartTest().click();
			dashboard.getCancelButton().click();
			dashboard.getMathsSubjectSection().click();
			executor.softWaitForWebElement(dashboard.getStartTest());

			break;
		default:
			Assert.fail("Subject :" + user.get("subject") + "not found");

		}
		Assert.assertTrue(!executor.isElementExist(By.cssSelector("div.toast-title")),
				util.takeScreenshot(driver, "Verify if no more tests error does not exist for this subject"));
		verifications.verifyEquals(dashboard.getTimeRequired().getText().trim(), user.get("timeRequired").trim(),
				util.takeScreenshot(driver, "verify time required"));
		verifications.verifyEquals(dashboard.getTotalQuestions().getText().trim(), user.get("totalQuestions"),
				util.takeScreenshot(driver, "verify number of total questions"));

		dashboard.getStartTest().click();
		executor.softWaitForWebElement(dashboard.getNextButton());

		assessmentPage = new AssessmentPage(driver, wait);
		assessmentPage.takeAssessment(user);

		reportPage = new ReportPage(driver, wait);
		reportPage.verifyReport(user);
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
