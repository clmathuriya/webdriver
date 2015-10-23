package com.plancess.selenium.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
import com.plancess.selenium.utils.DataProviderClass;
import com.plancess.selenium.utils.ExcelReader;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;

public class UserProfileTest extends BaseTest {

	// private WebDriver driver;
	// private HomePage homePage;
	// private LoginPage loginPage;
	private String pageTitle = "Plancess Dashboard";
	// private WebDriverWait wait;
	// private Executioner executor;
	private ProfilePage userProfile;

	private Dashboard dashboard;

	
	@Test(dataProvider = "userProfileValidData", groups = { "smoke", "regression" })
	public void userProfileWithValidDataTest(Map<String, String> user) {

		dashboard = landingPage.openLoginDialogPage().doLogin(user);
		
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(dashboard.getStartAssessmentSection()));

		executor.assertTrue(dashboard.getStartAssessmentSection().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and start assessment section displayed"));
		userProfile = dashboard.navigateToUserProfile();
		executor.softWaitForWebElement(userProfile.getFirstName());
		userProfile.updateUserProfile(user);
		// executor.softWaitForWebElement(userProfile.getAlertMessage());
		executor.softWaitForCondition(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				System.out.println(userProfile.getAlertMessage().getAttribute("alert-message"));

				return (userProfile.getAlertMessage().getAttribute("alert-message")
						+ userProfile.getAlertMessage().getText()).contains("Your data have been saved successfully.");
			}
		});

		executor.assertEquals(
				userProfile.getAlertMessage().getAttribute("alert-message") + userProfile.getAlertMessage().getText(),
				"Your data have been saved successfully.", "Data have been saved successfully");
		/*
		 * Assert.assertEquals(userProfile.getToastMessage().getText(),
		 * "Your data have been saved successfully.",
		 * util.takeScreenshot(driver, "verify toast message displayed"));
		 */
	}

	@DataProvider(name = "userProfileValidData")
	public static Object[][] dashboardDataProvider() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "userProfile");

	}

}
