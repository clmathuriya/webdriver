package com.plancess.selenium.tests;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.AssessmentPage;
import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.LoginDialogPage;
import com.plancess.selenium.pages.ReportPage;
import com.plancess.selenium.pages.SignUpDialogPage;
import com.plancess.selenium.utils.ExcelReader;

public class TakeTourTest extends BaseTest {

	private LoginDialogPage loginDialogPage;
	private SignUpDialogPage signUpDialogPage;
	private Dashboard dashboard;
		
	private ReportPage reportPage;
	private AssessmentPage assessmentPage;


	@Test(enabled= false,dataProvider = "dashboardNewUserDataProvider", dataProviderClass = DashboardTest.class, groups = { "smoke","regression" })
	public void takeDashboardTourWithValidData(Map<String, String> user) {
		signUpDialogPage = landingPage.openSignUpDialogPage();
		signUpDialogPage.signUp(user);

		Dashboard.takeTour = true;
		dashboard = signUpDialogPage.verifyEmail(user).doLogin(user);

	}
	
	@Test(dataProvider = "takeTestValidData", groups = { "smoke", "regression" })
	public void takeTestTourWithValidDataTest(Map<String, String> user) {

		long timestamp = System.currentTimeMillis();

		user.put("email", "webuser" + timestamp + "@mailinator.com");
		user.put("subject", "physics");

		signUpDialogPage = landingPage.openSignUpDialogPage();
		signUpDialogPage.signUp(user);
		Dashboard.takeTour = true;
		dashboard = signUpDialogPage.verifyEmail(user).doLogin(user);

		executor.softWaitForWebElement(dashboard.getDashBoardButton());

		executor.assertTrue(dashboard.getStartAssessmentSection().isDisplayed(),
				"assert user login succefull and start assessment section displayed");

		executor.softWaitForWebElement(dashboard.getPhysicsSubjectSection());
		executor.softWaitForWebElement(dashboard.getPhysicsSubjectSection());
		executor.click(dashboard.getPhysicsSubjectSection(), "Physics subject section");
		executor.click(dashboard.getTakeSubjectTest(), "take subject test");
		
		executor.assertTrue(!executor.isElementExist(By.xpath("//*[@ng-if='noTopic']")),
				"Verify if no more tests error does not exist for this subject");
		executor.softWaitForWebElement(dashboard.getTimeRequired());
		executor.verifyEquals(dashboard.getTimeRequired().getText().trim(), user.get("timeRequired").trim(),
				"verify time required");
		executor.verifyEquals(dashboard.getTotalQuestions().getText().trim(), user.get("totalQuestions"),
				"verify number of total questions");
		executor.click(dashboard.getStartTest(), "start test button");
		AssessmentPage.takeTour = true;
		assessmentPage = new AssessmentPage(driver, wait);
		ReportPage.takeTour = true;
		reportPage = assessmentPage.takeAssessment(user);	
		
		//reportPage = new ReportPage(driver, wait);
		reportPage.verifyReport(user);
		dashboard.logoutUser();

	}
	
	@DataProvider(name = "takeTestValidData")
	public Object[][] takeTestValidData() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "assessment_valid");
	}
}
