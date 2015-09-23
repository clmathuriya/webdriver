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
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.FacebookLoginDialogPage;
import com.plancess.selenium.pages.GoogleSignInDialogPage;
import com.plancess.selenium.pages.LandingPage;
import com.plancess.selenium.pages.LoginDialogPage;
import com.plancess.selenium.utils.ExcelReader;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;

public class LoginFromLandingPageTest extends BaseTest {

	private WebDriver driver;
	private LandingPage landingPage;
	private LoginDialogPage loginDialogPage;
	private String pageTitle = "Plancess";
	private WebDriverWait wait;
	private Executioner executor;
	private FacebookLoginDialogPage facebookLoginDialog;
	private GoogleSignInDialogPage googleLoginDialog;

	@Parameters({ "host_ip", "port", "os", "browser", "browserVersion" })
	@BeforeMethod
	public void setUp(@Optional("localhost") String host, @Optional("4444") String port, @Optional("WINDOWS") String os,
			@Optional("firefox") String browser, @Optional("40.0") String browserVersion) {

		try {

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName(browser);
			// capabilities.setCapability("version", browserVersion);
			capabilities.setCapability("platform", Platform.valueOf(os));

			this.driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), capabilities);

			// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 30);
			executor = new Executioner(driver, wait);
			landingPage = new LandingPage(driver, wait);

