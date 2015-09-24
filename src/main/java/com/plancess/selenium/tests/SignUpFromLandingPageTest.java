package com.plancess.selenium.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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
import com.plancess.selenium.pages.LandingPage;
import com.plancess.selenium.pages.SignUpDialogPage;
import com.plancess.selenium.utils.DataProviderClass;
import com.plancess.selenium.utils.ExcelReader;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;

public class SignUpFromLandingPageTest extends BaseTest {

	private WebDriver driver;
	private LandingPage landingPage;
	private SignUpDialogPage signUpDialogPage;
	private String pageTitle = "Plancess";
	private WebDriverWait wait;
	private Executioner executor;
	private Dashboard dashboard;
	private String emailError = "Enter a valid email";
	private String passwordError = "Password must be atleast 6 characters";

	@Parameters({ "host_ip", "port", "os", "browser", "browserVersion" })
	@BeforeMethod(alwaysRun = true)
	public void setUp(@Optional("localhost") String host, @Optional("4444") String port, @Optional("WINDOWS") String os,
			@Optional("firefox") String browser, @Optional("40.0") String browserVersion) {

		try {

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName(browser);
			// capabilities.setCapability("version", browserVersion);
			capabilities.setCapability("platform", Platform.valueOf(os));

			this.driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), capabilities);

			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 30);
			executor = new Executioner(driver, wait);
			landingPage = new LandingPage(driver, wait);

			signUpDialogPage = landingPage.openSignUpDialogPage();
			util = Util.getInstance();
			verifications = Verifications.getInstance();

		} catch (MalformedURLException e) {
			Assert.fail("Unable to start selenium session make sure Grid hub is running on url :" + "http://" + host
					+ ":" + port + "/wd/hub");

		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

	@Test(alwaysRun = true, groups = { "smoke", "regression" })
	public void signUpContentTest() {

		Assert.assertEquals(signUpDialogPage.getTitle(), pageTitle, util.takeScreenshot(driver, "assert title"));
		verifications.verifyTrue(signUpDialogPage.getFirstName().isDisplayed(),
				util.takeScreenshot(driver, "verify firstname field displayed"));
		verifications.verifyTrue(signUpDialogPage.getLastName().isDisplayed(),
				util.takeScreenshot(driver, "verify lastname field displayed"));
		// verifications.verifyTrue(signUpDialogPage.getMobile().isDisplayed(),
		// util.takeScreenshot(driver, "verify mobile field displayed"));
		verifications.verifyTrue(signUpDialogPage.getEmail().isDisplayed(),
				util.takeScreenshot(driver, "verify email field displayed"));
		verifications.verifyTrue(signUpDialogPage.getPassword().isDisplayed(),
				util.takeScreenshot(driver, "verify password field displayed"));
		// verifications.verifyTrue(signUpDialogPage.getConfirmPassword().isDisplayed(),
		// util.takeScreenshot(driver, "verify confirm password field
		// displayed"));
		// verifications.verifyTrue(signUpDialogPage.getAgreeCheckbox().getAttribute("aria-checked").equals("false"),
		// util.takeScreenshot(driver, "verify agree checkbox displayed"));
		// verifications.verifyTrue(signUpDialogPage.getBack().isDisplayed(),
		// util.takeScreenshot(driver, "verify back button displayed"));
		verifications.verifyTrue(signUpDialogPage.getSubmit().isDisplayed(),
				util.takeScreenshot(driver, "verify submit button displayed"));

	}

	@Test(alwaysRun = true, groups = { "regression" })
	public void signUpWithEmptyFieldsTest() {

		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(signUpDialogPage.getSubmit()));
		Assert.assertEquals(signUpDialogPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for empty field values"));
	}

	@Test(alwaysRun = true, dataProvider = "mandatoryData", groups = { "smoke", "regression" })
	public void signUpWithMandatoryFieldsTest(Map<String, String> user) {

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

		signUpDialogPage.fillSignUpForm(user);

		Assert.assertEquals(signUpDialogPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for invalid email ids"));
		verifications.verifyEquals(signUpDialogPage.getEmailErrorMessage().getText(), emailError,
				util.takeScreenshot(driver, "assert error message for invalid email ids"));
	}

	@Test(alwaysRun = true, dataProvider = "invalidEmails", dataProviderClass = DataProviderClass.class, groups = {
			"regression" })
	public void signUpWithInvalidEmailsTest(Map<String, String> user) {

		signUpDialogPage.fillSignUpForm(user);

		Assert.assertEquals(signUpDialogPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for invalid email ids"));
		verifications.verifyEquals(signUpDialogPage.getEmailErrorMessage().getText(), emailError,
				util.takeScreenshot(driver, "assert error message for invalid email ids"));
	}

	@Test(alwaysRun = true, dataProvider = "invalidPassword", dataProviderClass = DataProviderClass.class, groups = {
			"regression" })
	public void signUpWithInvalidPasswordTest(Map<String, String> user) {

		signUpDialogPage.fillSignUpForm(user);
		Assert.assertEquals(signUpDialogPage.getSubmit().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for invalid password values"));
		verifications.verifyEquals(signUpDialogPage.getPasswordErrorMessage().getText(), passwordError,
				util.takeScreenshot(driver, "assert password error message for invalid password values"));
	}

	@Test(alwaysRun = true, dataProvider = "mandatoryData", dataProviderClass = DataProviderClass.class, groups = {
			"regression" })
	public void signUpWithExistingEmailTest(Map<String, String> user) {
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

		dashboard = signUpDialogPage.signUp(user);
		executor.softWaitForWebElement(dashboard.getWelcomeMessage());

		Assert.assertTrue(dashboard.getWelcomeMessage().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and welcome message displayed"));

	}

	@Test(alwaysRun = true, dataProvider = "validEmailPasswords", groups = { "smoke", "regression" })
	public void signUpWithValidEmailPasswordsTest(Map<String, String> user) {
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
