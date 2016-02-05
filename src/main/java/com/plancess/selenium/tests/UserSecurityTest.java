package com.plancess.selenium.tests;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.SecurityPage;

public class UserSecurityTest extends BaseTest {

	private SecurityPage userSecurity;

	@Test(dataProvider = "userSecurityValidData", groups = { "smoke", "regression" })
	public void userProfileSecurityWithValidDataTest(Map<String, String> user) {
		// user.put("email", "clmathuriya@gmail.com");

		Dashboard dashboard = landingPage.openLoginDialogPage().doLogin(user);
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(dashboard.getCreateAssessmentButton()));

		executor.assertTrue(dashboard.getCreateAssessmentButton().isDisplayed(),
				"assert user login succefull and start assessment section displayed");
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(dashboard.getToggleDropDown()));
		executor.softWaitForWebElement(ExpectedConditions.elementToBeClickable(dashboard.getToggleDropDown()));
		userSecurity = dashboard.navigateToUserSecurity();
		executor.softWaitForWebElement(userSecurity.getCurrentPassword());
		userSecurity.updateUserSecurity(user);
		executor.softWaitForCondition(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				System.out.println(userSecurity.getAlertMessage().getAttribute("alert-message")
						+ userSecurity.getAlertMessage().getText());

				return (userSecurity.getAlertMessage().getAttribute("alert-message")
						+ userSecurity.getAlertMessage().getText())
								.contains("Your password has been changed successfully");
			}
		});
		executor.assertEquals(userSecurity.getAlertMessage().getText(), "Your password has been changed successfully.",
				"Your password has been changed successfully");

		String currentPassword = user.get("currentPassword");
		user.put("currentPassword", user.get("newPassword"));
		user.put("newPassword", currentPassword);
		user.put("confirmPassword", currentPassword);
		userSecurity.updateUserSecurity(user);
		executor.softWaitForCondition(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				System.out.println(userSecurity.getAlertMessage().getAttribute("alert-message")
						+ userSecurity.getAlertMessage().getText());

				return (userSecurity.getAlertMessage().getAttribute("alert-message")
						+ userSecurity.getAlertMessage().getText())
								.contains("Your password has been changed successfully");
			}
		});	

	}

	// update user security data providers

	@DataProvider(name = "userSecurityValidData")
	public static Object[][] userSecurityDataProviderWithValidDetails() {

		Object[][] dataSet = new Object[1][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("email", "chhaganlal.m@plancess.com");
		user.put("password", "P@ssw0rd");
		user.put("currentPassword", "P@ssw0rd");
		user.put("newPassword", "P@ssw0rd1");
		user.put("confirmPassword", "P@ssw0rd1");

		dataSet[0][0] = user;

		return dataSet;
	}

}
