package com.plancess.selenium.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.HomePage;
import com.plancess.selenium.pages.LoginPage;
import com.plancess.selenium.utils.DataProviderClass;

public class LoginTest extends BaseTest {

	private WebDriver driver;
	private HomePage homePage;
	private LoginPage loginPage;
	private String pageTitle = "Plancess Dashboard";

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

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			homePage = new HomePage(driver);

			loginPage = homePage.openLoginPage();

		} catch (MalformedURLException e) {
			Assert.fail("Unable to start selenium session make sure Grid hub is running on url :" + "http://" + host
					+ ":" + port + "/wd/hub");

		}
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(groups = { "smoke", "regression" })
	public void loginContentTest() {

		Assert.assertEquals(loginPage.getTitle(), pageTitle, util.takeScreenshot(driver, "assert title"));

		verifications.verifyTrue(loginPage.getEmail().isDisplayed(),
				util.takeScreenshot(driver, "verify email field displayed"));
		verifications.verifyTrue(loginPage.getPassword().isDisplayed(),
				util.takeScreenshot(driver, "verify password field displayed"));

		verifications.verifyTrue(loginPage.getLoginButton().isDisplayed(),
				util.takeScreenshot(driver, "verify login button displayed"));
		verifications.verifyTrue(loginPage.getRemember().isDisplayed(),
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

		Assert.assertEquals(loginPage.getLoginButton().getAttribute("disabled"), "true",
				util.takeScreenshot(driver, "assert submit button disabled for empty field values"));
	}

	@Test(dataProvider = "invalidEmails", dataProviderClass = DataProviderClass.class, groups = { "regression" })
	public void loginWithNonExistingEmailsTest(Map<String, String> user) {

		loginPage.doLogin(user);

		verifications.verifyEquals(loginPage.getFailureMessage().getText(), "Invalid credentials given",
				util.takeScreenshot(driver, "assert error message for invalid credentials"));
	}

	@Test(dataProvider = "invalidPasswords", dataProviderClass = DataProviderClass.class, groups = { "regression" })
	public void signUpWithInvalidPasswordsTest(Map<String, String> user) {

		loginPage.doLogin(user);
		verifications.verifyEquals(loginPage.getFailureMessage().getText(), "Invalid credentials given",
				util.takeScreenshot(driver, "assert error message for invalid credentials"));
	}

	@Test(dataProvider = "validEmailPasswords", dataProviderClass = DataProviderClass.class, groups = { "smoke",
			"regression" })
	public void signUpWithValidEmailPasswordsTest(Map<String, String> user) {

		Dashboard dashboard = loginPage.doLogin(user);

		Assert.assertTrue(dashboard.getStartAssessmentSection().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and start assessment section displayed"));
	}

}
