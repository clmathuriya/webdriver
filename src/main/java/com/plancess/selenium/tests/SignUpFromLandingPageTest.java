package com.plancess.selenium.tests;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.SignUpDialogPage;
import com.plancess.selenium.utils.DataProviderClass;
import com.plancess.selenium.utils.ExcelReader;

public class SignUpFromLandingPageTest extends BaseTest {

	private SignUpDialogPage signUpDialogPage;

	private Dashboard dashboard;
	private String emailError = "Enter a valid email";
	private String passwordError = "Password must be atleast 6 characters";

	@Test(alwaysRun = true, groups = { "smoke", "regression" })
	public void signUpContentTest() {
		signUpDialogPage = landingPage.openSignUpDialogPage();

		Assert.assertEquals(signUpDialogPage.getTitle(), pageTitle, "assert title");
		executor.verifyTrue(signUpDialogPage.getFirstName().isDisplayed(), "verify firstname field displayed");
		executor.verifyTrue(signUpDialogPage.getLastName().isDisplayed(), "verify lastname field displayed");
		executor.verifyTrue(signUpDialogPage.getEmail().isDisplayed(), "verify email field displayed");
		executor.verifyTrue(signUpDialogPage.getPassword().isDisplayed(), "verify password field displayed");
		executor.verifyTrue(signUpDialogPage.getSubmit().isDisplayed(), "verify submit button displayed");

	}

	@Test(alwaysRun = true, groups = { "regression" })
	public void signUpWithEmptyFieldsTest() {
		signUpDialogPage = landingPage.openSignUpDialogPage();
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(signUpDialogPage.getSubmit()));
		executor.assertEquals(signUpDialogPage.getSubmit().getAttribute("disabled"), "true",
				"assert submit button disabled for empty field values");
	}

	@Test(alwaysRun = true, dataProvider = "mandatoryData", groups = { "smoke", "regression" })
	public void signUpWithMandatoryFieldsTest(Map<String, String> user) {
		signUpDialogPage = landingPage.openSignUpDialogPage();
		dashboard = signUpDialogPage.signUpWithMandatoryFiels(user);
		// executor.softWaitForWebElement(ExpectedConditions.visibilityOf(signUpDialogPage.getSuccessMessage()));
		// Assert.assertEquals(signUpDialogPage.getSuccessMessage().getText(),
		// "Account created successfully!",
		// util.takeScreenshot(driver, "assert create user success message"));
		executor.softWaitForWebElement(dashboard.getWelcomeMessage());

		Assert.assertTrue(dashboard.getWelcomeMessage().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and welcome message displayed"));

	}

	@Test(alwaysRun = true, dataProvider = "invalidEmail", dataProviderClass = DataProviderClass.class, groups = {
			"smoke" })
	public void signUpWithInvalidEmailTest(Map<String, String> user) {
		signUpDialogPage = landingPage.openSignUpDialogPage();

		signUpDialogPage.fillSignUpForm(user);

		Assert.assertEquals(signUpDialogPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for invalid email ids"));
		executor.verifyEquals(signUpDialogPage.getEmailErrorMessage().getText(), emailError,
				util.takeScreenshot(driver, "assert error message for invalid email ids"));
	}

	@Test(alwaysRun = true, dataProvider = "invalidEmails", dataProviderClass = DataProviderClass.class, groups = {
			"regression" })
	public void signUpWithInvalidEmailsTest(Map<String, String> user) {
		signUpDialogPage = landingPage.openSignUpDialogPage();

		signUpDialogPage.fillSignUpForm(user);

		Assert.assertEquals(signUpDialogPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for invalid email ids"));
		executor.verifyEquals(signUpDialogPage.getEmailErrorMessage().getText(), emailError,
				util.takeScreenshot(driver, "assert error message for invalid email ids"));
	}

	@Test(alwaysRun = true, dataProvider = "invalidPassword", dataProviderClass = DataProviderClass.class, groups = {
			"regression" })
	public void signUpWithInvalidPasswordTest(Map<String, String> user) {
		signUpDialogPage = landingPage.openSignUpDialogPage();

		signUpDialogPage.fillSignUpForm(user);
		Assert.assertEquals(signUpDialogPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for invalid password values"));
		executor.verifyEquals(signUpDialogPage.getPasswordErrorMessage().getText(), passwordError,
				util.takeScreenshot(driver, "assert password error message for invalid password values"));
	}

	@Test(alwaysRun = true, dataProvider = "mandatoryData", dataProviderClass = DataProviderClass.class, groups = {
			"regression" })
	public void signUpWithExistingEmailTest(Map<String, String> user) {
		signUpDialogPage = landingPage.openSignUpDialogPage();
		user.put("email", "testuser@gmail.com");
		wait.until(ExpectedConditions.visibilityOf(signUpDialogPage.getEmail()));
		signUpDialogPage.getEmail().clear();
		signUpDialogPage.getEmail().sendKeys(user.get("email"));
		signUpDialogPage.getPassword().clear();
		signUpDialogPage.getPassword().sendKeys(user.get("password"));

		new Actions(driver).click(signUpDialogPage.getSubmit()).build().perform();

		executor.softWaitForCondition(ExpectedConditions.textToBePresentInElement(signUpDialogPage.getFailureMessage(),
				"This field must be unique."));

		Assert.assertEquals(signUpDialogPage.getFailureMessage().getText(), "This field must be unique.",
				util.takeScreenshot(driver, "assert failure message for existing email"));
	}

	@Test(alwaysRun = true, dataProvider = "validEmailPassword", dataProviderClass = DataProviderClass.class, groups = {
			"smoke", "regression" })
	public void signUpWithValidEmailPasswordTest(Map<String, String> user) {
		signUpDialogPage = landingPage.openSignUpDialogPage();

		dashboard = signUpDialogPage.signUp(user);
		executor.softWaitForWebElement(dashboard.getWelcomeMessage());

		Assert.assertTrue(dashboard.getWelcomeMessage().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and welcome message displayed"));

	}

	@Test(alwaysRun = true, dataProvider = "validEmailPasswords", groups = { "smoke", "regression" })
	public void signUpWithValidEmailPasswordsTest(Map<String, String> user) {
		signUpDialogPage = landingPage.openSignUpDialogPage();
		long timestamp = System.currentTimeMillis();

		user.put("email", user.get("username") + timestamp + user.get("domain"));

		dashboard = signUpDialogPage.signUp(user);
		executor.softWaitForWebElement(dashboard.getWelcomeMessage());

		Assert.assertTrue(dashboard.getWelcomeMessage().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and welcome message displayed"));

	}

	// Signup data providers section
	@DataProvider(name = "mandatoryData")
	public static Object[][] signUpDataProviderMandatoryData() {
		Map<String, String> user = new HashMap<String, String>();

		Object[][] dataSet = new Object[1][1];
		long timestamp = System.currentTimeMillis();

		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd");
		user.put("email", "webuser" + timestamp + "@plancess.com");
		dataSet[0][0] = user;

		return dataSet;

	}

	@DataProvider(name = "validEmailPasswords")
	public static Object[][] signUpDataProviderWithValidEmailPasswords() {
		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "signUpvalidEmailPasswords");
	}

}
