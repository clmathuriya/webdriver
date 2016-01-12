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
import com.plancess.selenium.pages.PaymentPage;
import com.plancess.selenium.pages.ReportPage;
import com.plancess.selenium.pages.SignUpDialogPage;
import com.plancess.selenium.utils.ExcelReader;

public class PaymentTest extends BaseTest {

	private LoginDialogPage loginDialogPage;
	private SignUpDialogPage signUpDialogPage;
	private Dashboard dashboard;
		
	private ReportPage reportPage;
	private AssessmentPage assessmentPage;
	
	private PaymentPage paymentPage;


/*	@Test(enabled= false,dataProvider = "dashboardNewUserDataProvider", dataProviderClass = DashboardTest.class, groups = { "smoke","regression" })*/
	@Test(dataProvider = "makePaymentLandingPageValidData", groups = { "smoke","regression" })
	public void makePaymentFromLandingPageWithValidData(Map<String, String> user) {
		paymentPage = landingPage.openPaymentPage();
		executor.softWaitForWebElement(paymentPage.getPackages());
		executor.verifyTrue(paymentPage.getPackages().isDisplayed(), "Packages are displayed");
		
		executor.verifyTrue(executor.isElementExist(By.xpath(".//*[.='"+user.get("packageName")+"']")),
				"Verify if Package Exists: "+user.get("packageName"));
		
		executor.assertTrue(executor.isElementExist(By.xpath(".//*[.='"+user.get("price")+"']")),
				"Verify if Package Exists: "+user.get("price"));
				
		paymentPage.selectPackage(user.get("packageSelected"));
		
		//executor.softWaitForWebElement(paymentPage.getPaymentModal());
		
		paymentPage.makePayment(user);
		//paymentPage.getPaymentHeader().getText();
		executor.assertEquals(paymentPage.getPaymentHeader().getText().contains(user.get("packageName"))
				&&paymentPage.getPaymentHeader().getText().contains("Preplane"), 
				true, "Verify if Payment modal has correct product name");
		executor.assertEquals(paymentPage.getSubmitbtn().getText().contains(user.get("price")), 
				true, "Verify if Payment modal has correct product name");
		
		executor.sendKeys(paymentPage.getEmail(), user.get("email"), "Email");
		
		

		
		

	}
	
	@Test(enabled= false,dataProvider = "makePaymentValidData", groups = { "smoke", "regression" })
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
	
	@DataProvider(name = "dashboardNewUserDataProvider")
	public Object[][] takeTestValidData() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "assessment_valid");
	}
	
	@DataProvider(name = "makePaymentLandingPageValidData")
	public static Object[][] signUpDataProviderMandatoryData() {
		Map<String, String> user = new HashMap<String, String>();

		Object[][] dataSet = new Object[1][1];
		long timestamp = System.currentTimeMillis();

		user.put("password", "P@ssw0rd");
		//user.put("confirm_password", "P@ssw0rd");
		user.put("email", "webuser" + timestamp + "@mailinator.com");
		dataSet[0][0] = user;
		user.put("price", "1599");
		user.put("packageName", "JEE Main & Advanced Assessment 2016 Online");
		user.put("packageSelected", "1");
		user.put("contact", "9167360969");
		user.put("bank", "Axis Bank");
		return dataSet;

	}
}
