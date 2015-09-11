package com.plancess.selenium.lms.tests;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.lms.pages.LeadDetailsPage;
import com.plancess.selenium.lms.pages.LoginPage;
import com.plancess.selenium.lms.pages.SignUpPage;
import com.plancess.selenium.lms.pages.ThankYouPage;
import com.plancess.selenium.utils.ExcelReader;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;

public class SignUpTest {

	private WebDriver driver;
	private SignUpPage signUpPage;

	private WebDriverWait wait;
	private Executioner executor;

	private Util util;
	private Verifications verifications;

	private LeadDetailsPage leadDetailsPage;
	private LoginPage loginPage;

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
			executor = new Executioner(driver, wait);

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

	@Test(alwaysRun = true, dataProvider = "LmsDataProvider")
	public void signUpTest(Map<String, String> user) {

		executor.navigateToURL(user.get("campaignUrl"));
		signUpPage = new SignUpPage(driver, wait);

		signUpPage.signUp(user);
		String leadId = driver.getCurrentUrl().split("leadid=")[1].split("&")[0];
		verifications.verifyTrue(driver.getCurrentUrl().contains("thankyou"),
				util.takeScreenshot(driver, "Verify thank you page"));

		// to verify lead details

		executor.navigateToURL(user.get("baseUrl") + "auth");
		loginPage = new LoginPage(driver, wait);
		if (!driver.getTitle().contains("Plancess - CRM"))
			loginPage.login(user);
		executor.softWaitForCondition(ExpectedConditions.titleContains("Plancess - CRM"));
		verifications.verifyTrue(driver.getTitle().contains("Plancess - CRM"),
				util.takeScreenshot(driver, "Verify Dashboard"));
		executor.navigateToURL(user.get("baseUrl") + "lead/view/" + leadId);
		leadDetailsPage = new LeadDetailsPage(driver, wait);
		verifications.verifyEquals(leadDetailsPage.getPhone().getText(), user.get("mobile"),
				util.takeScreenshot(driver, "Verify Lead mobile Details"));
		verifications.verifyEquals(leadDetailsPage.getName().getText(), user.get("name"),
				util.takeScreenshot(driver, "Verify Lead name Details"));
		verifications.verifyEquals(leadDetailsPage.getEmail().getText(), user.get("email"),
				util.takeScreenshot(driver, "Verify Lead email Details"));
		verifications.verifyEquals(leadDetailsPage.getCity().getText(), user.get("city"),
				util.takeScreenshot(driver, "Verify Lead city Details"));
		verifications.verifyEquals(leadDetailsPage.getTarget_year().getText(), user.get("target_year"),
				util.takeScreenshot(driver, "Verify Lead Target Year Details"));
		executor.softWaitForWebElement(leadDetailsPage.getCampaignLink());
		verifications.verifyTrue(
				user.get("campaignUrl").contains(leadDetailsPage.getCampaignLink().getAttribute("href")),
				util.takeScreenshot(driver, "Verify Lead compaign Details"));
		verifications.verifyEquals(leadDetailsPage.getLeadType().getText(), user.get("leadType"),
				util.takeScreenshot(driver, "Verify Lead Type Details"));

	}

	@DataProvider(name = "LmsDataProvider")
	public Object[][] LmsSignUpDataProvider() {

		ExcelReader excelReader = new ExcelReader();
		return excelReader.getUserDataFromExcel("testData.xlsx", "lmsUserData");

	}

}