			loginDialogPage = landingPage.openLoginDialogPage();
			util = Util.getInstance();
			verifications = Verifications.getInstance();

		} catch (MalformedURLException e) {
			Assert.fail("Unable to start selenium session make sure Grid hub is running on url :" + "http://" + host
					+ ":" + port + "/wd/hub");

		}
	}

	@AfterMethod
	public void tearDown(ITestResult testResult) {

		if (testResult.getStatus() == ITestResult.FAILURE) {
			verifications.verifyEquals(testResult.getStatus(), ITestResult.SUCCESS,
					util.takeScreenshot(driver, "verify test status for :" + testResult.getTestName()));
		}
		driver.quit();
	}

	@Test(groups = { "smoke", "regression" })
	public void loginContentTest() {

		Assert.assertEquals(loginDialogPage.getTitle(), pageTitle, util.takeScreenshot(driver, "assert title"));
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(loginDialogPage.getEmail()));

		verifications.verifyTrue(loginDialogPage.getEmail().isDisplayed(),
				util.takeScreenshot(driver, "verify email field displayed"));
		verifications.verifyTrue(loginDialogPage.getPassword().isDisplayed(),
				util.takeScreenshot(driver, "verify password field displayed"));

		verifications.verifyTrue(loginDialogPage.getLoginButton().isDisplayed(),
				util.takeScreenshot(driver, "verify login button displayed"));
		// verifications.verifyTrue(loginDialogPage.getRemember().getAttribute("name").equals("remember"),
		// util.takeScreenshot(driver, "verify remember checkbox displayed"));

		verifications.verifyTrue(loginDialogPage.getForgotPasswordLink().isDisplayed(),
				util.takeScreenshot(driver, "verify forgot password  link displayed"));
				// verifications.verifyTrue(loginDialogPage.getSignupButton().isDisplayed(),
				// util.takeScreenshot(driver, "verify signup button
				// displayed"));

		// verifications.verifyTrue(loginDialogPage.getPlancessFooterLogo().isDisplayed(),
		// util.takeScreenshot(driver, "verify plancess footer logo
		// displayed"));
		// verifications.verifyTrue(loginDialogPage.getPlancessHeaderLogo().isDisplayed(),
		// util.takeScreenshot(driver, "verify plancess header logo
		// displayed"));

	}

	@Test(groups = { "regression" })
	public void loginWithEmptyFieldsTest() {
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(loginDialogPage.getLoginButton()));
		Assert.assertEquals(loginDialogPage.getLoginButton().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for empty field values"));
	}

	@Test(dataProvider = "loginWithNonExistingEmail", groups = { "regression" })
	public void loginWithNonExistingEmailsTest(Map<String, String> user) {

		wait.until(ExpectedConditions.visibilityOf(loginDialogPage.getEmail()));
		loginDialogPage.getEmail().clear();
		loginDialogPage.getEmail().sendKeys(user.get("email"));
		loginDialogPage.getPassword().clear();
		loginDialogPage.getPassword().sendKeys(user.get("password"));
		loginDialogPage.getActions().click(loginDialogPage.getLoginButton()).build().perform();
		executor.softWaitForCondition(ExpectedConditions.textToBePresentInElement(loginDialogPage.getFailureMessage(),
				"Invalid username or password"));
		verifications.verifyEquals(loginDialogPage.getFailureMessage().getText(), "Invalid username or password",
				util.takeScreenshot(driver, "assert error message for invalid credentials"));
	}

	@Test(dataProvider = "loginWithInvalidPassword", groups = { "regression" })
	public void loginWithInvalidPasswordTest(Map<String, String> user) {

		wait.until(ExpectedConditions.visibilityOf(loginDialogPage.getEmail()));
		loginDialogPage.getEmail().clear();
		loginDialogPage.getEmail().sendKeys(user.get("email"));
		loginDialogPage.getPassword().clear();
		loginDialogPage.getPassword().sendKeys(user.get("password"));
		loginDialogPage.getActions().click(loginDialogPage.getLoginButton()).build().perform();
		executor.softWaitForCondition(ExpectedConditions.textToBePresentInElement(loginDialogPage.getFailureMessage(),
				"Invalid username or password"));
		verifications.verifyEquals(loginDialogPage.getFailureMessage().getText(), "Invalid username or password",
				util.takeScreenshot(driver, "assert error message for invalid credentials"));
	}

	@Test(dataProvider = "loginWithValidEmailPassword", groups = { "smoke", "regression" })
	public void loginWithValidEmailPasswordTest(Map<String, String> user) {

		Dashboard dashboard = loginDialogPage.doLogin(user);
		executor.softWaitForWebElement(dashboard.getWelcomeMessage());

		Assert.assertTrue(dashboard.getWelcomeMessage().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and welcome message displayed"));
	}

	@Test(groups = { "regression" })
	public void loginWithFacebookFailureTest() {

		facebookLoginDialog = loginDialogPage.navigateToFacebookLoginDialog();
		facebookLoginDialog.cancelLogin();
		verifications.verifyTrue(loginDialogPage.getFbBtn().isDisplayed(), "Verify facebook login button displayed");
	}

	@Test(dataProvider = "loginWithFacebook", groups = { "smoke", "regression" })
	public void loginWithFacebookSuccessTest(Map<String, String> user) {

		facebookLoginDialog = loginDialogPage.navigateToFacebookLoginDialog();
		Dashboard dashboard = facebookLoginDialog.doLogin(user);
		executor.softWaitForWebElement(dashboard.getWelcomeMessage());

		Assert.assertTrue(dashboard.getWelcomeMessage().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and welcome message displayed"));
	}

	@Test(groups = { "regression" })
	public void loginWithGoogleFailureTest() {

		googleLoginDialog = loginDialogPage.navigateToGoogleLoginDialog();
		facebookLoginDialog.cancelLogin();
		verifications.verifyTrue(loginDialogPage.getFbBtn().isDisplayed(), "Verify facebook login button displayed");
	}

	@Test(dataProvider = "loginWithGoogle", groups = { "smoke", "regression" })
	public void loginWithGoogleSuccessTest(Map<String, String> user) {

		googleLoginDialog = loginDialogPage.navigateToGoogleLoginDialog();
		Dashboard dashboard = googleLoginDialog.doLogin(user);
		executor.softWaitForWebElement(dashboard.getWelcomeMessage());

		Assert.assertTrue(dashboard.getWelcomeMessage().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and welcome message displayed"));
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
