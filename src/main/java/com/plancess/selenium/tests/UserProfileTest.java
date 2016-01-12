package com.plancess.selenium.tests;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.ProfilePage;
import com.plancess.selenium.utils.ExcelReader;

public class UserProfileTest extends BaseTest {

	private String pageTitle = "Plancess Dashboard";
	private ProfilePage userProfile;
	private Dashboard dashboard;
	//static String msg = "";

	@Test(dataProvider = "userProfileValidData", groups = { "smoke", "regression" })
	public void userProfileWithValidDataTest(Map<String, String> user) {
		// user.put("email", "clmathuriya@gmail.com");

		dashboard = landingPage.openLoginDialogPage().doLogin(user);

		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(dashboard.getStartAssessmentSection()));

		executor.assertTrue(dashboard.getStartAssessmentSection().isDisplayed(),
				"assert user login succefull and start assessment section displayed");
		userProfile = dashboard.navigateToUserProfile();
		executor.softWaitForWebElement(userProfile.getFirstName());
		userProfile.updateUserProfile(user);
		
		
		// System.out.println(userProfile.getAlertMessage().getText());
/*
		executor.softWaitForCondition(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				// System.out.println(userProfile.getAlertMessage().getAttribute("alert-message"));
				System.out.println(userProfile.getAlertMessage().getAttribute("innerHTML") + "1");
				System.out.println(userProfile.getAlertMessage().getText() + "2");
				msg = userProfile.getAlertMessage().getAttribute("innerHTML");
				return msg.contains("Your data have been saved successfully.");
				
				 * return
				 * (userProfile.getAlertMessage().getAttribute("alert-message")
				 * + userProfile.getAlertMessage().getText()).contains(
				 * "Your data have been saved successfully.");
				 
			}
		});

		System.out.println(msg);
		executor.assertEquals(msg, "Your data have been saved successfully.", "Data have been saved successfully");
*/
	}

	@DataProvider(name = "userProfileValidData")
	public static Object[][] dashboardDataProvider() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "userProfile");

	}

}
