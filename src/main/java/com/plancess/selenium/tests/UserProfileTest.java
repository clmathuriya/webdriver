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

		executor.softWaitForCondition(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				System.out.println(userProfile.getAlertMessage().getAttribute("alert-message"));

				return (userProfile.getAlertMessage().getAttribute("alert-message")
						+ userProfile.getAlertMessage().getText()).contains("Your data have been saved successfully.");
			}
		});

		executor.assertEquals(userProfile.getAlertMessage().getText(), "Your data have been saved successfully.",
				"Data have been saved successfully");

	}

	@DataProvider(name = "userProfileValidData")
	public static Object[][] dashboardDataProvider() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "userProfile");

	}

}
