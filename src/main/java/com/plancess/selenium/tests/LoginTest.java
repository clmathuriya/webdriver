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
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.HomePage;
import com.plancess.selenium.pages.LoginPage;
import com.plancess.selenium.utils.DataProviderClass;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;

public class LoginTest extends BaseTest {

	private WebDriver driver;
	private HomePage homePage;
	private LoginPage loginPage;
	private String pageTitle = "Plancess Dashboard";
	private WebDriverWait wait;
	private Executioner executor;

	@Parameters({ "host_ip", "port", "os", "browser", "browserVersion" })
	@BeforeMethod
	public void setUp(@Optional("localhost") String host, @Optional("4444") String port, @Optional("LINUX") String os,
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
			homePage = new HomePage(driver, wait);

			loginPage = homePage.openLoginPage();
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

		Assert.assertEquals(loginPage.getTitle(), pageTitle, util.takeScreenshot(driver, "assert title"));
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(loginPage.getEmail()));

		verifications.verifyTrue(loginPage.getEmail().isDisplayed(),
				util.takeScreenshot(driver, "verify email field displayed"));
		verifications.verifyTrue(loginPage.getPassword().isDisplayed(),
				util.takeScreenshot(driver, "verify password field displayed"));

		verifications.verifyTrue(loginPage.getLoginButton().isDisplayed(),
				util.takeScreenshot(driver, "verify login button displayed"));
		verifications.verifyTrue(loginPage.getRemember().getAttribute("name").equals("remember"),
				util.takeScreenshot(driver, "verify remember checkbox displayed"));

		verifications.verifyTrue(loginPage.getForgotPasswordLink().isDisplayed(),
				util.takeScreenshot(driver, "verify forgot password  link displayed"));
		verifications.verifyTrue(loginPage.getSignupButton().isDisplayed(),
				util.takeScreenshot(driver, "verify signup button displayed"));

		verifications.verifyTrue(loginPage.getPlancessFooterLogo().isDisplayed(),
				util.takeScreenshot(driver, "verify plancess footer logo displayed"));
		verifications.verifyTrue(loginPage.getPlancessHeaderLogo().isDisplayed(),
				util.takeScreenshot(driver, "verify plancess header logo displayed"));

	}

	@Test(groups = { "regression" })
	public void loginWithEmptyFieldsTest() {
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(loginPage.getLoginButton()));
		Assert.assertEquals(loginPage.getLoginButton().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for empty field values"));
	}

	@Test(dataProvider = "loginWithNonExistingEmail", dataProviderClass = DataProviderClass.class, groups = {
			"regression" })
	public void loginWithNonExistingEmailsTest(Map<String, String> user) {

		loginPage.doLogin(user);

		verifications.verifyEquals(loginPage.getFailureMessage().getText(), "Invalid username or password",
				util.takeScreenshot(driver, "assert error message for invalid credentials"));
	}

	@Test(dataProvider = "loginWithInvalidPassword", dataProviderClass = DataProviderClass.class, groups = {
			"regression" })
	public void loginWithInvalidPasswordTest(Map<String, String> user) {

		loginPage.doLogin(user);
		executor.softWaitForWebElement(loginPage.getFailureMessage());
		verifications.verifyEquals(loginPage.getFailureMessage().getText(), "Invalid username or password",
				util.takeScreenshot(driver, "assert error message for invalid credentials"));
	}

	@Test(dataProvider = "loginWithValidEmailPassword", dataProviderClass = DataProviderClass.class, groups = { "smoke",
			"regression" })
	public void loginWithValidEmailPasswordTest(Map<String, String> user) {

		Dashboard dashboard = loginPage.doLogin(user);
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(dashboard.getStartAssessmentSection()));

		Assert.assertTrue(dashboard.getStartAssessmentSection().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and start assessment section displayed"));
	}

}
