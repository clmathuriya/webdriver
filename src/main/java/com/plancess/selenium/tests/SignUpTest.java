package com.plancess.selenium.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.HomePage;
import com.plancess.selenium.pages.SignUpPage;
import com.plancess.selenium.utils.DataProviderClass;

public class SignUpTest extends BaseTest {

	private WebDriver driver;
	private HomePage homePage;
	private SignUpPage signUpPage;
	private String pageTitle = "Plancess Dashboard";
	private WebDriverWait wait;

	@Parameters({ "host_ip", "port", "os", "browser", "browserVersion" })
	@BeforeMethod(alwaysRun = true)
	public void setUp(@Optional("localhost") String host, @Optional("4444") String port, @Optional("LINUX") String os,
			@Optional("firefox") String browser, @Optional("40.0") String browserVersion) {

		try {

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName(browser);
			// capabilities.setCapability("version", browserVersion);
			capabilities.setCapability("platform", Platform.valueOf(os));

			this.driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), capabilities);

			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 30);
			homePage = new HomePage(driver, wait);

			signUpPage = homePage.openSignUpPage();

		} catch (MalformedURLException e) {
			Assert.fail("Unable to start selenium session make sure Grid hub is running on url :" + "http://" + host
					+ ":" + port + "/wd/hub");

		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

	@Test(groups = { "smoke", "regression" })
	public void signUpContentTest() {

		Assert.assertEquals(signUpPage.getTitle(), pageTitle, util.takeScreenshot(driver, "assert title"));
		verifications.verifyTrue(signUpPage.getFirstName().isDisplayed(),
				util.takeScreenshot(driver, "verify firstname field displayed"));
		verifications.verifyTrue(signUpPage.getLastName().isDisplayed(),
				util.takeScreenshot(driver, "verify lastname field displayed"));
		verifications.verifyTrue(signUpPage.getMobile().isDisplayed(),
				util.takeScreenshot(driver, "verify mobile field displayed"));
		verifications.verifyTrue(signUpPage.getEmail().isDisplayed(),
				util.takeScreenshot(driver, "verify email field displayed"));
		verifications.verifyTrue(signUpPage.getPassword().isDisplayed(),
				util.takeScreenshot(driver, "verify password field displayed"));
		verifications.verifyTrue(signUpPage.getConfirmPassword().isDisplayed(),
				util.takeScreenshot(driver, "verify confirm password field displayed"));
		verifications.verifyTrue(signUpPage.getAgreeCheckbox().getAttribute("aria-checked").equals("false"),
				util.takeScreenshot(driver, "verify agree checkbox displayed"));
		verifications.verifyTrue(signUpPage.getBack().isDisplayed(),
				util.takeScreenshot(driver, "verify back button displayed"));
		verifications.verifyTrue(signUpPage.getSubmit().isDisplayed(),
				util.takeScreenshot(driver, "verify submit button displayed"));

	}

	@Test(groups = { "regression" })
	public void signUpWithEmptyFieldsTest() {

		wait.until(ExpectedConditions.visibilityOf(signUpPage.getSubmit()));
		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for empty field values"));
	}

	@Test(dataProvider = "mandatoryData", dataProviderClass = DataProviderClass.class, groups = { "smoke",
			"regression" })
	public void signUpWithMandatoryFieldsTest(Map<String, String> user) {

		signUpPage.signUpWithMandatoryFiels(user);
		wait.until(ExpectedConditions.visibilityOf(signUpPage.getSuccessMessage()));
		Assert.assertEquals(signUpPage.getSuccessMessage().getText(), "Account created successfully!",
				util.takeScreenshot(driver, "assert create user success message"));
	}

	@Test(dataProvider = "invalidEmail", dataProviderClass = DataProviderClass.class, groups = { "smoke" })
	public void signUpWithInvalidEmailTest(Map<String, String> user) {

		signUpPage.fillSignUpForm(user);

		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for invalid email ids"));
		verifications.verifyEquals(signUpPage.getEmailErrorMessage().getText(), "Please enter correct email address",
				util.takeScreenshot(driver, "assert error message for invalid email ids"));
	}

	@Test(dataProvider = "invalidEmails", dataProviderClass = DataProviderClass.class, groups = { "regression" })
	public void signUpWithInvalidEmailsTest(Map<String, String> user) {

		signUpPage.fillSignUpForm(user);

		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for invalid email ids"));
		verifications.verifyEquals(signUpPage.getEmailErrorMessage().getText(), "Please enter correct email address",
				util.takeScreenshot(driver, "assert error message for invalid email ids"));
	}

	@Test(dataProvider = "invalidPassword", dataProviderClass = DataProviderClass.class, groups = { "regression" })
	public void signUpWithInvalidPasswordTest(Map<String, String> user) {

		signUpPage.fillSignUpForm(user);
		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for invalid password values"));
		verifications.verifyEquals(signUpPage.getPasswordErrorMessage().getText(),
				"Must contain at least 1 alphabet, 1 digit and 1 special character out of $@!%*?&^",
				util.takeScreenshot(driver, "assert password error message for invalid password values"));
	}

	@Test(dataProvider = "invalidPasswords", dataProviderClass = DataProviderClass.class, groups = { "regression" })
	public void signUpWithInvalidPasswordsTest(Map<String, String> user) {

		signUpPage.fillSignUpForm(user);
		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for invalid password values"));
		verifications.verifyEquals(signUpPage.getPasswordErrorMessage().getText(),
				"Must contain at least 1 alphabet, 1 digit and 1 special character out of $@!%*?&^",
				util.takeScreenshot(driver, "assert password error message for invalid password values"));
	}

	@Test(dataProvider = "misMatchPasswords", dataProviderClass = DataProviderClass.class, groups = { "regression" })
	public void signUpWithPasswordMismatchTest(Map<String, String> user) {

		signUpPage.fillSignUpForm(user);
		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for password mismatch"));
		verifications.verifyTrue(
				signUpPage.getConfirmPasswordErrorMessage().getText().contains("Passwords don't match"),
				util.takeScreenshot(driver, "assert password mismatch error message for password mismatch"));
	}

	@Test(dataProvider = "invalidMobile", dataProviderClass = DataProviderClass.class, groups = { "regression" })
	public void signUpWithInvalidMobileTest(Map<String, String> user) {

		signUpPage.fillSignUpForm(user);
		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for invalid mobile number"));
		verifications.verifyEquals(signUpPage.getMobileErrorMessage().getText(),
				"Valid phone number starting with 7,8 or 9 is required",
				util.takeScreenshot(driver, "assert error message for invalid mobile number"));
	}

	@Test(dataProvider = "invalidMobiles", dataProviderClass = DataProviderClass.class, groups = { "regression" })
	public void signUpWithInvalidMobilesTest(Map<String, String> user) {

		signUpPage.fillSignUpForm(user);
		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for invalid mobile number"));
		verifications.verifyEquals(signUpPage.getMobileErrorMessage().getText(),
				"Valid phone number starting with 7,8 or 9 is required",
				util.takeScreenshot(driver, "assert error message for invalid mobile number"));
	}

	@Test(dataProvider = "invalidNames", dataProviderClass = DataProviderClass.class, groups = { "regression" })
	public void signUpWithInvalidNameTest(Map<String, String> user) {
		signUpPage.fillSignUpForm(user);
		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for invalid names"));
		verifications.verifyEquals(signUpPage.getFirstNameErrorMessage().getText(), "Please enter minimum 3 characters",
				util.takeScreenshot(driver, "assert error message for invalid first name"));
		verifications.verifyEquals(signUpPage.getLastNameErrorMessage().getText(), "Please enter minimum 3 characters",
				util.takeScreenshot(driver, "assert error message for invalid last name"));
	}

	@Test(dataProvider = "mandatoryData", dataProviderClass = DataProviderClass.class, groups = { "regression" })
	public void signUpWithExistingEmailTest(Map<String, String> user) {
		user.put("email", "testuser@gmail.com");
		signUpPage.signUpWithMandatoryFiels(user);

		wait.until(ExpectedConditions.textToBePresentInElement(signUpPage.getFailureMessage(), user.get("email")));
		Assert.assertEquals(signUpPage.getFailureMessage().getText(),
				user.get("email") + " already exists in our database",
				util.takeScreenshot(driver, "assert failure message for existing email"));
	}

	@Test(dataProvider = "validEmailPassword", dataProviderClass = DataProviderClass.class, groups = { "smoke",
			"regression" })
	public void signUpWithValidEmailPasswordTest(Map<String, String> user) {

		signUpPage.signUp(user);
		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for empty field values"));
		Assert.assertEquals(signUpPage.getSuccessMessage().getText(), "Account created successfully!",
				util.takeScreenshot(driver, "assert create user success message"));
	}

	@Test(dataProvider = "validEmailPasswords", dataProviderClass = DataProviderClass.class, groups = { "smoke",
			"regression" })
	public void signUpWithValidEmailPasswordsTest(Map<String, String> user) {

		signUpPage.signUp(user);
		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for empty field values"));
		Assert.assertEquals(signUpPage.getSuccessMessage().getText(), "Account created successfully!",
				util.takeScreenshot(driver, "assert create user success message"));
	}

}
