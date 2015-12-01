package com.plancess.selenium.tests;

import java.util.Map;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.ForgotPasswordDialogPage;
import com.plancess.selenium.pages.LoginDialogPage;
import com.plancess.selenium.utils.ExcelReader;

public class ResetPasswordTest extends BaseTest {
	private LoginDialogPage loginDialogPage;

	private ForgotPasswordDialogPage forgotPasswordDialogPage;

	@Test(alwaysRun = true, groups = { "smoke", "regression" })
	public void resetPassContentTest() {
		loginDialogPage = landingPage.openLoginDialogPage();
		loginDialogPage = landingPage.openLoginDialogPage();
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(loginDialogPage.getEmail()),
				"wait for email field to be displayed");

		executor.click(loginDialogPage.getResetPasswordLink(), "Reset Password link");

		forgotPasswordDialogPage = new ForgotPasswordDialogPage(driver, wait);
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(forgotPasswordDialogPage.getEmail()),
				"wait for email field to be displayed");
		executor.verifyTrue(forgotPasswordDialogPage.getEmail().isDisplayed(), "verify email field displayed");
		executor.verifyTrue(forgotPasswordDialogPage.getSendInstructionsButton().isDisplayed(),
				"verify send instruction button displayed");

	}

	@Test(alwaysRun = true, groups = { "regression" })
	public void resetPasswordWithEmptyFieldsTest() {
		loginDialogPage = landingPage.openLoginDialogPage();
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(loginDialogPage.getEmail()),
				"wait for email field to be displayed");
		executor.click(loginDialogPage.getResetPasswordLink(), "Reset Password link");

		forgotPasswordDialogPage = new ForgotPasswordDialogPage(driver, wait);
		executor.softWaitForWebElement(
				ExpectedConditions.visibilityOf(forgotPasswordDialogPage.getSendInstructionsButton()));
		executor.assertEquals(forgotPasswordDialogPage.getSendInstructionsButton().getAttribute("disabled"), "true",
				"assert send instructions button disabled for empty field values");
	}

	@Test(alwaysRun = true, dataProvider = "resetPasswordValid", groups = { "smoke", "regression" })
	public void resetPasswordWithValidEmailTest(Map<String, String> user) {
		loginDialogPage = landingPage.openLoginDialogPage();
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(loginDialogPage.getEmail()),
				"wait for email field to be displayed");

		executor.click(loginDialogPage.getResetPasswordLink(), "Reset Password link");
		forgotPasswordDialogPage = new ForgotPasswordDialogPage(driver, wait);
		executor.softWaitForWebElement(
				ExpectedConditions.visibilityOf(forgotPasswordDialogPage.getSendInstructionsButton()));
		executor.sendKeys(forgotPasswordDialogPage.getEmail(), user.get("email"), "Email");
		executor.click(forgotPasswordDialogPage.getSendInstructionsButton(), "send instruction button");
		executor.softWaitForCondition(ExpectedConditions.textToBePresentInElement(
				forgotPasswordDialogPage.getSuccessMessage(), "Instructions sent to your email."));
		executor.verifyEquals(forgotPasswordDialogPage.getSuccessMessage().getText().trim(),
				"Instructions sent to your email.", "verify instruction sent ,message displayed");

		// forgotPasswordDialogPage.resetPassword(user);

	}

	// data providers section

	@DataProvider(name = "resetPasswordValid")
	public static Object[][] loginDataProviderWithValidEmailPassword() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "resetPasswordValid");
	}

}
