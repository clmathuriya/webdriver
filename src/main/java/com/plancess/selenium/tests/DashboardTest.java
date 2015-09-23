package com.plancess.selenium.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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
import com.plancess.selenium.pages.LandingPage;
import com.plancess.selenium.pages.LoginDialogPage;
import com.plancess.selenium.pages.SignUpDialogPage;
import com.plancess.selenium.utils.ExcelReader;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;

public class DashboardTest extends BaseTest {

	private WebDriver driver;
	private LandingPage landingPage;
	private LoginDialogPage loginDialogPage;
	private String pageTitle = "Plancess";
	private WebDriverWait wait;
	private Executioner executor;
	private Dashboard dashboard;
	private SignUpDialogPage signUpDialogPage;

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

			// loginDialogPage = landingPage.openLoginDialogPage();

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

	@Test(dataProvider = "dashboardNewUserDataProvider", groups = { "smoke", "regression" })
	public void dashboardNewUserContentTest(Map<String, String> user) {
		signUpDialogPage = landingPage.openSignUpDialogPage();
		dashboard = signUpDialogPage.signUp(user);
		executor.softWaitForWebElement(dashboard.getWelcomeMessage());

		Assert.assertTrue(dashboard.getWelcomeMessage().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and welcome message displayed"));

		// dashboard = loginDialogPage.doLogin(user);

		executor.softWaitForWebElement(dashboard.getWelcomeMessage());
		dashboard.getDashBoardButton().click();

		Assert.assertTrue(dashboard.getWelcomeMessage().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and welcome message displayed"));

		verifications.verifyTrue(dashboard.getNotificationsButton().isDisplayed(),
				util.takeScreenshot(driver, "verify notifications button displayed"));

		verifications.verifyTrue(dashboard.getToggleDropDown().isDisplayed(),
				util.takeScreenshot(driver, "verify toggle dropdown displayed"));

		verifications.verifyTrue(dashboard.getPlancessHeaderLogo().isDisplayed(),
				util.takeScreenshot(driver, "verify toggle dropdown displayed"));
		verifications.verifyTrue(dashboard.getAcceptChallenges().size() >= 2,
				util.takeScreenshot(driver, "verify accept challenges displayed"));
		executor.softWaitForWebElement(dashboard.getPhysicsTakeTest());

		verifications.verifyTrue(dashboard.getPhysicsTakeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify physics take test button displayed"));

		verifications.verifyTrue(dashboard.getChemistryTakeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify chemistry take test button displayed"));
		verifications.verifyTrue(dashboard.getMathsTakeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify maths take test button  displayed"));

		verifications.verifyTrue(dashboard.getCreateCustomTestButton().isDisplayed(),
				util.takeScreenshot(driver, "verify create your own test link  displayed"));

		verifications.verifyTrue(!executor.isElementExist(By.xpath(dashboard.getPerformanceSummarySectionXpath())),
				util.takeScreenshot(driver, "verify performance summary section not displayed"));
		// more verifications to be added

	}

	@Test(dataProvider = "dashboardDataProvider", groups = { "smoke", "regression" })
	public void dashboardWithPerformanceSectionContentTest(Map<String, String> user) {
		loginDialogPage = landingPage.openLoginDialogPage();
		dashboard = loginDialogPage.doLogin(user);

		executor.softWaitForWebElement(dashboard.getWelcomeMessage());
		dashboard.getDashBoardButton().click();

		Assert.assertTrue(dashboard.getWelcomeMessage().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and welcome message displayed"));

		verifications.verifyTrue(dashboard.getNotificationsButton().isDisplayed(),
				util.takeScreenshot(driver, "verify notifications button displayed"));

		verifications.verifyTrue(dashboard.getToggleDropDown().isDisplayed(),
				util.takeScreenshot(driver, "verify toggle dropdown displayed"));

		verifications.verifyTrue(dashboard.getPlancessHeaderLogo().isDisplayed(),
				util.takeScreenshot(driver, "verify toggle dropdown displayed"));
		verifications.verifyTrue(dashboard.getAcceptChallenges().size() >= 2,
				util.takeScreenshot(driver, "verify accept challenges displayed"));
		executor.softWaitForWebElement(dashboard.getPhysicsTakeTest());

		verifications.verifyTrue(dashboard.getPhysicsTakeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify physics take test button displayed"));

		verifications.verifyTrue(dashboard.getChemistryTakeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify chemistry take test button displayed"));
		verifications.verifyTrue(dashboard.getMathsTakeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify maths take test button  displayed"));

		verifications.verifyTrue(dashboard.getCreateCustomTestButton().isDisplayed(),
				util.takeScreenshot(driver, "verify create your own test link  displayed"));

		verifications.verifyTrue(executor.isElementExist(By.xpath(dashboard.getPerformanceSummarySectionXpath())),
				util.takeScreenshot(driver, "verify performance summary section displayed"));

		// more verifications to be added

	}

	// data providers section

	@DataProvider(name = "dashboardDataProvider")
	public static Object[][] dashboardDataProvider() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "dashboard");

	}

	@DataProvider(name = "dashboardNewUserDataProvider")

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

}
