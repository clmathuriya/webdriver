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

import com.plancess.selenium.pages.HomePage;
import com.plancess.selenium.pages.SignUpPage;
import com.plancess.selenium.utils.DataProviderClass;

public class SignUpTest extends BaseTest {

	private WebDriver driver;
	private HomePage homePage;
	private SignUpPage signUpPage;
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

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			homePage = new HomePage(driver);

			signUpPage = homePage.openSignUpPage();

		} catch (MalformedURLException e) {
			Assert.fail("Unable to start selenium session make sure Grid hub is running on url :" + "http://" + host
					+ ":" + port + "/wd/hub");

		}
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test
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

	@Test
	public void signUpWithEmptyFieldsTest() {

		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "disabled",
				util.takeScreenshot(driver, "assert submit button disabled for empty field values"));
	}

	@Test(dataProvider = "mandatoryData", dataProviderClass = DataProviderClass.class)
	public void signUpWithMandatoryFieldsTest(Map<String, String> user) {

		signUpPage.signUpWithMandatoryFiels(user);

		Assert.assertEquals(signUpPage.getSuccessMessage().getText(), "Account created successfully!",
				util.takeScreenshot(driver, "assert create user success message"));
	}

	@Test(dataProvider = "invalidEmails", dataProviderClass = DataProviderClass.class)
	public void signUpWithInvalidEmailsTest(Map<String, String> user) {

		signUpPage.signUp(user);

		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "disabled",
				util.takeScreenshot(driver, "assert submit button disabled for empty field values"));
	}

	@Test(dataProvider = "invalidPasswords", dataProviderClass = DataProviderClass.class)
	public void signUpWithInvalidPasswordsTest(Map<String, String> user) {

		signUpPage.signUp(user);
		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "disabled",
				util.takeScreenshot(driver, "assert submit button disabled for empty field values"));
	}

	@Test(dataProvider = "invalidMobile", dataProviderClass = DataProviderClass.class)
	public void signUpWithInvalidMobileTest(Map<String, String> user) {

		signUpPage.signUp(user);
		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "disabled",
				util.takeScreenshot(driver, "assert submit button disabled for empty field values"));
	}

	@Test(dataProvider = "invalidNames", dataProviderClass = DataProviderClass.class)
	public void signUpWithInvalidNameTest(Map<String, String> user) {

		signUpPage.signUp(user);
		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "disabled",
				util.takeScreenshot(driver, "assert submit button disabled for empty field values"));
	}

	@Test(dataProvider = "mandatoryData", dataProviderClass = DataProviderClass.class)
	public void signUpWithExistingEmailTest(Map<String, String> user) {

		signUpPage.signUp(user);
		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "disabled",
				util.takeScreenshot(driver, "assert submit button disabled for empty field values"));
	}

	@Test(dataProvider = "validEmailPasswords", dataProviderClass = DataProviderClass.class)
	public void signUpWithValidEmailPasswordsTest(Map<String, String> user) {

		signUpPage.signUp(user);
		Assert.assertEquals(signUpPage.getSubmit().getAttribute("disabled"), "disabled",
				util.takeScreenshot(driver, "assert submit button disabled for empty field values"));
	}

}
