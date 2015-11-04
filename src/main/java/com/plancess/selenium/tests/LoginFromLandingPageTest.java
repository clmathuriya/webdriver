package com.plancess.selenium.tests;

import java.util.Map;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.FacebookLoginDialogPage;
import com.plancess.selenium.pages.GoogleSignInDialogPage;
import com.plancess.selenium.pages.LoginDialogPage;
import com.plancess.selenium.utils.ExcelReader;

public class LoginFromLandingPageTest extends BaseTest {
	private LoginDialogPage loginDialogPage;
	private FacebookLoginDialogPage facebookLoginDialog;
	private GoogleSignInDialogPage googleLoginDialog;

	@Test(alwaysRun = true, groups = { "smoke", "regression" })
	public void loginContentTest() {
		loginDialogPage = landingPage.openLoginDialogPage();
		executor.assertEquals(loginDialogPage.getTitle(), pageTitle, "assert title");
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(loginDialogPage.getEmail()),
				"wait for email field to be displayed");
		executor.verifyTrue(loginDialogPage.getEmail().isDisplayed(), "verify email field displayed");
		executor.verifyTrue(loginDialogPage.getPassword().isDisplayed(), "verify password field displayed");
		executor.verifyTrue(loginDialogPage.getLoginButton().isDisplayed(), "verify login button displayed");
		executor.verifyTrue(loginDialogPage.getResetPasswordLink().isDisplayed(),
				"verify forgot password  link displayed");

	}

	@Test(alwaysRun = true, groups = { "regression" })
	public void loginWithEmptyFieldsTest() {
		loginDialogPage = landingPage.openLoginDialogPage();
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(loginDialogPage.getLoginButton()));
		executor.assertEquals(loginDialogPage.getLoginButton().getAttribute("disabled"), "true",
				"assert submit button disabled for empty field values");
	}

	@Test(alwaysRun = true, dataProvider = "loginWithNonExistingEmail", groups = { "regression" })
	public void loginWithNonExistingEmailsTest(Map<String, String> user) {
		loginDialogPage = landingPage.openLoginDialogPage();

		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(loginDialogPage.getEmail()),
				"wait for email field to be displayed");
		executor.clear(loginDialogPage.getEmail(), "email");
		executor.sendKeys(loginDialogPage.getEmail(), user.get("email"), "email");
		executor.clear(loginDialogPage.getPassword(), "Password");
		executor.sendKeys(loginDialogPage.getPassword(), user.get("password"), "Password");
		executor.click(loginDialogPage.getLoginButton(), "login button");

		executor.softWaitForCondition(ExpectedConditions.textToBePresentInElement(loginDialogPage.getFailureMessage(),
				"Invalid username or password"), "wait for invalid username or password error message");
		executor.verifyEquals(loginDialogPage.getFailureMessage().getText(),
				"Your username and password did not match. Please try again",
				"assert error message for invalid credentials");
	}

	@Test(alwaysRun = true, dataProvider = "loginWithInvalidPassword", groups = { "regression" })
	public void loginWithInvalidPasswordTest(Map<String, String> user) {
		loginDialogPage = landingPage.openLoginDialogPage();

		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(loginDialogPage.getEmail()),
				"wait for email field to be displayed");
		executor.clear(loginDialogPage.getEmail(), "email");
		executor.sendKeys(loginDialogPage.getEmail(), user.get("email"), "email");
		executor.clear(loginDialogPage.getPassword(), "Password");
		executor.sendKeys(loginDialogPage.getPassword(), user.get("password"), "Password");
		executor.click(loginDialogPage.getLoginButton(), "login button");
		executor.softWaitForCondition(ExpectedConditions.textToBePresentInElement(loginDialogPage.getFailureMessage(),
				"Invalid username or password"), "wait for invalid username or password error message");

		executor.verifyEquals(loginDialogPage.getFailureMessage().getText(),
				"Your username and password did not match. Please try again",
				"assert error message for invalid credentials");
	}

	@Test(alwaysRun = true, dataProvider = "loginWithValidEmailPassword", groups = { "smoke", "regression" })
	public void loginWithValidEmailPasswordTest(Map<String, String> user) {
		loginDialogPage = landingPage.openLoginDialogPage();

		Dashboard dashboard = loginDialogPage.doLogin(user);
		executor.softWaitForWebElement(dashboard.getWelcomeMessage());
	}

	@Test(alwaysRun = true, groups = { "regression" })
	public void loginWithFacebookFailureTest() {
		loginDialogPage = landingPage.openLoginDialogPage();

		facebookLoginDialog = loginDialogPage.navigateToFacebookLoginDialog();
		facebookLoginDialog.cancelLogin();
		executor.verifyTrue(loginDialogPage.getFbBtn().isDisplayed(), "Verify facebook login button displayed");
	}

	@Test(alwaysRun = true, dataProvider = "loginWithFacebook", groups = { "smoke", "regression" })
	public void loginWithFacebookSuccessTest(Map<String, String> user) {
		loginDialogPage = landingPage.openLoginDialogPage();

		facebookLoginDialog = loginDialogPage.navigateToFacebookLoginDialog();
		Dashboard dashboard = facebookLoginDialog.doLogin(user);
		executor.softWaitForWebElement(dashboard.getWelcomeMessage());
	}

	@Test(alwaysRun = true, groups = { "regression" })
	public void loginWithGoogleFailureTest() {
		loginDialogPage = landingPage.openLoginDialogPage();

		googleLoginDialog = loginDialogPage.navigateToGoogleLoginDialog();
		googleLoginDialog.cancelLogin();
		executor.verifyTrue(loginDialogPage.getFbBtn().isDisplayed(), "Verify facebook login button displayed");
	}

	@Test(alwaysRun = true, dataProvider = "loginWithGoogle", groups = { "smoke", "regression" })
	public void loginWithGoogleSuccessTest(Map<String, String> user) {
		loginDialogPage = landingPage.openLoginDialogPage();

		googleLoginDialog = loginDialogPage.navigateToGoogleLoginDialog();
		googleLoginDialog.doLogin(user);

	}

	// login data providers section

	@DataProvider(name = "loginWithNonExistingEmail")
	public static Object[][] loginDataProviderWithNonExistingEmail() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "nonExistingEmails");

	}

	@DataProvider(name = "loginWithInvalidPassword")
	public static Object[][] loginDataProviderWithInvalidPassword() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "invalidPasswords");
	}

	@DataProvider(name = "loginWithValidEmailPassword")
	public static Object[][] loginDataProviderWithValidEmailPassword() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "loginWithValidEmailPassword");
	}

	@DataProvider(name = "loginWithFacebook")
	public static Object[][] loginWithFacebookDataProvider() {
		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "loginWithFacebook");
	}

	@DataProvider(name = "loginWithGoogle")
	public static Object[][] loginWithGoogleDataProvider() {
		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "loginWithGoogle");
	}

}
