package com.plancess.selenium.tests;

import java.util.Map;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.AssessmentPage;
import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.ReportPage;
import com.plancess.selenium.pages.SignUpDialogPage;
import com.plancess.selenium.utils.ExcelReader;

public class AssessmentTest extends BaseTest {

	private Dashboard dashboard;
	private ReportPage reportPage;
	private AssessmentPage assessmentPage;
	private SignUpDialogPage signUpDialogPage;

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

		long timestamp = System.currentTimeMillis();

		user.put("email", "webuser" + timestamp + "@mailinator.com");

		signUpDialogPage = landingPage.openSignUpDialogPage();
		signUpDialogPage.signUp(user);
		dashboard = signUpDialogPage.verifyEmail(user).doLogin(user);
		executor.softWaitForWebElement(dashboard.getDashBoardButton());

		executor.assertTrue(dashboard.getStartAssessmentSection().isDisplayed(),
				"assert user login succefull and start assessment section displayed");

		switch (user.get("subject").toLowerCase()) {

		case "physics":
			executor.softWaitForWebElement(dashboard.getPhysicsSubjectSection());
			executor.click(dashboard.getPhysicsSubjectSection(), "Physics subject section");
			executor.click(dashboard.getTakeSubjectTest(), "take subject test");
			// to test cancel button

			executor.softWaitForWebElement(dashboard.getStartTest());
			// dashboard.getStartTest().click();
			executor.click(dashboard.getCancelButton(), "Cancel button");

			executor.softWaitForWebElement(dashboard.getPhysicsSubjectSection());
			executor.click(dashboard.getPhysicsSubjectSection(), "Physics subject section");
			executor.click(dashboard.getTakeSubjectTest(), "take subject test");

			break;
		case "chemistry":
			executor.softWaitForWebElement(dashboard.getChemistrySubjectSection());
			executor.mouseClick(dashboard.getChemistrySubjectSection());
			executor.click(dashboard.getTakeSubjectTest(), "take subject test");
			// to test cancel button

			executor.softWaitForWebElement(dashboard.getStartTest());
			// dashboard.getStartTest().click();
			executor.click(dashboard.getCancelButton(), "Cancel button");

			executor.softWaitForWebElement(dashboard.getChemistrySubjectSection());
			executor.click(dashboard.getChemistrySubjectSection(), "Chemistry subject section");
			executor.click(dashboard.getTakeSubjectTest(), "take subject test");

			break;
		case "math":
		case "maths":
		case "mathematics":
			executor.softWaitForWebElement(dashboard.getMathsSubjectSection());
			executor.click(dashboard.getMathsSubjectSection(), "Maths subject section");
			executor.click(dashboard.getTakeSubjectTest(), "take subject test");
			// to test cancel button

			executor.softWaitForWebElement(dashboard.getStartTest());
			// dashboard.getStartTest().click();
			executor.click(dashboard.getCancelButton(), "Cancel button");

			executor.softWaitForWebElement(dashboard.getMathsSubjectSection());
			executor.click(dashboard.getMathsSubjectSection(), "Maths subject section");
			executor.click(dashboard.getTakeSubjectTest(), "take subject test");
			executor.softWaitForWebElement(dashboard.getStartTest());

			break;
		default:
			Assert.fail("Subject :" + user.get("subject") + "not found");

		}
		executor.assertTrue(!executor.isElementExist(By.xpath("//*[@ng-if='noTopic']")),
				"Verify if no more tests error does not exist for this subject");
		executor.verifyEquals(dashboard.getTimeRequired().getText().trim(), user.get("timeRequired").trim(),
				"verify time required");
		executor.verifyEquals(dashboard.getTotalQuestions().getText().trim(), user.get("totalQuestions"),
				"verify number of total questions");
		executor.click(dashboard.getStartTest(), "start test button");

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
